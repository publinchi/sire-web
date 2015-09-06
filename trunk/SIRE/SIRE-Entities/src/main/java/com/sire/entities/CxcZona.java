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
@Table(name = "CXC_ZONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcZona.findAll", query = "SELECT c FROM CxcZona c"),
    @NamedQuery(name = "CxcZona.findByCodEmpresa", query = "SELECT c FROM CxcZona c WHERE c.cxcZonaPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcZona.findByCodZona", query = "SELECT c FROM CxcZona c WHERE c.cxcZonaPK.codZona = :codZona"),
    @NamedQuery(name = "CxcZona.findByDescZona", query = "SELECT c FROM CxcZona c WHERE c.descZona = :descZona")})
public class CxcZona implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcZonaPK cxcZonaPK;
    @Basic(optional = false)
    @Column(name = "DESC_ZONA")
    private String descZona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cxcZona")
    private List<CxcCliente> cxcClienteList;

    public CxcZona() {
    }

    public CxcZona(CxcZonaPK cxcZonaPK) {
        this.cxcZonaPK = cxcZonaPK;
    }

    public CxcZona(CxcZonaPK cxcZonaPK, String descZona) {
        this.cxcZonaPK = cxcZonaPK;
        this.descZona = descZona;
    }

    public CxcZona(String codEmpresa, String codZona) {
        this.cxcZonaPK = new CxcZonaPK(codEmpresa, codZona);
    }

    public CxcZonaPK getCxcZonaPK() {
        return cxcZonaPK;
    }

    public void setCxcZonaPK(CxcZonaPK cxcZonaPK) {
        this.cxcZonaPK = cxcZonaPK;
    }

    public String getDescZona() {
        return descZona;
    }

    public void setDescZona(String descZona) {
        this.descZona = descZona;
    }

    @XmlTransient
    public List<CxcCliente> getCxcClienteList() {
        return cxcClienteList;
    }

    public void setCxcClienteList(List<CxcCliente> cxcClienteList) {
        this.cxcClienteList = cxcClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcZonaPK != null ? cxcZonaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcZona)) {
            return false;
        }
        CxcZona other = (CxcZona) object;
        if ((this.cxcZonaPK == null && other.cxcZonaPK != null) || (this.cxcZonaPK != null && !this.cxcZonaPK.equals(other.cxcZonaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcZona[ cxcZonaPK=" + cxcZonaPK + " ]";
    }
    
}
