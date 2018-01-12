package com.sire.sri.batch.autorizacion;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.Mensaje;
import autorizacion.ws.sri.gob.ec.RespuestaComprobante;
import com.sire.event.MailEvent;
import com.sire.service.IDatasourceService;
import com.sire.service.IMailService;
import com.sire.sri.batch.autorizacion.util.JaxbCharacterEscapeHandler;
import com.sun.xml.bind.marshaller.DataWriter;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import ec.gob.sri.comprobantes.modelo.LoteXml;
import ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.gob.sri.comprobantes.modelo.reportes.ComprobanteRetencionReporte;
import ec.gob.sri.comprobantes.modelo.reportes.FacturaReporte;
import ec.gob.sri.comprobantes.util.reportes.ReporteUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Named
public class F1_C1_Writer1 extends AbstractItemWriter {

    @Inject
    private JobContext jobCtx;
    private Connection connection;
    private IMailService mailService;
    private String urlReporte;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        Properties runtimeParams = BatchRuntime.getJobOperator().getParameters(jobCtx.getExecutionId());
        urlReporte = runtimeParams.getProperty("urlReporte");
    }

    @Override
    public void writeItems(List<Object> items) {
        Map duplaFinal = new HashMap<>();

        items.forEach((item) -> {
            try {
                RespuestaComprobante respuestaComprobante = (RespuestaComprobante) ((Map) item).get("respuestaComprobante");
                LoteXml lote = (LoteXml) ((Map) item).get("lote");
                for (Object comprobante : lote.getComprobantes()) {
                    String secuencial = null;
                    String claveAcceso = null;
                    String nombreTablaComprobante = null;
                    String nombreSecuencial = null;
                    if (comprobante instanceof Factura) {
                        Factura factura = (Factura) comprobante;
                        secuencial = factura.getInfoTributaria().getEstab() + "-" + factura.getInfoTributaria().getPtoEmi() + "-" + factura.getInfoTributaria().getSecuencial();
                        claveAcceso = factura.getInfoTributaria().getClaveAcceso();
                        nombreTablaComprobante = "FAC_FACTURA_C";
                        nombreSecuencial = "SECUENCIAL";
                    } else if (comprobante instanceof ComprobanteRetencion) {
                        ComprobanteRetencion comprobanteRetencion = (ComprobanteRetencion) comprobante;
                        secuencial = comprobanteRetencion.getInfoTributaria().getEstab() + "-" + comprobanteRetencion.getInfoTributaria().getPtoEmi() + "-" + comprobanteRetencion.getInfoTributaria().getSecuencial();
                        claveAcceso = comprobanteRetencion.getInfoTributaria().getClaveAcceso();
                        nombreTablaComprobante = "BAN_RETENCION_C";
                        nombreSecuencial = "NUM_SECUENCIAL";
                    }
                    for (Autorizacion autorizacion : respuestaComprobante.getAutorizaciones().getAutorizacion()) {
                        if (claveAcceso.equals(autorizacion.getNumeroAutorizacion())) {
                            System.out.println("----------------------------------------------------------------");
                            String estado = autorizacion.getEstado();
                            String fechaAutorizacion = autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime().toString();
                            String year = fechaAutorizacion.substring(24);
                            String date = fechaAutorizacion.substring(0, 19);
                            fechaAutorizacion = date + " " + year;

                            DateFormat oldFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH);
                            Date oldDate = oldFormat.parse(fechaAutorizacion);

                            DateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            fechaAutorizacion = newFormat.format(oldDate);
                            String claveAccesoConsultada = respuestaComprobante.getClaveAccesoConsultada();
                            String numeroComprobantes = respuestaComprobante.getNumeroComprobantes();

                            System.out.println("Secuencial: " + secuencial
                                    + ", Estado: " + estado
                                    + ", FechaAutorizacion: " + fechaAutorizacion
                                    + ", ClaveAccesoConsultada: " + claveAccesoConsultada
                                    + ", NumeroComprobantes: " + numeroComprobantes
                            );

                            String identificador = null;
                            String tipo = null;
                            String mensaje = null;
                            for (Mensaje m : autorizacion.getMensajes().getMensaje()) {
                                identificador = m.getIdentificador();
                                String informacionAdicional = m.getInformacionAdicional();
                                mensaje = m.getMensaje();
                                tipo = m.getTipo();

                                System.out.println("Identificador: " + identificador
                                        + ", InformacionAdicional: " + informacionAdicional
                                        + ", Mensaje: " + mensaje
                                        + ", Tipo: " + tipo
                                );
                            }
                            System.out.println("-----------------------------------------------------");
                            String motivo = "";
                            if (!estado.equals("AUTORIZADO") && !estado.equals("EN PROCESAMIENTO")) {
                                motivo = ", MOTIVO_SRI = '" + identificador + ":" + tipo + ":" + mensaje + "'";
                                fechaAutorizacion = "";
                            } else if (estado.equals("AUTORIZADO")) {
                                fechaAutorizacion = ", FECHA_AUTORIZACION = '" + fechaAutorizacion + "'";
                                motivo = ", MOTIVO_SRI = ''";
                                duplaFinal.put(comprobante, autorizacion);
                            } else {
                                fechaAutorizacion = "";
                            }

                            String cabeceraSQL = "UPDATE " + nombreTablaComprobante + " SET "
                                    + "ESTADO_SRI = '" + estado + "'"
                                    + motivo
                                    + fechaAutorizacion
                                    + " WHERE " + nombreSecuencial + " = '" + secuencial + "'";
                            System.out.println("update " + nombreTablaComprobante + " -> " + cabeceraSQL);
                            try (PreparedStatement preparedStatement = getConnection().prepareStatement(cabeceraSQL)) {
                                preparedStatement.executeQuery();
                                preparedStatement.close();
                            }
                        }
                    }
                }
                String loteSQL = "UPDATE CEL_LOTE_AUTORIZADO SET "
                        + "ESTADO_SRI = 'PROCESADA'"
                        + " WHERE CLAVE_ACCESO = '" + lote.getClaveAcceso() + "'";
                System.out.println("update CEL_LOTE_AUTORIZADO -> " + loteSQL);
                try (PreparedStatement preparedStatement = getConnection().prepareStatement(loteSQL)) {
                    preparedStatement.executeQuery();
                    preparedStatement.close();
                }
            } catch (SQLException | NamingException | ParseException ex) {
                Logger.getLogger(F1_C1_Writer1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        duplaFinal.keySet().forEach((key) -> {
            try {
                String claveAcceso = null;
                String secuencial = null;
                String recipient = null;
                ReporteUtil reporteUtil = new ReporteUtil();
                byte[] pdfBytes = null;
                String razonSocialComprador = null;
                String nombreComercial = null;
                String ruc = null;
                Autorizacion autorizacion = ((Autorizacion) duplaFinal.get(key));
                String numAut = autorizacion.getNumeroAutorizacion();
                String fechaAut = autorizacion.getFechaAutorizacion().toString();
                if (key instanceof Factura) {
                    Factura factura = (Factura) key;
                    for (Factura.InfoAdicional.CampoAdicional campoAdicional : factura.getInfoAdicional().getCampoAdicional()) {
                        if (campoAdicional.getNombre().equals("Email")) {
                            recipient = campoAdicional.getValue();
                        }
                    }
                    claveAcceso = factura.getInfoTributaria().getClaveAcceso();
                    secuencial = factura.getInfoTributaria().getSecuencial();
                    FacturaReporte fact = new FacturaReporte(factura);
                    pdfBytes = reporteUtil.generarReporte(urlReporte, fact, numAut, fechaAut);
                    razonSocialComprador = factura.getInfoFactura().getRazonSocialComprador();
                    nombreComercial = factura.getInfoTributaria().getNombreComercial();
                    ruc = factura.getInfoTributaria().getRuc();
                } else if (key instanceof ComprobanteRetencion) {
                    ComprobanteRetencion comprobanteRetencion = (ComprobanteRetencion) key;
                    for (ComprobanteRetencion.InfoAdicional.CampoAdicional campoAdicional : comprobanteRetencion.getInfoAdicional().getCampoAdicional()) {
                        if (campoAdicional.getNombre().equals("Email")) {
                            recipient = campoAdicional.getValue();
                        }
                    }
                    claveAcceso = comprobanteRetencion.getInfoTributaria().getClaveAcceso();
                    secuencial = comprobanteRetencion.getInfoTributaria().getSecuencial();
                    ComprobanteRetencionReporte comprobanteRetencionReporte = new ComprobanteRetencionReporte(comprobanteRetencion);
                    pdfBytes = reporteUtil.generarReporte(urlReporte, comprobanteRetencionReporte, numAut, fechaAut);
                    razonSocialComprador = comprobanteRetencion.getInfoCompRetencion().getRazonSocialSujetoRetenido();
                    nombreComercial = comprobanteRetencion.getInfoTributaria().getNombreComercial();
                    ruc = comprobanteRetencion.getInfoTributaria().getRuc();
                }

                //construct the mime multi part
                MimeMultipart mimeMultipart = new MimeMultipart();
                addBodyPart(pdfBytes, "application/pdf", claveAcceso + ".pdf", mimeMultipart);

                String autorizacionXml = object2xmlUnicode(autorizacion);
                addBodyPart(autorizacionXml.getBytes(), "application/xml", claveAcceso + ".xml", mimeMultipart);

                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent("Estimado Cliente " + razonSocialComprador + ",<br>"
                        + "<br>"
                        + "Nos complace adjuntar su e-Comprobante con el siguiente detalle:<br>"
                        + "<br>"
                        + "e - Comprobante No: <b>" + numAut.substring(24, 39) + "</b><br>"
                        + "<br>"
                        + "Fecha Emisión: <b>" + fechaAut + "</b><br>"
                        + "<br>"
                        + "El documento pdf y xml de su comprobante se encuentra adjunto a este correo.<br>"
                        + "<br>"
                        + "Atentamente:<br>"
                        + nombreComercial + "<br>"
                        + "RUC: " + ruc, "text/html; charset=utf-8");
                mimeMultipart.addBodyPart(messageBodyPart);

                MailEvent event = new MailEvent();
                event.setTo(recipient);
                event.setSubject("Comprobante EMITIDO");
                event.setMimeMultipart(mimeMultipart);

                if (recipient != null && !recipient.isEmpty()) {
                    System.out.println("Secuencial de Comprobante a ser enviado por mail: " + secuencial);
                    getMailService().sendMail(event); //firing event!
                }
            } catch (NamingException | MessagingException | JAXBException | SQLException | ClassNotFoundException | IOException ex) {
                Logger.getLogger(F1_C1_Writer1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private String object2xmlUnicode(Object item) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(item.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());

        jaxbMarshaller.marshal(item, dataWriter);

        return stringWriter.toString();
    }

    private void addBodyPart(byte[] bytes, String type, String fileName, MimeMultipart mimeMultipart) throws MessagingException {
        DataSource dataSource = new ByteArrayDataSource(bytes, type);
        MimeBodyPart xmlBodyPart = new MimeBodyPart();
        xmlBodyPart.setDataHandler(new DataHandler(dataSource));
        xmlBodyPart.setFileName(fileName);
        mimeMultipart.addBodyPart(xmlBodyPart);
    }

    private Connection getConnection() throws SQLException, NamingException {
        if (connection == null || (connection != null && connection.isClosed())) {
            InitialContext ic = new InitialContext();
            IDatasourceService datasourceService = (IDatasourceService) ic.lookup("java:global/SIRE-Batch/DatasourceService!com.sire.service.IDatasourceService");
            connection = datasourceService.getConnection();
        }
        return connection;
    }

    private IMailService getMailService() throws NamingException {
        if (mailService == null) {
            InitialContext ic = new InitialContext();
            mailService = (IMailService) ic.lookup("java:global/SIRE-Batch/MailService!com.sire.service.IMailService");
        }
        return mailService;
    }
}
