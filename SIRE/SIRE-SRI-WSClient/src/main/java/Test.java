
import ec.gob.sri.ws.recepcion.Comprobante;
import ec.gob.sri.ws.recepcion.Mensaje;
import ec.gob.sri.ws.recepcion.RespuestaSolicitud;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pestupinan
 */
public class Test {

    public static void main(String args[]) throws JAXBException {
//        RecepcionComprobantesOfflineService recepcionComprobantesOfflineService = new RecepcionComprobantesOfflineService();
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
//                + "<factura id=\"comprobante\" version=\"1.0.0\">\n"
//                + "	<infoTributaria>\n"
//                + "		<ambiente>2</ambiente>\n"
//                + "		<tipoEmision>1</tipoEmision>\n"
//                + "		<razonSocial>ALMACENES JOTAYETESA S.A</razonSocial>\n"
//                + "		<nombreComercial>JORGE YEPEZ TERAN</nombreComercial>\n"
//                + "		<ruc>1091724371001</ruc>\n"
//                + "		<claveAcceso>0611201701109172437100120020010000322174280085015</claveAcceso>\n"
//                + "		<codDoc>01</codDoc>\n"
//                + "		<estab>002</estab>\n"
//                + "		<ptoEmi>001</ptoEmi>\n"
//                + "		<secuencial>000032217</secuencial>\n"
//                + "		<dirMatriz>AV.HELEODORO AYALA Y DARIO EGAS GRIJALVA</dirMatriz>\n"
//                + "	</infoTributaria>\n"
//                + "	<infoFactura>\n"
//                + "		<fechaEmision>06/11/2017</fechaEmision>\n"
//                + "		<dirEstablecimiento>AV. JAIME RIVADENEIRA 6-49 Y PEDRO MONCAYO</dirEstablecimiento>\n"
//                + "		<obligadoContabilidad>SI</obligadoContabilidad>\n"
//                + "		<tipoIdentificacionComprador>05</tipoIdentificacionComprador>\n"
//                + "		<razonSocialComprador>ROSERO MIEL GALO ERNESTO</razonSocialComprador>\n"
//                + "		<identificacionComprador>1000001766</identificacionComprador>\n"
//                + "		<totalSinImpuestos>70.26</totalSinImpuestos>\n"
//                + "		<totalDescuento>22.97</totalDescuento>\n"
//                + "		<totalConImpuestos>\n"
//                + "			<totalImpuesto>\n"
//                + "				<codigo>2</codigo>\n"
//                + "				<codigoPorcentaje>2</codigoPorcentaje>\n"
//                + "				<baseImponible>70.26</baseImponible>\n"
//                + "				<valor>8.43</valor>\n"
//                + "			</totalImpuesto>\n"
//                + "		</totalConImpuestos>\n"
//                + "		<propina>0</propina>\n"
//                + "		<importeTotal>78.69</importeTotal>\n"
//                + "		<moneda>DOLAR</moneda>\n"
//                + "	</infoFactura>\n"
//                + "	<detalles>\n"
//                + "		<detalle>\n"
//                + "			<codigoPrincipal>6745</codigoPrincipal>\n"
//                + "			<descripcion>PISO MANUELITA BEIGE 42.5*42.5 CORONA</descripcion>\n"
//                + "			<cantidad>6.00</cantidad>\n"
//                + "			<precioUnitario>13.38</precioUnitario>\n"
//                + "			<descuento>21.68</descuento>\n"
//                + "			<precioTotalSinImpuesto>58.61</precioTotalSinImpuesto>\n"
//                + "			<impuestos>\n"
//                + "				<impuesto>\n"
//                + "					<codigo>2</codigo>\n"
//                + "					<codigoPorcentaje>2</codigoPorcentaje>\n"
//                + "					<tarifa>12</tarifa>\n"
//                + "					<baseImponible>58.61</baseImponible>\n"
//                + "					<valor>7.03</valor>\n"
//                + "				</impuesto>\n"
//                + "			</impuestos>\n"
//                + "		</detalle>\n"
//                + "		<detalle>\n"
//                + "			<codigoPrincipal>158</codigoPrincipal>\n"
//                + "			<descripcion>BONDEX PLUS 25KG</descripcion>\n"
//                + "			<cantidad>2.00</cantidad>\n"
//                + "			<precioUnitario>6.47</precioUnitario>\n"
//                + "			<descuento>1.29</descuento>\n"
//                + "			<precioTotalSinImpuesto>11.65</precioTotalSinImpuesto>\n"
//                + "			<impuestos>\n"
//                + "				<impuesto>\n"
//                + "					<codigo>2</codigo>\n"
//                + "					<codigoPorcentaje>2</codigoPorcentaje>\n"
//                + "					<tarifa>12</tarifa>\n"
//                + "					<baseImponible>11.65</baseImponible>\n"
//                + "					<valor>1.40</valor>\n"
//                + "				</impuesto>\n"
//                + "			</impuestos>\n"
//                + "		</detalle>\n"
//                + "	</detalles>\n"
//                + "	<infoAdicional>\n"
//                + "		<campoAdicional nombre=\"Direccion\">IBARRA</campoAdicional>\n"
//                + "		<campoAdicional nombre=\"Telefono\">2418703</campoAdicional>\n"
//                + "		<campoAdicional nombre=\"Email\">jalmeida@jorgeyepezteran.com</campoAdicional>\n"
//                + "	</infoAdicional>\n"
//                + "</factura>";
//
//        RespuestaSolicitud respuestaSolicitud = recepcionComprobantesOfflineService.getRecepcionComprobantesOfflinePort().validarComprobante(xml.getBytes());
//        if (respuestaSolicitud.getComprobantes() != null && respuestaSolicitud.getComprobantes().getComprobante() != null) {
//            System.out.println(respuestaSolicitud.getComprobantes().getComprobante().size());
//        }
//        System.out.println(respuestaSolicitud.getEstado());

//        AutorizacionComprobantesOfflineService autorizacionComprobantesOfflineService = new AutorizacionComprobantesOfflineService();
//        RespuestaComprobante respuestaComprobante = autorizacionComprobantesOfflineService.getAutorizacionComprobantesOfflinePort().autorizacionComprobante("2511201704109172437100120020010000000357572237219");
//        System.out.println(respuestaComprobante.getAutorizaciones().getAutorizacion().size());
//        System.out.println(respuestaComprobante.getClaveAccesoConsultada());
//        System.out.println(respuestaComprobante.getNumeroComprobantes());
//        AutorizacionComprobantesOfflineService autorizacionComprobantesOfflineService = new AutorizacionComprobantesOfflineService();
//        RespuestaComprobante respuestaComprobante = autorizacionComprobantesOfflineService.getAutorizacionComprobantesOfflinePort().autorizacionComprobante("0412201701109172437100110040010000000037260892418");
//        System.out.println(respuestaComprobante.getAutorizaciones().getAutorizacion().size());
//        System.out.println(respuestaComprobante.getClaveAccesoConsultada());
//        System.out.println(respuestaComprobante.getNumeroComprobantes());
        RespuestaSolicitud respuestaSolicitud = new RespuestaSolicitud();
        RespuestaSolicitud.Comprobantes comprobantes = new RespuestaSolicitud.Comprobantes();
        Comprobante comprobante = new Comprobante();
        comprobante.setClaveAcceso("0000000000000000000000");
        Comprobante.Mensajes mensajes = new Comprobante.Mensajes();
        Mensaje mensaje = new Mensaje();
        mensaje.setIdentificador("qfqwcqw");
        mensaje.setInformacionAdicional("cdsv");
        mensaje.setMensaje("dfsdfd");
        mensaje.setTipo("avdvd");
        mensajes.getMensaje().add(mensaje);
        comprobante.setMensajes(mensajes);
        comprobantes.getComprobante().add(comprobante);
        respuestaSolicitud.setComprobantes(comprobantes);
        respuestaSolicitud.setEstado("estado");

        JAXBContext jaxbContext = JAXBContext.newInstance(RespuestaSolicitud.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(respuestaSolicitud, sw);
        String xmlString = sw.toString();
        System.out.println("xml -> " + xmlString);
    }
}
