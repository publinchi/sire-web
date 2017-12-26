package ec.gob.sri.comprobantes.util;

public enum TipoEmisionEnum {
    NORMAL("NORMAL"), PREAUTORIZADA("INDISPONIBILIDAD DE SISTEMA");

    private String code;

    private TipoEmisionEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
