package com.sire.sri.batch.recepcion;

import com.sire.service.DatasourceService;
import ec.gob.sri.comprobantes.modelo.InfoTributaria;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles;
import ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles.Detalle;
import ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles.Detalle.Impuestos;
import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoAdicional;
import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoAdicional.CampoAdicional;
import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoFactura;
import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoFactura.TotalConImpuestos;
import ec.gob.sri.comprobantes.modelo.factura.Factura.InfoFactura.TotalConImpuestos.TotalImpuesto;
import ec.gob.sri.comprobantes.modelo.factura.Impuesto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Named
public class F1_C1_Reader1 extends AbstractItemReader {

    @Inject
    private JobContext jobCtx;
    private List facturas;
    private Iterator iterator;
    private Connection connection;

    @Override
    public Object readItem() throws Exception {
//        System.out.print("F1_C1_Reader1");
        if (iterator.hasNext()) {
            Factura factura = (Factura) iterator.next();
//            System.out.print(factura);
            return factura;
        }
        return null;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        facturas = new ArrayList();

        String cabeceraSQL = "SELECT COD_EMPRESA, RUC_EMPRESA, RAZON_SOCIAL_EMPRESA, "
                + "NOMBRE_COMERCIAL, COD_DOCUMENTO, NUM_FACTURA_INTERNO, ESTABLECIMIENTO, "
                + "PUNTO_EMISION, SECUENCIAL, DIRECCION_MATRIZ, DIRECCION_ESTABLECIMIENTO, "
                + "CONTRIBUYENTE_ESPECIAL, LLEVA_CONTABILIDAD, RAZON_SOCIAL_COMPRADOR, "
                + "FECHA_FACTURA, TIPO_IDENTIFICACION_COMPRADOR, IDENTIFICACION_COMPRADOR, "
                + "DIRECCION_COMPRADOR, TELEFONO_COMPRADOR, EMAIL_COMPRADOR, "
                + "TOTAL_SIN_IMPUESTOS, TOTAL_DESCUENTOS, PROPINA, IMPORTE_TOTAL, "
                + "CLAVE_ACCESO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, BASE_IMPONIBLE, "
                + "VALOR, MONEDA FROM V_FACTURA_ELECTRONICA_C WHERE "
                + "ESTADO_SRI='GRABADA' AND ROWNUM <= 40 ORDER BY FECHA_FACTURA";
        PreparedStatement preparedStatemenT = getConnection().prepareStatement(cabeceraSQL);
        ResultSet rs = preparedStatemenT.executeQuery();
        while (rs.next()) {
            Factura factura = new Factura();

            InfoAdicional infoAdicional = new InfoAdicional();
//            List<CampoAdicional> camposAdicionales = new ArrayList<>();
            CampoAdicional direccion = new CampoAdicional();
            direccion.setValue(rs.getString("DIRECCION_COMPRADOR"));
            direccion.setNombre("Direccion");
//            camposAdicionales.add(direccion);
            CampoAdicional telefono = new CampoAdicional();
            telefono.setValue(rs.getString("TELEFONO_COMPRADOR"));
            telefono.setNombre("Telefono");
//            camposAdicionales.add(telefono);
            CampoAdicional email = new CampoAdicional();
            email.setValue(rs.getString("EMAIL_COMPRADOR"));
            email.setNombre("Email");
//            camposAdicionales.add(email);
//            infoAdicional.setCampoAdicional(camposAdicionales);
            infoAdicional.getCampoAdicional().add(direccion);
            infoAdicional.getCampoAdicional().add(telefono);
            infoAdicional.getCampoAdicional().add(email);
            factura.setInfoAdicional(infoAdicional);

            InfoFactura infoFactura = new InfoFactura();
            infoFactura.setDirEstablecimiento(rs.getString("DIRECCION_ESTABLECIMIENTO"));
            String oldDate = rs.getString("FECHA_FACTURA");
            LocalDateTime datetime = LocalDateTime.parse(oldDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
            String newDate = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            infoFactura.setFechaEmision(newDate);
            infoFactura.setIdentificacionComprador(rs.getString("IDENTIFICACION_COMPRADOR"));
            infoFactura.setImporteTotal(BigDecimal.valueOf(Double.valueOf(rs.getString("IMPORTE_TOTAL"))));
            infoFactura.setMoneda(rs.getString("MONEDA"));
            infoFactura.setObligadoContabilidad(rs.getString("LLEVA_CONTABILIDAD"));
            infoFactura.setPropina(BigDecimal.valueOf(Double.valueOf(rs.getString("PROPINA"))));
            infoFactura.setRazonSocialComprador(rs.getString("RAZON_SOCIAL_COMPRADOR"));
            infoFactura.setTipoIdentificacionComprador(rs.getString("TIPO_IDENTIFICACION_COMPRADOR"));
            TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
//            List<TotalImpuesto> totalImpuestos = new ArrayList<>();
            TotalImpuesto totalImpuesto = new TotalImpuesto();
            totalImpuesto.setBaseImponible(BigDecimal.valueOf(Double.valueOf(rs.getString("BASE_IMPONIBLE"))));
            totalImpuesto.setCodigo(rs.getString("CODIGO_IMPUESTO"));
            totalImpuesto.setCodigoPorcentaje(rs.getString("CODIGO_PORCENTAJE"));
            totalImpuesto.setValor(BigDecimal.valueOf(Double.valueOf(rs.getString("VALOR"))));
//            totalImpuestos.add(totalImpuesto);
            totalConImpuestos.getTotalImpuesto().add(totalImpuesto);
//            totalConImpuestos.setTotalImpuesto(totalImpuestos);
            infoFactura.setTotalConImpuestos(totalConImpuestos);
            infoFactura.setTotalDescuento(BigDecimal.valueOf(Double.valueOf(rs.getString("TOTAL_DESCUENTOS"))));
            infoFactura.setTotalSinImpuestos(BigDecimal.valueOf(Double.valueOf(rs.getString("TOTAL_SIN_IMPUESTOS"))));
            factura.setInfoFactura(infoFactura);

            InfoTributaria infoTributaria = new InfoTributaria();
            infoTributaria.setClaveAcceso(rs.getString("CLAVE_ACCESO"));
            infoTributaria.setAmbiente(infoTributaria.getClaveAcceso().substring(23, 24));
            infoTributaria.setCodDoc(rs.getString("COD_DOCUMENTO"));
            infoTributaria.setDirMatriz(rs.getString("DIRECCION_MATRIZ"));
            infoTributaria.setEstab(rs.getString("ESTABLECIMIENTO"));
            infoTributaria.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
            infoTributaria.setPtoEmi(rs.getString("PUNTO_EMISION"));
            infoTributaria.setRazonSocial(rs.getString("RAZON_SOCIAL_EMPRESA"));
            infoTributaria.setRuc(rs.getString("RUC_EMPRESA"));
            infoTributaria.setSecuencial(rs.getString("SECUENCIAL"));
            infoTributaria.setTipoEmision("1");
            factura.setInfoTributaria(infoTributaria);

            factura.setId("comprobante");
            factura.setVersion("1.1.0");

            Detalles detalles = new Detalles();
//            List<Detalle> detallesList = new ArrayList<>();

            String detalleSQL = "SELECT COD_EMPRESA, COD_DOCUMENTO, NUM_DOCUMENTO_INTERNO, "
                    + "COD_ARTICULO, NOMBRE_ARTICULO, CANTIDAD, PRECIO_UNITARIO, "
                    + "DESCUENTO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, TARIFA, "
                    + "BASE_IMPONIBLE, VALOR, PRECIO_TOTAL_SIN_IMPUESTOS "
                    + "FROM V_FACTURA_ELECTRONICA_D WHERE "
                    + "NUM_DOCUMENTO_INTERNO = " + rs.getString("NUM_FACTURA_INTERNO");
            PreparedStatement preparedStatement = getConnection().prepareStatement(detalleSQL);
            ResultSet rsd = preparedStatement.executeQuery();
            while (rsd.next()) {
                Detalle detalle = new Detalle();
                detalle.setCantidad(BigDecimal.valueOf(Double.valueOf(rsd.getString("CANTIDAD"))));
                detalle.setCodigoPrincipal(rsd.getString("COD_ARTICULO"));
                detalle.setDescripcion(rsd.getString("NOMBRE_ARTICULO"));
                detalle.setDescuento(BigDecimal.valueOf(Double.valueOf(rsd.getString("DESCUENTO"))));
                Impuestos impuestos = new Impuestos();
//                List<Impuesto> impuestosList = new ArrayList<>();
                Impuesto impuesto = new Impuesto();
                impuesto.setBaseImponible(BigDecimal.valueOf(Double.valueOf(rsd.getString("BASE_IMPONIBLE"))));
                impuesto.setCodigo(rsd.getString("CODIGO_IMPUESTO"));
                impuesto.setCodigoPorcentaje(rsd.getString("CODIGO_PORCENTAJE"));
                impuesto.setTarifa(BigDecimal.valueOf(Double.valueOf(rsd.getString("TARIFA"))));
                impuesto.setValor(BigDecimal.valueOf(Double.valueOf(rsd.getString("VALOR"))));
//                impuestosList.add(impuesto);
                impuestos.getImpuesto().add(impuesto);
//                impuestos.setImpuesto(impuestosList);
                detalle.setImpuestos(impuestos);
                detalle.setPrecioTotalSinImpuesto(BigDecimal.valueOf(Double.valueOf(rsd.getString("PRECIO_TOTAL_SIN_IMPUESTOS"))));
                detalle.setPrecioUnitario(BigDecimal.valueOf(Double.valueOf(rsd.getString("PRECIO_UNITARIO"))));
//                detallesList.add(detalle);
                detalles.getDetalle().add(detalle);
            }
            rsd.close();
            preparedStatement.close();
//            detalles.setDetalle(detallesList);
            factura.setDetalles(detalles);

//            System.out.println("factura: " + factura);
            facturas.add(factura);
        }
        rs.close();
        preparedStatemenT.close();
        iterator = facturas.iterator();
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
