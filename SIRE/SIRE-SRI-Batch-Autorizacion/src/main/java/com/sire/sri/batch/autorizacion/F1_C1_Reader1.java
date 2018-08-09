package com.sire.sri.batch.autorizacion;

import com.sire.sri.batch.constant.Constant;
import ec.gob.sri.comprobantes.modelo.LoteXml;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import com.sire.sri.batch.autorizacion.constant.AutorizacionConstant;
import com.sire.sri.batch.commons.CommonsItemReader;

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
    public Object readItem() {
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
        codEmpresa = runtimeParams.getProperty("codEmpresa");
        lotes = new ArrayList();

        log.log(Level.INFO, "tipoComprobante -> {0}", tipoComprobante);
        log.log(Level.INFO, "codEmpresa -> {0}", codEmpresa);

        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            AutorizacionConstant.codEmpresa = "COD_EMPRESA = '" + codEmpresa + "' AND ";
        }

        Connection connection = getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String databaseProductName = databaseMetaData.getDatabaseProductName();

        String subString = null;

        if(Constant.MYSQL.equals(databaseProductName))
            subString = "SUBSTR";
        else if(Constant.ORACLE.equals(databaseProductName))
            subString = "SUBSTR";
        else if(Constant.MICROSOFT_SQL_SERVER.equals(databaseProductName))
            subString = "SUBSTRING";

        String loteSQL = "SELECT COD_EMPRESA, SECUENCIAL, COD_DOCUMENTO, "
                + "CLAVE_ACCESO, ESTADO_SRI, FECHA_ESTADO "
                + "FROM CEL_LOTE_AUTORIZADO WHERE COD_EMPRESA = ? AND ESTADO_SRI = 'RECIBIDA' "
                + "AND " + subString + "(CLAVE_ACCESO,9,2) = ?";
        try (PreparedStatement preparedStatemenT = getConnection().prepareStatement(loteSQL)) {
            ResultSet loteRs = getResultSet(tipoComprobante, preparedStatemenT);
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
                comprobanteSQL = AutorizacionConstant.FACTURA_SQL;
                break;
            case "04":
                comprobanteSQL = AutorizacionConstant.NOTA_CREDITO_SQL;
                break;
            case "05":
                comprobanteSQL = AutorizacionConstant.NOTA_DEBITO_SQL;
                break;
            case "06":
                comprobanteSQL = AutorizacionConstant.GUIA_REMISION_SQL;
                break;
            case "07":
                comprobanteSQL = AutorizacionConstant.RETENCION_SQL;
                break;
            default:
                throw new RuntimeException("No se ha encontrado ninguna sentencia sql "
                        + "asociada al tipo de comprobante " + tipoComprobante);
        }

        log.log(Level.INFO, "comprobanteSQL -> {0}", comprobanteSQL);
        try (PreparedStatement comprobantePreparedStatement = getConnection().prepareStatement(comprobanteSQL)) {
            ResultSet rs = getResultSet(claveAccesoLote, comprobantePreparedStatement);
            validarTipoComprobante(tipoComprobante, rs, comprobantes);
            lote.setComprobantes(comprobantes);
            lotes.add(lote);
            rs.close();
            comprobantePreparedStatement.close();
        }
    }

    private ResultSet getResultSet(String claveAccesoLote, PreparedStatement comprobantePreparedStatement) throws SQLException {
        comprobantePreparedStatement.setString(1, codEmpresa);
        comprobantePreparedStatement.setString(2, claveAccesoLote);
        return comprobantePreparedStatement.executeQuery();
    }
}
