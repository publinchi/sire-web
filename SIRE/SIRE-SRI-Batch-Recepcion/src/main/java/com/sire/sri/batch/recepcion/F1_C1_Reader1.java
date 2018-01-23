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
    private List comprobantes;
    private Iterator iterator;
    static int COUNT = 0;

    @Override
    public Object readItem() throws Exception {
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
        comprobantes = new ArrayList();

        log.log(Level.INFO, "tipoComprobante -> {0}", tipoComprobante);

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
}
