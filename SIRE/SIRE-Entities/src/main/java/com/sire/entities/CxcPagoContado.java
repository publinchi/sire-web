/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author publio
 */
@Entity
@Table(name = "CXC_PAGO_CONTADO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcPagoContado.findAll", query = "SELECT c FROM CxcPagoContado c"),
    @NamedQuery(name = "CxcPagoContado.findByCodEmpresa", query = "SELECT c FROM CxcPagoContado c WHERE c.cxcPagoContadoPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcPagoContado.findByCodDocumento", query = "SELECT c FROM CxcPagoContado c WHERE c.cxcPagoContadoPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "CxcPagoContado.findByNumPago", query = "SELECT c FROM CxcPagoContado c WHERE c.cxcPagoContadoPK.numPago = :numPago"),
    @NamedQuery(name = "CxcPagoContado.findByNombreUsuario", query = "SELECT c FROM CxcPagoContado c WHERE c.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "CxcPagoContado.findByPagoTotal", query = "SELECT c FROM CxcPagoContado c WHERE c.pagoTotal = :pagoTotal"),
    @NamedQuery(name = "CxcPagoContado.findByRetencion", query = "SELECT c FROM CxcPagoContado c WHERE c.retencion = :retencion"),
    @NamedQuery(name = "CxcPagoContado.findByCredito", query = "SELECT c FROM CxcPagoContado c WHERE c.credito = :credito"),
    @NamedQuery(name = "CxcPagoContado.findByRetencionIva", query = "SELECT c FROM CxcPagoContado c WHERE c.retencionIva = :retencionIva"),
    @NamedQuery(name = "CxcPagoContado.findByEfectivo", query = "SELECT c FROM CxcPagoContado c WHERE c.efectivo = :efectivo"),
    @NamedQuery(name = "CxcPagoContado.findByCtaCorriente", query = "SELECT c FROM CxcPagoContado c WHERE c.ctaCorriente = :ctaCorriente"),
    @NamedQuery(name = "CxcPagoContado.findByCheques", query = "SELECT c FROM CxcPagoContado c WHERE c.cheques = :cheques"),
    @NamedQuery(name = "CxcPagoContado.findByTarjeta", query = "SELECT c FROM CxcPagoContado c WHERE c.tarjeta = :tarjeta"),
    @NamedQuery(name = "CxcPagoContado.findByCodTarjeta", query = "SELECT c FROM CxcPagoContado c WHERE c.codTarjeta = :codTarjeta"),
    @NamedQuery(name = "CxcPagoContado.findByDeposito", query = "SELECT c FROM CxcPagoContado c WHERE c.deposito = :deposito"),
    @NamedQuery(name = "CxcPagoContado.findByOtros", query = "SELECT c FROM CxcPagoContado c WHERE c.otros = :otros"),
    @NamedQuery(name = "CxcPagoContado.findByDetalle", query = "SELECT c FROM CxcPagoContado c WHERE c.detalle = :detalle"),
    @NamedQuery(name = "CxcPagoContado.findByFechaDocumento", query = "SELECT c FROM CxcPagoContado c WHERE c.fechaDocumento = :fechaDocumento"),
    @NamedQuery(name = "CxcPagoContado.findByCodVendedor", query = "SELECT c FROM CxcPagoContado c WHERE c.codVendedor = :codVendedor"),
    @NamedQuery(name = "CxcPagoContado.findByFechas", query = "SELECT c FROM CxcPagoContado c WHERE (c.fechaDocumento BETWEEN :fechaInicio AND :fechaFin) "
            + "AND c.cxcPagoContadoPK.codEmpresa = :codEmpresa AND c.codVendedor = :codVendedor ORDER BY c.fechaDocumento DESC")})
public class CxcPagoContado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcPagoContadoPK cxcPagoContadoPK;
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "PAGO_TOTAL")
    private Double pagoTotal;
    @Basic(optional = false)
    @Column(name = "RETENCION")
    private Double retencion;
    @Basic(optional = false)
    @Column(name = "CREDITO")
    private BigInteger credito;
    @Basic(optional = false)
    @Column(name = "RETENCION_IVA")
    private Double retencionIva;
    @Basic(optional = false)
    @Column(name = "EFECTIVO")
    private Double efectivo;
    @Column(name = "CTA_CORRIENTE")
    private String ctaCorriente;
    @Basic(optional = false)
    @Column(name = "CHEQUES")
    private Double cheques;
    @Basic(optional = false)
    @Column(name = "TARJETA")
    private BigInteger tarjeta;
    @Column(name = "COD_TARJETA")
    private String codTarjeta;
    @Basic(optional = false)
    @Column(name = "DEPOSITO")
    private Double deposito;
    @Basic(optional = false)
    @Column(name = "OTROS")
    private Double otros;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "COD_VENDEDOR")
    private BigInteger codVendedor;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_CLIENTE", referencedColumnName = "COD_CLIENTE")})
    @ManyToOne(optional = false)
    private CxcCliente cxcCliente;

    public CxcPagoContado() {
    }

    public CxcPagoContado(CxcPagoContadoPK cxcPagoContadoPK) {
        this.cxcPagoContadoPK = cxcPagoContadoPK;
    }

    public CxcPagoContado(CxcPagoContadoPK cxcPagoContadoPK, Double retencion, BigInteger credito, Double retencionIva, Double efectivo, Double cheques, BigInteger tarjeta, Double deposito, Double otros) {
        this.cxcPagoContadoPK = cxcPagoContadoPK;
        this.retencion = retencion;
        this.credito = credito;
        this.retencionIva = retencionIva;
        this.efectivo = efectivo;
        this.cheques = cheques;
        this.tarjeta = tarjeta;
        this.deposito = deposito;
        this.otros = otros;
    }

    public CxcPagoContado(String codEmpresa, String codDocumento, BigInteger numPago) {
        this.cxcPagoContadoPK = new CxcPagoContadoPK(codEmpresa, codDocumento, numPago);
    }

    public CxcPagoContadoPK getCxcPagoContadoPK() {
        return cxcPagoContadoPK;
    }

    public void setCxcPagoContadoPK(CxcPagoContadoPK cxcPagoContadoPK) {
        this.cxcPagoContadoPK = cxcPagoContadoPK;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Double getPagoTotal() {
        return pagoTotal;
    }

    public void setPagoTotal(Double pagoTotal) {
        this.pagoTotal = pagoTotal;
    }

    public Double getRetencion() {
        return retencion;
    }

    public void setRetencion(Double retencion) {
        this.retencion = retencion;
    }

    public BigInteger getCredito() {
        return credito;
    }

    public void setCredito(BigInteger credito) {
        this.credito = credito;
    }

    public Double getRetencionIva() {
        return retencionIva;
    }

    public void setRetencionIva(Double retencionIva) {
        this.retencionIva = retencionIva;
    }

    public Double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Double efectivo) {
        this.efectivo = efectivo;
    }

    public String getCtaCorriente() {
        return ctaCorriente;
    }

    public void setCtaCorriente(String ctaCorriente) {
        this.ctaCorriente = ctaCorriente;
    }

    public Double getCheques() {
        return cheques;
    }

    public void setCheques(Double cheques) {
        this.cheques = cheques;
    }

    public BigInteger getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(BigInteger tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getCodTarjeta() {
        return codTarjeta;
    }

    public void setCodTarjeta(String codTarjeta) {
        this.codTarjeta = codTarjeta;
    }

    public Double getDeposito() {
        return deposito;
    }

    public void setDeposito(Double deposito) {
        this.deposito = deposito;
    }

    public Double getOtros() {
        return otros;
    }

    public void setOtros(Double otros) {
        this.otros = otros;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public BigInteger getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(BigInteger codVendedor) {
        this.codVendedor = codVendedor;
    }

    public CxcCliente getCxcCliente() {
        return cxcCliente;
    }

    public void setCxcCliente(CxcCliente cxcCliente) {
        this.cxcCliente = cxcCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcPagoContadoPK != null ? cxcPagoContadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcPagoContado)) {
            return false;
        }
        CxcPagoContado other = (CxcPagoContado) object;
        if ((this.cxcPagoContadoPK == null && other.cxcPagoContadoPK != null) || (this.cxcPagoContadoPK != null && !this.cxcPagoContadoPK.equals(other.cxcPagoContadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcPagoContado[ cxcPagoContadoPK=" + cxcPagoContadoPK + " ]";
    }

}
