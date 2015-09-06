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
public class FacTmpFactDPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "EI")
    private String ei;
    @Basic(optional = false)
    @Column(name = "EGRESO_INV")
    private int egresoInv;
    @Basic(optional = false)
    @Column(name = "AUXILIAR")
    private long auxiliar;

    public FacTmpFactDPK() {
    }

    public FacTmpFactDPK(String codEmpresa, String ei, int egresoInv, long auxiliar) {
        this.codEmpresa = codEmpresa;
        this.ei = ei;
        this.egresoInv = egresoInv;
        this.auxiliar = auxiliar;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getEi() {
        return ei;
    }

    public void setEi(String ei) {
        this.ei = ei;
    }

    public int getEgresoInv() {
        return egresoInv;
    }

    public void setEgresoInv(int egresoInv) {
        this.egresoInv = egresoInv;
    }

    public long getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(long auxiliar) {
        this.auxiliar = auxiliar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (ei != null ? ei.hashCode() : 0);
        hash += (int) egresoInv;
        hash += (int) auxiliar;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacTmpFactDPK)) {
            return false;
        }
        FacTmpFactDPK other = (FacTmpFactDPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.ei == null && other.ei != null) || (this.ei != null && !this.ei.equals(other.ei))) {
            return false;
        }
        if (this.egresoInv != other.egresoInv) {
            return false;
        }
        if (this.auxiliar != other.auxiliar) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacTmpFactDPK[ codEmpresa=" + codEmpresa + ", ei=" + ei + ", egresoInv=" + egresoInv + ", auxiliar=" + auxiliar + " ]";
    }
    
}
