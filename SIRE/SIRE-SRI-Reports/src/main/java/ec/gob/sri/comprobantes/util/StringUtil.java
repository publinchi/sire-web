package ec.gob.sri.comprobantes.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class StringUtil {

    public static boolean validateEmail(String email) {
        Pattern p = Pattern.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String getTipoIdentificacion(String tipoIddentificacion) {
        if (tipoIddentificacion.equals("TODOS")) {
            return null;
        }
        return TipoIdentificacionEnum.obtenerTipoIdentificacionEnumPorDescripcion(tipoIddentificacion).getCode();
    }

    public static String getSlectedItem(String tipoIddentificacion) {
        return TipoIdentificacionEnum.obtenerTipoIdentificacionEnumPorCodigo(tipoIddentificacion).getDescripcion();
    }

    public static String getSlectedItemTipoProducto(String tipoIddentificacion) {
        if (tipoIddentificacion.equals("B")) {
            return "BIEN";
        }
        if (tipoIddentificacion.equals("S")) {
            return "SERVICIO";
        }
        return null;
    }

    public static String getTipoProducto(String tipoProducto) {
        if (tipoProducto.equals("BIEN")) {
            return "B";
        }
        if (tipoProducto.equals("SERVICIO")) {
            return "S";
        }
        return null;
    }

    public static String getSelectedTipoAmbiente(String tipoIddentificacion) {
        if (tipoIddentificacion.equals("2")) {
            return "PRODUCCION";
        }
        if (tipoIddentificacion.equals("1")) {
            return "PRUEBAS";
        }
        return null;
    }

    public static void convertirMayusculas(JTextField field) {
        ConvertirMayusculas filter = new ConvertirMayusculas();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(filter);
    }

    public static void convertirMinusculas(JTextField field) {
        ConvertirMinusculas filter = new ConvertirMinusculas();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(filter);
    }

    public static String obtenerTipoEmision(String valorCombo) {
        if (valorCombo.equalsIgnoreCase("NORMAL")) {
            return "1";
        }
        if (valorCombo.equalsIgnoreCase("INDISPONIBILIDAD DE SISTEMA")) {
            return "2";
        }
        return null;
    }

    public static String obtenerNumeroTipoEmision(String tipoEmision) {
        if (tipoEmision.equalsIgnoreCase("1")) {
            return "NORMAL";
        }
        if (tipoEmision.equalsIgnoreCase("3")) {
            return "BAJA CONECTIVIDAD";
        }
        if (tipoEmision.equalsIgnoreCase("2")) {
            return "INDISPONIBILIDAD DE SISTEMA";
        }
        return null;
    }

    public static String quitarEnters(String cadenConEnters) {
        String cadenaSinEnters = null;
        for (int x = 0; x < cadenConEnters.length(); x++) {
            if (cadenConEnters.charAt(x) == '\t') {
                cadenaSinEnters = cadenaSinEnters + cadenConEnters.charAt(x);
            }
        }
        return cadenaSinEnters;
    }

    public static boolean validarExpresionRegular(String patron, String valor) {
        if ((patron != null) && (valor != null)) {
            Pattern pattern = Pattern.compile(patron);
            Matcher matcher = pattern.matcher(valor);
            return matcher.matches();
        }
        return false;
    }

    public static String obtenerDocumentoModificado(String codDoc) {
        if ("01".equals(codDoc)) {
            return "FACTURA";
        }
        if ("04".equals(codDoc)) {
            return "NOTA DE CRÉDITO";
        }
        if ("05".equals(codDoc)) {
            return "NOTA DE DÉBITO";
        }
        if ("06".equals(codDoc)) {
            return "GUÍA REMISIÓN";
        }
        if ("07".equals(codDoc)) {
            return "COMPROBANTE DE RETENCIÓN";
        }
        return null;
    }
}
