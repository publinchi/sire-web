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
public class PryProyectoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_PROYECTO")
    private int codProyecto;

    public PryProyectoPK() {
    }

    public PryProyectoPK(String codEmpresa, int codProyecto) {
        this.codEmpresa = codEmpresa;
        this.codProyecto = codProyecto;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(int codProyecto) {
        this.codProyecto = codProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) codProyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PryProyectoPK)) {
            return false;
        }
        PryProyectoPK other = (PryProyectoPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.codProyecto != other.codProyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.PryProyectoPK[ codEmpresa=" + codEmpresa + ", codProyecto=" + codProyecto + " ]";
    }
    
}
