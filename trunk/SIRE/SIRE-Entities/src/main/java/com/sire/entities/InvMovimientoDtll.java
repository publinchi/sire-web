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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "INV_MOVIMIENTO_DTLL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvMovimientoDtll.findAll", query = "SELECT i FROM InvMovimientoDtll i"),
    @NamedQuery(name = "InvMovimientoDtll.findByCodEmpresa", query = "SELECT i FROM InvMovimientoDtll i WHERE i.invMovimientoDtllPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvMovimientoDtll.findByCodDocumento", query = "SELECT i FROM InvMovimientoDtll i WHERE i.invMovimientoDtllPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "InvMovimientoDtll.findByNumDocumento", query = "SELECT i FROM InvMovimientoDtll i WHERE i.invMovimientoDtllPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "InvMovimientoDtll.findByNumLinea", query = "SELECT i FROM InvMovimientoDtll i WHERE i.invMovimientoDtllPK.numLinea = :numLinea"),
    @NamedQuery(name = "InvMovimientoDtll.findByCodUnidad", query = "SELECT i FROM InvMovimientoDtll i WHERE i.codUnidad = :codUnidad"),
    @NamedQuery(name = "InvMovimientoDtll.findByCantidad", query = "SELECT i FROM InvMovimientoDtll i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "InvMovimientoDtll.findByNumPedido", query = "SELECT i FROM InvMovimientoDtll i WHERE i.numPedido = :numPedido"),
    @NamedQuery(name = "InvMovimientoDtll.findByAuxCantidad", query = "SELECT i FROM InvMovimientoDtll i WHERE i.auxCantidad = :auxCantidad"),
    @NamedQuery(name = "InvMovimientoDtll.findByCostoTotal", query = "SELECT i FROM InvMovimientoDtll i WHERE i.costoTotal = :costoTotal"),
    @NamedQuery(name = "InvMovimientoDtll.findByCostoUnitario", query = "SELECT i FROM InvMovimientoDtll i WHERE i.costoUnitario = :costoUnitario"),
    @NamedQuery(name = "InvMovimientoDtll.findByPrecioVenta", query = "SELECT i FROM InvMovimientoDtll i WHERE i.precioVenta = :precioVenta"),
    @NamedQuery(name = "InvMovimientoDtll.findByEstado", query = "SELECT i FROM InvMovimientoDtll i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvMovimientoDtll.findByValorCompra", query = "SELECT i FROM InvMovimientoDtll i WHERE i.valorCompra = :valorCompra"),
    @NamedQuery(name = "InvMovimientoDtll.findByFechaEstado", query = "SELECT i FROM InvMovimientoDtll i WHERE i.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "InvMovimientoDtll.findByPorcDesc1", query = "SELECT i FROM InvMovimientoDtll i WHERE i.porcDesc1 = :porcDesc1"),
    @NamedQuery(name = "InvMovimientoDtll.findByPorcDesc2", query = "SELECT i FROM InvMovimientoDtll i WHERE i.porcDesc2 = :porcDesc2"),
    @NamedQuery(name = "InvMovimientoDtll.findByPorcentajeIva", query = "SELECT i FROM InvMovimientoDtll i WHERE i.porcentajeIva = :porcentajeIva"),
    @NamedQuery(name = "InvMovimientoDtll.findByDescuento", query = "SELECT i FROM InvMovimientoDtll i WHERE i.descuento = :descuento"),
    @NamedQuery(name = "InvMovimientoDtll.findByPorcDesc3", query = "SELECT i FROM InvMovimientoDtll i WHERE i.porcDesc3 = :porcDesc3"),
    @NamedQuery(name = "InvMovimientoDtll.findByOperador", query = "SELECT i FROM InvMovimientoDtll i WHERE i.operador = :operador"),
    @NamedQuery(name = "InvMovimientoDtll.findByFactor", query = "SELECT i FROM InvMovimientoDtll i WHERE i.factor = :factor")})
public class InvMovimientoDtll implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvMovimientoDtllPK invMovimientoDtllPK;
    @Column(name = "COD_UNIDAD")
    private String codUnidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "NUM_PEDIDO")
    private BigInteger numPedido;
    @Basic(optional = false)
    @Column(name = "AUX_CANTIDAD")
    private BigInteger auxCantidad;
    @Basic(optional = false)
    @Column(name = "COSTO_TOTAL")
    private Double costoTotal;
    @Basic(optional = false)
    @Column(name = "COSTO_UNITARIO")
    private Double costoUnitario;
    @Column(name = "PRECIO_VENTA")
    private Double precioVenta;
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "VALOR_COMPRA")
    private BigInteger valorCompra;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Basic(optional = false)
    @Column(name = "PORC_DESC1")
    private BigDecimal porcDesc1;
    @Basic(optional = false)
    @Column(name = "PORC_DESC2")
    private BigDecimal porcDesc2;
    @Basic(optional = false)
    @Column(name = "PORCENTAJE_IVA")
    private BigDecimal porcentajeIva;
    @Basic(optional = false)
    @Column(name = "DESCUENTO")
    private BigInteger descuento;
    @Basic(optional = false)
    @Column(name = "PORC_DESC3")
    private BigDecimal porcDesc3;
    @Column(name = "OPERADOR")
    private String operador;
    @Column(name = "FACTOR")
    private BigInteger factor;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_ARTICULO", referencedColumnName = "COD_ARTICULO"),
        @JoinColumn(name = "COD_BODEGA", referencedColumnName = "COD_BODEGA"),
        @JoinColumn(name = "COD_INVENTARIO", referencedColumnName = "COD_INVENTARIO")})
    @ManyToOne(optional = false)
    private InvBodegaArt invBodegaArt;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "NUM_DOCUMENTO", referencedColumnName = "NUM_DOCUMENTO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private InvMovimientoCab invMovimientoCab;
    @Transient
    @Getter
    @Setter
    private InvArticulo invArticulo;

    public InvMovimientoDtll() {
    }

    public InvMovimientoDtll(InvMovimientoDtllPK invMovimientoDtllPK) {
        this.invMovimientoDtllPK = invMovimientoDtllPK;
    }

    public InvMovimientoDtll(InvMovimientoDtllPK invMovimientoDtllPK, BigDecimal cantidad, BigInteger auxCantidad, Double costoTotal, Double costoUnitario, BigInteger valorCompra, BigDecimal porcDesc1, BigDecimal porcDesc2, BigDecimal porcentajeIva, BigInteger descuento, BigDecimal porcDesc3) {
        this.invMovimientoDtllPK = invMovimientoDtllPK;
        this.cantidad = cantidad;
        this.auxCantidad = auxCantidad;
        this.costoTotal = costoTotal;
        this.costoUnitario = costoUnitario;
        this.valorCompra = valorCompra;
        this.porcDesc1 = porcDesc1;
        this.porcDesc2 = porcDesc2;
        this.porcentajeIva = porcentajeIva;
        this.descuento = descuento;
        this.porcDesc3 = porcDesc3;
    }

    public InvMovimientoDtll(String codEmpresa, String codDocumento, long numDocumento, short numLinea) {
        this.invMovimientoDtllPK = new InvMovimientoDtllPK(codEmpresa, codDocumento, numDocumento, numLinea);
    }

    public InvMovimientoDtllPK getInvMovimientoDtllPK() {
        return invMovimientoDtllPK;
    }

    public void setInvMovimientoDtllPK(InvMovimientoDtllPK invMovimientoDtllPK) {
        this.invMovimientoDtllPK = invMovimientoDtllPK;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public void setCodUnidad(String codUnidad) {
        this.codUnidad = codUnidad;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigInteger getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(BigInteger numPedido) {
        this.numPedido = numPedido;
    }

    public BigInteger getAuxCantidad() {
        return auxCantidad;
    }

    public void setAuxCantidad(BigInteger auxCantidad) {
        this.auxCantidad = auxCantidad;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(Double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigInteger valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public BigDecimal getPorcDesc1() {
        return porcDesc1;
    }

    public void setPorcDesc1(BigDecimal porcDesc1) {
        this.porcDesc1 = porcDesc1;
    }

    public BigDecimal getPorcDesc2() {
        return porcDesc2;
    }

    public void setPorcDesc2(BigDecimal porcDesc2) {
        this.porcDesc2 = porcDesc2;
    }

    public BigDecimal getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(BigDecimal porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public BigInteger getDescuento() {
        return descuento;
    }

    public void setDescuento(BigInteger descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPorcDesc3() {
        return porcDesc3;
    }

    public void setPorcDesc3(BigDecimal porcDesc3) {
        this.porcDesc3 = porcDesc3;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public BigInteger getFactor() {
        return factor;
    }

    public void setFactor(BigInteger factor) {
        this.factor = factor;
    }

    public InvBodegaArt getInvBodegaArt() {
        return invBodegaArt;
    }

    public void setInvBodegaArt(InvBodegaArt invBodegaArt) {
        this.invBodegaArt = invBodegaArt;
    }

    public InvMovimientoCab getInvMovimientoCab() {
        return invMovimientoCab;
    }

    public void setInvMovimientoCab(InvMovimientoCab invMovimientoCab) {
        this.invMovimientoCab = invMovimientoCab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invMovimientoDtllPK != null ? invMovimientoDtllPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMovimientoDtll)) {
            return false;
        }
        InvMovimientoDtll other = (InvMovimientoDtll) object;
        if ((this.invMovimientoDtllPK == null && other.invMovimientoDtllPK != null) || (this.invMovimientoDtllPK != null && !this.invMovimientoDtllPK.equals(other.invMovimientoDtllPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvMovimientoDtll[ invMovimientoDtllPK=" + invMovimientoDtllPK + " ]";
    }

}
