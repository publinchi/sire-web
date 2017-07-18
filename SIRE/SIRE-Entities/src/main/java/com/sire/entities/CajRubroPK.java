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
public class CajRubroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_RUBRO")
    private int codRubro;

    public CajRubroPK() {
    }

    public CajRubroPK(String codEmpresa, int codRubro) {
        this.codEmpresa = codEmpresa;
        this.codRubro = codRubro;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodRubro() {
        return codRubro;
    }

    public void setCodRubro(int codRubro) {
        this.codRubro = codRubro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) codRubro;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajRubroPK)) {
            return false;
        }
        CajRubroPK other = (CajRubroPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.codRubro != other.codRubro) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CajRubroPK[ codEmpresa=" + codEmpresa + ", codRubro=" + codRubro + " ]";
    }
    
}
