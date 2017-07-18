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
public class PrySupervisorPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_SUPERVISOR")
    private int codSupervisor;

    public PrySupervisorPK() {
    }

    public PrySupervisorPK(String codEmpresa, int codSupervisor) {
        this.codEmpresa = codEmpresa;
        this.codSupervisor = codSupervisor;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodSupervisor() {
        return codSupervisor;
    }

    public void setCodSupervisor(int codSupervisor) {
        this.codSupervisor = codSupervisor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) codSupervisor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrySupervisorPK)) {
            return false;
        }
        PrySupervisorPK other = (PrySupervisorPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.codSupervisor != other.codSupervisor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.PrySupervisorPK[ codEmpresa=" + codEmpresa + ", codSupervisor=" + codSupervisor + " ]";
    }
    
}
