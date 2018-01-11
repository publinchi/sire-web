//
//import ec.gob.sri.comprobantes.modelo.InfoTributaria;
//import ec.gob.sri.comprobantes.modelo.factura.Factura;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles.Detalle;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles.Detalle.Impuestos;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoAdicional;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoAdicional.CampoAdicional;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoFactura;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoFactura.TotalConImpuestos;
//import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoFactura.TotalConImpuestos.TotalImpuesto;
//import ec.gob.sri.comprobantes.modelo.factura.Impuesto;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    /*
    public static void main(String args[]) {
        try {
            Factura factura = new Factura();
            factura.setId("comprobante");
            factura.setVersion("1.0.0");

            // DETALLES
            Detalles detalles = new Detalles();
//            List<Detalle> dts = new ArrayList<>();
//            detalles.setDetalle(dts);
            Detalle detalle = new Detalle();
            detalle.setCantidad(BigDecimal.valueOf(21.00));
            detalle.setCodigoPrincipal("6504");
            detalle.setDescripcion("PISO NUEVO URANO MARFIL");
            detalle.setDescuento(BigDecimal.valueOf(107.33));
            detalle.setPrecioUnitario(BigDecimal.valueOf(16.49));
            detalle.setPrecioTotalSinImpuesto(BigDecimal.valueOf(238.89));
            detalles.getDetalle().add(detalle);

            Impuestos impuestos = new Impuestos();
//            List<Impuesto> imps = new ArrayList<>();
            Impuesto impuesto = new Impuesto();
            impuesto.setBaseImponible(BigDecimal.valueOf(238.89));
            impuesto.setCodigo("2");
            impuesto.setCodigoPorcentaje("2");
            impuesto.setTarifa(BigDecimal.valueOf(12));
            impuesto.setValor(BigDecimal.valueOf(28.67));
//            imps.add(impuesto);
            impuestos.getImpuesto().add(impuesto);
            detalle.setImpuestos(impuestos);
//            dts.add(detalle);
            factura.setDetalles(detalles);

            // InfoAdicional
//            List<CampoAdicional> cas = new ArrayList<>();
            CampoAdicional direccion = new CampoAdicional();
            direccion.setValue("OTAVALO");
            direccion.setNombre("Direccion");
//            cas.add(direccion);

            CampoAdicional telefono = new CampoAdicional();
            telefono.setValue("2418703");
            telefono.setNombre("Telefono");
//            cas.add(telefono);

            CampoAdicional email = new CampoAdicional();
            email.setValue("juancho79be@gmail.com");
            email.setNombre("Email");
//            cas.add(email);

            InfoAdicional infoAdicional = new InfoAdicional();
//            infoAdicional.setCampoAdicional(cas);
            infoAdicional.getCampoAdicional().add(direccion);
            infoAdicional.getCampoAdicional().add(telefono);
            infoAdicional.getCampoAdicional().add(email);
            factura.setInfoAdicional(infoAdicional);

            // InfoFactura
            InfoFactura infoFactura = new InfoFactura();
            infoFactura.setDirEstablecimiento("AV. JAIME RIVADENEIRA");
            infoFactura.setFechaEmision("25/11/2017");
            infoFactura.setIdentificacionComprador("1002565495");
            infoFactura.setImporteTotal(BigDecimal.valueOf(314.26));
            infoFactura.setMoneda("DOLAR");
            infoFactura.setObligadoContabilidad("SI");
            infoFactura.setPropina(BigDecimal.valueOf(0));
            infoFactura.setRazonSocialComprador("VILLAMARIN JUAN CARLOS");
            infoFactura.setTipoIdentificacionComprador("05");
            TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
//            List<TotalImpuesto> tcis = new ArrayList<>();
            TotalImpuesto totalImpuesto = new TotalImpuesto();
            totalImpuesto.setBaseImponible(BigDecimal.valueOf(280.59));
            totalImpuesto.setCodigo("2");
            totalImpuesto.setCodigoPorcentaje("2");
            totalImpuesto.setValor(BigDecimal.valueOf(33.67));
//            tcis.add(totalImpuesto);
//            totalConImpuestos.setTotalImpuesto(tcis);
            totalConImpuestos.getTotalImpuesto().add(totalImpuesto);
            infoFactura.setTotalConImpuestos(totalConImpuestos);
            infoFactura.setTotalDescuento(BigDecimal.valueOf(111.96));
            infoFactura.setTotalSinImpuestos(BigDecimal.valueOf(280.59));
            factura.setInfoFactura(infoFactura);

            // InfoTributaria
            InfoTributaria infoTributaria = new InfoTributaria();
            infoTributaria.setAmbiente("1");
            infoTributaria.setCodDoc("01");
            infoTributaria.setClaveAcceso("2511201701109172437100120020010000323820668973317");
            infoTributaria.setDirMatriz("AV. HELEODORO AYALA");
            infoTributaria.setEstab("002");
            infoTributaria.setNombreComercial("JORGE YEPEZ TERAN");
            infoTributaria.setPtoEmi("001");
            infoTributaria.setRazonSocial("ALMACENES JOTAYETESA S.A.");
            infoTributaria.setRuc("1091724371001");
            infoTributaria.setSecuencial("000032382");
            infoTributaria.setTipoEmision("1");

            factura.setInfoTributaria(infoTributaria);

            JAXBContext jaxbContext = JAXBContext.newInstance(Factura.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(factura, sw);
            String xmlString = sw.toString();

            System.out.println(xmlString);
        } catch (JAXBException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
}
