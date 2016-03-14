/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author publio
 */
@Entity
@Table(name = "FAC_DESC_VOL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacDescVol.findAll", query = "SELECT f FROM FacDescVol f"),
    @NamedQuery(name = "FacDescVol.findByCodEmpresa", query = "SELECT f FROM FacDescVol f WHERE f.facDescVolPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacDescVol.findByCodGrupo1", query = "SELECT f FROM FacDescVol f WHERE f.facDescVolPK.codGrupo1 = :codGrupo1"),
    @NamedQuery(name = "FacDescVol.findByCodGrupo", query = "SELECT f FROM FacDescVol f WHERE f.facDescVolPK.codGrupo = :codGrupo"),
    @NamedQuery(name = "FacDescVol.findByCodGrupo2", query = "SELECT f FROM FacDescVol f WHERE f.facDescVolPK.codGrupo2 = :codGrupo2"),
    @NamedQuery(name = "FacDescVol.findByCodTipo", query = "SELECT f FROM FacDescVol f WHERE f.facDescVolPK.codTipo = :codTipo"),
    @NamedQuery(name = "FacDescVol.findByCodGrupo3", query = "SELECT f FROM FacDescVol f WHERE f.facDescVolPK.codGrupo3 = :codGrupo3"),
    @NamedQuery(name = "FacDescVol.findByPorcDescuento", query = "SELECT f FROM FacDescVol f WHERE f.porcDescuento = :porcDescuento"),
    @NamedQuery(name = "FacDescVol.findByFechaModificacion", query = "SELECT f FROM FacDescVol f WHERE f.fechaModificacion = :fechaModificacion")})
public class FacDescVol implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacDescVolPK facDescVolPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PORC_DESCUENTO")
    private BigDecimal porcDescuento;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public FacDescVol() {
    }

    public FacDescVol(FacDescVolPK facDescVolPK) {
        this.facDescVolPK = facDescVolPK;
    }

    public FacDescVol(FacDescVolPK facDescVolPK, BigDecimal porcDescuento) {
        this.facDescVolPK = facDescVolPK;
        this.porcDescuento = porcDescuento;
    }

    public FacDescVol(String codEmpresa, String codGrupo1, String codGrupo, String codGrupo2, String codTipo, String codGrupo3) {
        this.facDescVolPK = new FacDescVolPK(codEmpresa, codGrupo1, codGrupo, codGrupo2, codTipo, codGrupo3);
    }

    public FacDescVolPK getFacDescVolPK() {
        return facDescVolPK;
    }

    public void setFacDescVolPK(FacDescVolPK facDescVolPK) {
        this.facDescVolPK = facDescVolPK;
    }

    public BigDecimal getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(BigDecimal porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facDescVolPK != null ? facDescVolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacDescVol)) {
            return false;
        }
        FacDescVol other = (FacDescVol) object;
        if ((this.facDescVolPK == null && other.facDescVolPK != null) || (this.facDescVolPK != null && !this.facDescVolPK.equals(other.facDescVolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacDescVol[ facDescVolPK=" + facDescVolPK + " ]";
    }
    
}
