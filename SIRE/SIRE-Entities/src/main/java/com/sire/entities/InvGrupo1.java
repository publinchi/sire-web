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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "INV_GRUPO1")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvGrupo1.findAll", query = "SELECT i FROM InvGrupo1 i"),
    @NamedQuery(name = "InvGrupo1.findByCodEmpresa", query = "SELECT i FROM InvGrupo1 i WHERE i.invGrupo1PK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvGrupo1.findByCodGrupo1", query = "SELECT i FROM InvGrupo1 i WHERE i.invGrupo1PK.codGrupo1 = :codGrupo1"),
    @NamedQuery(name = "InvGrupo1.findByDescGrupo1", query = "SELECT i FROM InvGrupo1 i WHERE i.descGrupo1 = :descGrupo1")})
public class InvGrupo1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvGrupo1PK invGrupo1PK;
    @Column(name = "DESC_GRUPO1")
    private String descGrupo1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invGrupo1")
    private List<InvGrupo2> invGrupo2List;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;

    public InvGrupo1() {
    }

    public InvGrupo1(InvGrupo1PK invGrupo1PK) {
        this.invGrupo1PK = invGrupo1PK;
    }

    public InvGrupo1(String codEmpresa, String codGrupo1) {
        this.invGrupo1PK = new InvGrupo1PK(codEmpresa, codGrupo1);
    }

    public InvGrupo1PK getInvGrupo1PK() {
        return invGrupo1PK;
    }

    public void setInvGrupo1PK(InvGrupo1PK invGrupo1PK) {
        this.invGrupo1PK = invGrupo1PK;
    }

    public String getDescGrupo1() {
        return descGrupo1;
    }

    public void setDescGrupo1(String descGrupo1) {
        this.descGrupo1 = descGrupo1;
    }

    @XmlTransient
    public List<InvGrupo2> getInvGrupo2List() {
        return invGrupo2List;
    }

    public void setInvGrupo2List(List<InvGrupo2> invGrupo2List) {
        this.invGrupo2List = invGrupo2List;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invGrupo1PK != null ? invGrupo1PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvGrupo1)) {
            return false;
        }
        InvGrupo1 other = (InvGrupo1) object;
        if ((this.invGrupo1PK == null && other.invGrupo1PK != null) || (this.invGrupo1PK != null && !this.invGrupo1PK.equals(other.invGrupo1PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvGrupo1[ invGrupo1PK=" + invGrupo1PK + " ]";
    }
    
}
