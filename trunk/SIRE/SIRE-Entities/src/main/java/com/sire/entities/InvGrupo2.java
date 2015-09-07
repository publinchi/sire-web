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
import javax.persistence.JoinColumns;
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
@Table(name = "INV_GRUPO2")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvGrupo2.findAll", query = "SELECT i FROM InvGrupo2 i"),
    @NamedQuery(name = "InvGrupo2.findByCodEmpresa", query = "SELECT i FROM InvGrupo2 i WHERE i.invGrupo2PK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvGrupo2.findByCodGrupo1", query = "SELECT i FROM InvGrupo2 i WHERE i.invGrupo2PK.codGrupo1 = :codGrupo1"),
    @NamedQuery(name = "InvGrupo2.findByCodGrupo2", query = "SELECT i FROM InvGrupo2 i WHERE i.invGrupo2PK.codGrupo2 = :codGrupo2"),
    @NamedQuery(name = "InvGrupo2.findByDescGrupo2", query = "SELECT i FROM InvGrupo2 i WHERE i.descGrupo2 = :descGrupo2")})
public class InvGrupo2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvGrupo2PK invGrupo2PK;
    @Column(name = "DESC_GRUPO2")
    private String descGrupo2;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_GRUPO1", referencedColumnName = "COD_GRUPO1", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private InvGrupo1 invGrupo1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invGrupo2")
    private List<InvGrupo3> invGrupo3List;

    public InvGrupo2() {
    }

    public InvGrupo2(InvGrupo2PK invGrupo2PK) {
        this.invGrupo2PK = invGrupo2PK;
    }

    public InvGrupo2(String codEmpresa, String codGrupo1, String codGrupo2) {
        this.invGrupo2PK = new InvGrupo2PK(codEmpresa, codGrupo1, codGrupo2);
    }

    public InvGrupo2PK getInvGrupo2PK() {
        return invGrupo2PK;
    }

    public void setInvGrupo2PK(InvGrupo2PK invGrupo2PK) {
        this.invGrupo2PK = invGrupo2PK;
    }

    public String getDescGrupo2() {
        return descGrupo2;
    }

    public void setDescGrupo2(String descGrupo2) {
        this.descGrupo2 = descGrupo2;
    }

    public InvGrupo1 getInvGrupo1() {
        return invGrupo1;
    }

    public void setInvGrupo1(InvGrupo1 invGrupo1) {
        this.invGrupo1 = invGrupo1;
    }

    @XmlTransient
    public List<InvGrupo3> getInvGrupo3List() {
        return invGrupo3List;
    }

    public void setInvGrupo3List(List<InvGrupo3> invGrupo3List) {
        this.invGrupo3List = invGrupo3List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invGrupo2PK != null ? invGrupo2PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvGrupo2)) {
            return false;
        }
        InvGrupo2 other = (InvGrupo2) object;
        if ((this.invGrupo2PK == null && other.invGrupo2PK != null) || (this.invGrupo2PK != null && !this.invGrupo2PK.equals(other.invGrupo2PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvGrupo2[ invGrupo2PK=" + invGrupo2PK + " ]";
    }
    
}
