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
public class CxcDocCobrarPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @Column(name = "NUM_DOCUMENTO")
    private BigInteger numDocumento;
    @Basic(optional = false)
    @Column(name = "NUMERO_CUOTA")
    private BigInteger numeroCuota;

    public CxcDocCobrarPK() {
    }

    public CxcDocCobrarPK(String codEmpresa, String codDocumento, BigInteger numDocumento, BigInteger numeroCuota) {
        this.codEmpresa = codEmpresa;
        this.codDocumento = codDocumento;
        this.numDocumento = numDocumento;
        this.numeroCuota = numeroCuota;
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

    public BigInteger getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(BigInteger numDocumento) {
        this.numDocumento = numDocumento;
    }

    public BigInteger getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(BigInteger numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (numDocumento != null ? numDocumento.hashCode() : 0);
        hash += (numeroCuota != null ? numeroCuota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcDocCobrarPK)) {
            return false;
        }
        CxcDocCobrarPK other = (CxcDocCobrarPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if ((this.numDocumento == null && other.numDocumento != null) || (this.numDocumento != null && !this.numDocumento.equals(other.numDocumento))) {
            return false;
        }
        if ((this.numeroCuota == null && other.numeroCuota != null) || (this.numeroCuota != null && !this.numeroCuota.equals(other.numeroCuota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcDocCobrarPK[ codEmpresa=" + codEmpresa + ", codDocumento=" + codDocumento + ", numDocumento=" + numDocumento + ", numeroCuota=" + numeroCuota + " ]";
    }
    
}
