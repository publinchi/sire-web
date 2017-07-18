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
public class PrySupervisorUsuarioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_SUPERVISOR")
    private int codSupervisor;
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;

    public PrySupervisorUsuarioPK() {
    }

    public PrySupervisorUsuarioPK(String codEmpresa, int codSupervisor, String nombreUsuario) {
        this.codEmpresa = codEmpresa;
        this.codSupervisor = codSupervisor;
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodSupervisor() {
        return codSupervisor;
    }

    public void setCodSupervisor(int codSupervisor) {
        this.codSupervisor = codSupervisor;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) codSupervisor;
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrySupervisorUsuarioPK)) {
            return false;
        }
        PrySupervisorUsuarioPK other = (PrySupervisorUsuarioPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.codSupervisor != other.codSupervisor) {
            return false;
        }
        if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.PrySupervisorUsuarioPK[ codEmpresa=" + codEmpresa + ", codSupervisor=" + codSupervisor + ", nombreUsuario=" + nombreUsuario + " ]";
    }
    
}
