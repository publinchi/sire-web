package com.sire.sri.batch.recepcion;

import com.sire.service.IDatasourceService;
import com.sire.sri.batch.recepcion.constant.RecepcionConstant;
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
import ec.gob.sri.comprobantes.modelo.notacredito.NotaCredito;
import ec.gob.sri.comprobantes.modelo.notadebito.*;
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
import java.util.logging.Logger;
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
	private Logger log = Logger.getLogger(F1_C1_Reader1.class.getName());

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
		String tipoComprobante = runtimeParams.getProperty("tipoComprobante");
		comprobantes = new ArrayList();

		log.info("tipoComprobante -> " + tipoComprobante);

		buildComprobantes(tipoComprobante);
	}

	private Connection getConnection() throws SQLException, NamingException {
		if (connection == null || (connection != null && connection.isClosed())) {
			InitialContext ic = new InitialContext();
			IDatasourceService datasourceService = (IDatasourceService) ic.lookup("java:global/SIRE-Batch/DatasourceService!com.sire.service.IDatasourceService");
			connection = datasourceService.getConnection();
		}
		return connection;
	}

	private void buildComprobantes(String tipoComprobante) throws SQLException, NamingException {
		log.info("-> buildComprobantes");
		String comprobanteSQL;

		switch (tipoComprobante) {
			case "01":
				comprobanteSQL = RecepcionConstant.FACTURA_SQL;
				break;
			case "04":
				comprobanteSQL = RecepcionConstant.NOTA_CREDITO_SQL;
				break;
			case "05":
				comprobanteSQL = RecepcionConstant.NOTA_DEBITO_SQL;
				break;
			case "06":
				comprobanteSQL = RecepcionConstant.GUIA_REMISION_SQL;
				break;
			case "07":
				comprobanteSQL = RecepcionConstant.RETENCION_SQL;
				break;
			default:
				throw new RuntimeException("No se ha encontrado ninguna sentencia sql "
						+ "asociada al tipo de comprobante " + tipoComprobante);
		}

		try (PreparedStatement comprobantePreparedStatement = getConnection().prepareStatement(comprobanteSQL);
				ResultSet rs = comprobantePreparedStatement.executeQuery()) {
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
			iterator = comprobantes.iterator();
			rs.close();
			comprobantePreparedStatement.close();
		}
	}

	private void _buildFacturas(ResultSet rs, List comprobantes) throws SQLException, NamingException {
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

		String detalleSQL = "SELECT COD_EMPRESA, COD_DOCUMENTO, NUM_DOCUMENTO_INTERNO, "
				+ "COD_ARTICULO, NOMBRE_ARTICULO, CANTIDAD, PRECIO_UNITARIO, "
				+ "DESCUENTO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, TARIFA, "
				+ "BASE_IMPONIBLE, VALOR, PRECIO_TOTAL_SIN_IMPUESTOS "
				+ "FROM V_FACTURA_ELECTRONICA_D WHERE "
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

	private void _buildNotasCredito(ResultSet rs, List comprobantes) throws SQLException {
		log.info("-> _buildNotasCredito");
		String numFacturaInterno = rs.getString("NUM_FACTURA_INTERNO");

		NotaCredito notaCredito = new NotaCredito();

		NotaCredito.Detalles detalles = new NotaCredito.Detalles();
		NotaCredito.Detalles.Detalle detalle = new NotaCredito.Detalles.Detalle();
		detalles.getDetalle().add(detalle);
		notaCredito.setDetalles(detalles);

		notaCredito.setId("comprobante");

		NotaCredito.InfoAdicional infoAdicional = new NotaCredito.InfoAdicional();
		NotaCredito.InfoAdicional.CampoAdicional campoAdicional = new NotaCredito.InfoAdicional.CampoAdicional();
		infoAdicional.getCampoAdicional().add(campoAdicional);
		notaCredito.setInfoAdicional(infoAdicional);

		NotaCredito.InfoNotaCredito infoNotaCredito = new NotaCredito.InfoNotaCredito();
		notaCredito.setInfoNotaCredito(infoNotaCredito);

		InfoTributaria infoTributaria = new InfoTributaria();

		notaCredito.setInfoTributaria(infoTributaria);

		notaCredito.setVersion("1.0.0");
	}

	private void _buildNotasDebito(ResultSet rs, List comprobantes) throws SQLException, NamingException {
		log.info("-> _buildNotasDebito");

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
		infoNotaDebito.setTipoIdentificacionComprador(rs.getString("IDENTIFICACION_COMPRADOR"));
		infoNotaDebito.setRazonSocialComprador(rs.getString("RAZON_SOCIAL_COMPRADOR"));
		infoNotaDebito.setIdentificacionComprador(rs.getString("IDENTIFICACION_COMPRADOR"));
		infoNotaDebito.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
		infoNotaDebito.setObligadoContabilidad(rs.getString("OBLIGADO_CONTABILIDAD"));
		infoNotaDebito.setCodDocModificado(rs.getString("COD_DOC_MODIFICADO"));
		infoNotaDebito.setNumDocModificado(rs.getString("NUM_DOC_MODIFICADO"));
		infoNotaDebito.setFechaEmisionDocSustento(rs.getString("FECHA_EMISION_DOC_SUSTENTO"));
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

		//Pagos
		NotaDebito.InfoNotaDebito.Pago pagos = new NotaDebito.InfoNotaDebito.Pago();
		NotaDebito.InfoNotaDebito.Pago.DetallePago detallePago = new NotaDebito.InfoNotaDebito.Pago.DetallePago();
		detallePago.setFormaPago(rs.getString("FORMA_PAGO"));
		detallePago.setTotal(rs.getBigDecimal("TOTAL_SIN_IMPUESTOS"));
		detallePago.setPlazo(rs.getString("PLAZO"));
		detallePago.setUnidadTiempo(rs.getString("UNIDAD_TIEMPO"));
		pagos.getPagos().add(detallePago);
		infoNotaDebito.setPagos(pagos);
		notaDebito.setInfoNotaDebito(infoNotaDebito);

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

	private void _buildGuiasRemision(ResultSet rs, List comprobantes) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void _buildRetenciones(ResultSet rs, List comprobantes) throws SQLException, NamingException {
		log.info("-> _buildRetenciones");
		String numRetencionInterno = rs.getString("NUM_RETENCION_INTERNO");

		ComprobanteRetencion comprobanteRetencion = new ComprobanteRetencion();
		comprobanteRetencion.setId("comprobante");
		ComprobanteRetencion.Impuestos impuestos = new ComprobanteRetencion.Impuestos();

		String detalleSQL = "SELECT CODIGO, CODIGORETENCION, BASEIMPONIBLE, "
				+ "PORCENTAJERETENR, VALORRETENIDO, CODDOCSUSTENTO, NUMDOCSUSTENTO, "
				+ "FECHAEMISIONDOCSUSTENTO FROM V_RETENCION_ELECTRONICA_D WHERE "
				+ "NUM_RETENCION_INTERNO = " + numRetencionInterno;
		try (PreparedStatement detallePreparedStatement = getConnection().prepareStatement(detalleSQL);
				ResultSet rsd = detallePreparedStatement.executeQuery()) {
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
}
