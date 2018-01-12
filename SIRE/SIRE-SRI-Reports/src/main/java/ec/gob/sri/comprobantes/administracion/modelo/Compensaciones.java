package ec.gob.sri.comprobantes.administracion.modelo;

import java.util.Date;

public class Compensaciones {

    private int codigoCompensacion;
    private int tarifaCompensacion;
    private int codigoPorcentajeIva;
    private String tipoCompensacion;
    private Date fechaInicio;
    private Date fechaFin;

    public int getCodigoCompensacion() {
        return this.codigoCompensacion;
    }

    public void setCodigoCompensacion(int codigoCompensacion) {
        this.codigoCompensacion = codigoCompensacion;
    }

    public int getTarifa() {
        return this.tarifaCompensacion;
    }

    public double getTarifaDouble() {
        return this.tarifaCompensacion;
    }

    public void setTarifa(int tarifaCompensacion) {
        this.tarifaCompensacion = tarifaCompensacion;
    }

    public int getCodigoPorcentajeIva() {
        return this.codigoPorcentajeIva;
    }

    public void setCodigoPorcentajeIva(int codigoPorcentajeIva) {
        this.codigoPorcentajeIva = codigoPorcentajeIva;
    }

    public String getTipoCompensacion() {
        return this.tipoCompensacion;
    }

    public void setTipoCompensacion(String tipoCompensacion) {
        this.tipoCompensacion = tipoCompensacion;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String toString() {
        if (getCodigoCompensacion() != 0) {
            return getCodigoCompensacion() + " - " + getTipoCompensacion() + " - " + getTarifaDouble() + "%";
        }
        return "Seleccione....";
    }
}
