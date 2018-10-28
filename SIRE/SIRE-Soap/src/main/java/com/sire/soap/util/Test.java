package com.sire.soap.util;


import javax.xml.soap.*;
import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String args[]) throws MalformedURLException {

        int hilos = 1;
        int numExecPorHilo = 1;

        URL endpoint =
                new URL(null,
                        "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline",
                        new URLStreamHandler() {
                            @Override
                            protected URLConnection openConnection(URL url) throws IOException {
                                URL target = new URL(url.toString());
                                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.65.253", 8080));
                                URLConnection connection = target.openConnection(proxy);
                                Authenticator authenticator = new Authenticator() {
                                    public PasswordAuthentication getPasswordAuthentication() {
                                        return (new PasswordAuthentication("pestupinan",
                                                "Gamma20181".toCharArray()));
                                    }
                                };
                                Authenticator.setDefault(authenticator);
                                // Connection settings
                                connection.setConnectTimeout(10000); // 10 sec
                                connection.setReadTimeout(12000); // 1 min
                                return (connection);
                            }
                        });

        ExecutorService executor = Executors.newFixedThreadPool(hilos);

        for (int i = 0; i < hilos; i++) {
            Runnable worker = new MyRunnable(endpoint, numExecPorHilo);
            executor.execute(worker);
        }
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {

        }
        System.out.println("\nFinished all threads");
    }

    private static SOAPMessage createSOAPMessage(String claveAcceso) {
        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapMsg = factory.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();

            SOAPEnvelope envelope = part.getEnvelope();
            envelope.addNamespaceDeclaration("ec", "http://ec.gob.sri.ws.autorizacion");
            SOAPBody body = envelope.getBody();
            MimeHeaders headers = soapMsg.getMimeHeaders();
            headers.addHeader("SOAPAction", "");
            SOAPBodyElement element = body.addBodyElement(
                    envelope.createName("ec:autorizacionComprobanteLote"));
            element.addChildElement("claveAccesoLote").addTextNode(claveAcceso);

            soapMsg.saveChanges();
            return soapMsg;
        } catch (SOAPException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public static class MyRunnable implements Runnable {

        private final URL endpoint;
        private final int numExecPorHilo;

        public MyRunnable(URL endpoint, int numExecPorHilo) {
            this.endpoint = endpoint;
            this.numExecPorHilo = numExecPorHilo;
        }

        @Override
        public void run() {
            //testXml2Object();
            //testObject2Xml();
            testSOAP2Object();
        }

        private void testXml2Object() {
            String xml = "<autorizacionComprobanteLoteResponse>\n" +
                    "    <RespuestaAutorizacionLote>\n" +
                    "        <claveAccesoLoteConsultada>123</claveAccesoLoteConsultada>\n" +
                    "        <numeroComprobantesLote>abc</numeroComprobantesLote>\n" +
                    "        <autorizaciones>\n" +
                    "            <autorizacion>\n" +
                    "                <estado>RECIBIDA</estado>\n" +
                    "            </autorizacion>\n" +
                    "        </autorizaciones>\n" +
                    "    </RespuestaAutorizacionLote>\n" +
                    "</autorizacionComprobanteLoteResponse>";
            //AutorizacionComprobanteLoteResponse autorizacionComprobanteLoteResponse = (AutorizacionComprobanteLoteResponse) SoapUtil.getObjectFromString(xml, AutorizacionComprobanteLoteResponse.class);
            //System.out.println("ClaveAccesoLoteConsultada: " + autorizacionComprobanteLoteResponse.getRespuestaAutorizacionLote().getClaveAccesoLoteConsultada());
            //System.out.println("Estado: " + autorizacionComprobanteLoteResponse.getRespuestaAutorizacionLote().getAutorizaciones().getAutorizacion().get(0).getEstado());
        }

        private void testObject2Xml() {
            //AutorizacionComprobanteLoteResponse autorizacionComprobanteLoteResponse = new AutorizacionComprobanteLoteResponse();
            //RespuestaLote respuestaLote = new RespuestaLote();
            //respuestaLote.setClaveAccesoLoteConsultada("123");
            //respuestaLote.setNumeroComprobantesLote("abc");
            //RespuestaLote.Autorizaciones autorizaciones = new RespuestaLote.Autorizaciones();
            //Autorizacion autorizacion = new Autorizacion();
            //autorizacion.setEstado("RECIBIDA");
            //autorizaciones.getAutorizacion().add(autorizacion);
            //respuestaLote.setAutorizaciones(autorizaciones);
            //autorizacionComprobanteLoteResponse.setRespuestaAutorizacionLote(respuestaLote);
            //SoapUtil.getStringFromObject(autorizacionComprobanteLoteResponse);
        }

        private void testSOAP2Object() {
            long startTotal = System.currentTimeMillis();
            for (int i = 0; i < numExecPorHilo; i++) {
                long startPartial = System.currentTimeMillis();
                //try {
                    //Map map = SoapUtil.call(createSOAPMessage(String.valueOf(i)), endpoint, AutorizacionComprobanteLoteResponse.class);
                    //AutorizacionComprobanteLoteResponse autorizacionComprobanteLoteResponse = (AutorizacionComprobanteLoteResponse) map.get("object");
                    //if (autorizacionComprobanteLoteResponse != null) {
                    //    System.out.println("# Clave: " + autorizacionComprobanteLoteResponse.getRespuestaAutorizacionLote().getClaveAccesoLoteConsultada());
                    //System.out.println("# Estado: " + autorizacionComprobanteLoteResponse.getRespuestaAutorizacionLote().getAutorizaciones().getAutorizacion().get(0).getEstado());
                    //    System.out.println("# Mensaje: " + autorizacionComprobanteLoteResponse.getRespuestaAutorizacionLote().getAutorizaciones().getAutorizacion().get(0).getMensajes().getMensaje().get(0).getMensaje());
                    //}
                //} catch (SOAPException e) {
                //    e.printStackTrace();
                //}

                long finishPartial = System.currentTimeMillis();
                System.out.println("Thread -> " + Thread.currentThread().getId() + ": " + "#" + (i + 1) + ": " + String.valueOf(finishPartial - startPartial) + " ms");
            }
            long finishTotal = System.currentTimeMillis();
            System.out.println("Total " + Thread.currentThread().getId() + ": " + String.valueOf(finishTotal - startTotal) + " ms");
        }
    }
}
