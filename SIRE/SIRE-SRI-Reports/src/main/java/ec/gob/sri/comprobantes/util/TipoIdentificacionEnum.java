package ec.gob.sri.comprobantes.util;

import java.util.ArrayList;
import java.util.List;

public enum TipoIdentificacionEnum {
    C("CEDULA", "C", ConstantesDimensiones.LONGITUD_CEDULA, TipoCampo.NUMERICO), R("RUC", "R", ConstantesDimensiones.LONGITUD_RUC, TipoCampo.NUMERICO), P("PASAPORTE", "P", ConstantesDimensiones.LONGITUD_PASAPORTE, TipoCampo.TEXTO), I("IDENTIFICACION DEL EXTERIOR", "I", ConstantesDimensiones.LONGITUD_IDENTIFICACION_EXTERIOR, TipoCampo.TEXTO), L("PLACA", "L", ConstantesDimensiones.LONGITUD_PLACA, TipoCampo.TEXTO);

    private String code;
    private String descripcion;
    private Integer longitud;
    private TipoCampo tipoCampo;

    private TipoIdentificacionEnum(String descripcion, String code, Integer longitud, TipoCampo tipoCampo) {
        this.code = code;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.tipoCampo = tipoCampo;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String toString() {
        return this.descripcion;
    }

    public Integer getLongitud() {
        return this.longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public TipoCampo getTipoCampo() {
        return this.tipoCampo;
    }

    public void setTipoCampo(TipoCampo tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public static TipoIdentificacionEnum[] obtenerTipoIdentificacionTransportista() {
        List<TipoIdentificacionEnum> listaTipoIdentificacion = new ArrayList();
        for (TipoIdentificacionEnum tipoIdentificacionEnum : values()) {
            if (!L.equals(tipoIdentificacionEnum)) {
                listaTipoIdentificacion.add(tipoIdentificacionEnum);
            }
        }
        return (TipoIdentificacionEnum[]) listaTipoIdentificacion.toArray(new TipoIdentificacionEnum[listaTipoIdentificacion.size()]);
    }

    public static TipoIdentificacionEnum obtenerTipoIdentificacionEnumPorDescripcion(String descripcion) {
        for (TipoIdentificacionEnum tipoIdentificacionEnum : values()) {
            if (tipoIdentificacionEnum.getDescripcion().equals(descripcion)) {
                return tipoIdentificacionEnum;
            }
        }
        return null;
    }

    public static TipoIdentificacionEnum obtenerTipoIdentificacionEnumPorCodigo(String codigo) {
        for (TipoIdentificacionEnum tipoIdentificacionEnum : values()) {
            if (tipoIdentificacionEnum.getCode().equals(codigo)) {
                return tipoIdentificacionEnum;
            }
        }
        return null;
    }
}
