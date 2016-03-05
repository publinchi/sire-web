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
public class InvTransaccionesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_MOVIMIENTO")
    private String codMovimiento;

    public InvTransaccionesPK() {
    }

    public InvTransaccionesPK(String codEmpresa, String codMovimiento) {
        this.codEmpresa = codEmpresa;
        this.codMovimiento = codMovimiento;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodMovimiento() {
        return codMovimiento;
    }

    public void setCodMovimiento(String codMovimiento) {
        this.codMovimiento = codMovimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codMovimiento != null ? codMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvTransaccionesPK)) {
            return false;
        }
        InvTransaccionesPK other = (InvTransaccionesPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codMovimiento == null && other.codMovimiento != null) || (this.codMovimiento != null && !this.codMovimiento.equals(other.codMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvTransaccionesPK[ codEmpresa=" + codEmpresa + ", codMovimiento=" + codMovimiento + " ]";
    }
    
}
