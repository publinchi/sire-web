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
public class FacCatalogoPrecioCPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_CATALOGO")
    private String codCatalogo;
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;

    public FacCatalogoPrecioCPK() {
    }

    public FacCatalogoPrecioCPK(String codCatalogo, String codEmpresa) {
        this.codCatalogo = codCatalogo;
        this.codEmpresa = codEmpresa;
    }

    public String getCodCatalogo() {
        return codCatalogo;
    }

    public void setCodCatalogo(String codCatalogo) {
        this.codCatalogo = codCatalogo;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCatalogo != null ? codCatalogo.hashCode() : 0);
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCatalogoPrecioCPK)) {
            return false;
        }
        FacCatalogoPrecioCPK other = (FacCatalogoPrecioCPK) object;
        if ((this.codCatalogo == null && other.codCatalogo != null) || (this.codCatalogo != null && !this.codCatalogo.equals(other.codCatalogo))) {
            return false;
        }
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacCatalogoPrecioCPK[ codCatalogo=" + codCatalogo + ", codEmpresa=" + codEmpresa + " ]";
    }
    
}
