
import ec.gob.sri.comprobantes.modelo.InfoTributaria;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.modelo.factura.Impuesto;
import ec.gob.sri.comprobantes.modelo.reportes.FacturaReporte;
import ec.gob.sri.comprobantes.util.reportes.ReporteUtil;
import java.math.BigDecimal;
import java.sql.SQLException;

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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ReporteUtil reporteUtil = new ReporteUtil();
        String urlReporte = "/opt/payara41/SIRE/reportes/factura.jasper";

        Factura factura = new Factura();
        factura.setId("comprobante");
        factura.setVersion("1.0.0");

        // DETALLES
        Factura.Detalles detalles = new Factura.Detalles();
//            List<Detalle> dts = new ArrayList<>();
//            detalles.setDetalle(dts);
        Factura.Detalles.Detalle detalle = new Factura.Detalles.Detalle();
        detalle.setCantidad(BigDecimal.valueOf(21.00));
        detalle.setCodigoPrincipal("6504");
        detalle.setDescripcion("PISO NUEVO URANO MARFIL");
        detalle.setDescuento(BigDecimal.valueOf(107.33));
        detalle.setPrecioUnitario(BigDecimal.valueOf(16.49));
        detalle.setPrecioTotalSinImpuesto(BigDecimal.valueOf(238.89));
        detalles.getDetalle().add(detalle);

        Factura.Detalles.Detalle.Impuestos impuestos = new Factura.Detalles.Detalle.Impuestos();
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
        Factura.InfoAdicional.CampoAdicional direccion = new Factura.InfoAdicional.CampoAdicional();
        direccion.setValue("OTAVALO");
        direccion.setNombre("Direccion");
//            cas.add(direccion);

        Factura.InfoAdicional.CampoAdicional telefono = new Factura.InfoAdicional.CampoAdicional();
        telefono.setValue("2418703");
        telefono.setNombre("Telefono");
//            cas.add(telefono);

        Factura.InfoAdicional.CampoAdicional email = new Factura.InfoAdicional.CampoAdicional();
        email.setValue("juancho79be@gmail.com");
        email.setNombre("Email");
//            cas.add(email);

        Factura.InfoAdicional infoAdicional = new Factura.InfoAdicional();
//            infoAdicional.setCampoAdicional(cas);
        infoAdicional.getCampoAdicional().add(direccion);
        infoAdicional.getCampoAdicional().add(telefono);
        infoAdicional.getCampoAdicional().add(email);
        factura.setInfoAdicional(infoAdicional);

        // InfoFactura
        Factura.InfoFactura infoFactura = new Factura.InfoFactura();
        infoFactura.setDirEstablecimiento("AV. JAIME RIVADENEIRA");
        infoFactura.setFechaEmision("25/11/2017");
        infoFactura.setIdentificacionComprador("1002565495");
        infoFactura.setImporteTotal(BigDecimal.valueOf(314.26));
        infoFactura.setMoneda("DOLAR");
        infoFactura.setObligadoContabilidad("SI");
        infoFactura.setPropina(BigDecimal.valueOf(0));
        infoFactura.setRazonSocialComprador("VILLAMARIN JUAN CARLOS");
        infoFactura.setTipoIdentificacionComprador("05");
        Factura.InfoFactura.TotalConImpuestos totalConImpuestos = new Factura.InfoFactura.TotalConImpuestos();
//            List<TotalImpuesto> tcis = new ArrayList<>();
        Factura.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuesto = new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
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
        FacturaReporte fact = new FacturaReporte(factura);
        fact.setFactura(factura);
        String numAut = "1";
        String fechaAut = "12/19/2017";
        reporteUtil.generarReporte(urlReporte, fact, numAut, fechaAut);
    }
}
