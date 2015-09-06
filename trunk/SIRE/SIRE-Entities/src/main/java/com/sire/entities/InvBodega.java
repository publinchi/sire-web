/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "INV_BODEGA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvBodega.findAll", query = "SELECT i FROM InvBodega i"),
    @NamedQuery(name = "InvBodega.findByCodEmpresa", query = "SELECT i FROM InvBodega i WHERE i.invBodegaPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvBodega.findByCodBodega", query = "SELECT i FROM InvBodega i WHERE i.invBodegaPK.codBodega = :codBodega"),
    @NamedQuery(name = "InvBodega.findByDescBodega", query = "SELECT i FROM InvBodega i WHERE i.descBodega = :descBodega"),
    @NamedQuery(name = "InvBodega.findByCodigoCuenta", query = "SELECT i FROM InvBodega i WHERE i.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "InvBodega.findByDireccion", query = "SELECT i FROM InvBodega i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "InvBodega.findByAliasBodega", query = "SELECT i FROM InvBodega i WHERE i.aliasBodega = :aliasBodega")})
public class InvBodega implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvBodegaPK invBodegaPK;
    @Basic(optional = false)
    @Column(name = "DESC_BODEGA")
    private String descBodega;
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "ALIAS_BODEGA")
    private String aliasBodega;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invBodega")
    private List<InvInventario> invInventarioList;

    public InvBodega() {
    }

    public InvBodega(InvBodegaPK invBodegaPK) {
        this.invBodegaPK = invBodegaPK;
    }

    public InvBodega(InvBodegaPK invBodegaPK, String descBodega) {
        this.invBodegaPK = invBodegaPK;
        this.descBodega = descBodega;
    }

    public InvBodega(String codEmpresa, String codBodega) {
        this.invBodegaPK = new InvBodegaPK(codEmpresa, codBodega);
    }

    public InvBodegaPK getInvBodegaPK() {
        return invBodegaPK;
    }

    public void setInvBodegaPK(InvBodegaPK invBodegaPK) {
        this.invBodegaPK = invBodegaPK;
    }

    public String getDescBodega() {
        return descBodega;
    }

    public void setDescBodega(String descBodega) {
        this.descBodega = descBodega;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAliasBodega() {
        return aliasBodega;
    }

    public void setAliasBodega(String aliasBodega) {
        this.aliasBodega = aliasBodega;
    }

    @XmlTransient
    public List<InvInventario> getInvInventarioList() {
        return invInventarioList;
    }

    public void setInvInventarioList(List<InvInventario> invInventarioList) {
        this.invInventarioList = invInventarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invBodegaPK != null ? invBodegaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvBodega)) {
            return false;
        }
        InvBodega other = (InvBodega) object;
        if ((this.invBodegaPK == null && other.invBodegaPK != null) || (this.invBodegaPK != null && !this.invBodegaPK.equals(other.invBodegaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvBodega[ invBodegaPK=" + invBodegaPK + " ]";
    }
    
}
