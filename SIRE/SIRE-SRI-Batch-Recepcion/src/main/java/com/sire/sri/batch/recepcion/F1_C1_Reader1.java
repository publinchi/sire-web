package com.sire.sri.batch.recepcion;

import com.sire.sri.batch.commons.CommonsItemReader;
import com.sire.sri.batch.recepcion.constant.RecepcionConstant;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private String codEmpresa;

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
        String tipoComprobante = runtimeParams.getProperty("tipoComprobante");
        codEmpresa = runtimeParams.getProperty("codEmpresa");
        comprobantes = new ArrayList();

        log.log(Level.INFO, "tipoComprobante -> {0}", tipoComprobante);
        log.log(Level.INFO, "codEmpresa -> {0}", codEmpresa);

        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            RecepcionConstant.codEmpresa = "COD_EMPRESA = '" + codEmpresa + "' AND ";
        }

        buildComprobantes(tipoComprobante);
    }

    private void buildComprobantes(String tipoComprobante) throws SQLException, NamingException {
        log.log(Level.INFO, "-> buildComprobantes -> {0}", tipoComprobante);
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

        log.log(Level.INFO, "comprobanteSQL -> {0}", comprobanteSQL);
        try (PreparedStatement comprobantePreparedStatement = getConnection().prepareStatement(comprobanteSQL)) {
            comprobantePreparedStatement.setString(1, codEmpresa);
            ResultSet rs = comprobantePreparedStatement.executeQuery();

            validarTipoComprobante(tipoComprobante, rs);
            iterator = comprobantes.iterator();
            rs.close();
            comprobantePreparedStatement.close();
        }
    }
}
