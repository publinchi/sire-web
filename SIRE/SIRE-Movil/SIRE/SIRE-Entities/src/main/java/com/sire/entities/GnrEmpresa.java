/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "GNR_EMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrEmpresa.findAll", query = "SELECT g FROM GnrEmpresa g"),
    @NamedQuery(name = "GnrEmpresa.findByCodEmpresa", query = "SELECT g FROM GnrEmpresa g WHERE g.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "GnrEmpresa.findByCodPais", query = "SELECT g FROM GnrEmpresa g WHERE g.codPais = :codPais"),
    @NamedQuery(name = "GnrEmpresa.findByCodProvincia", query = "SELECT g FROM GnrEmpresa g WHERE g.codProvincia = :codProvincia"),
    @NamedQuery(name = "GnrEmpresa.findByCodDivisa", query = "SELECT g FROM GnrEmpresa g WHERE g.codDivisa = :codDivisa"),
    @NamedQuery(name = "GnrEmpresa.findByDescEmpresa", query = "SELECT g FROM GnrEmpresa g WHERE g.descEmpresa = :descEmpresa"),
    @NamedQuery(name = "GnrEmpresa.findByCedula", query = "SELECT g FROM GnrEmpresa g WHERE g.cedula = :cedula"),
    @NamedQuery(name = "GnrEmpresa.findByCodCanton", query = "SELECT g FROM GnrEmpresa g WHERE g.codCanton = :codCanton"),
    @NamedQuery(name = "GnrEmpresa.findByRuc", query = "SELECT g FROM GnrEmpresa g WHERE g.ruc = :ruc"),
    @NamedQuery(name = "GnrEmpresa.findByCedContador", query = "SELECT g FROM GnrEmpresa g WHERE g.cedContador = :cedContador"),
    @NamedQuery(name = "GnrEmpresa.findByDireccion", query = "SELECT g FROM GnrEmpresa g WHERE g.direccion = :direccion"),
    @NamedQuery(name = "GnrEmpresa.findByTelefono", query = "SELECT g FROM GnrEmpresa g WHERE g.telefono = :telefono"),
    @NamedQuery(name = "GnrEmpresa.findByFax", query = "SELECT g FROM GnrEmpresa g WHERE g.fax = :fax"),
    @NamedQuery(name = "GnrEmpresa.findByCasilla", query = "SELECT g FROM GnrEmpresa g WHERE g.casilla = :casilla"),
    @NamedQuery(name = "GnrEmpresa.findByEmail", query = "SELECT g FROM GnrEmpresa g WHERE g.email = :email"),
    @NamedQuery(name = "GnrEmpresa.findByPatronal", query = "SELECT g FROM GnrEmpresa g WHERE g.patronal = :patronal"),
    @NamedQuery(name = "GnrEmpresa.findByResponsable", query = "SELECT g FROM GnrEmpresa g WHERE g.responsable = :responsable")})
public class GnrEmpresa implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<ComVisitaCliente> comVisitaClienteList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<PrySupervisor> prySupervisorList;

    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<CajRubro> cajRubroList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<GnrUsuaMod> gnrUsuaModList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<FacTarCredito> facTarCreditoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvIva> invIvaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<FacTmpFactC> facTmpFactCList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_PAIS")
    private String codPais;
    @Column(name = "COD_PROVINCIA")
    private String codProvincia;
    @Basic(optional = false)
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Basic(optional = false)
    @Column(name = "DESC_EMPRESA")
    private String descEmpresa;
    @Column(name = "CEDULA")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "COD_CANTON")
    private String codCanton;
    @Column(name = "RUC")
    private String ruc;
    @Column(name = "CED_CONTADOR")
    private String cedContador;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "FAX")
    private String fax;
    @Column(name = "CASILLA")
    private String casilla;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PATRONAL")
    private String patronal;
    @Column(name = "RESPONSABLE")
    private String responsable;
    @JoinTable(name = "INV_DEF_TRANS", joinColumns = {
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA"),
        @JoinColumn(name = "COD_MOVIMIENTO", referencedColumnName = "COD_MOVIMIENTO")})
    @ManyToMany
    private List<InvTransacciones> invTransaccionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<GnrDivisa> gnrDivisaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvGrupo1> invGrupo1List;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvArticulo> invArticuloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvMarcas> invMarcasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvMovimientoCabF> invMovimientoCabFList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvProveedor> invProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvGrupoProveedor> invGrupoProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvMovimientoCab> invMovimientoCabList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrEmpresa")
    private List<InvTransacciones> invTransaccionesList1;

    public GnrEmpresa() {
    }

    public GnrEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public GnrEmpresa(String codEmpresa, String codPais, String codDivisa, String descEmpresa, String codCanton) {
        this.codEmpresa = codEmpresa;
        this.codPais = codPais;
        this.codDivisa = codDivisa;
        this.descEmpresa = descEmpresa;
        this.codCanton = codCanton;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    public String getCodDivisa() {
        return codDivisa;
    }

    public void setCodDivisa(String codDivisa) {
        this.codDivisa = codDivisa;
    }

    public String getDescEmpresa() {
        return descEmpresa;
    }

    public void setDescEmpresa(String descEmpresa) {
        this.descEmpresa = descEmpresa;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCodCanton() {
        return codCanton;
    }

    public void setCodCanton(String codCanton) {
        this.codCanton = codCanton;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCedContador() {
        return cedContador;
    }

    public void setCedContador(String cedContador) {
        this.cedContador = cedContador;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCasilla() {
        return casilla;
    }

    public void setCasilla(String casilla) {
        this.casilla = casilla;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatronal() {
        return patronal;
    }

    public void setPatronal(String patronal) {
        this.patronal = patronal;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @XmlTransient
    public List<InvTransacciones> getInvTransaccionesList() {
        return invTransaccionesList;
    }

    public void setInvTransaccionesList(List<InvTransacciones> invTransaccionesList) {
        this.invTransaccionesList = invTransaccionesList;
    }

    @XmlTransient
    public List<GnrDivisa> getGnrDivisaList() {
        return gnrDivisaList;
    }

    public void setGnrDivisaList(List<GnrDivisa> gnrDivisaList) {
        this.gnrDivisaList = gnrDivisaList;
    }

    @XmlTransient
    public List<InvGrupo1> getInvGrupo1List() {
        return invGrupo1List;
    }

    public void setInvGrupo1List(List<InvGrupo1> invGrupo1List) {
        this.invGrupo1List = invGrupo1List;
    }

    @XmlTransient
    public List<InvArticulo> getInvArticuloList() {
        return invArticuloList;
    }

    public void setInvArticuloList(List<InvArticulo> invArticuloList) {
        this.invArticuloList = invArticuloList;
    }

    @XmlTransient
    public List<InvMarcas> getInvMarcasList() {
        return invMarcasList;
    }

    public void setInvMarcasList(List<InvMarcas> invMarcasList) {
        this.invMarcasList = invMarcasList;
    }

    @XmlTransient
    public List<InvMovimientoCabF> getInvMovimientoCabFList() {
        return invMovimientoCabFList;
    }

    public void setInvMovimientoCabFList(List<InvMovimientoCabF> invMovimientoCabFList) {
        this.invMovimientoCabFList = invMovimientoCabFList;
    }

    @XmlTransient
    public List<InvProveedor> getInvProveedorList() {
        return invProveedorList;
    }

    public void setInvProveedorList(List<InvProveedor> invProveedorList) {
        this.invProveedorList = invProveedorList;
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

    @XmlTransient
    public List<InvTransacciones> getInvTransaccionesList1() {
        return invTransaccionesList1;
    }

    public void setInvTransaccionesList1(List<InvTransacciones> invTransaccionesList1) {
        this.invTransaccionesList1 = invTransaccionesList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrEmpresa)) {
            return false;
        }
        GnrEmpresa other = (GnrEmpresa) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrEmpresa[ codEmpresa=" + codEmpresa + " ]";
    }

    @XmlTransient
    public List<FacTmpFactC> getFacTmpFactCList() {
        return facTmpFactCList;
    }

    public void setFacTmpFactCList(List<FacTmpFactC> facTmpFactCList) {
        this.facTmpFactCList = facTmpFactCList;
    }

    @XmlTransient
    public List<InvIva> getInvIvaList() {
        return invIvaList;
    }

    public void setInvIvaList(List<InvIva> invIvaList) {
        this.invIvaList = invIvaList;
    }

    @XmlTransient
    public List<FacTarCredito> getFacTarCreditoList() {
        return facTarCreditoList;
    }

    public void setFacTarCreditoList(List<FacTarCredito> facTarCreditoList) {
        this.facTarCreditoList = facTarCreditoList;
    }

    @XmlTransient
    public List<GnrUsuaMod> getGnrUsuaModList() {
        return gnrUsuaModList;
    }

    public void setGnrUsuaModList(List<GnrUsuaMod> gnrUsuaModList) {
        this.gnrUsuaModList = gnrUsuaModList;
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
    public List<CajRubro> getCajRubroList() {
        return cajRubroList;
    }

    public void setCajRubroList(List<CajRubro> cajRubroList) {
        this.cajRubroList = cajRubroList;
    }

    @XmlTransient
    public List<PrySupervisor> getPrySupervisorList() {
        return prySupervisorList;
    }

    public void setPrySupervisorList(List<PrySupervisor> prySupervisorList) {
        this.prySupervisorList = prySupervisorList;
    }

    @XmlTransient
    public List<ComVisitaCliente> getComVisitaClienteList() {
        return comVisitaClienteList;
    }

    public void setComVisitaClienteList(List<ComVisitaCliente> comVisitaClienteList) {
        this.comVisitaClienteList = comVisitaClienteList;
    }
    
}
