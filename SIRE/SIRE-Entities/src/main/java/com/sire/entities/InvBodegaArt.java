/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "INV_BODEGA_ART")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvBodegaArt.findAll", query = "SELECT i FROM InvBodegaArt i"),
    @NamedQuery(name = "InvBodegaArt.findByCodEmpresa", query = "SELECT i FROM InvBodegaArt i WHERE i.invBodegaArtPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvBodegaArt.findByCodBodega", query = "SELECT i FROM InvBodegaArt i WHERE i.invBodegaArtPK.codBodega = :codBodega"),
    @NamedQuery(name = "InvBodegaArt.findByCodArticulo", query = "SELECT i FROM InvBodegaArt i WHERE i.invBodegaArtPK.codArticulo = :codArticulo AND i.invBodegaArtPK.codInventario = :codInventario"),
    @NamedQuery(name = "InvBodegaArt.findByCodInventario", query = "SELECT i FROM InvBodegaArt i WHERE i.invBodegaArtPK.codInventario = :codInventario"),
    @NamedQuery(name = "InvBodegaArt.findByStockMinimo", query = "SELECT i FROM InvBodegaArt i WHERE i.stockMinimo = :stockMinimo"),
    @NamedQuery(name = "InvBodegaArt.findByExistencia", query = "SELECT i FROM InvBodegaArt i WHERE i.existencia = :existencia"),
    @NamedQuery(name = "InvBodegaArt.findByExistPendEnt", query = "SELECT i FROM InvBodegaArt i WHERE i.existPendEnt = :existPendEnt"),
    @NamedQuery(name = "InvBodegaArt.findByExistPendSal", query = "SELECT i FROM InvBodegaArt i WHERE i.existPendSal = :existPendSal")})
public class InvBodegaArt implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvBodegaArtPK invBodegaArtPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "STOCK_MINIMO")
    private BigDecimal stockMinimo;
    @Basic(optional = false)
    @Column(name = "EXISTENCIA")
    private BigDecimal existencia;
    @Basic(optional = false)
    @Column(name = "EXIST_PEND_ENT")
    private BigDecimal existPendEnt;
    @Basic(optional = false)
    @Column(name = "EXIST_PEND_SAL")
    private BigDecimal existPendSal;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_BODEGA", referencedColumnName = "COD_BODEGA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_INVENTARIO", referencedColumnName = "COD_INVENTARIO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private InvInventario invInventario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invBodegaArt")
    private List<InvMovimientoDtll> invMovimientoDtllList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invBodegaArt")
    private List<InvMovimientoDtllF> invMovimientoDtllFList;

    public InvBodegaArt() {
    }

    public InvBodegaArt(InvBodegaArtPK invBodegaArtPK) {
        this.invBodegaArtPK = invBodegaArtPK;
    }

    public InvBodegaArt(InvBodegaArtPK invBodegaArtPK, BigDecimal existencia, BigDecimal existPendEnt, BigDecimal existPendSal) {
        this.invBodegaArtPK = invBodegaArtPK;
        this.existencia = existencia;
        this.existPendEnt = existPendEnt;
        this.existPendSal = existPendSal;
    }

    public InvBodegaArt(String codEmpresa, String codBodega, int codArticulo, String codInventario) {
        this.invBodegaArtPK = new InvBodegaArtPK(codEmpresa, codBodega, codArticulo, codInventario);
    }

    public InvBodegaArtPK getInvBodegaArtPK() {
        return invBodegaArtPK;
    }

    public void setInvBodegaArtPK(InvBodegaArtPK invBodegaArtPK) {
        this.invBodegaArtPK = invBodegaArtPK;
    }

    public BigDecimal getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(BigDecimal stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public BigDecimal getExistencia() {
        return existencia;
    }

    public void setExistencia(BigDecimal existencia) {
        this.existencia = existencia;
    }

    public BigDecimal getExistPendEnt() {
        return existPendEnt;
    }

    public void setExistPendEnt(BigDecimal existPendEnt) {
        this.existPendEnt = existPendEnt;
    }

    public BigDecimal getExistPendSal() {
        return existPendSal;
    }

    public void setExistPendSal(BigDecimal existPendSal) {
        this.existPendSal = existPendSal;
    }

    public InvInventario getInvInventario() {
        return invInventario;
    }

    public void setInvInventario(InvInventario invInventario) {
        this.invInventario = invInventario;
    }

    @XmlTransient
    public List<InvMovimientoDtll> getInvMovimientoDtllList() {
        return invMovimientoDtllList;
    }

    public void setInvMovimientoDtllList(List<InvMovimientoDtll> invMovimientoDtllList) {
        this.invMovimientoDtllList = invMovimientoDtllList;
    }

    @XmlTransient
    public List<InvMovimientoDtllF> getInvMovimientoDtllFList() {
        return invMovimientoDtllFList;
    }

    public void setInvMovimientoDtllFList(List<InvMovimientoDtllF> invMovimientoDtllFList) {
        this.invMovimientoDtllFList = invMovimientoDtllFList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invBodegaArtPK != null ? invBodegaArtPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvBodegaArt)) {
            return false;
        }
        InvBodegaArt other = (InvBodegaArt) object;
        if ((this.invBodegaArtPK == null && other.invBodegaArtPK != null) || (this.invBodegaArtPK != null && !this.invBodegaArtPK.equals(other.invBodegaArtPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvBodegaArt[ invBodegaArtPK=" + invBodegaArtPK + " ]";
    }

}
