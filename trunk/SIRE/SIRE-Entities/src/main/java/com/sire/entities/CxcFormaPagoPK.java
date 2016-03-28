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
public class CxcFormaPagoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_PAGO")
    private String codPago;

    public CxcFormaPagoPK() {
    }

    public CxcFormaPagoPK(String codEmpresa, String codPago) {
        this.codEmpresa = codEmpresa;
        this.codPago = codPago;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodPago() {
        return codPago;
    }

    public void setCodPago(String codPago) {
        this.codPago = codPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codPago != null ? codPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcFormaPagoPK)) {
            return false;
        }
        CxcFormaPagoPK other = (CxcFormaPagoPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codPago == null && other.codPago != null) || (this.codPago != null && !this.codPago.equals(other.codPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcFormaPagoPK[ codEmpresa=" + codEmpresa + ", codPago=" + codPago + " ]";
    }
    
}
