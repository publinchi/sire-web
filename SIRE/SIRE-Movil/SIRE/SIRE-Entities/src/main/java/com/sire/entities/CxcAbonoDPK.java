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
public class CxcAbonoDPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @Column(name = "NUM_ABONO")
    private int numAbono;
    @Basic(optional = false)
    @Column(name = "AUXILIAR")
    private int auxiliar;

    public CxcAbonoDPK() {
    }

    public CxcAbonoDPK(String codEmpresa, String codDocumento, int numAbono, int auxiliar) {
        this.codEmpresa = codEmpresa;
        this.codDocumento = codDocumento;
        this.numAbono = numAbono;
        this.auxiliar = auxiliar;
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

    public int getNumAbono() {
        return numAbono;
    }

    public void setNumAbono(int numAbono) {
        this.numAbono = numAbono;
    }

    public int getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(int auxiliar) {
        this.auxiliar = auxiliar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (int) numAbono;
        hash += (int) auxiliar;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcAbonoDPK)) {
            return false;
        }
        CxcAbonoDPK other = (CxcAbonoDPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if (this.numAbono != other.numAbono) {
            return false;
        }
        if (this.auxiliar != other.auxiliar) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcAbonoDPK[ codEmpresa=" + codEmpresa + ", codDocumento=" + codDocumento + ", numAbono=" + numAbono + ", auxiliar=" + auxiliar + " ]";
    }
    
}
