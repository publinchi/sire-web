package com.sire.sri.batch.recepcion;

import com.sire.sri.batch.commons.CommonsItemReader;
import com.sire.sri.batch.constant.Constant;
import com.sire.sri.batch.recepcion.constant.RecepcionConstant;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

@Named
public class F1_C1_Reader1 extends CommonsItemReader {

    @Inject
    private JobContext jobCtx;
    private Iterator iterator;
    static int COUNT = 0;

    @Override
    public Object readItem() {
        if (iterator.hasNext()) {
            COUNT++;
            return iterator.next();
        }
        return null;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        Properties runtimeParams = BatchRuntime.getJobOperator().getParameters(jobCtx.getExecutionId());
        String tipoComprobante = runtimeParams.getProperty(Constant.TIPO_COMPROBANTE);
        codEmpresa = runtimeParams.getProperty(Constant.COD_EMPRESA);

        log.log(Level.INFO, "tipoComprobante -> {0}", tipoComprobante);
        log.log(Level.INFO, "codEmpresa -> {0}", codEmpresa);

        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            RecepcionConstant.codEmpresa = "COD_EMPRESA = '" + codEmpresa + "' AND ";
        }

        buildComprobantes(tipoComprobante);
    }

    private void buildComprobantes(String tipoComprobante) throws SQLException, NamingException {
        Connection connection = getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        String databaseProductName = databaseMetaData.getDatabaseProductName();
        log.log(Level.INFO, "databaseProductName -> {0}", databaseProductName);
        List comprobantes = new ArrayList();
        log.log(Level.INFO, "-> buildComprobantes -> {0}", tipoComprobante);
        String comprobanteSQL = null;

        switch (tipoComprobante) {
            case Constant.CERO_UNO:
                if(Constant.MYSQL.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.FACTURA_SQL_MYSQL;
                else if(Constant.ORACLE.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.FACTURA_SQL_ORACLE;
                else if(Constant.MICROSOFT_SQL_SERVER.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.FACTURA_SQL_MICROSOFT_SQL_SERVER;
                break;
            case Constant.CERO_CUATRO:
                if(Constant.MYSQL.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.NOTA_CREDITO_SQL_MYSQL;
                else if(Constant.ORACLE.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.NOTA_CREDITO_SQL_ORACLE;
                else if(Constant.MICROSOFT_SQL_SERVER.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.NOTA_CREDITO_SQL_MICROSOFT_SQL_SERVER;
                break;
            case Constant.CERO_CINCO:
                if(Constant.MYSQL.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.NOTA_DEBITO_SQL_MYSQL;
                else if(Constant.ORACLE.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.NOTA_DEBITO_SQL_ORACLE;
                else if(Constant.MICROSOFT_SQL_SERVER.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.NOTA_DEBITO_SQL_MICROSOFT_SQL_SERVER;
                break;
            case Constant.CERO_SEIS:
                comprobanteSQL = RecepcionConstant.GUIA_REMISION_SQL;
                break;
            case Constant.CERO_SIETE:
                if(Constant.MYSQL.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.RETENCION_SQL_MYSQL;
                else if(Constant.ORACLE.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.RETENCION_SQL_ORACLE;
                else if(Constant.MICROSOFT_SQL_SERVER.equals(databaseProductName))
                    comprobanteSQL = RecepcionConstant.RETENCION_SQL_MICROSOFT_SQL_SERVER;
                break;
            default:
                throw new RuntimeException("No se ha encontrado ninguna sentencia sql "
                        + "asociada al tipo de comprobante " + tipoComprobante);
        }

        log.log(Level.INFO, "comprobanteSQL -> {0}", comprobanteSQL);
        try(PreparedStatement comprobantePreparedStatement = connection.prepareStatement(comprobanteSQL)) {
            comprobantePreparedStatement.setString(1, codEmpresa);
            try(ResultSet rs = comprobantePreparedStatement.executeQuery()){
                validarTipoComprobante(tipoComprobante, rs, comprobantes);
                iterator = comprobantes.iterator();
            }
        }
    }
}
