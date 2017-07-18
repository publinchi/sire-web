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
 * @author publio
 */
@Embeddable
public class BanCtaCtePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "CTA_CORRIENTE")
    private String ctaCorriente;

    public BanCtaCtePK() {
    }

    public BanCtaCtePK(String codEmpresa, String ctaCorriente) {
        this.codEmpresa = codEmpresa;
        this.ctaCorriente = ctaCorriente;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCtaCorriente() {
        return ctaCorriente;
    }

    public void setCtaCorriente(String ctaCorriente) {
        this.ctaCorriente = ctaCorriente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (ctaCorriente != null ? ctaCorriente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BanCtaCtePK)) {
            return false;
        }
        BanCtaCtePK other = (BanCtaCtePK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.ctaCorriente == null && other.ctaCorriente != null) || (this.ctaCorriente != null && !this.ctaCorriente.equals(other.ctaCorriente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.BanCtaCtePK[ codEmpresa=" + codEmpresa + ", ctaCorriente=" + ctaCorriente + " ]";
    }
    
}
