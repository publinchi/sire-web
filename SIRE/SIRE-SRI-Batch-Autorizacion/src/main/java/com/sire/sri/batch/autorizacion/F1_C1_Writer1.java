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
import com.sire.sri.batch.commons.CommonsItemWriter;
import com.sire.sri.batch.constant.Constant;
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
import javax.inject.Named;
import ec.gob.sri.comprobantes.modelo.LoteXml;
import ec.gob.sri.comprobantes.modelo.guia.GuiaRemision;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito;
import ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import ec.gob.sri.comprobantes.modelo.reportes.ComprobanteRetencionReporte;
import ec.gob.sri.comprobantes.modelo.reportes.FacturaReporte;
import ec.gob.sri.comprobantes.modelo.reportes.GuiaRemisionReporte;
import ec.gob.sri.comprobantes.modelo.reportes.NotaCreditoReporte;
import ec.gob.sri.comprobantes.modelo.reportes.NotaDebitoReporte;
import ec.gob.sri.comprobantes.util.reportes.ReporteUtil;
import java.io.IOException;
import java.io.Serializable;
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
import javax.xml.bind.JAXBException;

@Named
public class F1_C1_Writer1 extends CommonsItemWriter {

    @Inject
    private JobContext jobCtx;
    private Connection connection;
    private IMailService mailService;
    private String urlReporte;
    private Logger log = Logger.getLogger(F1_C1_Writer1.class.getName());

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
                    } else if (comprobante instanceof NotaCredito) {
                        NotaCredito notaCredito = (NotaCredito) comprobante;
                        secuencial = notaCredito.getInfoTributaria().getEstab() + "-" + notaCredito.getInfoTributaria().getPtoEmi() + "-" + notaCredito.getInfoTributaria().getSecuencial();
                        claveAcceso = notaCredito.getInfoTributaria().getClaveAcceso();
                        nombreTablaComprobante = "FAC_DEVOLUCION_C";
                        nombreSecuencial = "NUM_SECUENCIAL";
                    } else if (comprobante instanceof NotaDebito) {
                        NotaDebito notaDebito = (NotaDebito) comprobante;
                        secuencial = notaDebito.getInfoTributaria().getEstab() + "-" + notaDebito.getInfoTributaria().getPtoEmi() + "-" + notaDebito.getInfoTributaria().getSecuencial();
                        claveAcceso = notaDebito.getInfoTributaria().getClaveAcceso();
                        nombreTablaComprobante = "CXC_DOC_COBRAR";
                        nombreSecuencial = "NUM_SECUENCIAL";
                    } else if (comprobante instanceof GuiaRemision) {
                        GuiaRemision guiaRemision = (GuiaRemision) comprobante;
                        secuencial = guiaRemision.getInfoTributaria().getEstab() + "-" + guiaRemision.getInfoTributaria().getPtoEmi() + "-" + guiaRemision.getInfoTributaria().getSecuencial();
                        claveAcceso = guiaRemision.getInfoTributaria().getClaveAcceso();
                        nombreTablaComprobante = "PED_DESPACHO_C";
                        nombreSecuencial = "NUM_SECUENCIAL";
                    } else if (comprobante instanceof ComprobanteRetencion) {
                        ComprobanteRetencion comprobanteRetencion = (ComprobanteRetencion) comprobante;
                        secuencial = comprobanteRetencion.getInfoTributaria().getEstab() + "-" + comprobanteRetencion.getInfoTributaria().getPtoEmi() + "-" + comprobanteRetencion.getInfoTributaria().getSecuencial();
                        claveAcceso = comprobanteRetencion.getInfoTributaria().getClaveAcceso();
                        nombreTablaComprobante = "BAN_RETENCION_C";
                        nombreSecuencial = "NUM_SECUENCIAL";
                    }
                    for (Autorizacion autorizacion : respuestaComprobante.getAutorizaciones().getAutorizacion()) {
                        if (claveAcceso.equals(autorizacion.getNumeroAutorizacion())) {
                            log.info("----------------------------------------------------------------");
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

                            log.info("Secuencial: " + secuencial
                                    + ", Estado: " + estado
                                    + ", FechaAutorizacion: " + fechaAutorizacion
                                    + ", ClaveAccesoConsultada: " + claveAccesoConsultada
                                    + ", NumeroComprobantes: " + numeroComprobantes
                            );

                            String identificador = null;
                            String tipo = null;
                            String mensaje = null;

                            Autorizacion.Mensajes mensajes = autorizacion.getMensajes();
                            if(mensajes != null)
                                for (Mensaje m : mensajes.getMensaje()) {
                                    identificador = m.getIdentificador();
                                    String informacionAdicional = m.getInformacionAdicional();
                                    mensaje = m.getMensaje();
                                    tipo = m.getTipo();

                                    log.info("Identificador: " + identificador
                                            + ", InformacionAdicional: " + informacionAdicional
                                            + ", Mensaje: " + mensaje
                                            + ", Tipo: " + tipo
                                    );
                                }

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
                                    + "ESTADO_SRI = ?"
                                    + motivo
                                    + fechaAutorizacion
                                    + " WHERE " + nombreSecuencial + " = ?";
                            log.info("update " + nombreTablaComprobante + " -> " + cabeceraSQL);
                            executeSql(cabeceraSQL, estado, secuencial);
                        }
                    }
                }
                String loteSQL = "UPDATE CEL_LOTE_AUTORIZADO SET "
                        + "ESTADO_SRI = 'PROCESADA'"
                        + " WHERE CLAVE_ACCESO = ?";
                log.info("update CEL_LOTE_AUTORIZADO -> " + loteSQL);
                try (PreparedStatement preparedStatement = getConnection().prepareStatement(loteSQL)) {
                    preparedStatement.setString(1, lote.getClaveAcceso());
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            } catch (SQLException | NamingException | ParseException ex) {
                Logger.getLogger(F1_C1_Writer1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        duplaFinal.keySet().forEach((key) -> {
            try {
                String nombreComprobante = null;
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
                    nombreComprobante = "Factura";
                    Factura factura = (Factura) key;
                    for (Factura.InfoAdicional.CampoAdicional campoAdicional : factura.getInfoAdicional().getCampoAdicional()) {
                        if (campoAdicional.getNombre().equals(Constant.EMAIL)) {
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
                } else if (key instanceof NotaCredito) {
                    nombreComprobante = "Nota de Crédito";
                    NotaCredito notaCredito = (NotaCredito) key;
                    for (NotaCredito.InfoAdicional.CampoAdicional campoAdicional : notaCredito.getInfoAdicional().getCampoAdicional()) {
                        if (campoAdicional.getNombre().equals("Email")) {
                            recipient = campoAdicional.getValue();
                        }
                    }
                    claveAcceso = notaCredito.getInfoTributaria().getClaveAcceso();
                    secuencial = notaCredito.getInfoTributaria().getSecuencial();
                    NotaCreditoReporte notaCreditoReporte = new NotaCreditoReporte(notaCredito);
                    pdfBytes = reporteUtil.generarReporte(urlReporte, notaCreditoReporte, numAut, fechaAut);
                    razonSocialComprador = notaCredito.getInfoNotaCredito().getRazonSocialComprador();
                    nombreComercial = notaCredito.getInfoTributaria().getNombreComercial();
                    ruc = notaCredito.getInfoTributaria().getRuc();
                } else if (key instanceof NotaDebito) {
                    nombreComprobante = "Nota de Débito";
                    NotaDebito notaDebito = (NotaDebito) key;
                    for (NotaDebito.InfoAdicional.CampoAdicional campoAdicional : notaDebito.getInfoAdicional().getCampoAdicional()) {
                        if (campoAdicional.getNombre().equals("Email")) {
                            recipient = campoAdicional.getValue();
                        }
                    }
                    claveAcceso = notaDebito.getInfoTributaria().getClaveAcceso();
                    secuencial = notaDebito.getInfoTributaria().getSecuencial();
                    NotaDebitoReporte notaDebitoReporte = new NotaDebitoReporte(notaDebito);
                    pdfBytes = reporteUtil.generarReporte(urlReporte, notaDebitoReporte, numAut, fechaAut);
                    razonSocialComprador = notaDebito.getInfoNotaDebito().getRazonSocialComprador();
                    nombreComercial = notaDebito.getInfoTributaria().getNombreComercial();
                    ruc = notaDebito.getInfoTributaria().getRuc();
                } else if (key instanceof GuiaRemision) {
                    nombreComprobante = "Guía de Remisión";
                    GuiaRemision guiaRemision = (GuiaRemision) key;
                    for (GuiaRemision.InfoAdicional.CampoAdicional campoAdicional : guiaRemision.getInfoAdicional().getCampoAdicional()) {
                        if (campoAdicional.getNombre().equals("Email")) {
                            recipient = campoAdicional.getValue();
                        }
                    }
                    claveAcceso = guiaRemision.getInfoTributaria().getClaveAcceso();
                    secuencial = guiaRemision.getInfoTributaria().getSecuencial();
                    GuiaRemisionReporte guiaRemisionReporte = new GuiaRemisionReporte(guiaRemision);
                    pdfBytes = reporteUtil.generarReporte(urlReporte, guiaRemisionReporte, numAut, fechaAut, guiaRemision);
                    razonSocialComprador = guiaRemision.getDestinatarios().getDestinatario().get(0).getRazonSocialDestinatario();
                    nombreComercial = guiaRemision.getInfoTributaria().getNombreComercial();
                    ruc = guiaRemision.getInfoTributaria().getRuc();
                } else if (key instanceof ComprobanteRetencion) {
                    nombreComprobante = "Retención";
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

                String autorizacionXml = object2xmlUnicode(autorizacion, Autorizacion.class, "ec.gob.sri.ws.autorizacion",
                        "autorizacion");
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
                event.setSubject("Comprobante - " + nombreComprobante);
                event.setMimeMultipart(mimeMultipart);
                Map properties = new HashMap<String, String>();
                properties.put("secuencial", secuencial);
                event.setProperties(properties);

                if (recipient != null && !recipient.isEmpty()) {
                    log.info("Secuencial de Comprobante a ser enviado por mail: " + secuencial);
                    getMailService().sendMail(event); //firing event!
                }
            } catch (NamingException | MessagingException | JAXBException | SQLException | ClassNotFoundException | IOException ex) {
                Logger.getLogger(F1_C1_Writer1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addBodyPart(byte[] bytes, String type, String fileName, MimeMultipart mimeMultipart) throws MessagingException {
        DataSource dataSource = new ByteArrayDataSource(bytes, type);
        MimeBodyPart xmlBodyPart = new MimeBodyPart();
        xmlBodyPart.setDataHandler(new DataHandler(dataSource));
        xmlBodyPart.setFileName(fileName);
        mimeMultipart.addBodyPart(xmlBodyPart);
    }

    private IMailService getMailService() throws NamingException {
        if (mailService == null) {
            InitialContext ic = new InitialContext();
            mailService = (IMailService) ic.lookup("java:global/SIRE-Batch/MailService!com.sire.service.IMailService");
        }
        return mailService;
    }
}
