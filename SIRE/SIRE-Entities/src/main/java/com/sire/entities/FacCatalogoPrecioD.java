/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FAC_CATALOGO_PRECIO_D")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacCatalogoPrecioD.findAll", query = "SELECT f FROM FacCatalogoPrecioD f"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCodEmpresa", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.facCatalogoPrecioDPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCodCatalogo", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.facCatalogoPrecioDPK.codCatalogo = :codCatalogo"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCodArticulo", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.facCatalogoPrecioDPK.codArticulo = :codArticulo"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByPrecioVenta1", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.precioVenta1 = :precioVenta1"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCodUnidad", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.codUnidad = :codUnidad"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCodDivisa1", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.codDivisa1 = :codDivisa1"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByPrecioVenta2", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.precioVenta2 = :precioVenta2"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCodDivisa2", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.codDivisa2 = :codDivisa2"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByPrecioVenta3", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.precioVenta3 = :precioVenta3"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCodDivisa3", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.codDivisa3 = :codDivisa3"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByPorcDescuento", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.porcDescuento = :porcDescuento"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByCantidadRequerida", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.cantidadRequerida = :cantidadRequerida"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByComentario", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.comentario = :comentario"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByAuxPrecio", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.auxPrecio = :auxPrecio"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByFactor", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.factor = :factor"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByOperador", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.operador = :operador"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByFechaModPrecio1", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.fechaModPrecio1 = :fechaModPrecio1"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByFechaModPrecio2", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.fechaModPrecio2 = :fechaModPrecio2"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByFechaModPrecio3", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.fechaModPrecio3 = :fechaModPrecio3"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByAnteriorPrecioVenta", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.anteriorPrecioVenta = :anteriorPrecioVenta"),
    @NamedQuery(name = "FacCatalogoPrecioD.findByAnteriorFechaPrecio", query = "SELECT f FROM FacCatalogoPrecioD f WHERE f.anteriorFechaPrecio = :anteriorFechaPrecio")})
public class FacCatalogoPrecioD implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacCatalogoPrecioDPK facCatalogoPrecioDPK;
    @Column(name = "PRECIO_VENTA1", precision = 0, scale = -127)
    private Double precioVenta1;
    @Column(name = "COD_UNIDAD")
    private String codUnidad;
    @Column(name = "COD_DIVISA1")
    private String codDivisa1;
    @Column(name = "PRECIO_VENTA2")
    private BigInteger precioVenta2;
    @Column(name = "COD_DIVISA2")
    private String codDivisa2;
    @Column(name = "PRECIO_VENTA3")
    private BigInteger precioVenta3;
    @Column(name = "COD_DIVISA3")
    private String codDivisa3;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PORC_DESCUENTO")
    private BigDecimal porcDescuento;
    @Column(name = "CANTIDAD_REQUERIDA")
    private BigDecimal cantidadRequerida;
    @Column(name = "COMENTARIO")
    private String comentario;
    @Column(name = "AUX_PRECIO")
    private BigDecimal auxPrecio;
    @Column(name = "FACTOR")
    private BigInteger factor;
    @Column(name = "OPERADOR")
    private String operador;
    @Column(name = "FECHA_MOD_PRECIO1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModPrecio1;
    @Column(name = "FECHA_MOD_PRECIO2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModPrecio2;
    @Column(name = "FECHA_MOD_PRECIO3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModPrecio3;
    @Column(name = "ANTERIOR_PRECIO_VENTA")
    private BigInteger anteriorPrecioVenta;
    @Column(name = "ANTERIOR_FECHA_PRECIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date anteriorFechaPrecio;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_CATALOGO", referencedColumnName = "COD_CATALOGO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private FacCatalogoPrecioC facCatalogoPrecioC;
    @JoinColumn(name = "USUARIO", referencedColumnName = "NOMBRE_USUARIO")
    @ManyToOne
    private GnrUsuarios usuario;
    @JoinColumn(name = "ANTERIOR_USUARIO_PRECIO", referencedColumnName = "NOMBRE_USUARIO")
    @ManyToOne
    private GnrUsuarios anteriorUsuarioPrecio;

    public FacCatalogoPrecioD() {
    }

    public FacCatalogoPrecioD(FacCatalogoPrecioDPK facCatalogoPrecioDPK) {
        this.facCatalogoPrecioDPK = facCatalogoPrecioDPK;
    }

    public FacCatalogoPrecioD(String codEmpresa, String codCatalogo, int codArticulo) {
        this.facCatalogoPrecioDPK = new FacCatalogoPrecioDPK(codEmpresa, codCatalogo, codArticulo);
    }

    public FacCatalogoPrecioDPK getFacCatalogoPrecioDPK() {
        return facCatalogoPrecioDPK;
    }

    public void setFacCatalogoPrecioDPK(FacCatalogoPrecioDPK facCatalogoPrecioDPK) {
        this.facCatalogoPrecioDPK = facCatalogoPrecioDPK;
    }

    public Double getPrecioVenta1() {
        return precioVenta1;
    }

    public void setPrecioVenta1(Double precioVenta1) {
        this.precioVenta1 = precioVenta1;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public void setCodUnidad(String codUnidad) {
        this.codUnidad = codUnidad;
    }

    public String getCodDivisa1() {
        return codDivisa1;
    }

    public void setCodDivisa1(String codDivisa1) {
        this.codDivisa1 = codDivisa1;
    }

    public BigInteger getPrecioVenta2() {
        return precioVenta2;
    }

    public void setPrecioVenta2(BigInteger precioVenta2) {
        this.precioVenta2 = precioVenta2;
    }

    public String getCodDivisa2() {
        return codDivisa2;
    }

    public void setCodDivisa2(String codDivisa2) {
        this.codDivisa2 = codDivisa2;
    }

    public BigInteger getPrecioVenta3() {
        return precioVenta3;
    }

    public void setPrecioVenta3(BigInteger precioVenta3) {
        this.precioVenta3 = precioVenta3;
    }

    public String getCodDivisa3() {
        return codDivisa3;
    }

    public void setCodDivisa3(String codDivisa3) {
        this.codDivisa3 = codDivisa3;
    }

    public BigDecimal getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(BigDecimal porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public BigDecimal getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(BigDecimal cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public BigDecimal getAuxPrecio() {
        return auxPrecio;
    }

    public void setAuxPrecio(BigDecimal auxPrecio) {
        this.auxPrecio = auxPrecio;
    }

    public BigInteger getFactor() {
        return factor;
    }

    public void setFactor(BigInteger factor) {
        this.factor = factor;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Date getFechaModPrecio1() {
        return fechaModPrecio1;
    }

    public void setFechaModPrecio1(Date fechaModPrecio1) {
        this.fechaModPrecio1 = fechaModPrecio1;
    }

    public Date getFechaModPrecio2() {
        return fechaModPrecio2;
    }

    public void setFechaModPrecio2(Date fechaModPrecio2) {
        this.fechaModPrecio2 = fechaModPrecio2;
    }

    public Date getFechaModPrecio3() {
        return fechaModPrecio3;
    }

    public void setFechaModPrecio3(Date fechaModPrecio3) {
        this.fechaModPrecio3 = fechaModPrecio3;
    }

    public BigInteger getAnteriorPrecioVenta() {
        return anteriorPrecioVenta;
    }

    public void setAnteriorPrecioVenta(BigInteger anteriorPrecioVenta) {
        this.anteriorPrecioVenta = anteriorPrecioVenta;
    }

    public Date getAnteriorFechaPrecio() {
        return anteriorFechaPrecio;
    }

    public void setAnteriorFechaPrecio(Date anteriorFechaPrecio) {
        this.anteriorFechaPrecio = anteriorFechaPrecio;
    }

    public FacCatalogoPrecioC getFacCatalogoPrecioC() {
        return facCatalogoPrecioC;
    }

    public void setFacCatalogoPrecioC(FacCatalogoPrecioC facCatalogoPrecioC) {
        this.facCatalogoPrecioC = facCatalogoPrecioC;
    }

    public GnrUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(GnrUsuarios usuario) {
        this.usuario = usuario;
    }

    public GnrUsuarios getAnteriorUsuarioPrecio() {
        return anteriorUsuarioPrecio;
    }

    public void setAnteriorUsuarioPrecio(GnrUsuarios anteriorUsuarioPrecio) {
        this.anteriorUsuarioPrecio = anteriorUsuarioPrecio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facCatalogoPrecioDPK != null ? facCatalogoPrecioDPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCatalogoPrecioD)) {
            return false;
        }
        FacCatalogoPrecioD other = (FacCatalogoPrecioD) object;
        if ((this.facCatalogoPrecioDPK == null && other.facCatalogoPrecioDPK != null) || (this.facCatalogoPrecioDPK != null && !this.facCatalogoPrecioDPK.equals(other.facCatalogoPrecioDPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacCatalogoPrecioD[ facCatalogoPrecioDPK=" + facCatalogoPrecioDPK + " ]";
    }

}
