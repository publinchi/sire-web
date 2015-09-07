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
public class InvGrupo2PK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO1")
    private String codGrupo1;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO2")
    private String codGrupo2;

    public InvGrupo2PK() {
    }

    public InvGrupo2PK(String codEmpresa, String codGrupo1, String codGrupo2) {
        this.codEmpresa = codEmpresa;
        this.codGrupo1 = codGrupo1;
        this.codGrupo2 = codGrupo2;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodGrupo1() {
        return codGrupo1;
    }

    public void setCodGrupo1(String codGrupo1) {
        this.codGrupo1 = codGrupo1;
    }

    public String getCodGrupo2() {
        return codGrupo2;
    }

    public void setCodGrupo2(String codGrupo2) {
        this.codGrupo2 = codGrupo2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codGrupo1 != null ? codGrupo1.hashCode() : 0);
        hash += (codGrupo2 != null ? codGrupo2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvGrupo2PK)) {
            return false;
        }
        InvGrupo2PK other = (InvGrupo2PK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codGrupo1 == null && other.codGrupo1 != null) || (this.codGrupo1 != null && !this.codGrupo1.equals(other.codGrupo1))) {
            return false;
        }
        if ((this.codGrupo2 == null && other.codGrupo2 != null) || (this.codGrupo2 != null && !this.codGrupo2.equals(other.codGrupo2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvGrupo2PK[ codEmpresa=" + codEmpresa + ", codGrupo1=" + codGrupo1 + ", codGrupo2=" + codGrupo2 + " ]";
    }
    
}
