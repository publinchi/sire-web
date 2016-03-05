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
@Table(name = "INV_GRUPO3")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvGrupo3.findAll", query = "SELECT i FROM InvGrupo3 i"),
    @NamedQuery(name = "InvGrupo3.findByCodEmpresa", query = "SELECT i FROM InvGrupo3 i WHERE i.invGrupo3PK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvGrupo3.findByCodGrupo1", query = "SELECT i FROM InvGrupo3 i WHERE i.invGrupo3PK.codGrupo1 = :codGrupo1"),
    @NamedQuery(name = "InvGrupo3.findByCodGrupo2", query = "SELECT i FROM InvGrupo3 i WHERE i.invGrupo3PK.codGrupo2 = :codGrupo2"),
    @NamedQuery(name = "InvGrupo3.findByCodGrupo3", query = "SELECT i FROM InvGrupo3 i WHERE i.invGrupo3PK.codGrupo3 = :codGrupo3"),
    @NamedQuery(name = "InvGrupo3.findByDescGrupo3", query = "SELECT i FROM InvGrupo3 i WHERE i.descGrupo3 = :descGrupo3")})
public class InvGrupo3 implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvGrupo3PK invGrupo3PK;
    @Column(name = "DESC_GRUPO3")
    private String descGrupo3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invGrupo3")
    private List<InvArticulo> invArticuloList;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_GRUPO2", referencedColumnName = "COD_GRUPO2", insertable = false, updatable = false),
        @JoinColumn(name = "COD_GRUPO1", referencedColumnName = "COD_GRUPO1", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private InvGrupo2 invGrupo2;

    public InvGrupo3() {
    }

    public InvGrupo3(InvGrupo3PK invGrupo3PK) {
        this.invGrupo3PK = invGrupo3PK;
    }

    public InvGrupo3(String codEmpresa, String codGrupo1, String codGrupo2, String codGrupo3) {
        this.invGrupo3PK = new InvGrupo3PK(codEmpresa, codGrupo1, codGrupo2, codGrupo3);
    }

    public InvGrupo3PK getInvGrupo3PK() {
        return invGrupo3PK;
    }

    public void setInvGrupo3PK(InvGrupo3PK invGrupo3PK) {
        this.invGrupo3PK = invGrupo3PK;
    }

    public String getDescGrupo3() {
        return descGrupo3;
    }

    public void setDescGrupo3(String descGrupo3) {
        this.descGrupo3 = descGrupo3;
    }

    @XmlTransient
    public List<InvArticulo> getInvArticuloList() {
        return invArticuloList;
    }

    public void setInvArticuloList(List<InvArticulo> invArticuloList) {
        this.invArticuloList = invArticuloList;
    }

    public InvGrupo2 getInvGrupo2() {
        return invGrupo2;
    }

    public void setInvGrupo2(InvGrupo2 invGrupo2) {
        this.invGrupo2 = invGrupo2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invGrupo3PK != null ? invGrupo3PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvGrupo3)) {
            return false;
        }
        InvGrupo3 other = (InvGrupo3) object;
        if ((this.invGrupo3PK == null && other.invGrupo3PK != null) || (this.invGrupo3PK != null && !this.invGrupo3PK.equals(other.invGrupo3PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvGrupo3[ invGrupo3PK=" + invGrupo3PK + " ]";
    }
    
}
