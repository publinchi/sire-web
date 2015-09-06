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
public class InvMovimientoCabPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @Column(name = "NUM_DOCUMENTO")
    private long numDocumento;

    public InvMovimientoCabPK() {
    }

    public InvMovimientoCabPK(String codEmpresa, String codDocumento, long numDocumento) {
        this.codEmpresa = codEmpresa;
        this.codDocumento = codDocumento;
        this.numDocumento = numDocumento;
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

    public long getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(long numDocumento) {
        this.numDocumento = numDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (int) numDocumento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMovimientoCabPK)) {
            return false;
        }
        InvMovimientoCabPK other = (InvMovimientoCabPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if (this.numDocumento != other.numDocumento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvMovimientoCabPK[ codEmpresa=" + codEmpresa + ", codDocumento=" + codDocumento + ", numDocumento=" + numDocumento + " ]";
    }
    
}
