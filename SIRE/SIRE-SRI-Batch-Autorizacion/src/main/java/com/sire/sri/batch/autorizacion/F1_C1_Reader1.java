package com.sire.sri.batch.autorizacion;

import ec.gob.sri.comprobantes.modelo.LoteXml;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import com.sire.sri.batch.autorizacion.constant.AutorizacionConstant;
import com.sire.sri.batch.commons.CommonsItemReader;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.naming.NamingException;

@Named
public class F1_C1_Reader1 extends CommonsItemReader {

    @Inject
    private JobContext jobCtx;
    private List lotes;
    private Iterator iterator;
    static int COUNT = 0;

    @Override
    public Object readItem() throws Exception {
        if (iterator.hasNext()) {
            LoteXml lote = (LoteXml) iterator.next();
            COUNT++;
            return lote;
        }
        return null;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        Properties runtimeParams = BatchRuntime.getJobOperator().getParameters(jobCtx.getExecutionId());
        String tipoComprobante = runtimeParams.getProperty("tipoComprobante");
        lotes = new ArrayList();

        String loteSQL = "SELECT COD_EMPRESA, SECUENCIAL, COD_DOCUMENTO, "
                + "CLAVE_ACCESO, ESTADO_SRI, FECHA_ESTADO "
                + "FROM CEL_LOTE_AUTORIZADO WHERE ESTADO_SRI = 'RECIBIDA' "
                + "AND SUBSTR(CLAVE_ACCESO,9,2) = '" + tipoComprobante + "'";
        try (PreparedStatement preparedStatemenT = getConnection().prepareStatement(loteSQL);
                ResultSet loteRs = preparedStatemenT.executeQuery()) {
            while (loteRs.next()) {
                String claveAccesoLote = loteRs.getString("CLAVE_ACCESO");
                buildComprobantes(claveAccesoLote, tipoComprobante);
            }
            iterator = lotes.iterator();
            loteRs.close();
            preparedStatemenT.close();
        }
    }

    private void buildComprobantes(String claveAccesoLote, String tipoComprobante) throws SQLException, NamingException {
        List comprobantes = new ArrayList();
        LoteXml lote = new LoteXml();
        lote.setClaveAcceso(claveAccesoLote);
        String comprobanteSQL = null;

        log.log(Level.INFO, "tipoComprobante -> {0}", tipoComprobante);

        switch (tipoComprobante) {
            case "01":
                comprobanteSQL = AutorizacionConstant.FACTURA_SQL.replace("claveAccesoLote", claveAccesoLote);
                break;
            case "04":
                comprobanteSQL = AutorizacionConstant.NOTA_CREDITO_SQL.replace("claveAccesoLote", claveAccesoLote);
                break;
            case "05":
                comprobanteSQL = AutorizacionConstant.NOTA_DEBITO.replace("claveAccesoLote", claveAccesoLote);
                break;
            case "06":
                comprobanteSQL = AutorizacionConstant.GUIA_REMISION_SQL.replace("claveAccesoLote", claveAccesoLote);
                break;
            case "07":
                comprobanteSQL = AutorizacionConstant.RETENCION_SQL.replace("claveAccesoLote", claveAccesoLote);
                break;
            default:
                break;
        }

        log.log(Level.INFO, "comprobanteSQL -> {0}", comprobanteSQL);
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
            lote.setComprobantes(comprobantes);
            lotes.add(lote);
            rs.close();
            comprobantePreparedStatement.close();
        }
    }
}
