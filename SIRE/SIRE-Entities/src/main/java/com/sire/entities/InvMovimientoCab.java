/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "INV_MOVIMIENTO_CAB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvMovimientoCab.findAll", query = "SELECT i FROM InvMovimientoCab i"),
    @NamedQuery(name = "InvMovimientoCab.findByCodEmpresa", query = "SELECT i FROM InvMovimientoCab i WHERE i.invMovimientoCabPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvMovimientoCab.findByCodDocumento", query = "SELECT i FROM InvMovimientoCab i WHERE i.invMovimientoCabPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "InvMovimientoCab.findByNumDocumento", query = "SELECT i FROM InvMovimientoCab i WHERE i.invMovimientoCabPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "InvMovimientoCab.findByNumRetencion", query = "SELECT i FROM InvMovimientoCab i WHERE i.numRetencion = :numRetencion"),
    @NamedQuery(name = "InvMovimientoCab.findByFechaEmision", query = "SELECT i FROM InvMovimientoCab i WHERE i.fechaEmision = :fechaEmision"),
    @NamedQuery(name = "InvMovimientoCab.findByReferencia", query = "SELECT i FROM InvMovimientoCab i WHERE i.referencia = :referencia"),
    @NamedQuery(name = "InvMovimientoCab.findByDetalle", query = "SELECT i FROM InvMovimientoCab i WHERE i.detalle = :detalle"),
    @NamedQuery(name = "InvMovimientoCab.findByDigitadoPor", query = "SELECT i FROM InvMovimientoCab i WHERE i.digitadoPor = :digitadoPor"),
    @NamedQuery(name = "InvMovimientoCab.findByEstado", query = "SELECT i FROM InvMovimientoCab i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvMovimientoCab.findByTotalDocumento", query = "SELECT i FROM InvMovimientoCab i WHERE i.totalDocumento = :totalDocumento"),
    @NamedQuery(name = "InvMovimientoCab.findBySubtotal", query = "SELECT i FROM InvMovimientoCab i WHERE i.subtotal = :subtotal"),
    @NamedQuery(name = "InvMovimientoCab.findByDescuentos", query = "SELECT i FROM InvMovimientoCab i WHERE i.descuentos = :descuentos"),
    @NamedQuery(name = "InvMovimientoCab.findByFechaEstado", query = "SELECT i FROM InvMovimientoCab i WHERE i.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "InvMovimientoCab.findByOtrDescuentos", query = "SELECT i FROM InvMovimientoCab i WHERE i.otrDescuentos = :otrDescuentos"),
    @NamedQuery(name = "InvMovimientoCab.findByIva", query = "SELECT i FROM InvMovimientoCab i WHERE i.iva = :iva"),
    @NamedQuery(name = "InvMovimientoCab.findByRecargos", query = "SELECT i FROM InvMovimientoCab i WHERE i.recargos = :recargos"),
    @NamedQuery(name = "InvMovimientoCab.findByFletes", query = "SELECT i FROM InvMovimientoCab i WHERE i.fletes = :fletes"),
    @NamedQuery(name = "InvMovimientoCab.findByOtrCargos", query = "SELECT i FROM InvMovimientoCab i WHERE i.otrCargos = :otrCargos"),
    @NamedQuery(name = "InvMovimientoCab.findByRetencion", query = "SELECT i FROM InvMovimientoCab i WHERE i.retencion = :retencion"),
    @NamedQuery(name = "InvMovimientoCab.findByFormaPago", query = "SELECT i FROM InvMovimientoCab i WHERE i.formaPago = :formaPago"),
    @NamedQuery(name = "InvMovimientoCab.findByDiasPlazo", query = "SELECT i FROM InvMovimientoCab i WHERE i.diasPlazo = :diasPlazo"),
    @NamedQuery(name = "InvMovimientoCab.findByFechaVencimiento", query = "SELECT i FROM InvMovimientoCab i WHERE i.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "InvMovimientoCab.findByNroCuotas", query = "SELECT i FROM InvMovimientoCab i WHERE i.nroCuotas = :nroCuotas"),
    @NamedQuery(name = "InvMovimientoCab.findByTConIva", query = "SELECT i FROM InvMovimientoCab i WHERE i.tConIva = :tConIva"),
    @NamedQuery(name = "InvMovimientoCab.findByTSinIva", query = "SELECT i FROM InvMovimientoCab i WHERE i.tSinIva = :tSinIva"),
    @NamedQuery(name = "InvMovimientoCab.findByAutoContDoc", query = "SELECT i FROM InvMovimientoCab i WHERE i.autoContDoc = :autoContDoc"),
    @NamedQuery(name = "InvMovimientoCab.findByAutoContImprDoc", query = "SELECT i FROM InvMovimientoCab i WHERE i.autoContImprDoc = :autoContImprDoc"),
    @NamedQuery(name = "InvMovimientoCab.findByFechEmisDoc", query = "SELECT i FROM InvMovimientoCab i WHERE i.fechEmisDoc = :fechEmisDoc"),
    @NamedQuery(name = "InvMovimientoCab.findByFechCaduDoc", query = "SELECT i FROM InvMovimientoCab i WHERE i.fechCaduDoc = :fechCaduDoc"),
    @NamedQuery(name = "InvMovimientoCab.findByNumFactRete", query = "SELECT i FROM InvMovimientoCab i WHERE i.numFactRete = :numFactRete")})
public class InvMovimientoCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvMovimientoCabPK invMovimientoCabPK;
    @Column(name = "NUM_RETENCION")
    private BigInteger numRetencion;
    @Column(name = "FECHA_EMISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "DIGITADO_POR")
    private String digitadoPor;
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "TOTAL_DOCUMENTO")
    private Double totalDocumento;
    @Basic(optional = false)
    @Column(name = "SUBTOTAL")
    private Double subtotal;
    @Basic(optional = false)
    @Column(name = "DESCUENTOS")
    private BigInteger descuentos;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Basic(optional = false)
    @Column(name = "OTR_DESCUENTOS")
    private BigInteger otrDescuentos;
    @Basic(optional = false)
    @Column(name = "IVA")
    private BigInteger iva;
    @Basic(optional = false)
    @Column(name = "RECARGOS")
    private BigInteger recargos;
    @Basic(optional = false)
    @Column(name = "FLETES")
    private BigInteger fletes;
    @Basic(optional = false)
    @Column(name = "OTR_CARGOS")
    private BigInteger otrCargos;
    @Column(name = "RETENCION")
    private BigInteger retencion;
    @Column(name = "FORMA_PAGO")
    private String formaPago;
    @Column(name = "DIAS_PLAZO")
    private Integer diasPlazo;
    @Column(name = "FECHA_VENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Column(name = "NRO_CUOTAS")
    private Integer nroCuotas;
    @Column(name = "T_CON_IVA")
    private Double tConIva;
    @Column(name = "T_SIN_IVA")
    private Double tSinIva;
    @Column(name = "AUTO_CONT_DOC")
    private String autoContDoc;
    @Column(name = "AUTO_CONT_IMPR_DOC")
    private String autoContImprDoc;
    @Column(name = "FECH_EMIS_DOC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechEmisDoc;
    @Column(name = "FECH_CADU_DOC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechCaduDoc;
    @Column(name = "NUM_FACT_RETE")
    private String numFactRete;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_CLIENTE", referencedColumnName = "COD_CLIENTE")})
    @ManyToOne(optional = false)
    private CxcCliente cxcCliente;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_DIVISA", referencedColumnName = "COD_DIVISA")})
    @ManyToOne(optional = false)
    private GnrDivisa gnrDivisa;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_PROVEEDOR", referencedColumnName = "COD_PROVEEDOR")})
    @ManyToOne(optional = false)
    private InvProveedor invProveedor;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_MOVIMIENTO", referencedColumnName = "COD_MOVIMIENTO")})
    @ManyToOne(optional = false)
    private InvTransacciones invTransacciones;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "invMovimientoCab")
    private List<InvMovimientoDtll> invMovimientoDtllList;

    @Transient
    private Integer codVendedor;
    @Transient
    private GnrUsuarios nombreUsuario;
    @Transient
    private String razonSocial;

    public InvMovimientoCab() {
    }

    public InvMovimientoCab(InvMovimientoCabPK invMovimientoCabPK) {
        this.invMovimientoCabPK = invMovimientoCabPK;
    }

    public InvMovimientoCab(InvMovimientoCabPK invMovimientoCabPK, Double totalDocumento, Double subtotal, BigInteger descuentos, BigInteger otrDescuentos, BigInteger iva, BigInteger recargos, BigInteger fletes, BigInteger otrCargos) {
        this.invMovimientoCabPK = invMovimientoCabPK;
        this.totalDocumento = totalDocumento;
        this.subtotal = subtotal;
        this.descuentos = descuentos;
        this.otrDescuentos = otrDescuentos;
        this.iva = iva;
        this.recargos = recargos;
        this.fletes = fletes;
        this.otrCargos = otrCargos;
    }

    public InvMovimientoCab(String codEmpresa, String codDocumento, long numDocumento) {
        this.invMovimientoCabPK = new InvMovimientoCabPK(codEmpresa, codDocumento, numDocumento);
    }

    public InvMovimientoCabPK getInvMovimientoCabPK() {
        return invMovimientoCabPK;
    }

    public void setInvMovimientoCabPK(InvMovimientoCabPK invMovimientoCabPK) {
        this.invMovimientoCabPK = invMovimientoCabPK;
    }

    public BigInteger getNumRetencion() {
        return numRetencion;
    }

    public void setNumRetencion(BigInteger numRetencion) {
        this.numRetencion = numRetencion;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDigitadoPor() {
        return digitadoPor;
    }

    public void setDigitadoPor(String digitadoPor) {
        this.digitadoPor = digitadoPor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(Double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public BigInteger getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigInteger descuentos) {
        this.descuentos = descuentos;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public BigInteger getOtrDescuentos() {
        return otrDescuentos;
    }

    public void setOtrDescuentos(BigInteger otrDescuentos) {
        this.otrDescuentos = otrDescuentos;
    }

    public BigInteger getIva() {
        return iva;
    }

    public void setIva(BigInteger iva) {
        this.iva = iva;
    }

    public BigInteger getRecargos() {
        return recargos;
    }

    public void setRecargos(BigInteger recargos) {
        this.recargos = recargos;
    }

    public BigInteger getFletes() {
        return fletes;
    }

    public void setFletes(BigInteger fletes) {
        this.fletes = fletes;
    }

    public BigInteger getOtrCargos() {
        return otrCargos;
    }

    public void setOtrCargos(BigInteger otrCargos) {
        this.otrCargos = otrCargos;
    }

    public BigInteger getRetencion() {
        return retencion;
    }

    public void setRetencion(BigInteger retencion) {
        this.retencion = retencion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Integer getDiasPlazo() {
        return diasPlazo;
    }

    public void setDiasPlazo(Integer diasPlazo) {
        this.diasPlazo = diasPlazo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getNroCuotas() {
        return nroCuotas;
    }

    public void setNroCuotas(Integer nroCuotas) {
        this.nroCuotas = nroCuotas;
    }

    public Double getTConIva() {
        return tConIva;
    }

    public void setTConIva(Double tConIva) {
        this.tConIva = tConIva;
    }

    public Double getTSinIva() {
        return tSinIva;
    }

    public void setTSinIva(Double tSinIva) {
        this.tSinIva = tSinIva;
    }

    public String getAutoContDoc() {
        return autoContDoc;
    }

    public void setAutoContDoc(String autoContDoc) {
        this.autoContDoc = autoContDoc;
    }

    public String getAutoContImprDoc() {
        return autoContImprDoc;
    }

    public void setAutoContImprDoc(String autoContImprDoc) {
        this.autoContImprDoc = autoContImprDoc;
    }

    public Date getFechEmisDoc() {
        return fechEmisDoc;
    }

    public void setFechEmisDoc(Date fechEmisDoc) {
        this.fechEmisDoc = fechEmisDoc;
    }

    public Date getFechCaduDoc() {
        return fechCaduDoc;
    }

    public void setFechCaduDoc(Date fechCaduDoc) {
        this.fechCaduDoc = fechCaduDoc;
    }

    public String getNumFactRete() {
        return numFactRete;
    }

    public void setNumFactRete(String numFactRete) {
        this.numFactRete = numFactRete;
    }

    public CxcCliente getCxcCliente() {
        return cxcCliente;
    }

    public void setCxcCliente(CxcCliente cxcCliente) {
        this.cxcCliente = cxcCliente;
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

    public InvProveedor getInvProveedor() {
        return invProveedor;
    }

    public void setInvProveedor(InvProveedor invProveedor) {
        this.invProveedor = invProveedor;
    }

    public InvTransacciones getInvTransacciones() {
        return invTransacciones;
    }

    public void setInvTransacciones(InvTransacciones invTransacciones) {
        this.invTransacciones = invTransacciones;
    }

//    @XmlTransient
    public List<InvMovimientoDtll> getInvMovimientoDtllList() {
        return invMovimientoDtllList;
    }

    public void setInvMovimientoDtllList(List<InvMovimientoDtll> invMovimientoDtllList) {
        this.invMovimientoDtllList = invMovimientoDtllList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invMovimientoCabPK != null ? invMovimientoCabPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMovimientoCab)) {
            return false;
        }
        InvMovimientoCab other = (InvMovimientoCab) object;
        if ((this.invMovimientoCabPK == null && other.invMovimientoCabPK != null) || (this.invMovimientoCabPK != null && !this.invMovimientoCabPK.equals(other.invMovimientoCabPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvMovimientoCab[ invMovimientoCabPK=" + invMovimientoCabPK + " ]";
    }

    public Integer getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(Integer codVendedor) {
        this.codVendedor = codVendedor;
    }

    public void setNombreUsuario(GnrUsuarios gnrUsuarios) {
        this.nombreUsuario = gnrUsuarios;
    }

    public GnrUsuarios getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

}
