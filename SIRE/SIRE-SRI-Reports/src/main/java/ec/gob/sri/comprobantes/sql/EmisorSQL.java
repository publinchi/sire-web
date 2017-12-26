package ec.gob.sri.comprobantes.sql;

import ec.gob.sri.comprobantes.administracion.modelo.Emisor;
import ec.gob.sri.comprobantes.util.Constantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmisorSQL {

    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs;
    private String url;

    public EmisorSQL() {
        this.url = ("jdbc:hsqldb:file:" + Constantes.obtenerUrlBD() + "/" + "emisor");
    }

    private void crear(Emisor em)
            throws SQLException, ClassNotFoundException {
        Constantes.cargarJDC();
        this.conn = DriverManager.getConnection(this.url);
        String values = getInsertValues(em);
        StringBuilder sql = new StringBuilder("INSERT INTO EMISOR VALUES(");
        sql.append(values);
        this.statement = this.conn.createStatement();
        this.statement.executeUpdate(sql.toString());
    }

    public void crearEmisor(Emisor em)
            throws SQLException, ClassNotFoundException {
        if (obtenerDatosEmisor() == null) {
            crear(em);
        } else {
            eliminarEmisor();
            crear(em);
        }
        flushDataBase();
        cerrarConexion();
    }

    public void eliminarEmisor()
            throws SQLException, ClassNotFoundException {
        Constantes.cargarJDC();
        this.conn = DriverManager.getConnection(this.url);
        StringBuilder sql = new StringBuilder("DELETE FROM EMISOR");
        this.statement = this.conn.createStatement();
        this.statement.executeUpdate(sql.toString());
    }

    public Emisor obtenerDatosEmisor()
            throws SQLException, ClassNotFoundException {
        if (Constantes.obtenerUrlBD() != null) {
            Constantes.cargarJDC();
            this.conn = DriverManager.getConnection(this.url);
            StringBuilder sql = new StringBuilder("select * from EMISOR");
            this.statement = this.conn.createStatement();
            this.rs = this.statement.executeQuery(sql.toString());
            return getEmisor();
        }
        return null;
    }

    private Emisor getEmisor()
            throws SQLException {
        Emisor emi = null;
        if (this.rs.next()) {
            emi = new Emisor();
            emi.setCodigo(Integer.valueOf(this.rs.getInt("CODIGO")));
            emi.setRazonSocial(this.rs.getString("RAZONSOCIAL"));
            emi.setRuc(this.rs.getString("RUC"));
            emi.setNombreComercial(this.rs.getString("NOMCOMERCIAL"));
            emi.setDirEstablecimiento(this.rs.getString("DIRESTABLECIMIENTO"));
            emi.setCodigoEstablecimiento(this.rs.getString("CODESTABLECIMIENTO"));
            emi.setNumeroResolusion(this.rs.getString("RESOLUSION"));
            emi.setContribuyenteEspecial(this.rs.getString("CONTRIBUYENTEESPECIAL"));
            emi.setCodPuntoEmision(this.rs.getString("CODPINTOEMISION"));
            emi.setLlevaContabilidad(this.rs.getString("LLEVACONTABILIDAD"));
            emi.setPathLogo(this.rs.getString("LOGOIMAGEN"));
            emi.setTipoEmision(this.rs.getString("TIPOEMISION"));
            emi.setTiempoEspera(Integer.valueOf(this.rs.getInt("TIEMPOESPERA")));
            emi.setTipoAmbiente(this.rs.getString("TIPO_AMBIENTE"));
            emi.setClaveInterna(this.rs.getString("CLAVE_INTERNA"));
            emi.setDireccionMatriz(this.rs.getString("DIRECCION_MATRIZ"));
            emi.setToken(this.rs.getString("TOKEN"));
            return emi;
        }
        return emi;
    }

    public void actualizarImagen(String imagen)
            throws SQLException, ClassNotFoundException {
        Constantes.cargarJDC();
        this.conn = DriverManager.getConnection(this.url);
        StringBuilder sql = new StringBuilder("UPDATE  EMISOR SET LOGOIMAGEN='" + imagen + "'");
        this.statement = this.conn.createStatement();
        this.statement.executeUpdate(sql.toString());
        cerrarConexion();
    }

    private String getInsertValues(Emisor em) {
        String insertValues = em.getCodigo() + ",'" + em.getRazonSocial() + "','" + em.getRuc() + "','" + em.getNombreComercial() + "','" + em.getDirEstablecimiento() + "','" + em.getCodigoEstablecimiento() + "','" + em.getNumeroResolusion() + "','" + em.getContribuyenteEspecial() + "','" + em.getCodPuntoEmision() + "','" + em.getLlevaContabilidad() + "','" + em.getPathLogo() + "','" + em.getTipoEmision() + "'," + em.getTiempoEspera() + ",'" + em.getClaveInterna() + "','" + em.getTipoAmbiente() + "','" + em.getDireccionMatriz() + "','" + em.getToken() + "')";

        return insertValues;
    }

    private void cerrarConexion()
            throws SQLException {
        this.statement.close();
        this.conn.close();
    }

    private void flushDataBase()
            throws SQLException {
        this.statement = this.conn.createStatement();
        this.statement.executeUpdate("SHUTDOWN");
    }
}
