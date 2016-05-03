/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pestupinan
 */
@NamedStoredProcedureQuery(
        name = "PRC_NUM_COMPROBANTE2",
        procedureName = "PRC_NUM_COMPROBANTE2",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "wempresa"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "wcod_modulo"),
            @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "wcod_documento"),
            @StoredProcedureParameter(mode = ParameterMode.OUT, type = Number.class, name = "wnum_documento"),
            @StoredProcedureParameter(mode = ParameterMode.OUT, type = String.class, name = "mensaje")
        }
)
@Entity
@Table(name = "GNR_CONTADOR_DOC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrContadorDoc.findAll", query = "SELECT g FROM GnrContadorDoc g"),
    @NamedQuery(name = "GnrContadorDoc.findByCodEmpresa", query = "SELECT g FROM GnrContadorDoc g WHERE g.gnrContadorDocPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "GnrContadorDoc.findByCodModulo", query = "SELECT g FROM GnrContadorDoc g WHERE g.gnrContadorDocPK.codModulo = :codModulo"),
    @NamedQuery(name = "GnrContadorDoc.findByCodDocumento", query = "SELECT g FROM GnrContadorDoc g WHERE g.gnrContadorDocPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "GnrContadorDoc.findByNumContador", query = "SELECT g FROM GnrContadorDoc g WHERE g.gnrContadorDocPK.numContador = :numContador"),
    @NamedQuery(name = "GnrContadorDoc.findByNumeroInicial", query = "SELECT g FROM GnrContadorDoc g WHERE g.numeroInicial = :numeroInicial"),
    @NamedQuery(name = "GnrContadorDoc.findByNumeroActual", query = "SELECT g FROM GnrContadorDoc g WHERE g.numeroActual = :numeroActual"),
    @NamedQuery(name = "GnrContadorDoc.findByNumeroFinal", query = "SELECT g FROM GnrContadorDoc g WHERE g.numeroFinal = :numeroFinal")})
public class GnrContadorDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GnrContadorDocPK gnrContadorDocPK;
    @Basic(optional = false)
    @Column(name = "NUMERO_INICIAL")
    private long numeroInicial;
    @Basic(optional = false)
    @Column(name = "NUMERO_ACTUAL")
    private long numeroActual;
    @Basic(optional = false)
    @Column(name = "NUMERO_FINAL")
    private long numeroFinal;
    @ManyToMany(mappedBy = "gnrContadorDocList")
    private List<GnrUsuarios> gnrUsuariosList;
    @JoinColumns({
        @JoinColumn(name = "COD_MODULO", referencedColumnName = "COD_MODULO", insertable = false, updatable = false),
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private GnrModuloDoc gnrModuloDoc;

    public GnrContadorDoc() {
    }

    public GnrContadorDoc(GnrContadorDocPK gnrContadorDocPK) {
        this.gnrContadorDocPK = gnrContadorDocPK;
    }

    public GnrContadorDoc(GnrContadorDocPK gnrContadorDocPK, long numeroInicial, long numeroActual, long numeroFinal) {
        this.gnrContadorDocPK = gnrContadorDocPK;
        this.numeroInicial = numeroInicial;
        this.numeroActual = numeroActual;
        this.numeroFinal = numeroFinal;
    }

    public GnrContadorDoc(String codEmpresa, String codModulo, String codDocumento, BigDecimal numContador) {
        this.gnrContadorDocPK = new GnrContadorDocPK(codEmpresa, codModulo, codDocumento, numContador);
    }

    public GnrContadorDocPK getGnrContadorDocPK() {
        return gnrContadorDocPK;
    }

    public void setGnrContadorDocPK(GnrContadorDocPK gnrContadorDocPK) {
        this.gnrContadorDocPK = gnrContadorDocPK;
    }

    public long getNumeroInicial() {
        return numeroInicial;
    }

    public void setNumeroInicial(long numeroInicial) {
        this.numeroInicial = numeroInicial;
    }

    public long getNumeroActual() {
        return numeroActual;
    }

    public void setNumeroActual(long numeroActual) {
        this.numeroActual = numeroActual;
    }

    public long getNumeroFinal() {
        return numeroFinal;
    }

    public void setNumeroFinal(long numeroFinal) {
        this.numeroFinal = numeroFinal;
    }

    @XmlTransient
    public List<GnrUsuarios> getGnrUsuariosList() {
        return gnrUsuariosList;
    }

    public void setGnrUsuariosList(List<GnrUsuarios> gnrUsuariosList) {
        this.gnrUsuariosList = gnrUsuariosList;
    }

    public GnrModuloDoc getGnrModuloDoc() {
        return gnrModuloDoc;
    }

    public void setGnrModuloDoc(GnrModuloDoc gnrModuloDoc) {
        this.gnrModuloDoc = gnrModuloDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnrContadorDocPK != null ? gnrContadorDocPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrContadorDoc)) {
            return false;
        }
        GnrContadorDoc other = (GnrContadorDoc) object;
        if ((this.gnrContadorDocPK == null && other.gnrContadorDocPK != null) || (this.gnrContadorDocPK != null && !this.gnrContadorDocPK.equals(other.gnrContadorDocPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrContadorDoc[ gnrContadorDocPK=" + gnrContadorDocPK + " ]";
    }

}
