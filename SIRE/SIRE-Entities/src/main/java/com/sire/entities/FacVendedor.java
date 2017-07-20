/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sireRomero
 */
@Entity
@Table(name = "FAC_VENDEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacVendedor.findAll", query = "SELECT f FROM FacVendedor f")
    , @NamedQuery(name = "FacVendedor.findByCodEmpresa", query = "SELECT f FROM FacVendedor f WHERE f.facVendedorPK.codEmpresa = :codEmpresa")
    , @NamedQuery(name = "FacVendedor.findByCodVendedor", query = "SELECT f FROM FacVendedor f WHERE f.facVendedorPK.codVendedor = :codVendedor")
    , @NamedQuery(name = "FacVendedor.findByFechaInicio", query = "SELECT f FROM FacVendedor f WHERE f.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "FacVendedor.findByEstado", query = "SELECT f FROM FacVendedor f WHERE f.estado = :estado")
    , @NamedQuery(name = "FacVendedor.findByFechaEstado", query = "SELECT f FROM FacVendedor f WHERE f.fechaEstado = :fechaEstado")
    , @NamedQuery(name = "FacVendedor.findBySueldo", query = "SELECT f FROM FacVendedor f WHERE f.sueldo = :sueldo")
    , @NamedQuery(name = "FacVendedor.findByRetencion", query = "SELECT f FROM FacVendedor f WHERE f.retencion = :retencion")
    , @NamedQuery(name = "FacVendedor.findByPorcVenta", query = "SELECT f FROM FacVendedor f WHERE f.porcVenta = :porcVenta")
    , @NamedQuery(name = "FacVendedor.findByPorcCobro", query = "SELECT f FROM FacVendedor f WHERE f.porcCobro = :porcCobro")
    , @NamedQuery(name = "FacVendedor.findByPorcEntrega", query = "SELECT f FROM FacVendedor f WHERE f.porcEntrega = :porcEntrega")})
public class FacVendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacVendedorPK facVendedorPK;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Size(max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUELDO")
    private BigInteger sueldo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RETENCION")
    private BigInteger retencion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "PORC_VENTA")
    private BigDecimal porcVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PORC_COBRO")
    private BigDecimal porcCobro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PORC_ENTREGA")
    private BigDecimal porcEntrega;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
        , @JoinColumn(name = "COD_PERSONA", referencedColumnName = "COD_PERSONA")})
    @ManyToOne(optional = false)
    private GnrPersona gnrPersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facVendedor")
    private List<ComVisitaCliente> comVisitaClienteList;

    public FacVendedor() {
    }

    public FacVendedor(FacVendedorPK facVendedorPK) {
        this.facVendedorPK = facVendedorPK;
    }

    public FacVendedor(FacVendedorPK facVendedorPK, BigInteger sueldo, BigInteger retencion, BigDecimal porcVenta, BigDecimal porcCobro, BigDecimal porcEntrega) {
        this.facVendedorPK = facVendedorPK;
        this.sueldo = sueldo;
        this.retencion = retencion;
        this.porcVenta = porcVenta;
        this.porcCobro = porcCobro;
        this.porcEntrega = porcEntrega;
    }

    public FacVendedor(String codEmpresa, int codVendedor) {
        this.facVendedorPK = new FacVendedorPK(codEmpresa, codVendedor);
    }

    public FacVendedorPK getFacVendedorPK() {
        return facVendedorPK;
    }

    public void setFacVendedorPK(FacVendedorPK facVendedorPK) {
        this.facVendedorPK = facVendedorPK;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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

    public BigInteger getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigInteger sueldo) {
        this.sueldo = sueldo;
    }

    public BigInteger getRetencion() {
        return retencion;
    }

    public void setRetencion(BigInteger retencion) {
        this.retencion = retencion;
    }

    public BigDecimal getPorcVenta() {
        return porcVenta;
    }

    public void setPorcVenta(BigDecimal porcVenta) {
        this.porcVenta = porcVenta;
    }

    public BigDecimal getPorcCobro() {
        return porcCobro;
    }

    public void setPorcCobro(BigDecimal porcCobro) {
        this.porcCobro = porcCobro;
    }

    public BigDecimal getPorcEntrega() {
        return porcEntrega;
    }

    public void setPorcEntrega(BigDecimal porcEntrega) {
        this.porcEntrega = porcEntrega;
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

    @XmlTransient
    public List<ComVisitaCliente> getComVisitaClienteList() {
        return comVisitaClienteList;
    }

    public void setComVisitaClienteList(List<ComVisitaCliente> comVisitaClienteList) {
        this.comVisitaClienteList = comVisitaClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facVendedorPK != null ? facVendedorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacVendedor)) {
            return false;
        }
        FacVendedor other = (FacVendedor) object;
        if ((this.facVendedorPK == null && other.facVendedorPK != null) || (this.facVendedorPK != null && !this.facVendedorPK.equals(other.facVendedorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacVendedor[ facVendedorPK=" + facVendedorPK + " ]";
    }
    
}
