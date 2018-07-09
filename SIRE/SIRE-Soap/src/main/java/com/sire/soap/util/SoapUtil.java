/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.soap.util;

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
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author pestupinan
 */
public class SoapUtil {

    static {
        disableSslVerification();
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
            getStringFromSoapMessage(soapMessage);
            if (returnObjectName != null && aClass != null) {
                map.put("object", SoapUtil.getObjectFromSoapMessage(soapMessage, returnObjectName, aClass));
            } else {
                map.put("soapMessage", soapMessage);
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

    protected static Object getObjectFromSoapMessage(SOAPMessage soapResponse, String localName, Class aClass) {
        XMLStreamReader xsr = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            soapResponse.writeTo(bos);
            XMLInputFactory xif = XMLInputFactory.newInstance();
            StreamSource xml = new StreamSource(new ByteArrayInputStream(bos.toByteArray()));
            xsr = xif.createXMLStreamReader(xml);
            xsr.nextTag();
            while(xsr.hasNext()) {
                if(xsr.isStartElement() && xsr.getLocalName().equals(localName)) {
                    break;
                }
                xsr.next();
            }

            JAXBContext jc = JAXBContext.newInstance(aClass);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return unmarshaller.unmarshal(xsr, aClass).getValue();
        } catch (SOAPException | IOException | XMLStreamException | JAXBException ex) {
            Logger.getLogger(SoapUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (xsr != null) {
                    xsr.close();
                }
            } catch (XMLStreamException ex) {
                Logger.getLogger(SoapUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
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
}
