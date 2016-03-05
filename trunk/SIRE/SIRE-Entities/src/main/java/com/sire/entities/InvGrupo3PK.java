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
public class InvGrupo3PK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO1")
    private String codGrupo1;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO2")
    private String codGrupo2;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO3")
    private String codGrupo3;

    public InvGrupo3PK() {
    }

    public InvGrupo3PK(String codEmpresa, String codGrupo1, String codGrupo2, String codGrupo3) {
        this.codEmpresa = codEmpresa;
        this.codGrupo1 = codGrupo1;
        this.codGrupo2 = codGrupo2;
        this.codGrupo3 = codGrupo3;
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

    public String getCodGrupo3() {
        return codGrupo3;
    }

    public void setCodGrupo3(String codGrupo3) {
        this.codGrupo3 = codGrupo3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codGrupo1 != null ? codGrupo1.hashCode() : 0);
        hash += (codGrupo2 != null ? codGrupo2.hashCode() : 0);
        hash += (codGrupo3 != null ? codGrupo3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvGrupo3PK)) {
            return false;
        }
        InvGrupo3PK other = (InvGrupo3PK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codGrupo1 == null && other.codGrupo1 != null) || (this.codGrupo1 != null && !this.codGrupo1.equals(other.codGrupo1))) {
            return false;
        }
        if ((this.codGrupo2 == null && other.codGrupo2 != null) || (this.codGrupo2 != null && !this.codGrupo2.equals(other.codGrupo2))) {
            return false;
        }
        if ((this.codGrupo3 == null && other.codGrupo3 != null) || (this.codGrupo3 != null && !this.codGrupo3.equals(other.codGrupo3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvGrupo3PK[ codEmpresa=" + codEmpresa + ", codGrupo1=" + codGrupo1 + ", codGrupo2=" + codGrupo2 + ", codGrupo3=" + codGrupo3 + " ]";
    }
    
}
