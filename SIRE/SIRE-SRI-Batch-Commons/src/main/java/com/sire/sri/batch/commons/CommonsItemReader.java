/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.batch.commons;

import ec.gob.sri.comprobantes.modelo.guia.Destinatario;
import ec.gob.sri.comprobantes.modelo.guia.GuiaRemision;
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito;
import ec.gob.sri.comprobantes.modelo.notadebito.NotaDebito;
import ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import javax.batch.api.chunk.AbstractItemReader;
import javax.naming.NamingException;
import com.sire.service.IDatasourceService;
import com.sire.sri.batch.constant.Constant;
import javax.naming.InitialContext;

/**
 *
 * @author pestupinan
 */
public abstract class CommonsItemReader extends AbstractItemReader {

    protected Connection connection;
    protected Logger log = Logger.getLogger(this.getClass().getName());
    protected String codEmpresa;

    protected void _buildFacturas(ResultSet rs, List comprobantes) throws SQLException, NamingException {
        log.info("-> _buildFacturas");
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

        TotalImpuesto totalImpuesto1 = new TotalImpuesto();
        totalImpuesto1.setBaseImponible(rs.getBigDecimal("BASE_IMPONIBLE"));
        totalImpuesto1.setCodigo(rs.getString("CODIGO_IMPUESTO"));
        totalImpuesto1.setCodigoPorcentaje(rs.getString("CODIGO_PORCENTAJE"));
        totalImpuesto1.setValor(rs.getBigDecimal("VALOR"));
        totalConImpuestos.getTotalImpuesto().add(totalImpuesto1);

        TotalImpuesto totalImpuesto2 = new TotalImpuesto();
        totalImpuesto2.setBaseImponible(rs.getBigDecimal("BASE_IMPONIBLE_SIN_IVA"));
        totalImpuesto2.setCodigo(rs.getString("CODIGO_IMPUESTO_SIN_IVA"));
        totalImpuesto2.setCodigoPorcentaje(rs.getString("CODIGO_PORCENTAJE_SIN_IVA"));
        totalImpuesto2.setTarifa(rs.getBigDecimal("TARIFA_IVA_SIN_IVA"));
        totalImpuesto2.setValor(rs.getBigDecimal("VALOR_IVA_SIN_IVA"));
        totalConImpuestos.getTotalImpuesto().add(totalImpuesto2);

        infoFactura.setTotalConImpuestos(totalConImpuestos);
        infoFactura.setTotalDescuento(rs.getBigDecimal("TOTAL_DESCUENTOS"));
        infoFactura.setTotalSinImpuestos(rs.getBigDecimal("TOTAL_SIN_IMPUESTOS"));

        String pagosSQL = Constant.FACTURA_PAGO_SQL
                + "NUM_FACTURA = " + numFacturaInterno;
        try (PreparedStatement pagosPreparedStatement = getConnection().prepareStatement(pagosSQL);
                ResultSet prs = pagosPreparedStatement.executeQuery()) {
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
        }

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

        String detalleSQL = Constant.FACTURA_D_SQL
                + "NUM_DOCUMENTO_INTERNO = " + numFacturaInterno;
        try (PreparedStatement detallePreparedStatement = getConnection().prepareStatement(detalleSQL);
                ResultSet rsd = detallePreparedStatement.executeQuery()) {
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
    }

    protected void _buildNotasCredito(ResultSet rs, List comprobantes) throws SQLException, NamingException {
        log.info("-> _buildNotasCredito");

        String numNotaCreditoInterno = rs.getString("NUM_FACTURA_INTERNO");

        NotaCredito notaCredito = new NotaCredito();
        NotaCredito.InfoAdicional infoAdicional = new NotaCredito.InfoAdicional();
        NotaCredito.InfoAdicional.CampoAdicional direccion = new NotaCredito.InfoAdicional.CampoAdicional();
        direccion.setValue(rs.getString("DIRECCION_COMPRADOR"));
        direccion.setNombre("Direccion");
        NotaCredito.InfoAdicional.CampoAdicional telefono = new NotaCredito.InfoAdicional.CampoAdicional();
        telefono.setValue(rs.getString("TELEFONO_COMPRADOR"));
        telefono.setNombre("Telefono");
        NotaCredito.InfoAdicional.CampoAdicional email = new NotaCredito.InfoAdicional.CampoAdicional();
        email.setValue(rs.getString("EMAIL_COMPRADOR"));
        email.setNombre("Email");
        NotaCredito.InfoAdicional.CampoAdicional observacion = new NotaCredito.InfoAdicional.CampoAdicional();
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

        NotaCredito.InfoNotaCredito infoNotaCredito = new NotaCredito.InfoNotaCredito();

        String oldDate = rs.getString("FECHA_EMISION");
        LocalDateTime datetime = LocalDateTime.parse(oldDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String newDate = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        infoNotaCredito.setFechaEmision(newDate);

        infoNotaCredito.setDirEstablecimiento(rs.getString("DIRECCION_ESTABLECIMIENTO"));
        infoNotaCredito.setTipoIdentificacionComprador(rs.getString("TIPO_IDENTIFICACION_COMPRADOR"));
        infoNotaCredito.setRazonSocialComprador(rs.getString("RAZON_SOCIAL_COMPRADOR"));
        infoNotaCredito.setIdentificacionComprador(rs.getString("IDENTIFICACION_COMPRADOR"));
        infoNotaCredito.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
        infoNotaCredito.setObligadoContabilidad(rs.getString("LLEVA_CONTABILIDAD"));
        infoNotaCredito.setRise(rs.getString("RISE"));
        infoNotaCredito.setCodDocModificado(rs.getString("COD_DOC_MODIFICADO"));
        infoNotaCredito.setNumDocModificado(rs.getString("NUM_DOC_MODIFICADO"));
        infoNotaCredito.setFechaEmisionDocSustento(rs.getString("FECHA_EMISION_DOCSUSTENTO"));
        infoNotaCredito.setTotalSinImpuestos(rs.getBigDecimal("TOTAL_SIN_IMPUESTOS"));
        infoNotaCredito.setValorModificacion(rs.getBigDecimal("VALOR_MODIFICADO"));
        infoNotaCredito.setMoneda(rs.getString("MONEDA"));

        ec.gob.sri.comprobantes.modelo.notacredito.TotalConImpuestos totalConImpuestos = new ec.gob.sri.comprobantes.modelo.notacredito.TotalConImpuestos();
        ec.gob.sri.comprobantes.modelo.notacredito.TotalConImpuestos.TotalImpuesto totalImpuesto = new ec.gob.sri.comprobantes.modelo.notacredito.TotalConImpuestos.TotalImpuesto();
        totalImpuesto.setBaseImponible(rs.getBigDecimal("BASE_IMPONIBLE"));
        totalImpuesto.setCodigo(rs.getString("CODIGO_IMPUESTO"));
        totalImpuesto.setCodigoPorcentaje(rs.getString("CODIGO_PORCENTAJE"));
        totalImpuesto.setValor(rs.getBigDecimal("VALOR"));
        totalConImpuestos.getTotalImpuesto().add(totalImpuesto);
        infoNotaCredito.setTotalConImpuestos(totalConImpuestos);

        infoNotaCredito.setMotivo(rs.getString("MOTIVO"));

        notaCredito.setInfoNotaCredito(infoNotaCredito);

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
        notaCredito.setInfoTributaria(infoTributaria);

        notaCredito.setId("comprobante");
        notaCredito.setVersion("1.1.0");

        NotaCredito.Detalles detalles = new NotaCredito.Detalles();

        String detalleSQL = Constant.NOTA_CREDITO_D_SQL + "NUM_DOCUMENTO_INTERNO = " + numNotaCreditoInterno;
        try (PreparedStatement detallePreparedStatement = getConnection().prepareStatement(detalleSQL);
                ResultSet rsd = detallePreparedStatement.executeQuery()) {
            while (rsd.next()) {
                NotaCredito.Detalles.Detalle detalle = new NotaCredito.Detalles.Detalle();
                detalle.setCodigoInterno(rsd.getString("COD_ARTICULO"));
                detalle.setDescripcion(rsd.getString("NOMBRE_ARTICULO"));
                detalle.setCantidad(rsd.getBigDecimal("CANTIDAD"));
                detalle.setPrecioUnitario(rsd.getBigDecimal("PRECIO_UNITARIO"));
                detalle.setDescuento(rsd.getBigDecimal("DESCUENTO"));
                detalle.setPrecioTotalSinImpuesto(rsd.getBigDecimal("PRECIO_TOTAL_SIN_IMPUESTOS"));

                NotaCredito.Detalles.Detalle.Impuestos impuestos = new NotaCredito.Detalles.Detalle.Impuestos();
                ec.gob.sri.comprobantes.modelo.notacredito.Impuesto impuesto = new ec.gob.sri.comprobantes.modelo.notacredito.Impuesto();
                impuesto.setCodigo(rsd.getString("CODIGO_IMPUESTO"));
                impuesto.setCodigoPorcentaje(rsd.getString("CODIGO_PORCENTAJE"));
                impuesto.setTarifa(rsd.getBigDecimal("TARIFA"));
                impuesto.setBaseImponible(rsd.getBigDecimal("BASE_IMPONIBLE"));
                impuesto.setValor(rsd.getBigDecimal("VALOR"));

                impuestos.getImpuesto().add(impuesto);
                detalle.setImpuestos(impuestos);
                detalles.getDetalle().add(detalle);
            }
            notaCredito.setDetalles(detalles);
            notaCredito.setInfoAdicional(infoAdicional);

            comprobantes.add(notaCredito);
            rsd.close();
            detallePreparedStatement.close();
        }
    }

    protected void _buildNotasDebito(ResultSet rs, List comprobantes) throws SQLException, NamingException {
        log.info("-> _buildNotasDebito");

        String numDocumentoInterno = rs.getString("NUM_DOCUMENTO_INTERNO");

        /* Nota de Débito */
        NotaDebito notaDebito = new NotaDebito();
        notaDebito.setId("comprobante");
        notaDebito.setVersion("1.0.0");

        /* Información Tributaria */
        InfoTributaria infoTributaria = new InfoTributaria();
        infoTributaria.setTipoEmision("1");
        infoTributaria.setAmbiente(infoTributaria.getClaveAcceso().substring(23, 24));
        infoTributaria.setRazonSocial(rs.getString("RAZON_SOCIAL_EMPRESA"));
        infoTributaria.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
        infoTributaria.setRuc(rs.getString("RUC_EMPRESA"));
        infoTributaria.setClaveAcceso(rs.getString("CLAVE_ACCESO"));
        infoTributaria.setCodDoc(rs.getString("COD_DOCUMENTO"));
        infoTributaria.setEstab(rs.getString("ESTABLECIMIENTO"));
        infoTributaria.setPtoEmi(rs.getString("PUNTO_EMISION"));
        infoTributaria.setSecuencial(rs.getString("SECUENCIAL"));
        infoTributaria.setDirMatriz(rs.getString("DIRECCION_MATRIZ"));
        notaDebito.setInfoTributaria(infoTributaria);

        /* Información Nota de Débito */
        NotaDebito.InfoNotaDebito infoNotaDebito = new NotaDebito.InfoNotaDebito();
        infoNotaDebito.setFechaEmision(rs.getString("FECHA_EMISION"));
        infoNotaDebito.setDirEstablecimiento(rs.getString("DIRECCION_ESTABLECIMIENTO"));
        infoNotaDebito.setTipoIdentificacionComprador(rs.getString("TIPO_IDENTIFICACION_COMPRADOR"));
        infoNotaDebito.setRazonSocialComprador(rs.getString("RAZON_SOCIAL_COMPRADOR"));
        infoNotaDebito.setIdentificacionComprador(rs.getString("IDENTIFICACION_COMPRADOR"));
        infoNotaDebito.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
        infoNotaDebito.setObligadoContabilidad(rs.getString("LLEVA_CONTABILIDAD"));
        infoNotaDebito.setCodDocModificado(rs.getString("COD_DOC_MODIFICADO"));
        infoNotaDebito.setNumDocModificado(rs.getString("NUM_DOC_MODIFICADO"));
        infoNotaDebito.setFechaEmisionDocSustento(rs.getString("FECHA_EMISION_DOCSUSTENTO"));
        infoNotaDebito.setTotalSinImpuestos(rs.getBigDecimal("TOTAL_SIN_IMPUESTOS"));

        //Impuestos
        NotaDebito.InfoNotaDebito.Impuestos impuestos = new NotaDebito.InfoNotaDebito.Impuestos();
        ec.gob.sri.comprobantes.modelo.notadebito.Impuesto impuesto;
        impuesto = new ec.gob.sri.comprobantes.modelo.notadebito.Impuesto();
        impuesto.setCodigo(rs.getString("CODIGO_IMPUESTO"));
        impuesto.setCodigoPorcentaje(rs.getString("CODIGO_PORCENTAJE"));
        impuesto.setTarifa(rs.getBigDecimal("TARIFA"));
        impuesto.setBaseImponible(rs.getBigDecimal("BASE_IMPONIBLE"));
        impuesto.setValor(rs.getBigDecimal("VALOR"));
        impuestos.getImpuesto().add(impuesto);
        infoNotaDebito.setImpuestos(impuestos);
        infoNotaDebito.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));

        String pagoSQL = Constant.NOTA_DEBITO_PAGO_SQL + "NUM_DOCUMENTO_INTERNO = " + numDocumentoInterno;

        //Pagos
        NotaDebito.InfoNotaDebito.Pago pagos = new NotaDebito.InfoNotaDebito.Pago();
        try (PreparedStatement pagoPreparedStatement = getConnection().prepareStatement(pagoSQL);
                ResultSet rsp = pagoPreparedStatement.executeQuery()) {
            while (rsp.next()) {
                NotaDebito.InfoNotaDebito.Pago.DetallePago detallePago = new NotaDebito.InfoNotaDebito.Pago.DetallePago();
                detallePago.setFormaPago(rsp.getString("FORMA_PAGO"));
                detallePago.setTotal(rsp.getBigDecimal("VALOR_FORMA_PAGO"));
                detallePago.setPlazo(rsp.getString("PLAZO"));
                detallePago.setUnidadTiempo(rsp.getString("TIEMPO"));
                pagos.getPagos().add(detallePago);
            }
            infoNotaDebito.setPagos(pagos);
            notaDebito.setInfoNotaDebito(infoNotaDebito);

            rsp.close();
            pagoPreparedStatement.close();
        }

        /* Motivos */
        NotaDebito.Motivos motivos = new NotaDebito.Motivos();
        NotaDebito.Motivos.Motivo motivo = new NotaDebito.Motivos.Motivo();
        motivo.setRazon(rs.getString("RAZON"));
        motivo.setValor(rs.getBigDecimal("VALOR"));
        notaDebito.setMotivos(motivos);

        /* Información Adicional */
        NotaDebito.InfoAdicional infoAdicional = new NotaDebito.InfoAdicional();
        NotaDebito.InfoAdicional.CampoAdicional direccion = new NotaDebito.InfoAdicional.CampoAdicional();
        direccion.setValue(rs.getString("DIRECCION"));
        direccion.setNombre("Dirección");

        NotaDebito.InfoAdicional.CampoAdicional email = new NotaDebito.InfoAdicional.CampoAdicional();
        email.setValue(rs.getString("EMAIL"));
        email.setNombre("Email");

        NotaDebito.InfoAdicional.CampoAdicional telefono = new NotaDebito.InfoAdicional.CampoAdicional();
        telefono.setValue(rs.getString("TELEFONO"));
        telefono.setNombre("Teléfono");

        if (direccion.getValue() != null && !direccion.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(direccion);
        }

        if (email.getValue() != null && !email.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(email);
        }

        if (telefono.getValue() != null && !telefono.getValue().isEmpty()) {
            infoAdicional.getCampoAdicional().add(telefono);
        }

        notaDebito.setInfoAdicional(infoAdicional);
        comprobantes.add(notaDebito);

    }

    protected void _buildGuiasRemision(ResultSet rs, List comprobantes) throws SQLException, NamingException {
        log.info("-> _buildGuiasRemision");

        String numDespachoInterno = rs.getString("NUM_DESPACHO_INTERNO");
        GuiaRemision guiaRemision = new GuiaRemision();
        guiaRemision.setId("comprobante");
        guiaRemision.setVersion("1.1.0");

        /* Información Tributaria */
        InfoTributaria infoTributaria = new InfoTributaria();
        infoTributaria.setClaveAcceso(rs.getString("CLAVE_ACCESO"));
        infoTributaria.setAmbiente(infoTributaria.getClaveAcceso().substring(23, 24));
        infoTributaria.setTipoEmision("1");
        infoTributaria.setRazonSocial(rs.getString("RAZON_SOCIAL_EMPRESA"));
        infoTributaria.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
        infoTributaria.setRuc(rs.getString("RUC_EMPRESA"));
        infoTributaria.setCodDoc(rs.getString("COD_DOCUMENTO"));
        infoTributaria.setEstab(rs.getString("ESTABLECIMIENTO"));
        infoTributaria.setPtoEmi(rs.getString("PUNTO_EMISION"));
        infoTributaria.setSecuencial(rs.getString("SECUENCIAL"));
        infoTributaria.setDirMatriz(rs.getString("DIRECCION_MATRIZ"));
        guiaRemision.setInfoTributaria(infoTributaria);

        /* Información Guia de Remisión */
        GuiaRemision.InfoGuiaRemision infoGuiaRemision = new GuiaRemision.InfoGuiaRemision();
        infoGuiaRemision.setDirEstablecimiento(rs.getString("DIRECCION_ESTABLECIMIENTO"));
        infoGuiaRemision.setDirPartida(rs.getString("DIR_PARTIDA"));
        infoGuiaRemision.setRazonSocialTransportista(rs.getString("RAZON_SOCIAL_TRANSPORTISTA"));
        infoGuiaRemision.setTipoIdentificacionTransportista(rs.getString("TIPO_IDENT_TRANSPORTISTA"));
        infoGuiaRemision.setRucTransportista(rs.getString("RUC_TRANSPORTISTA"));
        infoGuiaRemision.setRise(rs.getString("RISE"));
        infoGuiaRemision.setObligadoContabilidad(rs.getString("LLEVA_CONTABILIDAD"));
        infoGuiaRemision.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));

        String oldDate = rs.getString("FECHA_INICIO_TRANSPORTE");
        LocalDateTime datetime = LocalDateTime.parse(oldDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String newDate = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        infoGuiaRemision.setFechaIniTransporte(newDate);

        String oldDate1 = rs.getString("FECHA_FIN_TRANSPORTE");
        LocalDateTime datetime1 = LocalDateTime.parse(oldDate1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String newDate1 = datetime1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        infoGuiaRemision.setFechaFinTransporte(newDate1);

        infoGuiaRemision.setPlaca(rs.getString("PLACA"));
        guiaRemision.setInfoGuiaRemision(infoGuiaRemision);

        /* Destinatarios */
        GuiaRemision.Destinatarios destinatarios = new GuiaRemision.Destinatarios();

        /* Detalles */
        Destinatario.Detalles detalles = new Destinatario.Detalles();

        String articuloSQL = Constant.GUIA_REMISION_ARTICULO_SQL + "NUM_DESPACHO_INTERNO = " + numDespachoInterno;

        try (PreparedStatement articuloPreparedStatement = getConnection().prepareStatement(articuloSQL);
                ResultSet rsa = articuloPreparedStatement.executeQuery()) {
            while (rsa.next()) {
                ec.gob.sri.comprobantes.modelo.guia.Detalle detalle = new ec.gob.sri.comprobantes.modelo.guia.Detalle();
                detalle.setCodigoInterno(rsa.getString("CODIGOINTERNO"));
                detalle.setDescripcion(rsa.getString("DESCRIPCION"));
                detalle.setCantidad(rsa.getBigDecimal("CANTIDAD"));
                detalles.getDetalle().add(detalle);
            }

            rsa.close();
            articuloPreparedStatement.close();
        }

        String detalleSQL = Constant.GUIA_REMISION_D_SQL + "NUM_DESPACHO_INTERNO = " + numDespachoInterno;

        try (PreparedStatement detallePreparedStatement = getConnection().prepareStatement(detalleSQL);
                ResultSet rsd = detallePreparedStatement.executeQuery()) {
            while (rsd.next()) {

                Destinatario destinatario = new Destinatario();
                destinatario.setIdentificacionDestinatario(rsd.getString("IDENTIFICACION_DESTINATARIO"));
                destinatario.setDirDestinatario(rsd.getString("DIRDESTINATARIO"));
                destinatario.setMotivoTraslado(rsd.getString("MOTIVOTRASLADO"));
                destinatario.setDocAduaneroUnico(rsd.getString("DOCADUANEROUNICO"));
                destinatario.setCodEstabDestino(rsd.getString("COD_ESTAB_DESTINO"));
                destinatario.setRuta(rsd.getString("RUTA"));
                destinatario.setCodDocSustento(rsd.getString("CODDOSUSTENTO"));
                destinatario.setNumDocSustento(rsd.getString("NUMDOCSUSTENTO"));
                destinatario.setNumAutDocSustento(rsd.getString("NUMAUTDOCSUSTENTO"));
                String oldDate2 = rsd.getString("FECHAEMISIONDOCSUSTENTO");
                LocalDateTime datetime2 = LocalDateTime.parse(oldDate2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                String newDate2 = datetime2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                destinatario.setFechaEmisionDocSustento(newDate2);
                destinatario.setRazonSocialDestinatario(rsd.getString("RAZONSOCIALDESTINATARIO"));
                destinatario.setDetalles(detalles);

                destinatarios.getDestinatario().add(destinatario);

                /* Información Adicional */
                GuiaRemision.InfoAdicional infoAdicional = new GuiaRemision.InfoAdicional();

                GuiaRemision.InfoAdicional.CampoAdicional telefono = new GuiaRemision.InfoAdicional.CampoAdicional();
                telefono.setValue(rsd.getString("TELDESTINATARIO"));
                telefono.setNombre("TELEFONO");

                GuiaRemision.InfoAdicional.CampoAdicional email = new GuiaRemision.InfoAdicional.CampoAdicional();
                email.setValue(rsd.getString("MAILDESTINATARIO"));
                email.setNombre("EMAIL");

                GuiaRemision.InfoAdicional.CampoAdicional sucursal = new GuiaRemision.InfoAdicional.CampoAdicional();
                email.setValue(rsd.getString("DIRDESTINATARIO"));
                email.setNombre("DIRECCION");

                if (sucursal.getValue() != null && !sucursal.getValue().isEmpty()) {
                    infoAdicional.getCampoAdicional().add(sucursal);
                }

                if (email.getValue() != null && !email.getValue().isEmpty()) {
                    infoAdicional.getCampoAdicional().add(email);
                }

                if (telefono.getValue() != null && !telefono.getValue().isEmpty()) {
                    infoAdicional.getCampoAdicional().add(telefono);
                }

                guiaRemision.setInfoAdicional(infoAdicional);
            }

            guiaRemision.setDestinatarios(destinatarios);

            comprobantes.add(guiaRemision);
            rsd.close();
            detallePreparedStatement.close();
        }
    }

    protected void _buildRetenciones(ResultSet rs, List comprobantes) throws SQLException, NamingException {
        log.info("-> _buildRetenciones");
        String numRetencionInterno = rs.getString("NUM_RETENCION_INTERNO");

        ComprobanteRetencion comprobanteRetencion = new ComprobanteRetencion();
        comprobanteRetencion.setId("comprobante");
        ComprobanteRetencion.Impuestos impuestos = new ComprobanteRetencion.Impuestos();

        String detalleSQL = Constant.RETENCION_D_SQL + "NUM_RETENCION_INTERNO = ? AND COD_EMPRESA = ?";
        try (PreparedStatement detallePreparedStatement = getConnection().prepareStatement(detalleSQL)) {
            detallePreparedStatement.setString(1, numRetencionInterno);
            detallePreparedStatement.setString(2, codEmpresa);
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
            direccion.setValue(rs.getString("DIRECCION_RETENIDO"));
            ComprobanteRetencion.InfoAdicional.CampoAdicional telefono = new ComprobanteRetencion.InfoAdicional.CampoAdicional();
            telefono.setValue(rs.getString("TELEFONO_RETENIDO"));
            telefono.setNombre("Telefono");
            ComprobanteRetencion.InfoAdicional.CampoAdicional email = new ComprobanteRetencion.InfoAdicional.CampoAdicional();
            email.setValue(rs.getString("EMAIL_RETENIDO"));
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

    protected Connection getConnection() throws SQLException, NamingException {
        if (connection == null || (connection != null && connection.isClosed())) {
            InitialContext ic = new InitialContext();
            IDatasourceService datasourceService = (IDatasourceService) ic.lookup("java:global/SIRE-Batch/DatasourceService!com.sire.service.IDatasourceService");
            connection = datasourceService.getConnection();
        }
        return connection;
    }

    protected void validarTipoComprobante(String tipoComprobante, ResultSet rs, List comprobantes) throws SQLException, NamingException {
        while (rs.next()) {
            switch (tipoComprobante) {
                case "01":
                    _buildFacturas(rs, comprobantes);
                    break;
                case "04":
                    _buildNotasCredito(rs, comprobantes);
                    break;
                case "05":
                    _buildNotasDebito(rs, comprobantes);
                    break;
                case "06":
                    _buildGuiasRemision(rs, comprobantes);
                    break;
                case "07":
                    _buildRetenciones(rs, comprobantes);
                    break;
                default:
                    break;
            }
        }
    }
}
