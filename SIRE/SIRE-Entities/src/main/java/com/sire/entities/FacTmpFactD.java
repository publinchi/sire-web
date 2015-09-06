/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FAC_TMP_FACT_D")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacTmpFactD.findAll", query = "SELECT f FROM FacTmpFactD f"),
    @NamedQuery(name = "FacTmpFactD.findByCodEmpresa", query = "SELECT f FROM FacTmpFactD f WHERE f.facTmpFactDPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacTmpFactD.findByEi", query = "SELECT f FROM FacTmpFactD f WHERE f.facTmpFactDPK.ei = :ei"),
    @NamedQuery(name = "FacTmpFactD.findByEgresoInv", query = "SELECT f FROM FacTmpFactD f WHERE f.facTmpFactDPK.egresoInv = :egresoInv"),
    @NamedQuery(name = "FacTmpFactD.findByCantidad", query = "SELECT f FROM FacTmpFactD f WHERE f.cantidad = :cantidad"),
    @NamedQuery(name = "FacTmpFactD.findByAuxiliar", query = "SELECT f FROM FacTmpFactD f WHERE f.facTmpFactDPK.auxiliar = :auxiliar"),
    @NamedQuery(name = "FacTmpFactD.findByCodBodega", query = "SELECT f FROM FacTmpFactD f WHERE f.codBodega = :codBodega"),
    @NamedQuery(name = "FacTmpFactD.findByCodInventario", query = "SELECT f FROM FacTmpFactD f WHERE f.codInventario = :codInventario"),
    @NamedQuery(name = "FacTmpFactD.findByFactor", query = "SELECT f FROM FacTmpFactD f WHERE f.factor = :factor"),
    @NamedQuery(name = "FacTmpFactD.findByOperador", query = "SELECT f FROM FacTmpFactD f WHERE f.operador = :operador"),
    @NamedQuery(name = "FacTmpFactD.findByPrecioUnitario", query = "SELECT f FROM FacTmpFactD f WHERE f.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "FacTmpFactD.findByAuxCantidad", query = "SELECT f FROM FacTmpFactD f WHERE f.auxCantidad = :auxCantidad"),
    @NamedQuery(name = "FacTmpFactD.findByPorcentajeIva", query = "SELECT f FROM FacTmpFactD f WHERE f.porcentajeIva = :porcentajeIva"),
    @NamedQuery(name = "FacTmpFactD.findByPorcDescVol", query = "SELECT f FROM FacTmpFactD f WHERE f.porcDescVol = :porcDescVol"),
    @NamedQuery(name = "FacTmpFactD.findBySerie", query = "SELECT f FROM FacTmpFactD f WHERE f.serie = :serie"),
    @NamedQuery(name = "FacTmpFactD.findByCantidadDevuelta", query = "SELECT f FROM FacTmpFactD f WHERE f.cantidadDevuelta = :cantidadDevuelta"),
    @NamedQuery(name = "FacTmpFactD.findByPorcDescPago", query = "SELECT f FROM FacTmpFactD f WHERE f.porcDescPago = :porcDescPago"),
    @NamedQuery(name = "FacTmpFactD.findByTotalReg", query = "SELECT f FROM FacTmpFactD f WHERE f.totalReg = :totalReg"),
    @NamedQuery(name = "FacTmpFactD.findByPromocion", query = "SELECT f FROM FacTmpFactD f WHERE f.promocion = :promocion"),
    @NamedQuery(name = "FacTmpFactD.findByPorcDescProm", query = "SELECT f FROM FacTmpFactD f WHERE f.porcDescProm = :porcDescProm"),
    @NamedQuery(name = "FacTmpFactD.findByEntregado", query = "SELECT f FROM FacTmpFactD f WHERE f.entregado = :entregado"),
    @NamedQuery(name = "FacTmpFactD.findByDetalle", query = "SELECT f FROM FacTmpFactD f WHERE f.detalle = :detalle")})
public class FacTmpFactD implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacTmpFactDPK facTmpFactDPK;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private BigInteger cantidad;
    @Column(name = "COD_BODEGA")
    private String codBodega;
    @Column(name = "COD_INVENTARIO")
    private String codInventario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FACTOR")
    private BigDecimal factor;
    @Column(name = "OPERADOR")
    private String operador;
    @Basic(optional = false)
    @Column(name = "PRECIO_UNITARIO")
    private BigInteger precioUnitario;
    @Column(name = "AUX_CANTIDAD")
    private BigInteger auxCantidad;
    @Column(name = "PORCENTAJE_IVA")
    private BigDecimal porcentajeIva;
    @Basic(optional = false)
    @Column(name = "PORC_DESC_VOL")
    private BigDecimal porcDescVol;
    @Column(name = "SERIE")
    private String serie;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_DEVUELTA")
    private BigInteger cantidadDevuelta;
    @Basic(optional = false)
    @Column(name = "PORC_DESC_PAGO")
    private BigDecimal porcDescPago;
    @Column(name = "TOTAL_REG")
    private BigInteger totalReg;
    @Column(name = "PROMOCION")
    private String promocion;
    @Column(name = "PORC_DESC_PROM")
    private BigDecimal porcDescProm;
    @Column(name = "ENTREGADO")
    private String entregado;
    @Column(name = "DETALLE")
    private String detalle;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "EGRESO_INV", referencedColumnName = "EGRESO_INV", insertable = false, updatable = false),
        @JoinColumn(name = "EI", referencedColumnName = "EI", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private FacTmpFactC facTmpFactC;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_ARTICULO", referencedColumnName = "COD_ARTICULO"),
        @JoinColumn(name = "COD_UNIDAD", referencedColumnName = "COD_UNIDAD")})
    @ManyToOne(optional = false)
    private InvUnidadAlternativa invUnidadAlternativa;

    public FacTmpFactD() {
    }

    public FacTmpFactD(FacTmpFactDPK facTmpFactDPK) {
        this.facTmpFactDPK = facTmpFactDPK;
    }

    public FacTmpFactD(FacTmpFactDPK facTmpFactDPK, BigInteger cantidad, BigInteger precioUnitario, BigDecimal porcDescVol, BigInteger cantidadDevuelta, BigDecimal porcDescPago) {
        this.facTmpFactDPK = facTmpFactDPK;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.porcDescVol = porcDescVol;
        this.cantidadDevuelta = cantidadDevuelta;
        this.porcDescPago = porcDescPago;
    }

    public FacTmpFactD(String codEmpresa, String ei, int egresoInv, long auxiliar) {
        this.facTmpFactDPK = new FacTmpFactDPK(codEmpresa, ei, egresoInv, auxiliar);
    }

    public FacTmpFactDPK getFacTmpFactDPK() {
        return facTmpFactDPK;
    }

    public void setFacTmpFactDPK(FacTmpFactDPK facTmpFactDPK) {
        this.facTmpFactDPK = facTmpFactDPK;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodBodega() {
        return codBodega;
    }

    public void setCodBodega(String codBodega) {
        this.codBodega = codBodega;
    }

    public String getCodInventario() {
        return codInventario;
    }

    public void setCodInventario(String codInventario) {
        this.codInventario = codInventario;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public BigInteger getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigInteger precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigInteger getAuxCantidad() {
        return auxCantidad;
    }

    public void setAuxCantidad(BigInteger auxCantidad) {
        this.auxCantidad = auxCantidad;
    }

    public BigDecimal getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(BigDecimal porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public BigDecimal getPorcDescVol() {
        return porcDescVol;
    }

    public void setPorcDescVol(BigDecimal porcDescVol) {
        this.porcDescVol = porcDescVol;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public BigInteger getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(BigInteger cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public BigDecimal getPorcDescPago() {
        return porcDescPago;
    }

    public void setPorcDescPago(BigDecimal porcDescPago) {
        this.porcDescPago = porcDescPago;
    }

    public BigInteger getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(BigInteger totalReg) {
        this.totalReg = totalReg;
    }

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    public BigDecimal getPorcDescProm() {
        return porcDescProm;
    }

    public void setPorcDescProm(BigDecimal porcDescProm) {
        this.porcDescProm = porcDescProm;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public FacTmpFactC getFacTmpFactC() {
        return facTmpFactC;
    }

    public void setFacTmpFactC(FacTmpFactC facTmpFactC) {
        this.facTmpFactC = facTmpFactC;
    }

    public InvUnidadAlternativa getInvUnidadAlternativa() {
        return invUnidadAlternativa;
    }

    public void setInvUnidadAlternativa(InvUnidadAlternativa invUnidadAlternativa) {
        this.invUnidadAlternativa = invUnidadAlternativa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facTmpFactDPK != null ? facTmpFactDPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacTmpFactD)) {
            return false;
        }
        FacTmpFactD other = (FacTmpFactD) object;
        if ((this.facTmpFactDPK == null && other.facTmpFactDPK != null) || (this.facTmpFactDPK != null && !this.facTmpFactDPK.equals(other.facTmpFactDPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacTmpFactD[ facTmpFactDPK=" + facTmpFactDPK + " ]";
    }
    
}
