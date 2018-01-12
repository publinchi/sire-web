package com.sire.sri.batch.recepcion;

import com.sire.service.IDatasourceService;
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
import ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Named
public class F1_C1_Reader1 extends AbstractItemReader {
    
    @Inject
    private JobContext jobCtx;
    private List comprobantes;
    private Iterator iterator;
    private Connection connection;
    
    @Override
    public Object readItem() throws Exception {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
    
    @Override
    public void open(Serializable checkpoint) throws Exception {
        Properties runtimeParams = BatchRuntime.getJobOperator().getParameters(jobCtx.getExecutionId());
        String comprobanteSQL = runtimeParams.getProperty("comprobanteSQL");
        String tipoComprobante = runtimeParams.getProperty("tipoComprobante");
        comprobantes = new ArrayList();
        
        System.out.println("tipoComprobante -> " + tipoComprobante);
        
        buildComprobantes(comprobanteSQL, tipoComprobante);
    }
    
    private Connection getConnection() throws SQLException, NamingException {
        if (connection == null || (connection != null && connection.isClosed())) {
            InitialContext ic = new InitialContext();
            IDatasourceService datasourceService = (IDatasourceService) ic.lookup("java:global/SIRE-Batch/DatasourceService!com.sire.service.IDatasourceService");
            connection = datasourceService.getConnection();
        }
        return connection;
    }
    
    private void buildComprobantes(String comprobanteSQL, String tipoComprobante) throws SQLException, NamingException {
        System.out.println("-> buildComprobantes");
        PreparedStatement comprobantePreparedStatement = getConnection().prepareStatement(comprobanteSQL);
        ResultSet rs = comprobantePreparedStatement.executeQuery();
        while (rs.next()) {
            if (tipoComprobante.equals("01")) {
                _buildFacturas(rs, comprobantes);
            } else if (tipoComprobante.equals("07")) {
                _buildRetenciones(rs, comprobantes);
            }
        }
        iterator = comprobantes.iterator();
        rs.close();
        comprobantePreparedStatement.close();
    }
    
    private void _buildFacturas(ResultSet rs, List comprobantes) throws SQLException, NamingException {
        System.out.println("-> _buildFacturas");
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
        CampoAdicional observacion = new CampoAdicional();
        observacion.setValue(rs.getString("OBSERVACION"));
        observacion.setNombre("Observacion");
        if (direccion.getValue() != null && !direccion.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(direccion);
        }
        if (telefono.getValue() != null && !telefono.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(telefono);
        }
        if (email.getValue() != null && !email.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(email);
        }
        if (observacion.getValue() != null && !observacion.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(observacion);
        }
        factura.setInfoAdicional(infoAdicional);
        
        InfoFactura infoFactura = new InfoFactura();
        infoFactura.setDireccionComprador(rs.getString("DIRECCION_COMPRADOR"));
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
                + "NUM_FACTURA = " + numFacturaInterno;
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
        
        String detalleSQL = "SELECT COD_EMPRESA, COD_DOCUMENTO, NUM_DOCUMENTO_INTERNO, "
                + "COD_ARTICULO, NOMBRE_ARTICULO, CANTIDAD, PRECIO_UNITARIO, "
                + "DESCUENTO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, TARIFA, "
                + "BASE_IMPONIBLE, VALOR, PRECIO_TOTAL_SIN_IMPUESTOS "
                + "FROM V_FACTURA_ELECTRONICA_D WHERE "
                + "NUM_DOCUMENTO_INTERNO = " + numFacturaInterno;
        PreparedStatement detallePreparedStatement = getConnection().prepareStatement(detalleSQL);
        ResultSet rsd = detallePreparedStatement.executeQuery();
        while (rsd.next()) {
            Detalle detalle = new Detalle();
            detalle.setCantidad(rsd.getBigDecimal("CANTIDAD"));
            detalle.setCodigoPrincipal(rsd.getString("COD_ARTICULO"));
            detalle.setDescripcion(rsd.getString("NOMBRE_ARTICULO"));
            detalle.setDescuento(rsd.getBigDecimal("DESCUENTO"));
            Impuestos impuestos = new Impuestos();
            Impuesto impuesto = new Impuesto();
            impuesto.setBaseImponible(rsd.getBigDecimal("BASE_IMPONIBLE"));
            impuesto.setCodigo(rsd.getString("CODIGO_IMPUESTO"));
            impuesto.setCodigoPorcentaje(rsd.getString("CODIGO_PORCENTAJE"));
            impuesto.setTarifa(rsd.getBigDecimal("TARIFA"));
            impuesto.setValor(rsd.getBigDecimal("VALOR"));
            impuestos.getImpuesto().add(impuesto);
            detalle.setImpuestos(impuestos);
            detalle.setPrecioTotalSinImpuesto(rsd.getBigDecimal("PRECIO_TOTAL_SIN_IMPUESTOS"));
            detalle.setPrecioUnitario(rsd.getBigDecimal("PRECIO_UNITARIO"));
            detalles.getDetalle().add(detalle);
        }
        factura.setDetalles(detalles);
        
        comprobantes.add(factura);
        rsd.close();
        detallePreparedStatement.close();
    }
    
    private void _buildRetenciones(ResultSet rs, List comprobantes) throws SQLException, NamingException {
        System.out.println("-> _buildFacturas");
        String numRetencionInterno = rs.getString("NUM_RETENCION_INTERNO");
        
        ComprobanteRetencion comprobanteRetencion = new ComprobanteRetencion();
        comprobanteRetencion.setId("comprobante");
        ComprobanteRetencion.Impuestos impuestos = new ComprobanteRetencion.Impuestos();
        
        String detalleSQL = "SELECT CODIGO, CODIGORETENCION, BASEIMPONIBLE, "
                + "PORCENTAJERETENR, VALORRETENIDO, CODDOCSUSTENTO, NUMDOCSUSTENTO, "
                + "FECHAEMISIONDOCSUSTENTO FROM V_RETENCION_ELECTRONICA_D WHERE "
                + "NUM_RETENCION_INTERNO = " + numRetencionInterno;
        PreparedStatement detallePreparedStatement = getConnection().prepareStatement(detalleSQL);
        ResultSet rsd = detallePreparedStatement.executeQuery();
        while (rsd.next()) {
            ec.gob.sri.comprobantes.modelo.rentencion.Impuesto impuesto = new ec.gob.sri.comprobantes.modelo.rentencion.Impuesto();
            impuesto.setBaseImponible(rsd.getBigDecimal("BASEIMPONIBLE").setScale(2));
            impuesto.setCodDocSustento(rsd.getString("CODDOCSUSTENTO"));
            impuesto.setCodigo(rsd.getString("CODIGO"));
            impuesto.setCodigoRetencion(rsd.getString("CODIGORETENCION"));
            String oldDate = rsd.getString("FECHAEMISIONDOCSUSTENTO");
            LocalDateTime datetime = LocalDateTime.parse(oldDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
            String newDate = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            impuesto.setFechaEmisionDocSustento(newDate);
            impuesto.setNumDocSustento(rsd.getString("NUMDOCSUSTENTO"));
            impuesto.setPorcentajeRetener(rsd.getBigDecimal("PORCENTAJERETENR"));
            impuesto.setValorRetenido(rsd.getBigDecimal("VALORRETENIDO").setScale(2));
            impuestos.getImpuesto().add(impuesto);
        }
        comprobanteRetencion.setImpuestos(impuestos);
        
        ComprobanteRetencion.InfoAdicional infoAdicional = new ComprobanteRetencion.InfoAdicional();
        ComprobanteRetencion.InfoAdicional.CampoAdicional direccion = new ComprobanteRetencion.InfoAdicional.CampoAdicional();
        direccion.setNombre("Direccion");
        direccion.setValue(rs.getString("DIRECCION_RETENIDO")); //PENDIENTE
        ComprobanteRetencion.InfoAdicional.CampoAdicional telefono = new ComprobanteRetencion.InfoAdicional.CampoAdicional();
        telefono.setValue(rs.getString("TELEFONO_RETENIDO")); //PENDIENTE
        telefono.setNombre("Telefono");
        ComprobanteRetencion.InfoAdicional.CampoAdicional email = new ComprobanteRetencion.InfoAdicional.CampoAdicional();
        email.setValue(rs.getString("EMAIL_RETENIDO"));//PENDIENTE
        email.setNombre("Email");
        if (direccion.getValue() != null && !direccion.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(direccion);
        }
        if (telefono.getValue() != null && !telefono.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(telefono);
        }
        if (email.getValue() != null && !email.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(email);
        }
        comprobanteRetencion.setInfoAdicional(infoAdicional);
        
        ComprobanteRetencion.InfoCompRetencion infoCompRetencion = new ComprobanteRetencion.InfoCompRetencion();
        infoCompRetencion.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
        infoCompRetencion.setDirEstablecimiento(rs.getString("DIRECCION_ESTABLECIMIENTO"));
        String oldDate = rs.getString("FECHA_RETENCION");
        LocalDateTime datetime = LocalDateTime.parse(oldDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String newDate = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        infoCompRetencion.setFechaEmision(newDate);
        infoCompRetencion.setIdentificacionSujetoRetenido(rs.getString("IDENTIFICACION_SUJETO_RETENIDO"));
        infoCompRetencion.setObligadoContabilidad(rs.getString("LLEVA_CONTABILIDAD"));
        infoCompRetencion.setPeriodoFiscal(rs.getString("PERIODO_FISCAL"));
        infoCompRetencion.setRazonSocialSujetoRetenido(rs.getString("RAZON_SOCIAL_SUJETO_RETENIDO"));
        infoCompRetencion.setTipoIdentificacionSujetoRetenido(rs.getString("TIPO_IDENT_SUJETO_RETENIDO"));
        comprobanteRetencion.setInfoCompRetencion(infoCompRetencion);
        
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
        comprobanteRetencion.setInfoTributaria(infoTributaria);
        comprobanteRetencion.setVersion("1.0.0");
        
        comprobantes.add(comprobanteRetencion);
        rsd.close();
        detallePreparedStatement.close();
    }
}
