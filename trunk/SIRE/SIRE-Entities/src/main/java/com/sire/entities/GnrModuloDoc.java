/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pestupinan
 */
@Entity
@Table(name = "GNR_MODULO_DOC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrModuloDoc.findAll", query = "SELECT g FROM GnrModuloDoc g"),
    @NamedQuery(name = "GnrModuloDoc.findByCodModulo", query = "SELECT g FROM GnrModuloDoc g WHERE g.gnrModuloDocPK.codModulo = :codModulo"),
    @NamedQuery(name = "GnrModuloDoc.findByCodDocumento", query = "SELECT g FROM GnrModuloDoc g WHERE g.gnrModuloDocPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "GnrModuloDoc.findByDescDocumento", query = "SELECT g FROM GnrModuloDoc g WHERE g.descDocumento = :descDocumento"),
    @NamedQuery(name = "GnrModuloDoc.findByAlias", query = "SELECT g FROM GnrModuloDoc g WHERE g.alias = :alias")})
public class GnrModuloDoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GnrModuloDocPK gnrModuloDocPK;
    @Column(name = "DESC_DOCUMENTO")
    private String descDocumento;
    @Column(name = "ALIAS")
    private String alias;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrModuloDoc")
    private List<GnrContadorDoc> gnrContadorDocList;

    public GnrModuloDoc() {
    }

    public GnrModuloDoc(GnrModuloDocPK gnrModuloDocPK) {
        this.gnrModuloDocPK = gnrModuloDocPK;
    }

    public GnrModuloDoc(String codModulo, String codDocumento) {
        this.gnrModuloDocPK = new GnrModuloDocPK(codModulo, codDocumento);
    }

    public GnrModuloDocPK getGnrModuloDocPK() {
        return gnrModuloDocPK;
    }

    public void setGnrModuloDocPK(GnrModuloDocPK gnrModuloDocPK) {
        this.gnrModuloDocPK = gnrModuloDocPK;
    }

    public String getDescDocumento() {
        return descDocumento;
    }

    public void setDescDocumento(String descDocumento) {
        this.descDocumento = descDocumento;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @XmlTransient
    public List<GnrContadorDoc> getGnrContadorDocList() {
        return gnrContadorDocList;
    }

    public void setGnrContadorDocList(List<GnrContadorDoc> gnrContadorDocList) {
        this.gnrContadorDocList = gnrContadorDocList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnrModuloDocPK != null ? gnrModuloDocPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrModuloDoc)) {
            return false;
        }
        GnrModuloDoc other = (GnrModuloDoc) object;
        if ((this.gnrModuloDocPK == null && other.gnrModuloDocPK != null) || (this.gnrModuloDocPK != null && !this.gnrModuloDocPK.equals(other.gnrModuloDocPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrModuloDoc[ gnrModuloDocPK=" + gnrModuloDocPK + " ]";
    }
    
}
