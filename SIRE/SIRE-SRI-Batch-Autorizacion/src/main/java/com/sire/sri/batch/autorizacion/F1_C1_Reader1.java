package com.sire.sri.batch.autorizacion;

import ec.gob.sri.comprobantes.modelo.InfoTributaria;
import ec.gob.sri.comprobantes.modelo.LoteXml;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import com.sire.service.DatasourceService;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Named
public class F1_C1_Reader1 extends AbstractItemReader {

    @Inject
    private JobContext jobCtx;
    private List lotes;
    private Iterator iterator;
    private Connection connection;

    @Override
    public Object readItem() throws Exception {
//        System.out.print("F1_C1_Reader1");
        if (iterator.hasNext()) {
            LoteXml lote = (LoteXml) iterator.next();
            return lote;
        }
        return null;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        lotes = new ArrayList();

        String loteSQL = "SELECT COD_EMPRESA, SECUENCIAL, COD_DOCUMENTO, "
                + "CLAVE_ACCESO, ESTADO_SRI, FECHA_ESTADO "
                + "FROM CEL_LOTE_AUTORIZADO WHERE ESTADO_SRI = 'RECIBIDA'";
        PreparedStatement preparedStatemenT = getConnection().prepareStatement(loteSQL);
        ResultSet loteRs = preparedStatemenT.executeQuery();
        while (loteRs.next()) {
            List facturas = new ArrayList();
            LoteXml lote = new LoteXml();
            lote.setClaveAcceso(loteRs.getString("CLAVE_ACCESO"));

            String facturaSQL = "SELECT COD_EMPRESA, RUC_EMPRESA, RAZON_SOCIAL_EMPRESA, "
                    + "NOMBRE_COMERCIAL, COD_DOCUMENTO, NUM_FACTURA_INTERNO, ESTABLECIMIENTO, "
                    + "PUNTO_EMISION, SECUENCIAL, DIRECCION_MATRIZ, DIRECCION_ESTABLECIMIENTO, "
                    + "CONTRIBUYENTE_ESPECIAL, LLEVA_CONTABILIDAD, RAZON_SOCIAL_COMPRADOR, "
                    + "FECHA_FACTURA, TIPO_IDENTIFICACION_COMPRADOR, IDENTIFICACION_COMPRADOR, "
                    + "DIRECCION_COMPRADOR, TELEFONO_COMPRADOR, EMAIL_COMPRADOR, "
                    + "TOTAL_SIN_IMPUESTOS, TOTAL_DESCUENTOS, PROPINA, IMPORTE_TOTAL, "
                    + "CLAVE_ACCESO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, BASE_IMPONIBLE, "
                    + "VALOR, MONEDA FROM V_FACTURA_ELECTRONICA_C WHERE "
                    + "CLAVE_ACCESO_LOTE = '" + lote.getClaveAcceso() + "' AND "
                    + "(ESTADO_SRI='RECIBIDA' OR ESTADO_SRI='EN PROCESAMIENTO') "
                    + "AND ROWNUM <= 40 ORDER BY FECHA_FACTURA";
            PreparedStatement facturaPreparedStatement = getConnection().prepareStatement(facturaSQL);
            ResultSet rs = facturaPreparedStatement.executeQuery();
            while (rs.next()) {
                String numFacturaInterno = rs.getString("NUM_FACTURA_INTERNO");

                Factura factura = new Factura();

                InfoAdicional infoAdicional = new InfoAdicional();
                CampoAdicional direccion = new CampoAdicional();
                direccion.setValue(rs.getString("DIRECCION_COMPRADOR"));
                direccion.setNombre("Direccion");
                CampoAdicional telefono = new CampoAdicional();
                telefono.setValue(rs.getString("TELEFONO_COMPRADOR"));
                telefono.setNombre("Telefono");
                CampoAdicional email = new CampoAdicional();
                email.setValue(rs.getString("EMAIL_COMPRADOR"));
                email.setNombre("Email");
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
                infoFactura.setImporteTotal(rs.getBigDecimal("IMPORTE_TOTAL"));
                infoFactura.setMoneda(rs.getString("MONEDA"));
                infoFactura.setObligadoContabilidad(rs.getString("LLEVA_CONTABILIDAD"));
                infoFactura.setPropina(rs.getBigDecimal("PROPINA"));
                infoFactura.setRazonSocialComprador(rs.getString("RAZON_SOCIAL_COMPRADOR"));
                infoFactura.setTipoIdentificacionComprador(rs.getString("TIPO_IDENTIFICACION_COMPRADOR"));
                TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
                TotalImpuesto totalImpuesto = new TotalImpuesto();
                totalImpuesto.setBaseImponible(rs.getBigDecimal("BASE_IMPONIBLE"));
                totalImpuesto.setCodigo(rs.getString("CODIGO_IMPUESTO"));
                totalImpuesto.setCodigoPorcentaje(rs.getString("CODIGO_PORCENTAJE"));
                totalImpuesto.setValor(rs.getBigDecimal("VALOR"));
                totalConImpuestos.getTotalImpuesto().add(totalImpuesto);
                infoFactura.setTotalConImpuestos(totalConImpuestos);
                infoFactura.setTotalDescuento(rs.getBigDecimal("TOTAL_DESCUENTOS"));
                infoFactura.setTotalSinImpuestos(rs.getBigDecimal("TOTAL_SIN_IMPUESTOS"));

                String pagosSQL = "SELECT CODIGO, FORMA_PAGO, PLAZO, TIEMPO, "
                        + "VALOR_FORMA_PAGO FROM V_FACTURA_ELECTRONICA_PAGO WHERE "
                        + "NUM_FACTURA = " + rs.getString("NUM_FACTURA_INTERNO");
                PreparedStatement pagosPreparedStatement = getConnection().prepareStatement(pagosSQL);
                ResultSet prs = pagosPreparedStatement.executeQuery();
                InfoFactura.Pago pagos = new InfoFactura.Pago();
                while (prs.next()) {
                    InfoFactura.Pago.DetallePago detallePago = new InfoFactura.Pago.DetallePago();
                    detallePago.setFormaPago(prs.getString("CODIGO"));
                    detallePago.setPlazo(prs.getString("PLAZO"));
                    detallePago.setTotal(prs.getBigDecimal("VALOR_FORMA_PAGO"));
                    detallePago.setUnidadTiempo(prs.getString("TIEMPO"));
                    pagos.getPagos().add(detallePago);
                }
                infoFactura.setPagos(pagos);
                prs.close();
                pagosPreparedStatement.close();

                factura.setInfoFactura(infoFactura);

                InfoTributaria infoTributaria = new InfoTributaria();
                infoTributaria.setClaveAcceso(rs.getString("CLAVE_ACCESO"));
                infoTributaria.setAmbiente(rs.getString("CLAVE_ACCESO").substring(23, 24));
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

                String detalleSQL = "SELECT COD_EMPRESA, COD_DOCUMENTO, NUM_DOCUMENTO_INTERNO, "
                        + "COD_ARTICULO, NOMBRE_ARTICULO, CANTIDAD, PRECIO_UNITARIO, "
                        + "DESCUENTO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, TARIFA, "
                        + "BASE_IMPONIBLE, VALOR, PRECIO_TOTAL_SIN_IMPUESTOS "
                        + "FROM V_FACTURA_ELECTRONICA_D WHERE "
                        + "NUM_DOCUMENTO_INTERNO = " + numFacturaInterno;
                PreparedStatement datallePreparedStatement = getConnection().prepareStatement(detalleSQL);
                ResultSet rsd = datallePreparedStatement.executeQuery();
                while (rsd.next()) {
                    Detalle detalle = new Detalle();
                    detalle.setCantidad(BigDecimal.valueOf(Double.valueOf(rsd.getString("CANTIDAD"))));
                    detalle.setCodigoPrincipal(rsd.getString("COD_ARTICULO"));
                    detalle.setDescripcion(rsd.getString("NOMBRE_ARTICULO"));
                    detalle.setDescuento(BigDecimal.valueOf(Double.valueOf(rsd.getString("DESCUENTO"))));
                    Impuestos impuestos = new Impuestos();
                    Impuesto impuesto = new Impuesto();
                    impuesto.setBaseImponible(BigDecimal.valueOf(Double.valueOf(rsd.getString("BASE_IMPONIBLE"))));
                    impuesto.setCodigo(rsd.getString("CODIGO_IMPUESTO"));
                    impuesto.setCodigoPorcentaje(rsd.getString("CODIGO_PORCENTAJE"));
                    impuesto.setTarifa(BigDecimal.valueOf(Double.valueOf(rsd.getString("TARIFA"))));
                    impuesto.setValor(BigDecimal.valueOf(Double.valueOf(rsd.getString("VALOR"))));
                    impuestos.getImpuesto().add(impuesto);
                    detalle.setImpuestos(impuestos);
                    detalle.setPrecioTotalSinImpuesto(BigDecimal.valueOf(Double.valueOf(rsd.getString("PRECIO_TOTAL_SIN_IMPUESTOS"))));
                    detalle.setPrecioUnitario(BigDecimal.valueOf(Double.valueOf(rsd.getString("PRECIO_UNITARIO"))));
                    detalles.getDetalle().add(detalle);
                }
                factura.setDetalles(detalles);

//                System.out.println("factura: " + factura);
                facturas.add(factura);
                rsd.close();
                datallePreparedStatement.close();
            }
            lote.setFacturas(facturas);
//            System.out.println("lote: " + lote.getClaveAcceso() + " #facturas: " + facturas.size());
            lotes.add(lote);
            rs.close();
            facturaPreparedStatement.close();
        }
        iterator = lotes.iterator();
        loteRs.close();
        preparedStatemenT.close();
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
