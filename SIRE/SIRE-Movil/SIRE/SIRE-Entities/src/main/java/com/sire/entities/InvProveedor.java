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
@Table(name = "INV_PROVEEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvProveedor.findAll", query = "SELECT i FROM InvProveedor i"),
    @NamedQuery(name = "InvProveedor.findByCodEmpresa", query = "SELECT i FROM InvProveedor i WHERE i.invProveedorPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvProveedor.findByCodProveedor", query = "SELECT i FROM InvProveedor i WHERE i.invProveedorPK.codProveedor = :codProveedor"),
    @NamedQuery(name = "InvProveedor.findByCodigoPago", query = "SELECT i FROM InvProveedor i WHERE i.codigoPago = :codigoPago"),
    @NamedQuery(name = "InvProveedor.findByBeneficiario", query = "SELECT i FROM InvProveedor i WHERE i.beneficiario = :beneficiario"),
    @NamedQuery(name = "InvProveedor.findByCtaAnticipo", query = "SELECT i FROM InvProveedor i WHERE i.ctaAnticipo = :ctaAnticipo"),
    @NamedQuery(name = "InvProveedor.findByCtaPorPagar", query = "SELECT i FROM InvProveedor i WHERE i.ctaPorPagar = :ctaPorPagar"),
    @NamedQuery(name = "InvProveedor.findByActividad", query = "SELECT i FROM InvProveedor i WHERE i.actividad = :actividad"),
    @NamedQuery(name = "InvProveedor.findByPagaIva", query = "SELECT i FROM InvProveedor i WHERE i.pagaIva = :pagaIva"),
    @NamedQuery(name = "InvProveedor.findByEstado", query = "SELECT i FROM InvProveedor i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvProveedor.findByFechaCreacion", query = "SELECT i FROM InvProveedor i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "InvProveedor.findByFechaEstado", query = "SELECT i FROM InvProveedor i WHERE i.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "InvProveedor.findByContEspeProv", query = "SELECT i FROM InvProveedor i WHERE i.contEspeProv = :contEspeProv"),
    @NamedQuery(name = "InvProveedor.findByAutoImprProv", query = "SELECT i FROM InvProveedor i WHERE i.autoImprProv = :autoImprProv"),
    @NamedQuery(name = "InvProveedor.findByAutoContProv", query = "SELECT i FROM InvProveedor i WHERE i.autoContProv = :autoContProv"),
    @NamedQuery(name = "InvProveedor.findByFechCaduProv", query = "SELECT i FROM InvProveedor i WHERE i.fechCaduProv = :fechCaduProv"),
    @NamedQuery(name = "InvProveedor.findByFechIniContribEspec", query = "SELECT i FROM InvProveedor i WHERE i.fechIniContribEspec = :fechIniContribEspec"),
    @NamedQuery(name = "InvProveedor.findByNumResolSri", query = "SELECT i FROM InvProveedor i WHERE i.numResolSri = :numResolSri")})
public class InvProveedor implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invProveedor")
    private List<PrySupervisor> prySupervisorList;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvProveedorPK invProveedorPK;
    @Column(name = "CODIGO_PAGO")
    private String codigoPago;
    @Basic(optional = false)
    @Column(name = "BENEFICIARIO")
    private String beneficiario;
    @Column(name = "CTA_ANTICIPO")
    private String ctaAnticipo;
    @Column(name = "CTA_POR_PAGAR")
    private String ctaPorPagar;
    @Column(name = "ACTIVIDAD")
    private String actividad;
    @Column(name = "PAGA_IVA")
    private String pagaIva;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Column(name = "CONT_ESPE_PROV")
    private Short contEspeProv;
    @Column(name = "AUTO_IMPR_PROV")
    private String autoImprProv;
    @Column(name = "AUTO_CONT_PROV")
    private String autoContProv;
    @Column(name = "FECH_CADU_PROV")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechCaduProv;
    @Column(name = "FECH_INI_CONTRIB_ESPEC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechIniContribEspec;
    @Column(name = "NUM_RESOL_SRI")
    private String numResolSri;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invProveedor")
    private List<InvMovimientoCabF> invMovimientoCabFList;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_PERSONA", referencedColumnName = "COD_PERSONA")})
    @ManyToOne(optional = false)
    private GnrPersona gnrPersona;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_GRUPO", referencedColumnName = "COD_GRUPO")})
    @ManyToOne(optional = false)
    private InvGrupoProveedor invGrupoProveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invProveedor")
    private List<InvMovimientoCab> invMovimientoCabList;

    public InvProveedor() {
    }

    public InvProveedor(InvProveedorPK invProveedorPK) {
        this.invProveedorPK = invProveedorPK;
    }

    public InvProveedor(InvProveedorPK invProveedorPK, String beneficiario) {
        this.invProveedorPK = invProveedorPK;
        this.beneficiario = beneficiario;
    }

    public InvProveedor(String codEmpresa, int codProveedor) {
        this.invProveedorPK = new InvProveedorPK(codEmpresa, codProveedor);
    }

    public InvProveedorPK getInvProveedorPK() {
        return invProveedorPK;
    }

    public void setInvProveedorPK(InvProveedorPK invProveedorPK) {
        this.invProveedorPK = invProveedorPK;
    }

    public String getCodigoPago() {
        return codigoPago;
    }

    public void setCodigoPago(String codigoPago) {
        this.codigoPago = codigoPago;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCtaAnticipo() {
        return ctaAnticipo;
    }

    public void setCtaAnticipo(String ctaAnticipo) {
        this.ctaAnticipo = ctaAnticipo;
    }

    public String getCtaPorPagar() {
        return ctaPorPagar;
    }

    public void setCtaPorPagar(String ctaPorPagar) {
        this.ctaPorPagar = ctaPorPagar;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getPagaIva() {
        return pagaIva;
    }

    public void setPagaIva(String pagaIva) {
        this.pagaIva = pagaIva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public Short getContEspeProv() {
        return contEspeProv;
    }

    public void setContEspeProv(Short contEspeProv) {
        this.contEspeProv = contEspeProv;
    }

    public String getAutoImprProv() {
        return autoImprProv;
    }

    public void setAutoImprProv(String autoImprProv) {
        this.autoImprProv = autoImprProv;
    }

    public String getAutoContProv() {
        return autoContProv;
    }

    public void setAutoContProv(String autoContProv) {
        this.autoContProv = autoContProv;
    }

    public Date getFechCaduProv() {
        return fechCaduProv;
    }

    public void setFechCaduProv(Date fechCaduProv) {
        this.fechCaduProv = fechCaduProv;
    }

    public Date getFechIniContribEspec() {
        return fechIniContribEspec;
    }

    public void setFechIniContribEspec(Date fechIniContribEspec) {
        this.fechIniContribEspec = fechIniContribEspec;
    }

    public String getNumResolSri() {
        return numResolSri;
    }

    public void setNumResolSri(String numResolSri) {
        this.numResolSri = numResolSri;
    }

    @XmlTransient
    public List<InvMovimientoCabF> getInvMovimientoCabFList() {
        return invMovimientoCabFList;
    }

    public void setInvMovimientoCabFList(List<InvMovimientoCabF> invMovimientoCabFList) {
        this.invMovimientoCabFList = invMovimientoCabFList;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    public GnrPersona getGnrPersona() {
        return gnrPersona;
    }

    public void setGnrPersona(GnrPersona gnrPersona) {
        this.gnrPersona = gnrPersona;
    }

    public InvGrupoProveedor getInvGrupoProveedor() {
        return invGrupoProveedor;
    }

    public void setInvGrupoProveedor(InvGrupoProveedor invGrupoProveedor) {
        this.invGrupoProveedor = invGrupoProveedor;
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
        hash += (invProveedorPK != null ? invProveedorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvProveedor)) {
            return false;
        }
        InvProveedor other = (InvProveedor) object;
        if ((this.invProveedorPK == null && other.invProveedorPK != null) || (this.invProveedorPK != null && !this.invProveedorPK.equals(other.invProveedorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvProveedor[ invProveedorPK=" + invProveedorPK + " ]";
    }

    @XmlTransient
    public List<PrySupervisor> getPrySupervisorList() {
        return prySupervisorList;
    }

    public void setPrySupervisorList(List<PrySupervisor> prySupervisorList) {
        this.prySupervisorList = prySupervisorList;
    }
    
}
