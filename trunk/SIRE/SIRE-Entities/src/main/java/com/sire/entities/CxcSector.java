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
@Table(name = "CXC_SECTOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcSector.findAll", query = "SELECT c FROM CxcSector c"),
    @NamedQuery(name = "CxcSector.findByCodEmpresa", query = "SELECT c FROM CxcSector c WHERE c.cxcSectorPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcSector.findByCodZona", query = "SELECT c FROM CxcSector c WHERE c.cxcSectorPK.codZona = :codZona"),
    @NamedQuery(name = "CxcSector.findByCodSector", query = "SELECT c FROM CxcSector c WHERE c.cxcSectorPK.codSector = :codSector"),
    @NamedQuery(name = "CxcSector.findByDescSector", query = "SELECT c FROM CxcSector c WHERE c.descSector = :descSector"),
    @NamedQuery(name = "CxcSector.findByEstado", query = "SELECT c FROM CxcSector c WHERE c.estado = :estado")})
public class CxcSector implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcSectorPK cxcSectorPK;
    @Basic(optional = false)
    @Column(name = "DESC_SECTOR")
    private String descSector;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cxcSector")
    private List<CxcCliente> cxcClienteList;

    public CxcSector() {
    }

    public CxcSector(CxcSectorPK cxcSectorPK) {
        this.cxcSectorPK = cxcSectorPK;
    }

    public CxcSector(CxcSectorPK cxcSectorPK, String descSector, String estado) {
        this.cxcSectorPK = cxcSectorPK;
        this.descSector = descSector;
        this.estado = estado;
    }

    public CxcSector(String codEmpresa, String codZona, String codSector) {
        this.cxcSectorPK = new CxcSectorPK(codEmpresa, codZona, codSector);
    }

    public CxcSectorPK getCxcSectorPK() {
        return cxcSectorPK;
    }

    public void setCxcSectorPK(CxcSectorPK cxcSectorPK) {
        this.cxcSectorPK = cxcSectorPK;
    }

    public String getDescSector() {
        return descSector;
    }

    public void setDescSector(String descSector) {
        this.descSector = descSector;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (cxcSectorPK != null ? cxcSectorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcSector)) {
            return false;
        }
        CxcSector other = (CxcSector) object;
        if ((this.cxcSectorPK == null && other.cxcSectorPK != null) || (this.cxcSectorPK != null && !this.cxcSectorPK.equals(other.cxcSectorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcSector[ cxcSectorPK=" + cxcSectorPK + " ]";
    }
    
}
