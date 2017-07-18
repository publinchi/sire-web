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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author pestupinan
 */
@Embeddable
public class ComVisitaClientePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_VISITA")
    private int numVisita;

    public ComVisitaClientePK() {
    }

    public ComVisitaClientePK(String codEmpresa, String codDocumento, int numVisita) {
        this.codEmpresa = codEmpresa;
        this.codDocumento = codDocumento;
        this.numVisita = numVisita;
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

    public int getNumVisita() {
        return numVisita;
    }

    public void setNumVisita(int numVisita) {
        this.numVisita = numVisita;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (int) numVisita;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComVisitaClientePK)) {
            return false;
        }
        ComVisitaClientePK other = (ComVisitaClientePK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if (this.numVisita != other.numVisita) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.ComVisitaClientePK[ codEmpresa=" + codEmpresa + ", codDocumento=" + codDocumento + ", numVisita=" + numVisita + " ]";
    }
    
}
