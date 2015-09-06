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
public class InvBodegaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_BODEGA")
    private String codBodega;

    public InvBodegaPK() {
    }

    public InvBodegaPK(String codEmpresa, String codBodega) {
        this.codEmpresa = codEmpresa;
        this.codBodega = codBodega;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodBodega() {
        return codBodega;
    }

    public void setCodBodega(String codBodega) {
        this.codBodega = codBodega;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codBodega != null ? codBodega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvBodegaPK)) {
            return false;
        }
        InvBodegaPK other = (InvBodegaPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codBodega == null && other.codBodega != null) || (this.codBodega != null && !this.codBodega.equals(other.codBodega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvBodegaPK[ codEmpresa=" + codEmpresa + ", codBodega=" + codBodega + " ]";
    }
    
}
