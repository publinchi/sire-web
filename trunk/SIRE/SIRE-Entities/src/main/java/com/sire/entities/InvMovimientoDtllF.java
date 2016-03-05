/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "INV_MOVIMIENTO_DTLL_F")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvMovimientoDtllF.findAll", query = "SELECT i FROM InvMovimientoDtllF i"),
    @NamedQuery(name = "InvMovimientoDtllF.findByCodEmpresa", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.invMovimientoDtllFPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvMovimientoDtllF.findByCodDocumento", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.invMovimientoDtllFPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "InvMovimientoDtllF.findByNumDocumento", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.invMovimientoDtllFPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "InvMovimientoDtllF.findByNumLinea", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.invMovimientoDtllFPK.numLinea = :numLinea"),
    @NamedQuery(name = "InvMovimientoDtllF.findByCodUnidad", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.codUnidad = :codUnidad"),
    @NamedQuery(name = "InvMovimientoDtllF.findByCantidad", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "InvMovimientoDtllF.findByAuxCantidad", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.auxCantidad = :auxCantidad"),
    @NamedQuery(name = "InvMovimientoDtllF.findByFactor", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.factor = :factor"),
    @NamedQuery(name = "InvMovimientoDtllF.findByOperador", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.operador = :operador"),
    @NamedQuery(name = "InvMovimientoDtllF.findByEstado", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvMovimientoDtllF.findByFechaEstado", query = "SELECT i FROM InvMovimientoDtllF i WHERE i.fechaEstado = :fechaEstado")})
public class InvMovimientoDtllF implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvMovimientoDtllFPK invMovimientoDtllFPK;
    @Column(name = "COD_UNIDAD")
    private String codUnidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "AUX_CANTIDAD")
    private BigDecimal auxCantidad;
    @Column(name = "FACTOR")
    private Integer factor;
    @Column(name = "OPERADOR")
    private String operador;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
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
    private InvMovimientoCabF invMovimientoCabF;

    public InvMovimientoDtllF() {
    }

    public InvMovimientoDtllF(InvMovimientoDtllFPK invMovimientoDtllFPK) {
        this.invMovimientoDtllFPK = invMovimientoDtllFPK;
    }

    public InvMovimientoDtllF(String codEmpresa, String codDocumento, int numDocumento, int numLinea) {
        this.invMovimientoDtllFPK = new InvMovimientoDtllFPK(codEmpresa, codDocumento, numDocumento, numLinea);
    }

    public InvMovimientoDtllFPK getInvMovimientoDtllFPK() {
        return invMovimientoDtllFPK;
    }

    public void setInvMovimientoDtllFPK(InvMovimientoDtllFPK invMovimientoDtllFPK) {
        this.invMovimientoDtllFPK = invMovimientoDtllFPK;
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

    public BigDecimal getAuxCantidad() {
        return auxCantidad;
    }

    public void setAuxCantidad(BigDecimal auxCantidad) {
        this.auxCantidad = auxCantidad;
    }

    public Integer getFactor() {
        return factor;
    }

    public void setFactor(Integer factor) {
        this.factor = factor;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public InvBodegaArt getInvBodegaArt() {
        return invBodegaArt;
    }

    public void setInvBodegaArt(InvBodegaArt invBodegaArt) {
        this.invBodegaArt = invBodegaArt;
    }

    public InvMovimientoCabF getInvMovimientoCabF() {
        return invMovimientoCabF;
    }

    public void setInvMovimientoCabF(InvMovimientoCabF invMovimientoCabF) {
        this.invMovimientoCabF = invMovimientoCabF;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invMovimientoDtllFPK != null ? invMovimientoDtllFPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMovimientoDtllF)) {
            return false;
        }
        InvMovimientoDtllF other = (InvMovimientoDtllF) object;
        if ((this.invMovimientoDtllFPK == null && other.invMovimientoDtllFPK != null) || (this.invMovimientoDtllFPK != null && !this.invMovimientoDtllFPK.equals(other.invMovimientoDtllFPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvMovimientoDtllF[ invMovimientoDtllFPK=" + invMovimientoDtllFPK + " ]";
    }
    
}
