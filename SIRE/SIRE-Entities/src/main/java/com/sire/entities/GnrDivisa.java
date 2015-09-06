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
@Table(name = "GNR_DIVISA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrDivisa.findAll", query = "SELECT g FROM GnrDivisa g"),
    @NamedQuery(name = "GnrDivisa.findByCodDivisa", query = "SELECT g FROM GnrDivisa g WHERE g.gnrDivisaPK.codDivisa = :codDivisa"),
    @NamedQuery(name = "GnrDivisa.findByCodEmpresa", query = "SELECT g FROM GnrDivisa g WHERE g.gnrDivisaPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "GnrDivisa.findByDescripcion", query = "SELECT g FROM GnrDivisa g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GnrDivisa.findByCodigoCuenta", query = "SELECT g FROM GnrDivisa g WHERE g.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "GnrDivisa.findByCuentaCheques", query = "SELECT g FROM GnrDivisa g WHERE g.cuentaCheques = :cuentaCheques")})
public class GnrDivisa implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GnrDivisaPK gnrDivisaPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @Column(name = "CUENTA_CHEQUES")
    private String cuentaCheques;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrDivisa")
    private List<InvGrupoProveedor> invGrupoProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrDivisa")
    private List<InvMovimientoCab> invMovimientoCabList;

    public GnrDivisa() {
    }

    public GnrDivisa(GnrDivisaPK gnrDivisaPK) {
        this.gnrDivisaPK = gnrDivisaPK;
    }

    public GnrDivisa(String codDivisa, String codEmpresa) {
        this.gnrDivisaPK = new GnrDivisaPK(codDivisa, codEmpresa);
    }

    public GnrDivisaPK getGnrDivisaPK() {
        return gnrDivisaPK;
    }

    public void setGnrDivisaPK(GnrDivisaPK gnrDivisaPK) {
        this.gnrDivisaPK = gnrDivisaPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getCuentaCheques() {
        return cuentaCheques;
    }

    public void setCuentaCheques(String cuentaCheques) {
        this.cuentaCheques = cuentaCheques;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    @XmlTransient
    public List<InvGrupoProveedor> getInvGrupoProveedorList() {
        return invGrupoProveedorList;
    }

    public void setInvGrupoProveedorList(List<InvGrupoProveedor> invGrupoProveedorList) {
        this.invGrupoProveedorList = invGrupoProveedorList;
    }

    @XmlTransient
    public List<InvMovimientoCab> getInvMovimientoCabList() {
        return invMovimientoCabList;
    }

    public void setInvMovimientoCabList(List<InvMovimientoCab> invMovimientoCabList) {
        this.invMovimientoCabList = invMovimientoCabList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnrDivisaPK != null ? gnrDivisaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrDivisa)) {
            return false;
        }
        GnrDivisa other = (GnrDivisa) object;
        if ((this.gnrDivisaPK == null && other.gnrDivisaPK != null) || (this.gnrDivisaPK != null && !this.gnrDivisaPK.equals(other.gnrDivisaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrDivisa[ gnrDivisaPK=" + gnrDivisaPK + " ]";
    }
    
}
