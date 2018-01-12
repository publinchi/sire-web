package ec.gob.sri.comprobantes.sql;

import ec.gob.sri.comprobantes.administracion.modelo.Compensaciones;
import ec.gob.sri.comprobantes.util.CompensacionesEnum;
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

public class CompensacionSQL {

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs;
    String url = null;

    public CompensacionSQL() {
        getStringURL();
    }

    private String getStringURL() {
        if (Constantes.obtenerUrlBD() != null) {
            this.url = ("jdbc:hsqldb:file:" + Constantes.obtenerUrlBD() + "/" + "producto");
        }
        return null;
    }

    public Compensaciones obtenerCompensacionSolidaria(Date fechaActual)
            throws SQLException, ClassNotFoundException {
        int codigo = CompensacionesEnum.CompensacionSolidaria.getCode();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEmisionString = formateador.format(fechaActual);
        try {
            if (Constantes.obtenerUrlBD() != null) {
                Constantes.cargarJDC();
                this.conn = DriverManager.getConnection(this.url);
                StringBuilder sql = new StringBuilder("select * from compensaciones where CODIGO_COMPENSACION = '" + codigo + "' and FECHA_INICIO <=  '" + fechaEmisionString + "' and (FECHA_FIN >= '" + fechaEmisionString + "' or FECHA_FIN IS NULL)");
                this.statement = this.conn.createStatement();
                this.rs = this.statement.executeQuery(sql.toString());
                return (Compensaciones) obtenerCompensacion().get(0);
            }
        } catch (Exception ex) {
            return new Compensaciones();
        }
        return new Compensaciones();
    }

    public List<Compensaciones> obtenerCompensacionesVigentes(Date fechaActual, String codigoPorcentajeIva)
            throws SQLException, ClassNotFoundException {
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEmisionString = formateador.format(fechaActual);
        try {
            if (Constantes.obtenerUrlBD() != null) {
                Constantes.cargarJDC();
                this.conn = DriverManager.getConnection(this.url);
                StringBuilder sql = new StringBuilder("select * from compensaciones where CODIGO_PORCENTAJE_IVA = '" + codigoPorcentajeIva + "' and FECHA_INICIO <=  '" + fechaEmisionString + "' and (FECHA_FIN >= '" + fechaEmisionString + "' or FECHA_FIN IS NULL)");
                this.statement = this.conn.createStatement();
                this.rs = this.statement.executeQuery(sql.toString());
                return obtenerCompensacion();
            }
        } catch (Exception ex) {
            return new ArrayList();
        }
        return new ArrayList();
    }

    public List<Compensaciones> obtenerCompensacionesPorCodigo(int codigo)
            throws SQLException, ClassNotFoundException {
        try {
            if (Constantes.obtenerUrlBD() != null) {
                Constantes.cargarJDC();
                this.conn = DriverManager.getConnection(this.url);
                StringBuilder sql = new StringBuilder("select * from compensaciones where CODIGO_COMPENSACION = '" + codigo + "'");
                this.statement = this.conn.createStatement();
                this.rs = this.statement.executeQuery(sql.toString());
                return obtenerCompensacion();
            }
        } catch (Exception ex) {
            return new ArrayList();
        }
        return new ArrayList();
    }

    private List<Compensaciones> obtenerCompensacion()
            throws SQLException {
        List<Compensaciones> CompensacionesList = new ArrayList();
        if (Constantes.obtenerUrlBD() != null) {
            while (this.rs.next()) {
                Compensaciones imp = new Compensaciones();
                imp.setCodigoCompensacion(this.rs.getInt("CODIGO_COMPENSACION"));
                imp.setTarifa(this.rs.getInt("TARIFA_COMPENSACION"));
                imp.setCodigoPorcentajeIva(this.rs.getInt("CODIGO_PORCENTAJE_IVA"));
                imp.setTipoCompensacion(this.rs.getString("TIPO_COMPENSACION"));
                imp.setFechaInicio(this.rs.getDate("FECHA_INICIO"));
                imp.setFechaFin(this.rs.getDate("FECHA_FIN"));
                CompensacionesList.add(imp);
            }
            return CompensacionesList;
        }
        return new ArrayList();
    }
}
