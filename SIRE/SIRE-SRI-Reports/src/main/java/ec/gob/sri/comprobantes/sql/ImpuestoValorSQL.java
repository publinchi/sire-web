package ec.gob.sri.comprobantes.sql;

import ec.gob.sri.comprobantes.administracion.modelo.ImpuestoValor;
import ec.gob.sri.comprobantes.util.Constantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpuestoValorSQL {

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs;
    String url = null;

    public ImpuestoValorSQL() {
        getStringURL();
    }

    private String getStringURL() {
        if (Constantes.obtenerUrlBD() != null) {
            this.url = ("jdbc:hsqldb:file:" + Constantes.obtenerUrlBD() + "/" + "producto");
        }
        return null;
    }

    public List<ImpuestoValor> obtenerValorImpuestoIVA()
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=2and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A')");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerImpuestoValorIVA();
        }
        return new ArrayList();
    }

    public List<ImpuestoValor> obtenerIvaDiferenteCero()
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto =2 and MARCA_PORCENTAJE_LIBRE = 'N' and PORCENTAJE > 0 and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A')");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerImpuestoValorIVA();
        }
        return new ArrayList();
    }

    public List<ImpuestoValor> obtenerIvaComboProducto(Date fechaActual)
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            List<ImpuestoValor> impuestoValorList = new ArrayList();
            String fechaEmisionString = formateador.format(fechaActual);
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=2and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A') and FECHA_INICIO <= '" + fechaEmisionString + "' and (FECHA_FIN >= '" + fechaEmisionString + "' or FECHA_FIN IS NULL) order by codigo_adm");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            impuestoValorList = obtenerImpuestoValorIVA();
            for (int i = 0; i < impuestoValorList.size(); i++) {
                if (((ImpuestoValor) impuestoValorList.get(i)).getPorcentaje().doubleValue() > 0.0D) {
                    ((ImpuestoValor) impuestoValorList.get(i)).setDescripcion("GRAVA IVA");
                }
            }
            return impuestoValorList;
        }
        return new ArrayList();
    }

    public List<ImpuestoValor> obtenerDatosIvaVigente(Date fechaEmision)
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            String fechaEmisionString = formateador.format(fechaEmision);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=2 and PORCENTAJE > 0.00 and FECHA_INICIO <= '" + fechaEmisionString + "' and (FECHA_FIN >= '" + fechaEmisionString + "' or FECHA_FIN IS NULL) and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A')");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerImpuestoValorIVA();
        }
        return new ArrayList();
    }

    public List<ImpuestoValor> obtenerDatosIvaCodigoPorcentaje(String codigo_porcentaje)
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=2 and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A') and CODIGO_ADM = '" + codigo_porcentaje + "'");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerImpuestoValorIVA();
        }
        return new ArrayList();
    }

    public List<ImpuestoValor> obtenerValorImpuestoICE()
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=3and (TIPO_IMPUESTO='I' or TIPO_IMPUESTO='A' )");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerImpuestoValor();
        }
        return new ArrayList();
    }

    public List<ImpuestoValor> obtenerValorImpuestoIRBPNR()
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_impuesto=5 and TIPO_IMPUESTO='B'");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerImpuestoValor();
        }
        return new ArrayList();
    }

    private List<ImpuestoValor> obtenerImpuestoValor()
            throws SQLException {
        List<ImpuestoValor> impuestoValorList = new ArrayList();
        while (this.rs.next()) {
            ImpuestoValor imp = new ImpuestoValor();
            imp.setCodigo(this.rs.getString("CODIGO"));
            imp.setCodigoImpuesto(Integer.valueOf(this.rs.getInt("CODIGO_IMPUESTO")));
            imp.setPorcentaje(Double.valueOf(this.rs.getDouble("PORCENTAJE")));
            imp.setPorcentajeRentencion(Double.valueOf(this.rs.getDouble("PORCENTAJE_RETENCION")));
            imp.setTipoImpuesto(this.rs.getString("TIPO_IMPUESTO"));
            imp.setFechaInicio(this.rs.getDate("FECHA_INICIO"));
            imp.setFechaFin(this.rs.getDate("FECHA_FIN"));
            imp.setDescripcion(this.rs.getString("DESCRIPCION"));
            imp.setCodigo_Adm(Integer.valueOf(this.rs.getInt("CODIGO_ADM")));
            imp.setMarcaPorcentajeLibre(this.rs.getString("MARCA_PORCENTAJE_LIBRE"));
            impuestoValorList.add(imp);
        }
        return impuestoValorList;
    }

    private List<ImpuestoValor> obtenerImpuestoValorIVA()
            throws SQLException {
        List<ImpuestoValor> impuestoValorList = new ArrayList();
        while (this.rs.next()) {
            ImpuestoValor imp = new ImpuestoValor();
            imp.setCodigo(this.rs.getString("CODIGO_ADM"));
            imp.setCodigoImpuesto(Integer.valueOf(this.rs.getInt("CODIGO_IMPUESTO")));
            imp.setPorcentaje(Double.valueOf(this.rs.getDouble("PORCENTAJE")));
            imp.setPorcentajeRentencion(Double.valueOf(this.rs.getDouble("PORCENTAJE_RETENCION")));
            imp.setTipoImpuesto(this.rs.getString("TIPO_IMPUESTO"));
            imp.setFechaInicio(this.rs.getDate("FECHA_INICIO"));
            imp.setFechaFin(this.rs.getDate("FECHA_FIN"));
            imp.setDescripcion(this.rs.getString("DESCRIPCION"));
            imp.setMarcaPorcentajeLibre(this.rs.getString("MARCA_PORCENTAJE_LIBRE"));
            imp.setCodigo_Adm(Integer.valueOf(this.rs.getInt("CODIGO_ADM")));
            impuestoValorList.add(imp);
        }
        return impuestoValorList;
    }

    public List<ImpuestoValor> obtenerValorImpuestoRenta()
            throws SQLException, ClassNotFoundException {
        Constantes.cargarJDC();
        this.conn = DriverManager.getConnection(this.url);
        StringBuilder sql = new StringBuilder("select * from impuesto_valor where  codigo_impuesto= 1 and TIPO_IMPUESTO='R' ");
        this.statement = this.conn.createStatement();
        this.rs = this.statement.executeQuery(sql.toString());
        return obtenerImpuestoValor();
    }

    public List<ImpuestoValor> obtenerIVARetencion()
            throws SQLException, ClassNotFoundException {
        Constantes.cargarJDC();
        this.conn = DriverManager.getConnection(this.url);
        StringBuilder sql = new StringBuilder("select * from impuesto_valor where (codigo_impuesto=2 and TIPO_IMPUESTO='R') ");
        sql.append("or (codigo_impuesto= 2 and TIPO_IMPUESTO='A') order by CODIGO_ADM");
        this.statement = this.conn.createStatement();
        this.rs = this.statement.executeQuery(sql.toString());
        return obtenerImpuestoValorIVA();
    }

    public ImpuestoValor obtenerValorPorCodigo(String codigo)
            throws SQLException, ClassNotFoundException {
        if (this.url != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo = '");
            sql.append(codigo);
            sql.append("'");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            List<ImpuestoValor> impuestos = obtenerImpuestoValor();
            if (!impuestos.isEmpty()) {
                return (ImpuestoValor) impuestos.get(0);
            }
            return null;
        }
        return null;
    }

    public ImpuestoValor obtenerValorPorCodigoIVA(String codigo)
            throws SQLException, ClassNotFoundException {
        if (this.url != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from impuesto_valor where codigo_adm = '");
            sql.append(codigo);
            sql.append("'");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            List<ImpuestoValor> impuestos = obtenerImpuestoValorIVA();
            if (!impuestos.isEmpty()) {
                return (ImpuestoValor) impuestos.get(0);
            }
            return null;
        }
        return null;
    }
}
