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
@Table(name = "INV_MARCAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvMarcas.findAll", query = "SELECT i FROM InvMarcas i"),
    @NamedQuery(name = "InvMarcas.findByCodEmpresa", query = "SELECT i FROM InvMarcas i WHERE i.invMarcasPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvMarcas.findByCodMarca", query = "SELECT i FROM InvMarcas i WHERE i.invMarcasPK.codMarca = :codMarca"),
    @NamedQuery(name = "InvMarcas.findByDescripcion", query = "SELECT i FROM InvMarcas i WHERE i.descripcion = :descripcion")})
public class InvMarcas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvMarcasPK invMarcasPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invMarcas")
    private List<InvArticulo> invArticuloList;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;

    public InvMarcas() {
    }

    public InvMarcas(InvMarcasPK invMarcasPK) {
        this.invMarcasPK = invMarcasPK;
    }

    public InvMarcas(String codEmpresa, String codMarca) {
        this.invMarcasPK = new InvMarcasPK(codEmpresa, codMarca);
    }

    public InvMarcasPK getInvMarcasPK() {
        return invMarcasPK;
    }

    public void setInvMarcasPK(InvMarcasPK invMarcasPK) {
        this.invMarcasPK = invMarcasPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<InvArticulo> getInvArticuloList() {
        return invArticuloList;
    }

    public void setInvArticuloList(List<InvArticulo> invArticuloList) {
        this.invArticuloList = invArticuloList;
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
        hash += (invMarcasPK != null ? invMarcasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMarcas)) {
            return false;
        }
        InvMarcas other = (InvMarcas) object;
        if ((this.invMarcasPK == null && other.invMarcasPK != null) || (this.invMarcasPK != null && !this.invMarcasPK.equals(other.invMarcasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvMarcas[ invMarcasPK=" + invMarcasPK + " ]";
    }
    
}
