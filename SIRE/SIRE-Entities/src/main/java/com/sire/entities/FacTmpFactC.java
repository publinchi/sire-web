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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pestupinan
 */
@Entity
@Table(name = "FAC_TMP_FACT_C")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacTmpFactC.findAll", query = "SELECT f FROM FacTmpFactC f"),
    @NamedQuery(name = "FacTmpFactC.findByCodEmpresa", query = "SELECT f FROM FacTmpFactC f WHERE f.facTmpFactCPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacTmpFactC.findByEgresoInv", query = "SELECT f FROM FacTmpFactC f WHERE f.facTmpFactCPK.egresoInv = :egresoInv"),
    @NamedQuery(name = "FacTmpFactC.findByNumFactura", query = "SELECT f FROM FacTmpFactC f WHERE f.numFactura = :numFactura"),
    @NamedQuery(name = "FacTmpFactC.findByEi", query = "SELECT f FROM FacTmpFactC f WHERE f.facTmpFactCPK.ei = :ei"),
    @NamedQuery(name = "FacTmpFactC.findByCodDocumento", query = "SELECT f FROM FacTmpFactC f WHERE f.codDocumento = :codDocumento"),
    @NamedQuery(name = "FacTmpFactC.findByCodPago", query = "SELECT f FROM FacTmpFactC f WHERE f.codPago = :codPago"),
    @NamedQuery(name = "FacTmpFactC.findByContCred", query = "SELECT f FROM FacTmpFactC f WHERE f.contCred = :contCred"),
    @NamedQuery(name = "FacTmpFactC.findByCodVendedor", query = "SELECT f FROM FacTmpFactC f WHERE f.codVendedor = :codVendedor"),
    @NamedQuery(name = "FacTmpFactC.findByCodCliente", query = "SELECT f FROM FacTmpFactC f WHERE f.codCliente = :codCliente"),
    @NamedQuery(name = "FacTmpFactC.findByPorcComision", query = "SELECT f FROM FacTmpFactC f WHERE f.porcComision = :porcComision"),
    @NamedQuery(name = "FacTmpFactC.findByCodDivisa", query = "SELECT f FROM FacTmpFactC f WHERE f.codDivisa = :codDivisa"),
    @NamedQuery(name = "FacTmpFactC.findByCodLista", query = "SELECT f FROM FacTmpFactC f WHERE f.codLista = :codLista"),
    @NamedQuery(name = "FacTmpFactC.findByRazonSocial", query = "SELECT f FROM FacTmpFactC f WHERE f.razonSocial = :razonSocial"),
    @NamedQuery(name = "FacTmpFactC.findByValorDivisa", query = "SELECT f FROM FacTmpFactC f WHERE f.valorDivisa = :valorDivisa"),
    @NamedQuery(name = "FacTmpFactC.findByTipoFactura", query = "SELECT f FROM FacTmpFactC f WHERE f.tipoFactura = :tipoFactura"),
    @NamedQuery(name = "FacTmpFactC.findByFechaFactura", query = "SELECT f FROM FacTmpFactC f WHERE f.fechaFactura = :fechaFactura"),
    @NamedQuery(name = "FacTmpFactC.findByDescuentos", query = "SELECT f FROM FacTmpFactC f WHERE f.descuentos = :descuentos"),
    @NamedQuery(name = "FacTmpFactC.findByOtrDescuentos", query = "SELECT f FROM FacTmpFactC f WHERE f.otrDescuentos = :otrDescuentos"),
    @NamedQuery(name = "FacTmpFactC.findByIva", query = "SELECT f FROM FacTmpFactC f WHERE f.iva = :iva"),
    @NamedQuery(name = "FacTmpFactC.findByRecargos", query = "SELECT f FROM FacTmpFactC f WHERE f.recargos = :recargos"),
    @NamedQuery(name = "FacTmpFactC.findByTotalSinIva", query = "SELECT f FROM FacTmpFactC f WHERE f.totalSinIva = :totalSinIva"),
    @NamedQuery(name = "FacTmpFactC.findByEstado", query = "SELECT f FROM FacTmpFactC f WHERE f.estado = :estado"),
    @NamedQuery(name = "FacTmpFactC.findByTotalConIva", query = "SELECT f FROM FacTmpFactC f WHERE f.totalConIva = :totalConIva"),
    @NamedQuery(name = "FacTmpFactC.findByFechaEstado", query = "SELECT f FROM FacTmpFactC f WHERE f.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "FacTmpFactC.findByTotalFactura", query = "SELECT f FROM FacTmpFactC f WHERE f.totalFactura = :totalFactura"),
    @NamedQuery(name = "FacTmpFactC.findByFechas", query = "SELECT f FROM FacTmpFactC f WHERE f.estado BETWEEN :fechaInicio AND :fechaFin")})
public class FacTmpFactC implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacTmpFactCPK facTmpFactCPK;
    @Column(name = "NUM_FACTURA")
    private Long numFactura;
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Column(name = "COD_PAGO")
    private String codPago;
    @Column(name = "CONT_CRED")
    private String contCred;
    @Column(name = "COD_VENDEDOR")
    private Integer codVendedor;
    @Column(name = "COD_CLIENTE")
    private Long codCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PORC_COMISION")
    private BigDecimal porcComision;
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Column(name = "COD_LISTA")
    private String codLista;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Basic(optional = false)
    @Column(name = "VALOR_DIVISA")
    private BigInteger valorDivisa;
    @Column(name = "TIPO_FACTURA")
    private String tipoFactura;
    @Column(name = "FECHA_FACTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFactura;
    @Basic(optional = false)
    @Column(name = "DESCUENTOS")
    private BigInteger descuentos;
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
    @Column(name = "TOTAL_SIN_IVA")
    private Double totalSinIva;
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "TOTAL_CON_IVA")
    private long totalConIva;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Column(name = "TOTAL_FACTURA")
    private BigDecimal totalFactura;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO")
    @ManyToOne
    private GnrUsuarios nombreUsuario;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "facTmpFactC")
    private List<FacTmpFactD> facTmpFactDList;

    public FacTmpFactC() {
    }

    public FacTmpFactC(FacTmpFactCPK facTmpFactCPK) {
        this.facTmpFactCPK = facTmpFactCPK;
    }

    public FacTmpFactC(FacTmpFactCPK facTmpFactCPK, BigDecimal porcComision, BigInteger valorDivisa, BigInteger descuentos, BigInteger otrDescuentos, BigInteger iva, BigInteger recargos, Double totalSinIva, long totalConIva) {
        this.facTmpFactCPK = facTmpFactCPK;
        this.porcComision = porcComision;
        this.valorDivisa = valorDivisa;
        this.descuentos = descuentos;
        this.otrDescuentos = otrDescuentos;
        this.iva = iva;
        this.recargos = recargos;
        this.totalSinIva = totalSinIva;
        this.totalConIva = totalConIva;
    }

    public FacTmpFactC(String codEmpresa, int egresoInv, String ei) {
        this.facTmpFactCPK = new FacTmpFactCPK(codEmpresa, egresoInv, ei);
    }

    public FacTmpFactCPK getFacTmpFactCPK() {
        return facTmpFactCPK;
    }

    public void setFacTmpFactCPK(FacTmpFactCPK facTmpFactCPK) {
        this.facTmpFactCPK = facTmpFactCPK;
    }

    public Long getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(Long numFactura) {
        this.numFactura = numFactura;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getCodPago() {
        return codPago;
    }

    public void setCodPago(String codPago) {
        this.codPago = codPago;
    }

    public String getContCred() {
        return contCred;
    }

    public void setContCred(String contCred) {
        this.contCred = contCred;
    }

    public Integer getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(Integer codVendedor) {
        this.codVendedor = codVendedor;
    }

    public Long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Long codCliente) {
        this.codCliente = codCliente;
    }

    public BigDecimal getPorcComision() {
        return porcComision;
    }

    public void setPorcComision(BigDecimal porcComision) {
        this.porcComision = porcComision;
    }

    public String getCodDivisa() {
        return codDivisa;
    }

    public void setCodDivisa(String codDivisa) {
        this.codDivisa = codDivisa;
    }

    public String getCodLista() {
        return codLista;
    }

    public void setCodLista(String codLista) {
        this.codLista = codLista;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public BigInteger getValorDivisa() {
        return valorDivisa;
    }

    public void setValorDivisa(BigInteger valorDivisa) {
        this.valorDivisa = valorDivisa;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public BigInteger getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(BigInteger descuentos) {
        this.descuentos = descuentos;
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

    public Double getTotalSinIva() {
        return totalSinIva;
    }

    public void setTotalSinIva(Double totalSinIva) {
        this.totalSinIva = totalSinIva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getTotalConIva() {
        return totalConIva;
    }

    public void setTotalConIva(long totalConIva) {
        this.totalConIva = totalConIva;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    public GnrUsuarios getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(GnrUsuarios nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

//    @XmlTransient
    public List<FacTmpFactD> getFacTmpFactDList() {
        return facTmpFactDList;
    }

    public void setFacTmpFactDList(List<FacTmpFactD> facTmpFactDList) {
        this.facTmpFactDList = facTmpFactDList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facTmpFactCPK != null ? facTmpFactCPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacTmpFactC)) {
            return false;
        }
        FacTmpFactC other = (FacTmpFactC) object;
        if ((this.facTmpFactCPK == null && other.facTmpFactCPK != null) || (this.facTmpFactCPK != null && !this.facTmpFactCPK.equals(other.facTmpFactCPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacTmpFactC[ facTmpFactCPK=" + facTmpFactCPK + " ]";
    }
}
