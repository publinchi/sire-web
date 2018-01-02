package ec.gob.sri.comprobantes.sql;

import ec.gob.sri.comprobantes.administracion.modelo.FormasPago;
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

public class FormasPagoSQL {

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs;
    String url = null;

    public FormasPagoSQL() {
        getStringURL();
    }

    private String getStringURL() {
        if (Constantes.obtenerUrlBD() != null) {
            this.url = ("jdbc:hsqldb:file:" + Constantes.obtenerUrlBD() + "/" + "producto;readonly=true");
        }
        return null;
    }

    public List<FormasPago> obtenerFormasPagoCombo(Date fechaActual)
            throws SQLException, ClassNotFoundException {
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEmisionString = formateador.format(fechaActual);
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from formas_pago where FECHA_INICIO <=  '" + fechaEmisionString + "' and (FECHA_FIN >= '" + fechaEmisionString + "' or FECHA_FIN IS NULL)");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerFormasPago();
        }
        return new ArrayList();
    }

    private List<FormasPago> obtenerFormasPago()
            throws SQLException {
        List<FormasPago> impuestoValorList = new ArrayList();
        while (this.rs.next()) {
            FormasPago imp = new FormasPago();
            imp.setCodigo(this.rs.getString("CODIGO_FORMA_PAGO"));
            imp.setDescripcion(this.rs.getString("DESCRIPCION"));
            impuestoValorList.add(imp);
        }
        return impuestoValorList;
    }

    public List<FormasPago> obtenerDescripcionFormasPago(String codigo)
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from formas_pago where CODIGO_FORMA_PAGO = '" + codigo + "'");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return obtenerFormasPago();
        }
        return new ArrayList();
    }
}
