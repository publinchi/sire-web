/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author publio
 */
@Embeddable
public class CxcPagoContadoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @Column(name = "NUM_PAGO")
    private BigInteger numPago;

    public CxcPagoContadoPK() {
    }

    public CxcPagoContadoPK(String codEmpresa, String codDocumento, BigInteger numPago) {
        this.codEmpresa = codEmpresa;
        this.codDocumento = codDocumento;
        this.numPago = numPago;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public BigInteger getNumPago() {
        return numPago;
    }

    public void setNumPago(BigInteger numPago) {
        this.numPago = numPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (numPago != null ? numPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcPagoContadoPK)) {
            return false;
        }
        CxcPagoContadoPK other = (CxcPagoContadoPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if ((this.numPago == null && other.numPago != null) || (this.numPago != null && !this.numPago.equals(other.numPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcPagoContadoPK[ codEmpresa=" + codEmpresa + ", codDocumento=" + codDocumento + ", numPago=" + numPago + " ]";
    }
    
}
