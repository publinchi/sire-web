package com.sire.soap.util;

//import autorizacion.ws.sri.gob.ec.AutorizacionComprobanteLoteResponse;
import com.cobiscorp.ecobis.ficohsa.connector.vp.tarjeta.utils.TarjetasURLStreamHandler;

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
                                URLConnection connection = target.openConnection();
                                // Connection settings
                                connection.setConnectTimeout(10000); // 10 sec
                                connection.setReadTimeout(12000); // 1 min
                                return(connection);
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

    public static URL createEndPoint(String wsdl)
    {
        URL endPoint = null;
        try
        {
            TarjetasURLStreamHandler tarjetasURLStreamHandler = new TarjetasURLStreamHandler()
            {
                protected URLConnection openConnection(URL url)
                        throws IOException
                {
                    URL clone_url = new URL(url.toString());

                    HttpURLConnection clone_urlconnection = (HttpURLConnection)clone_url.openConnection();
                    clone_urlconnection.setConnectTimeout(10000);
                    clone_urlconnection.setReadTimeout(10000);
                    return clone_urlconnection;
                }
            };
            endPoint = new URL(null, wsdl, tarjetasURLStreamHandler);
        }
        catch (MalformedURLException e)
        {
            System.out.println("NO EUREKA........");
        }
        return endPoint;
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
       //     try{
                long startTotal = System.currentTimeMillis();
                for (int i = 0; i < numExecPorHilo; i++){
                    long startPartial = System.currentTimeMillis();
                    try {
                        Map map = SoapUtil.call(createSOAPMessage(String.valueOf(i)),endpoint, null,null);
                        System.out.println(SoapUtil.getStringFromSoapMessage((SOAPMessage) map.get("soapMessage")));
                    } catch (SOAPException e) {
                        e.printStackTrace();
                    }

                    //                  AutorizacionComprobanteLoteResponse autorizacionComprobanteLoteResponse =(AutorizacionComprobanteLoteResponse) map.get("object");
                    //                  System.out.println("# Clave: " + autorizacionComprobanteLoteResponse.getRespuestaAutorizacionLote().getAutorizaciones().getAutorizacion().get(0).getMensajes().getMensaje().size());
                    long finishPartial = System.currentTimeMillis();
                    //System.out.println("Thread -> " + Thread.currentThread().getId() + ": " +"#" + (i+1) +": " + String.valueOf(finishPartial-startPartial) + " ms");
                }
                long finishTotal = System.currentTimeMillis();
                System.out.println("Total " + Thread.currentThread().getId() + ": " + String.valueOf(finishTotal-startTotal) + " ms");
      //      }
                // catch (SOAPException e) {
    //            e.printStackTrace();
    //        }
        }
    }
}
