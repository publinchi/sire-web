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
public class FacDescVolPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO1")
    private String codGrupo1;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO")
    private String codGrupo;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO2")
    private String codGrupo2;
    @Basic(optional = false)
    @Column(name = "COD_TIPO")
    private String codTipo;
    @Basic(optional = false)
    @Column(name = "COD_GRUPO3")
    private String codGrupo3;

    public FacDescVolPK() {
    }

    public FacDescVolPK(String codEmpresa, String codGrupo1, String codGrupo, String codGrupo2, String codTipo, String codGrupo3) {
        this.codEmpresa = codEmpresa;
        this.codGrupo1 = codGrupo1;
        this.codGrupo = codGrupo;
        this.codGrupo2 = codGrupo2;
        this.codTipo = codTipo;
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

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public String getCodGrupo2() {
        return codGrupo2;
    }

    public void setCodGrupo2(String codGrupo2) {
        this.codGrupo2 = codGrupo2;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
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
        hash += (codGrupo != null ? codGrupo.hashCode() : 0);
        hash += (codGrupo2 != null ? codGrupo2.hashCode() : 0);
        hash += (codTipo != null ? codTipo.hashCode() : 0);
        hash += (codGrupo3 != null ? codGrupo3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacDescVolPK)) {
            return false;
        }
        FacDescVolPK other = (FacDescVolPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codGrupo1 == null && other.codGrupo1 != null) || (this.codGrupo1 != null && !this.codGrupo1.equals(other.codGrupo1))) {
            return false;
        }
        if ((this.codGrupo == null && other.codGrupo != null) || (this.codGrupo != null && !this.codGrupo.equals(other.codGrupo))) {
            return false;
        }
        if ((this.codGrupo2 == null && other.codGrupo2 != null) || (this.codGrupo2 != null && !this.codGrupo2.equals(other.codGrupo2))) {
            return false;
        }
        if ((this.codTipo == null && other.codTipo != null) || (this.codTipo != null && !this.codTipo.equals(other.codTipo))) {
            return false;
        }
        if ((this.codGrupo3 == null && other.codGrupo3 != null) || (this.codGrupo3 != null && !this.codGrupo3.equals(other.codGrupo3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacDescVolPK[ codEmpresa=" + codEmpresa + ", codGrupo1=" + codGrupo1 + ", codGrupo=" + codGrupo + ", codGrupo2=" + codGrupo2 + ", codTipo=" + codTipo + ", codGrupo3=" + codGrupo3 + " ]";
    }
    
}
