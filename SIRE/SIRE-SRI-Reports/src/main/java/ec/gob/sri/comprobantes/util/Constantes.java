package ec.gob.sri.comprobantes.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Constantes {

    public static final String JDBC_CLASS = "org.hsqldb.jdbcDriver";
    public static final String URL_CONECCION = "jdbc:hsqldb:file:";
    public static final String CLIENTES_TABLE = "clientes";
    public static final String TRANSPORTISTAS_TBLE = "transportistas";
    public static final String EMISOR_TABLE = "emisor";
    public static final String IMPUESTO_TABLE = "impuestos";
    public static final String CLAVES_TABLE = "claves";
    public static final String PRODUCTO_TABLE = "producto";
    public static final String FORMAS_PAGO_TABLE = "formas_pago";
    public static final String COMPROBANTES_TABLE = "comprobantes";
    public static final String PROXY_TABLE = "PROXY";
    public static final String RESPUESTA_TABLE = "respuesta";
    public static final String RESOURCE_IMPUESTO_VALOR = "resources/impuesto_valor.sql";
    public static final String CONFIGURACION_BD_TABLE = "configuracionBD";
    public static final String CONFIGURACION_DIRECTORIOS_TABLE = "configuracionDirectorio";
    public static final String IMPUESTO_SQL = "resources/impuesto.sql";
    public static final String IMPUESTO_VALOR_SQl = "resources/impuesto_valor.sql";
    public static final String IMPUESTO_VALOR_SQl_WIN = "resources/impuesto_valor_win.sql";
    public static final String ACTUALIZAR_IMPUESTO_VALOR = "resources/actualizarImpuestoValor.sql";
    public static final String ACTUALIZAR_IMPUESTO_IVA_IRBPNR = "resources/actualizarImpuestoIVA_IRBPNR.sql";
    public static final String ACTUALIZAR_IMPUESTO_MARCA_PORCENTAJE = "resources/actualizarImpuestos_MarcaPorcentaje.sql";
    public static final String COMPROBANTES_SQL = "resources/comprobantes.sql";
    public static final String VERSION = "1.0.0";
    public static final String AGREGAR_COLUMNA_IRBPNR = "resources/actualizarEstructuras/agregarColumnaIRBPNR.sql";
    public static final String AGREGAR_COLUMNA_CODIGO_ADM = "resources/actualizarEstructuras/agregarColumnaCODIGO_ADM.sql";
    public static final String AGREGAR_COLUMNA_MARCA_PORCENTAJE_LIBRE = "resources/actualizarEstructuras/agregarColumnaMARCA_PORCENTAJE_LIBRE.sql";
    public static final String ACTUALIZAR_UNIQUE_CODD_PRODUCTO = "resources/actualizarEstructuras/actualizarConstrainUNIQUE_CODD_PRODUCTO.sql";
    public static final String DIRECTORIO_RECHAZADOS = "rechazados";
    public static final String DIRECTORIO_TRANSMITIDOS = "transmitidosSinRespuesta";
    public static final String DIRECTORIO_LOTES = "lotes";
    public static final String XML = ".xml";
    public static final String COD_UTF8 = "UTF-8";
    public static final String ID_FICTICIO_CONSUMIDOR_FINAL = "9999999999999";
    public static final int LIMITE_COMP1 = 3;
    public static final int LIMITE_COMP2 = 9;
    public static final int LIMITE_COMP_MES = 2;
    public static final int LIMITE_COMP_ANIO = 4;
    public static final int LIMITE_CR_NUMDOC = 15;
    public static final int LIMITE_GR_NUMAUT = 49;
    public static final int LIMITE_NUEVA_LINEA = 160;
    public static final int LIMITE_DOC_ADUA = 20;
    public static final int LIMITE_TEXT_AREA = 300;
    public static final String AUTORIDAD_CERT_NO_DISPONIBLE = "61";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
    public static final Integer LONGITUD_CLAVE_PREAUTORIZADA = Integer.valueOf(8);
    public static final int INTENTOS_CONEXION_WS = 3;
    public static final int INTENTOS_RESPUESTA_AUTORIZACION_WS = 5;
    public static final String PRODUCTO_READONLY = "producto;readonly=true";

    private static final String SIRE_HOME = System.getProperty("sire.home");
    private static final String DB = "db";
    private static final String URL_BD = SIRE_HOME + File.separator + DB;

    public static void cargarJDC()
            throws ClassNotFoundException {
        Class.forName(JDBC_CLASS);
    }

    public static String obtenerUrlBD() {
        //Properties props = new Properties();
        String home = SIRE_HOME;
        if (home == null) {
            //log.warning("SIRE HOME NOT FOUND.");
            return null;
            //home = System.getProperty("user.home");
        }
        //String urlBD = home + File.separator + "db";
        //props.load(new FileInputStream(home + "/configuration.properties"));
        //urlBD = props.getProperty("database");
        return URL_BD;
    }
}
