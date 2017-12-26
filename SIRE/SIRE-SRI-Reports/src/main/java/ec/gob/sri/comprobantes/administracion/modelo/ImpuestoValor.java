package ec.gob.sri.comprobantes.administracion.modelo;

import java.util.Date;

public class ImpuestoValor {

    private String codigo;
    private Integer codigoImpuesto;
    private Double porcentaje;
    private Double porcentajeRentencion;
    private String tipoImpuesto;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer CodigoAdm;
    private String marcaPorcentajeLibre;

    public String getCodigo() {
        return this.codigo;
    }

    public ImpuestoValor() {
    }

    public ImpuestoValor(ImpuestoValor i) {
        this.codigo = i.getCodigo();
        this.codigoImpuesto = i.getCodigoImpuesto();
        this.porcentaje = i.getPorcentaje();
        this.porcentajeRentencion = i.getPorcentajeRentencion();
        this.tipoImpuesto = i.getTipoImpuesto();
        this.descripcion = i.getDescripcion();
        this.fechaInicio = i.getFechaInicio();
        this.fechaFin = i.getFechaFin();
        this.CodigoAdm = i.getCODIGO_ADM();
        this.marcaPorcentajeLibre = i.getMarcaPorcentajeLibre();
    }

    public ImpuestoValor(Integer codigoImpuesto, String tipoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
        this.tipoImpuesto = tipoImpuesto;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoImpuesto() {
        return this.codigoImpuesto;
    }

    public Integer getCODIGO_ADM() {
        return this.CodigoAdm;
    }

    public void setCodigo_Adm(Integer codigoAdm) {
        this.CodigoAdm = codigoAdm;
    }

    public void setCodigoImpuesto(Integer codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    public Double getPorcentaje() {
        return this.porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Double getPorcentajeRentencion() {
        return this.porcentajeRentencion;
    }

    public void setPorcentajeRentencion(Double porcentajeRentencion) {
        this.porcentajeRentencion = porcentajeRentencion;
    }

    public String getTipoImpuesto() {
        return this.tipoImpuesto;
    }

    public void setTipoImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getMarcaPorcentajeLibre() {
        return this.marcaPorcentajeLibre;
    }

    public void setMarcaPorcentajeLibre(String marcaPorcentajeLibre) {
        this.marcaPorcentajeLibre = marcaPorcentajeLibre;
    }

    public String toString() {
        if (((this.codigoImpuesto.intValue() == 1) || (this.codigoImpuesto.intValue() == 2) || (this.codigoImpuesto.intValue() == 3) || (this.codigoImpuesto.intValue() == 5)) && ((this.tipoImpuesto.equals("R")) || (this.tipoImpuesto.equals("I")) || (this.tipoImpuesto.equals("A")) || (this.tipoImpuesto.equals("B")))) {
            if (getCodigo() != null) {
                return getCodigo() + " - " + getDescripcion();
            }
            return "Seleccione....";
        }
        return null;
    }
}
