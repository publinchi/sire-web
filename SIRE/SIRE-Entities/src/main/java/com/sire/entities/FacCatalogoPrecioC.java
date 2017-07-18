/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "FAC_CATALOGO_PRECIO_C")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacCatalogoPrecioC.findAll", query = "SELECT f FROM FacCatalogoPrecioC f"),
    @NamedQuery(name = "FacCatalogoPrecioC.findByCodCatalogo", query = "SELECT f FROM FacCatalogoPrecioC f WHERE f.facCatalogoPrecioCPK.codCatalogo = :codCatalogo"),
    @NamedQuery(name = "FacCatalogoPrecioC.findByCodEmpresa", query = "SELECT f FROM FacCatalogoPrecioC f WHERE f.facCatalogoPrecioCPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacCatalogoPrecioC.findByDescCatalogo", query = "SELECT f FROM FacCatalogoPrecioC f WHERE f.descCatalogo = :descCatalogo"),
    @NamedQuery(name = "FacCatalogoPrecioC.findByFechaDesdeVigencia", query = "SELECT f FROM FacCatalogoPrecioC f WHERE f.fechaDesdeVigencia = :fechaDesdeVigencia"),
    @NamedQuery(name = "FacCatalogoPrecioC.findByFechaHastaVigencia", query = "SELECT f FROM FacCatalogoPrecioC f WHERE f.fechaHastaVigencia = :fechaHastaVigencia")})
public class FacCatalogoPrecioC implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacCatalogoPrecioCPK facCatalogoPrecioCPK;
    @Column(name = "DESC_CATALOGO")
    private String descCatalogo;
    @Column(name = "FECHA_DESDE_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesdeVigencia;
    @Column(name = "FECHA_HASTA_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHastaVigencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facCatalogoPrecioC")
    private List<FacCatalogoPrecioD> facCatalogoPrecioDList;

    public FacCatalogoPrecioC() {
    }

    public FacCatalogoPrecioC(FacCatalogoPrecioCPK facCatalogoPrecioCPK) {
        this.facCatalogoPrecioCPK = facCatalogoPrecioCPK;
    }

    public FacCatalogoPrecioC(String codCatalogo, String codEmpresa) {
        this.facCatalogoPrecioCPK = new FacCatalogoPrecioCPK(codCatalogo, codEmpresa);
    }

    public FacCatalogoPrecioCPK getFacCatalogoPrecioCPK() {
        return facCatalogoPrecioCPK;
    }

    public void setFacCatalogoPrecioCPK(FacCatalogoPrecioCPK facCatalogoPrecioCPK) {
        this.facCatalogoPrecioCPK = facCatalogoPrecioCPK;
    }

    public String getDescCatalogo() {
        return descCatalogo;
    }

    public void setDescCatalogo(String descCatalogo) {
        this.descCatalogo = descCatalogo;
    }

    public Date getFechaDesdeVigencia() {
        return fechaDesdeVigencia;
    }

    public void setFechaDesdeVigencia(Date fechaDesdeVigencia) {
        this.fechaDesdeVigencia = fechaDesdeVigencia;
    }

    public Date getFechaHastaVigencia() {
        return fechaHastaVigencia;
    }

    public void setFechaHastaVigencia(Date fechaHastaVigencia) {
        this.fechaHastaVigencia = fechaHastaVigencia;
    }

    @XmlTransient
    public List<FacCatalogoPrecioD> getFacCatalogoPrecioDList() {
        return facCatalogoPrecioDList;
    }

    public void setFacCatalogoPrecioDList(List<FacCatalogoPrecioD> facCatalogoPrecioDList) {
        this.facCatalogoPrecioDList = facCatalogoPrecioDList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facCatalogoPrecioCPK != null ? facCatalogoPrecioCPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacCatalogoPrecioC)) {
            return false;
        }
        FacCatalogoPrecioC other = (FacCatalogoPrecioC) object;
        if ((this.facCatalogoPrecioCPK == null && other.facCatalogoPrecioCPK != null) || (this.facCatalogoPrecioCPK != null && !this.facCatalogoPrecioCPK.equals(other.facCatalogoPrecioCPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacCatalogoPrecioC[ facCatalogoPrecioCPK=" + facCatalogoPrecioCPK + " ]";
    }
    
}
