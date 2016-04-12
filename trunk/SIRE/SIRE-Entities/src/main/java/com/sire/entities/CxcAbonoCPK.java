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
public class CxcAbonoCPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @Column(name = "NUM_ABONO")
    private BigInteger numAbono;

    public CxcAbonoCPK() {
    }

    public CxcAbonoCPK(String codEmpresa, String codDocumento, BigInteger numAbono) {
        this.codEmpresa = codEmpresa;
        this.codDocumento = codDocumento;
        this.numAbono = numAbono;
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

    public BigInteger getNumAbono() {
        return numAbono;
    }

    public void setNumAbono(BigInteger numAbono) {
        this.numAbono = numAbono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (numAbono != null ? numAbono.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcAbonoCPK)) {
            return false;
        }
        CxcAbonoCPK other = (CxcAbonoCPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if ((this.numAbono == null && other.numAbono != null) || (this.numAbono != null && !this.numAbono.equals(other.numAbono))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcAbonoCPK[ codEmpresa=" + codEmpresa + ", codDocumento=" + codDocumento + ", numAbono=" + numAbono + " ]";
    }
    
}
