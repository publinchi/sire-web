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
public class GnrUsuaModPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_MODULO")
    private String codModulo;
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;

    public GnrUsuaModPK() {
    }

    public GnrUsuaModPK(String codModulo, String nombreUsuario, String codEmpresa) {
        this.codModulo = codModulo;
        this.nombreUsuario = nombreUsuario;
        this.codEmpresa = codEmpresa;
    }

    public String getCodModulo() {
        return codModulo;
    }

    public void setCodModulo(String codModulo) {
        this.codModulo = codModulo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codModulo != null ? codModulo.hashCode() : 0);
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrUsuaModPK)) {
            return false;
        }
        GnrUsuaModPK other = (GnrUsuaModPK) object;
        if ((this.codModulo == null && other.codModulo != null) || (this.codModulo != null && !this.codModulo.equals(other.codModulo))) {
            return false;
        }
        if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
            return false;
        }
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrUsuaModPK[ codModulo=" + codModulo + ", nombreUsuario=" + nombreUsuario + ", codEmpresa=" + codEmpresa + " ]";
    }
    
}
