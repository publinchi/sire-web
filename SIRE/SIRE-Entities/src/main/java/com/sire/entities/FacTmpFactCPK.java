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
 * @author pestupinan
 */
@Embeddable
public class FacTmpFactCPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "EGRESO_INV")
    private int egresoInv;
    @Basic(optional = false)
    @Column(name = "EI")
    private String ei;

    public FacTmpFactCPK() {
    }

    public FacTmpFactCPK(String codEmpresa, int egresoInv, String ei) {
        this.codEmpresa = codEmpresa;
        this.egresoInv = egresoInv;
        this.ei = ei;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getEgresoInv() {
        return egresoInv;
    }

    public void setEgresoInv(int egresoInv) {
        this.egresoInv = egresoInv;
    }

    public String getEi() {
        return ei;
    }

    public void setEi(String ei) {
        this.ei = ei;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) egresoInv;
        hash += (ei != null ? ei.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacTmpFactCPK)) {
            return false;
        }
        FacTmpFactCPK other = (FacTmpFactCPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.egresoInv != other.egresoInv) {
            return false;
        }
        if ((this.ei == null && other.ei != null) || (this.ei != null && !this.ei.equals(other.ei))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacTmpFactCPK[ codEmpresa=" + codEmpresa + ", egresoInv=" + egresoInv + ", ei=" + ei + " ]";
    }
    
}
