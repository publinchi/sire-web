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
public class CxcInformePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_INFORME")
    private String codInforme;
    @Basic(optional = false)
    @Column(name = "NUM_INFORME")
    private BigInteger numInforme;

    public CxcInformePK() {
    }

    public CxcInformePK(String codEmpresa, String codInforme, BigInteger numInforme) {
        this.codEmpresa = codEmpresa;
        this.codInforme = codInforme;
        this.numInforme = numInforme;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodInforme() {
        return codInforme;
    }

    public void setCodInforme(String codInforme) {
        this.codInforme = codInforme;
    }

    public BigInteger getNumInforme() {
        return numInforme;
    }

    public void setNumInforme(BigInteger numInforme) {
        this.numInforme = numInforme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codInforme != null ? codInforme.hashCode() : 0);
        hash += (numInforme != null ? numInforme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcInformePK)) {
            return false;
        }
        CxcInformePK other = (CxcInformePK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codInforme == null && other.codInforme != null) || (this.codInforme != null && !this.codInforme.equals(other.codInforme))) {
            return false;
        }
        if ((this.numInforme == null && other.numInforme != null) || (this.numInforme != null && !this.numInforme.equals(other.numInforme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcInformePK[ codEmpresa=" + codEmpresa + ", codInforme=" + codInforme + ", numInforme=" + numInforme + " ]";
    }
    
}
