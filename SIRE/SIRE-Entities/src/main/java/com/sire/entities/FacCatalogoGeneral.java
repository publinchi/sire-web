/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FAC_CATALOGO_GENERAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacCatalogoGeneral.findAll", query = "SELECT f FROM FacCatalogoGeneral f"),
    @NamedQuery(name = "FacCatalogoGeneral.findByCodEmpresa", query = "SELECT f FROM FacCatalogoGeneral f WHERE f.facCatalogoGeneralPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacCatalogoGeneral.findByCodCatalogo", query = "SELECT f FROM FacCatalogoGeneral f WHERE f.facCatalogoGeneralPK.codCatalogo = :codCatalogo"),
    @NamedQuery(name = "FacCatalogoGeneral.findByCodCatalogoP", query = "SELECT f FROM FacCatalogoGeneral f WHERE f.codCatalogoP = :codCatalogoP")})
public class FacCatalogoGeneral implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacCatalogoGeneralPK facCatalogoGeneralPK;
    @Column(name = "COD_CATALOGO_P")
    private String codCatalogoP;

    public FacCatalogoGeneral() {
    }

    public FacCatalogoGeneral(FacCatalogoGeneralPK facCatalogoGeneralPK) {
        this.facCatalogoGeneralPK = facCatalogoGeneralPK;
    }

    public FacCatalogoGeneral(String codEmpresa, String codCatalogo) {
        this.facCatalogoGeneralPK = new FacCatalogoGeneralPK(codEmpresa, codCatalogo);
    }

    public FacCatalogoGeneralPK getFacCatalogoGeneralPK() {
        return facCatalogoGeneralPK;
    }

    public void setFacCatalogoGeneralPK(FacCatalogoGeneralPK facCatalogoGeneralPK) {
        this.facCatalogoGeneralPK = facCatalogoGeneralPK;
    }

    public String getCodCatalogoP() {
        return codCatalogoP;
    }

    public void setCodCatalogoP(String codCatalogoP) {
        this.codCatalogoP = codCatalogoP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facCatalogoGeneralPK != null ? facCatalogoGeneralPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCatalogoGeneral)) {
            return false;
        }
        FacCatalogoGeneral other = (FacCatalogoGeneral) object;
        if ((this.facCatalogoGeneralPK == null && other.facCatalogoGeneralPK != null) || (this.facCatalogoGeneralPK != null && !this.facCatalogoGeneralPK.equals(other.facCatalogoGeneralPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacCatalogoGeneral[ facCatalogoGeneralPK=" + facCatalogoGeneralPK + " ]";
    }
    
}
