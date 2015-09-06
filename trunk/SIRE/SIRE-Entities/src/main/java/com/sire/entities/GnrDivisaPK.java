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
public class GnrDivisaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;

    public GnrDivisaPK() {
    }

    public GnrDivisaPK(String codDivisa, String codEmpresa) {
        this.codDivisa = codDivisa;
        this.codEmpresa = codEmpresa;
    }

    public String getCodDivisa() {
        return codDivisa;
    }

    public void setCodDivisa(String codDivisa) {
        this.codDivisa = codDivisa;
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
        hash += (codDivisa != null ? codDivisa.hashCode() : 0);
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrDivisaPK)) {
            return false;
        }
        GnrDivisaPK other = (GnrDivisaPK) object;
        if ((this.codDivisa == null && other.codDivisa != null) || (this.codDivisa != null && !this.codDivisa.equals(other.codDivisa))) {
            return false;
        }
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrDivisaPK[ codDivisa=" + codDivisa + ", codEmpresa=" + codEmpresa + " ]";
    }
    
}
