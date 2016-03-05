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
public class FacCatalogoGeneralPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_CATALOGO")
    private String codCatalogo;

    public FacCatalogoGeneralPK() {
    }

    public FacCatalogoGeneralPK(String codEmpresa, String codCatalogo) {
        this.codEmpresa = codEmpresa;
        this.codCatalogo = codCatalogo;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodCatalogo() {
        return codCatalogo;
    }

    public void setCodCatalogo(String codCatalogo) {
        this.codCatalogo = codCatalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codCatalogo != null ? codCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCatalogoGeneralPK)) {
            return false;
        }
        FacCatalogoGeneralPK other = (FacCatalogoGeneralPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codCatalogo == null && other.codCatalogo != null) || (this.codCatalogo != null && !this.codCatalogo.equals(other.codCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacCatalogoGeneralPK[ codEmpresa=" + codEmpresa + ", codCatalogo=" + codCatalogo + " ]";
    }
    
}
