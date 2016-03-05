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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "INV_GRUPO_PROVEEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvGrupoProveedor.findAll", query = "SELECT i FROM InvGrupoProveedor i"),
    @NamedQuery(name = "InvGrupoProveedor.findByCodEmpresa", query = "SELECT i FROM InvGrupoProveedor i WHERE i.invGrupoProveedorPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvGrupoProveedor.findByCodGrupo", query = "SELECT i FROM InvGrupoProveedor i WHERE i.invGrupoProveedorPK.codGrupo = :codGrupo"),
    @NamedQuery(name = "InvGrupoProveedor.findByCodigoCuenta", query = "SELECT i FROM InvGrupoProveedor i WHERE i.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "InvGrupoProveedor.findByDescGrupo", query = "SELECT i FROM InvGrupoProveedor i WHERE i.descGrupo = :descGrupo"),
    @NamedQuery(name = "InvGrupoProveedor.findByEstado", query = "SELECT i FROM InvGrupoProveedor i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvGrupoProveedor.findByFechaEstado", query = "SELECT i FROM InvGrupoProveedor i WHERE i.fechaEstado = :fechaEstado")})
public class InvGrupoProveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvGrupoProveedorPK invGrupoProveedorPK;
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @Column(name = "DESC_GRUPO")
    private String descGrupo;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invGrupoProveedor")
    private List<InvProveedor> invProveedorList;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_DIVISA", referencedColumnName = "COD_DIVISA")})
    @ManyToOne(optional = false)
    private GnrDivisa gnrDivisa;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;

    public InvGrupoProveedor() {
    }

    public InvGrupoProveedor(InvGrupoProveedorPK invGrupoProveedorPK) {
        this.invGrupoProveedorPK = invGrupoProveedorPK;
    }

    public InvGrupoProveedor(String codEmpresa, String codGrupo) {
        this.invGrupoProveedorPK = new InvGrupoProveedorPK(codEmpresa, codGrupo);
    }

    public InvGrupoProveedorPK getInvGrupoProveedorPK() {
        return invGrupoProveedorPK;
    }

    public void setInvGrupoProveedorPK(InvGrupoProveedorPK invGrupoProveedorPK) {
        this.invGrupoProveedorPK = invGrupoProveedorPK;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getDescGrupo() {
        return descGrupo;
    }

    public void setDescGrupo(String descGrupo) {
        this.descGrupo = descGrupo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    @XmlTransient
    public List<InvProveedor> getInvProveedorList() {
        return invProveedorList;
    }

    public void setInvProveedorList(List<InvProveedor> invProveedorList) {
        this.invProveedorList = invProveedorList;
    }

    public GnrDivisa getGnrDivisa() {
        return gnrDivisa;
    }

    public void setGnrDivisa(GnrDivisa gnrDivisa) {
        this.gnrDivisa = gnrDivisa;
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
        hash += (invGrupoProveedorPK != null ? invGrupoProveedorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvGrupoProveedor)) {
            return false;
        }
        InvGrupoProveedor other = (InvGrupoProveedor) object;
        if ((this.invGrupoProveedorPK == null && other.invGrupoProveedorPK != null) || (this.invGrupoProveedorPK != null && !this.invGrupoProveedorPK.equals(other.invGrupoProveedorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvGrupoProveedor[ invGrupoProveedorPK=" + invGrupoProveedorPK + " ]";
    }
    
}
