package com.sire.sri.batch.recepcion;

import com.sire.service.DatasourceService;
import com.sire.sri.batch.recepcion.util.JaxbCharacterEscapeHandler;
import com.sire.signature.GenericXMLSignature;
import com.sire.signature.XAdESBESSignature;
import com.sire.soap.util.SoapUtil;
import com.sire.sri.entities.Lote;
import com.sun.xml.bind.marshaller.DataWriter;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import org.dom4j.CDATA;
import org.dom4j.DocumentHelper;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import recepcion.ws.sri.gob.ec.Mensaje;
import recepcion.ws.sri.gob.ec.RespuestaSolicitud;
import recepcion.ws.sri.gob.ec.ValidarComprobanteResponse;

@Named
public class F1_C1_Writer1 extends AbstractItemWriter {

    @Inject
    private JobContext jobCtx;
    private String pathSignature;
    private String passSignature;
    private String urlRecepcion;
    private String claveAccesoLote;
    private Connection connection;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        Properties runtimeParams = BatchRuntime.getJobOperator().getParameters(jobCtx.getExecutionId());
        pathSignature = runtimeParams.getProperty("pathSignature");
        passSignature = runtimeParams.getProperty("passSignature");
        urlRecepcion = runtimeParams.getProperty("urlRecepcion");
        getClaveAccesoLote("01");
    }

    @Override
    public void writeItems(List<Object> items) {
        try {
            System.out.print("F1_C1_Writer1");
            System.out.print("list in -> " + items.size());

            Lote lote = new Lote();
            lote.setClaveAcceso(claveAccesoLote);
            for (Object item : items) {
                Factura factura = (Factura) ((Map) item).get("factura");
                GenericXMLSignature genericXMLSignature = XAdESBESSignature.firmar(xml2document(object2xml(factura)), pathSignature, passSignature);
                String signedXml = genericXMLSignature.toSignedXml();
//            ComprobanteXml comprobanteXml = new ComprobanteXml();
//            comprobanteXml.setFileXML(signedXml);
//            comprobanteXml.setTipo("comprobante");
//            comprobanteXml.setVersion("1.0.0");
                lote.getComprobantes().getComprobante().add(appendCdata(signedXml));
            }
            lote.setRuc(lote.getClaveAcceso().substring(10, 23));
            lote.setVersion("1.0.0");

            String loteXml = object2xmlUnicode(lote);

            System.out.println("loteXml: " + loteXml);

            Map mapCall = (Map) SoapUtil.call(
                    createSOAPMessage(new String(Base64.getEncoder().encode(doc2bytes(xml2document(loteXml))))),
                    new URL(urlRecepcion),
                    null,
                    null);
            SOAPMessage soapMessage = (SOAPMessage) mapCall.get("soapMessage");
            System.out.println("Soap Recepcion Response:");
            System.out.println(SoapUtil.toString(soapMessage));
            ValidarComprobanteResponse validarComprobanteResponse = toValidarComprobanteResponse(soapMessage);

            String estadoLote = validarComprobanteResponse.getRespuestaRecepcionComprobante().getEstado();

            System.out.println("estadoLote -> " + estadoLote);
            for (Object item : items) {
                Factura factura = (Factura) ((Map) item).get("factura");
                String secuencial = factura.getInfoTributaria().getEstab() + "-" + factura.getInfoTributaria().getPtoEmi() + "-" + factura.getInfoTributaria().getSecuencial();
                boolean existsError = false;
                String cabeceraSQL;
                System.out.println("# Comprobantes devueltos por SRI: " + validarComprobanteResponse.getRespuestaRecepcionComprobante().getComprobantes().getComprobante().size());
                for (recepcion.ws.sri.gob.ec.Comprobante c : validarComprobanteResponse.getRespuestaRecepcionComprobante().getComprobantes().getComprobante()) {
                    System.out.println("Clave acceso factura: " + factura.getInfoTributaria().getClaveAcceso());
                    System.out.println("Clave acceso comprobante: " + c.getClaveAcceso());
                    if (factura.getInfoTributaria().getClaveAcceso().equals(c.getClaveAcceso())) {
                        try {
                            existsError = true;

                            c.getMensajes().getMensaje().stream().map((mensaje) -> {
                                System.out.println("Identificador -> " + mensaje.getIdentificador());
                                return mensaje;
                            }).map((mensaje) -> {
                                System.out.println("Tipo -> " + mensaje.getTipo());
                                return mensaje;
                            }).map((mensaje) -> {
                                System.out.println("Mensaje -> " + mensaje.getMensaje());
                                return mensaje;
                            }).forEachOrdered((mensaje) -> {
                                System.out.println("InformacionAdicional -> " + mensaje.getInformacionAdicional());
                            });
                            System.out.println("-------------------------------------------------------------");

                            String estado = "DEVUELTA";
                            String claveAcceso = c.getClaveAcceso();
                            String identificador = c.getMensajes().getMensaje().get(0).getIdentificador();
                            String informacionAdicional = c.getMensajes().getMensaje().get(0).getInformacionAdicional();
                            String mensaje = c.getMensajes().getMensaje().get(0).getMensaje();
                            String tipo = c.getMensajes().getMensaje().get(0).getTipo();

                            System.out.print("Secuencial: " + secuencial
                                    + ", Estado: " + estado
                                    + ", ClaveAcceso: " + claveAcceso
                                    + ", Identificador: " + identificador
                                    + ", InformacionAdicional: " + informacionAdicional
                                    + ", Mensaje: " + mensaje
                                    + ", Tipo: " + tipo
                            );

                            String motivo = ", MOTIVO_SRI = '" + identificador + ":" + tipo + ":" + mensaje + "'";

                            cabeceraSQL = "UPDATE FAC_FACTURA_C SET "
                                    + "ESTADO_SRI = '" + estado + "', CLAVE_ACCESO_LOTE = '" + claveAccesoLote + "'"
                                    + motivo
                                    + " WHERE SECUENCIAL = '" + secuencial + "'";
                            System.out.println("update -> " + cabeceraSQL);
                            try (PreparedStatement preparedStatement = getConnection().prepareStatement(cabeceraSQL)) {
                                preparedStatement.executeQuery();
                                preparedStatement.close();
                            }
                        } catch (SQLException | NamingException ex) {
                            Logger.getLogger(F1_C1_Writer1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (existsError == false) {
                    try {
                        cabeceraSQL = "UPDATE FAC_FACTURA_C SET "
                                + "ESTADO_SRI = 'RECIBIDA', CLAVE_ACCESO_LOTE = '" + claveAccesoLote + "'"
                                + " WHERE SECUENCIAL = '" + secuencial + "'";
                        System.out.println("update -> " + cabeceraSQL);
                        try (PreparedStatement preparedStatement = getConnection().prepareStatement(cabeceraSQL)) {
                            preparedStatement.executeQuery();
                            preparedStatement.close();
                        }
                    } catch (SQLException | NamingException ex) {
                        Logger.getLogger(F1_C1_Writer1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            String secuencial = claveAccesoLote.substring(30, 39);

            String fechaEstado = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

            String insertSQL = "INSERT INTO CEL_LOTE_AUTORIZADO "
                    + "VALUES ('01'," + secuencial + ",'01','" + claveAccesoLote + "','" + estadoLote + "','" + fechaEstado + "')";
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(insertSQL)) {
                preparedStatement.executeQuery();
                preparedStatement.close();
            }
        } catch (SQLException | NamingException | SOAPException | XPathExpressionException | MalformedURLException | JAXBException ex) {
            Logger.getLogger(F1_C1_Writer1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String object2xml(Object item) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(item.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(item, sw);

        return sw.toString();
    }

    private Document xml2document(String xml) throws JAXBException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name()));
            return db.parse(stream);
        } catch (ParserConfigurationException | SAXException | IOException | IllegalArgumentException ex) {
            System.err.println("Error al parsear el documento");
            System.exit(-1);
            return null;
        }
    }

    private String object2xmlUnicode(Object item) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(item.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());

        jaxbMarshaller.marshal(item, dataWriter);

//        System.out.println("xml unicode -> " + stringWriter.toString());
        return stringWriter.toString();
    }

    private SOAPMessage createSOAPMessage(String xmlBase64) {
        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapMsg = factory.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();

            SOAPEnvelope envelope = part.getEnvelope();
            envelope.addNamespaceDeclaration("ec", "http://ec.gob.sri.ws.recepcion");
            SOAPBody body = envelope.getBody();
            MimeHeaders headers = soapMsg.getMimeHeaders();
            headers.addHeader("SOAPAction", "");
            SOAPBodyElement element = body.addBodyElement(
                    envelope.createName("ec:validarComprobante"));
//            System.out.println("xmlBase64: " + xmlBase64);
            element.addChildElement("xml").addTextNode(xmlBase64);

            soapMsg.saveChanges();
            System.out.println("Request Recepcion SOAP Message:");
            System.out.println(SoapUtil.toString(soapMsg));
            return soapMsg;
        } catch (SOAPException ex) {
            System.err.println(ex);
        }
        return null;
    }

    private ValidarComprobanteResponse toValidarComprobanteResponse(SOAPMessage soapMessage) throws SOAPException, XPathExpressionException {
        ValidarComprobanteResponse validarComprobanteResponse = new ValidarComprobanteResponse();
        RespuestaSolicitud respuestaRecepcionComprobante = new RespuestaSolicitud();
        validarComprobanteResponse.setRespuestaRecepcionComprobante(respuestaRecepcionComprobante);
        RespuestaSolicitud.Comprobantes comprobantes = new RespuestaSolicitud.Comprobantes();
        respuestaRecepcionComprobante.setComprobantes(comprobantes);

        XPath xpath = XPathFactory.newInstance().newXPath();

        Node estadoNode = (Node) xpath.evaluate("//estado", soapMessage.getSOAPBody(), XPathConstants.NODE);
        if (estadoNode != null) {
            respuestaRecepcionComprobante.setEstado(estadoNode.getValue());
        }

        NodeList comprobantesNodeList = (NodeList) xpath.evaluate("//comprobantes/comprobante", soapMessage.getSOAPBody(), XPathConstants.NODESET);

        for (int i = 0; i < comprobantesNodeList.getLength(); i++) {
            recepcion.ws.sri.gob.ec.Comprobante comprobante = new recepcion.ws.sri.gob.ec.Comprobante();
            comprobantes.getComprobante().add(comprobante);
            Node comprobanteNode = (Node) comprobantesNodeList.item(i);

            NodeList hijosComprobante = comprobanteNode.getChildNodes();

            for (int j = 0; j < hijosComprobante.getLength(); j++) {
                if (hijosComprobante.item(j).getNodeName().equals("claveAcceso")) {
                    comprobante.setClaveAcceso(hijosComprobante.item(j).getTextContent());
                } else if (hijosComprobante.item(j).getNodeName().equals("mensajes")) {
                    NodeList mensajesNodeList = (NodeList) hijosComprobante.item(j);
                    recepcion.ws.sri.gob.ec.Comprobante.Mensajes mensajes = new recepcion.ws.sri.gob.ec.Comprobante.Mensajes();
                    comprobante.setMensajes(mensajes);
                    for (int k = 0; k < mensajesNodeList.getLength(); k++) {
                        Node mensajeNode = (Node) mensajesNodeList.item(k);
                        NodeList hijos = mensajeNode.getChildNodes();
                        Mensaje mensaje = new Mensaje();
                        for (int l = 0; l < hijos.getLength(); l++) {
                            switch (hijos.item(l).getNodeName()) {
                                case "identificador":
                                    mensaje.setIdentificador(hijos.item(l).getTextContent());
                                    break;
                                case "mensaje":
                                    mensaje.setMensaje(hijos.item(l).getTextContent());
                                    break;
                                case "tipo":
                                    mensaje.setTipo(hijos.item(l).getTextContent());
                                    break;
                                case "informacionAdicional":
                                    mensaje.setInformacionAdicional(hijos.item(l).getTextContent());
                                    break;
                                default:
                                    break;
                            }
                        }
                        mensajes.getMensaje().add(mensaje);
                    }
                }
            }
        }
        return validarComprobanteResponse;
    }

    private static byte[] doc2bytes(Document document) {
        try {
            Source source = new DOMSource(document);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Result result = new StreamResult(out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return out.toByteArray();
        } catch (TransformerException e) {
            System.err.println("Error al convertir documento a arreglo de bytes.");
        }
        return null;
    }

    public static String appendCdata(String input) {
        CDATA cdata = DocumentHelper.createCDATA(input);
        return cdata.asXML();
    }

    private void getClaveAccesoLote(String tipoComprobante) throws SQLException, NamingException {
        Connection dbConnection = null;
        CallableStatement callableStatement = null;

        String getClaveAccesoLote = "{call SP_CLAVE_ACCESO_LOTE(?,?)}";

        try {
            dbConnection = getConnection();
            callableStatement = dbConnection.prepareCall(getClaveAccesoLote);

            callableStatement.setString(1, tipoComprobante);
            callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

            callableStatement.executeUpdate();

            claveAccesoLote = callableStatement.getString(2);

//            System.out.println("claveAccesoLote : " + claveAccesoLote);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }

    }

    private Connection getConnection() throws SQLException, NamingException {
        if (connection == null || (connection != null && connection.isClosed())) {
            InitialContext ic = new InitialContext();
            DatasourceService datasourceService = (DatasourceService) ic.lookup("java:global/SIRE-Batch/DatasourceService!com.sire.service.DatasourceService");
            connection = datasourceService.getConnection();
        }
        return connection;
    }
}
