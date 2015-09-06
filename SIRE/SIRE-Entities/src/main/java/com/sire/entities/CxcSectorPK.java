/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class CxcSectorPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_ZONA")
    private String codZona;
    @Basic(optional = false)
    @Column(name = "COD_SECTOR")
    private String codSector;

    public CxcSectorPK() {
    }

    public CxcSectorPK(String codEmpresa, String codZona, String codSector) {
        this.codEmpresa = codEmpresa;
        this.codZona = codZona;
        this.codSector = codSector;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodZona() {
        return codZona;
    }

    public void setCodZona(String codZona) {
        this.codZona = codZona;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codZona != null ? codZona.hashCode() : 0);
        hash += (codSector != null ? codSector.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcSectorPK)) {
            return false;
        }
        CxcSectorPK other = (CxcSectorPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codZona == null && other.codZona != null) || (this.codZona != null && !this.codZona.equals(other.codZona))) {
            return false;
        }
        if ((this.codSector == null && other.codSector != null) || (this.codSector != null && !this.codSector.equals(other.codSector))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcSectorPK[ codEmpresa=" + codEmpresa + ", codZona=" + codZona + ", codSector=" + codSector + " ]";
    }
    
}
