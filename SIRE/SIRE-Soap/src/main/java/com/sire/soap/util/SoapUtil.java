/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.soap.util;

import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author pestupinan
 */
public class SoapUtil {

    static {
        disableSslVerification();
    }

    private static Map<Class, JAXBContext> contextStore = new ConcurrentHashMap<>();

    public static Map<String, Object> call(SOAPMessage soapMsg, URL url,
                                           Class aClass) throws SOAPException {
        return call(soapMsg,url,null, aClass);
    }
    /**
     *
     * @param soapMsg
     * @param url
     * @param returnObjectName
     * @param aClass
     * @return
     */
    public static Map<String, Object> call(SOAPMessage soapMsg, URL url, String returnObjectName,
                                           Class aClass) throws SOAPException {
        SOAPConnection soapConnection = null;
        String cookie;
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

//            printHeaders(soapMsg);
            // Send SOAP Message to SOAP Server
            SOAPMessage soapMessage = soapConnection.call(soapMsg, url);

            MimeHeaders session = soapMessage.getMimeHeaders();
            String[] cookies = session.getHeader("Set-Cookie");

//            printHeaders(soapMessage);
            Map<String, Object> map = new HashMap<>();

            if (cookies != null && cookies.length == 1) {
                cookie = cookies[0];
                map.put("cookie", cookie);
            }

//            Logger.getLogger(SoapUtil.class.getName()).log(Level.INFO,
//                    "Response SOAP Message cookie: {0}", cookie);
//
            map.put("soapMessage", clone(soapMessage));
            if (aClass != null) {
                map.put("object", SoapUtil.getObjectFromSoapMessage(soapMessage, aClass));
            }

            return map;
        } finally {
            if (soapConnection != null) {
                try {
                    soapConnection.close();
                } catch (SOAPException ex) {
                    Logger.getLogger(SoapUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    protected static Object getObjectFromSoapMessage(SOAPMessage soapResponse, Class aClass) {
        Object object = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            soapResponse.writeTo(bos);

            JAXBContext jaxbContext = getContextInstance(aClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            if(soapResponse.getSOAPBody().hasFault()){
                Logger.getLogger(SoapUtil.class.getName()).log(Level.INFO, soapResponse.getSOAPBody().getFault().getFaultString());
                object = unmarshaller.unmarshal(soapResponse.getSOAPBody().getFault());
            } else {
                object = unmarshaller.unmarshal(soapResponse.getSOAPBody().extractContentAsDocument());
            }
        } catch (SOAPException | IOException | JAXBException ex) {
            Logger.getLogger(SoapUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }

    protected static JAXBContext getContextInstance(Class objectClass) throws JAXBException{
        JAXBContext context = contextStore.get(objectClass);
        if (context==null){
            context = JAXBContext.newInstance(objectClass);
            contextStore.put(objectClass, context);
        }
        return context;
    }

    public static String getStringFromSoapMessage(SOAPMessage soapMessage) {
        try {
            Source sourceContent = soapMessage.getSOAPPart().getContent();
            StreamResult sr = new StreamResult();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            sr.setOutputStream(out);

            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            trans.transform(sourceContent, sr);

//            Logger.getLogger(SoapUtil.class.getName()).log(Level.INFO, "Transform: {0}", out);

            return out.toString();
        } catch (TransformerException | SOAPException ex) {
            Logger.getLogger(SoapUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (String hostname, SSLSession session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(SoapUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printHeaders(SOAPMessage soapMessage) {
        MimeHeaders mimeHeaders = soapMessage.getMimeHeaders();
        Iterator<MimeHeader> it = mimeHeaders.getAllHeaders();
        while (it.hasNext()) {
            MimeHeader mime = it.next();
            Logger.getLogger(SoapUtil.class.getName()).log(Level.INFO,
                    "Name: {0} Value: {1}", new Object[]{mime.getName(), mime.getValue()});
        }
    }

    public static SOAPMessage getSoapMessageFromString(String xml) {
        MessageFactory factory;
        SOAPMessage message = null;
        try {
            factory = MessageFactory.newInstance();
            message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
        } catch (IOException | SOAPException ex) {
            Logger.getLogger(SoapUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    public static SOAPMessage clone(SOAPMessage message) {
        return toSOAPMessage(toDocument(message));
    }

    public static SOAPMessage toSOAPMessage(Document doc) {
        return toSOAPMessage(doc, SOAPConstants.SOAP_1_2_PROTOCOL);
    }

    public static SOAPMessage toSOAPMessage(Document doc, String protocol) {
        DOMSource domSource;
        SOAPMessage retorno;
        MessageFactory messageFactory;
        try {
            domSource = new DOMSource(doc);
            messageFactory = MessageFactory.newInstance(protocol);
            retorno = messageFactory.createMessage();
            retorno.getSOAPPart().setContent(domSource);
            return retorno;
        } catch (SOAPException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Document toDocument(SOAPMessage soapMSG) {
        try {
            Source source = soapMSG.getSOAPPart().getContent();
            TransformerFactory factoryTransform = TransformerFactory.newInstance();
            Transformer transform = factoryTransform.newTransformer();
            DOMResult retorno = new DOMResult();
            transform.transform(source, retorno);
            return (Document) retorno.getNode();
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}
