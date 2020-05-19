/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import com.sire.utils.Round;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author publio
 */
@Entity
@Table(name = "CXC_DOC_COBRAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcDocCobrar.findAll", query = "SELECT c FROM CxcDocCobrar c"),
    @NamedQuery(name = "CxcDocCobrar.findByCodEmpresa", query = "SELECT c FROM CxcDocCobrar c WHERE c.cxcDocCobrarPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcDocCobrar.findByCodDocumento", query = "SELECT c FROM CxcDocCobrar c WHERE c.cxcDocCobrarPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "CxcDocCobrar.findByNumDocumento", query = "SELECT c FROM CxcDocCobrar c WHERE c.cxcDocCobrarPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "CxcDocCobrar.findByFechaEmision", query = "SELECT c FROM CxcDocCobrar c WHERE c.fechaEmision = :fechaEmision"),
    @NamedQuery(name = "CxcDocCobrar.findByNumeroCuota", query = "SELECT c FROM CxcDocCobrar c WHERE c.cxcDocCobrarPK.numeroCuota = :numeroCuota"),
    @NamedQuery(name = "CxcDocCobrar.findByCodMotivo", query = "SELECT c FROM CxcDocCobrar c WHERE c.codMotivo = :codMotivo"),
    @NamedQuery(name = "CxcDocCobrar.findByCodDivisa", query = "SELECT c FROM CxcDocCobrar c WHERE c.codDivisa = :codDivisa"),
    @NamedQuery(name = "CxcDocCobrar.findByDiasPlazo", query = "SELECT c FROM CxcDocCobrar c WHERE c.diasPlazo = :diasPlazo"),
    @NamedQuery(name = "CxcDocCobrar.findByFechaVencimiento", query = "SELECT c FROM CxcDocCobrar c WHERE c.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "CxcDocCobrar.findByCodVendedor", query = "SELECT c FROM CxcDocCobrar c WHERE c.codVendedor = :codVendedor"),
    @NamedQuery(name = "CxcDocCobrar.findByFechaCancelacion", query = "SELECT c FROM CxcDocCobrar c WHERE c.fechaCancelacion = :fechaCancelacion"),
    @NamedQuery(name = "CxcDocCobrar.findByPorcComision", query = "SELECT c FROM CxcDocCobrar c WHERE c.porcComision = :porcComision"),
    @NamedQuery(name = "CxcDocCobrar.findByValorDocumento", query = "SELECT c FROM CxcDocCobrar c WHERE c.valorDocumento = :valorDocumento"),
    @NamedQuery(name = "CxcDocCobrar.findBySaldoDocumento", query = "SELECT c FROM CxcDocCobrar c WHERE c.saldoDocumento = :saldoDocumento"),
    @NamedQuery(name = "CxcDocCobrar.findByDetalle", query = "SELECT c FROM CxcDocCobrar c WHERE c.detalle = :detalle"),
    @NamedQuery(name = "CxcDocCobrar.findByEstado", query = "SELECT c FROM CxcDocCobrar c WHERE c.estado = :estado"),
    @NamedQuery(name = "CxcDocCobrar.findByFechaEstado", query = "SELECT c FROM CxcDocCobrar c WHERE c.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "CxcDocCobrar.findByNroPagos", query = "SELECT c FROM CxcDocCobrar c WHERE c.nroPagos = :nroPagos"),
    @NamedQuery(name = "CxcDocCobrar.findByCodCliente", query = "SELECT c FROM CxcDocCobrar c WHERE c.cxcCliente.cxcClientePK.codCliente = :codCliente and c.saldoDocumento > 0"),
    @NamedQuery(name = "CxcDocCobrar.findByCodClienteCodVendedor", query = "SELECT c FROM CxcDocCobrar c WHERE c.cxcCliente.cxcClientePK.codCliente = :codCliente and c.saldoDocumento > 0 and c.codVendedor = :codVendedor"),
    @NamedQuery(name = "CxcDocCobrar.sumSaldoDocumentoByCodClienteCodEmpresaFechaFac", query = "SELECT SUM(c.saldoDocumento) FROM CxcDocCobrar c WHERE c.cxcDocCobrarPK.codEmpresa = :codEmpresa AND c.cxcCliente.cxcClientePK.codCliente = :codCliente AND c.saldoDocumento <> 0 AND FUNC('TO_CHAR', FUNC('TRUNC',c.fechaEmision),'MMRRRR') = :fechaFac"),
    @NamedQuery(name = "CxcDocCobrar.sumCapitalByCodClienteCodEmpresaFechaEmision", query = "SELECT SUM(a.capital) FROM VCobros a, CxcDocCobrar b WHERE a.codEmpresa = :codEmpresa AND a.codDocumento = :codDocumento AND a.numAbono = :numDocumento AND a.codEmpresa = b.cxcDocCobrarPK.codEmpresa AND a.codAbono = b.cxcDocCobrarPK.codDocumento AND a.numDocumento = b.cxcDocCobrarPK.numDocumento AND FUNC('TO_CHAR', FUNC('TRUNC',b.fechaEmision),'MMRRRR') = :fechaEmision")})
public class CxcDocCobrar implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcDocCobrarPK cxcDocCobrarPK;
    @Column(name = "FECHA_EMISION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    @Column(name = "COD_MOTIVO")
    private String codMotivo;
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Basic(optional = false)
    @Column(name = "DIAS_PLAZO")
    private BigInteger diasPlazo;
    @Column(name = "FECHA_VENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Column(name = "COD_VENDEDOR")
    private BigInteger codVendedor;
    @Column(name = "FECHA_CANCELACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCancelacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PORC_COMISION")
    private BigDecimal porcComision;
    @Basic(optional = false)
    @Column(name = "VALOR_DOCUMENTO")
    private Double valorDocumento;
    @Basic(optional = false)
    @Column(name = "SALDO_DOCUMENTO")
    private Double saldoDocumento;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Basic(optional = false)
    @Column(name = "NRO_PAGOS")
    private int nroPagos;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_CLIENTE", referencedColumnName = "COD_CLIENTE")})
    @ManyToOne(optional = false)
    private CxcCliente cxcCliente;

    @Getter
    @Setter
    @Transient
    private Double capital, saldoOri;

    public CxcDocCobrar() {
    }

    public CxcDocCobrar(CxcDocCobrarPK cxcDocCobrarPK) {
        this.cxcDocCobrarPK = cxcDocCobrarPK;
    }

    public CxcDocCobrar(CxcDocCobrarPK cxcDocCobrarPK, BigInteger diasPlazo, BigDecimal porcComision, Double valorDocumento, Double saldoDocumento, int nroPagos) {
        this.cxcDocCobrarPK = cxcDocCobrarPK;
        this.diasPlazo = diasPlazo;
        this.porcComision = porcComision;
        this.valorDocumento = valorDocumento;
        this.saldoDocumento = saldoDocumento;
        this.nroPagos = nroPagos;
    }

    public CxcDocCobrar(String codEmpresa, String codDocumento, BigInteger numDocumento, BigInteger numeroCuota) {
        this.cxcDocCobrarPK = new CxcDocCobrarPK(codEmpresa, codDocumento, numDocumento, numeroCuota);
    }

    public CxcDocCobrarPK getCxcDocCobrarPK() {
        return cxcDocCobrarPK;
    }

    public void setCxcDocCobrarPK(CxcDocCobrarPK cxcDocCobrarPK) {
        this.cxcDocCobrarPK = cxcDocCobrarPK;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public String getCodDivisa() {
        return codDivisa;
    }

    public void setCodDivisa(String codDivisa) {
        this.codDivisa = codDivisa;
    }

    public BigInteger getDiasPlazo() {
        return diasPlazo;
    }

    public void setDiasPlazo(BigInteger diasPlazo) {
        this.diasPlazo = diasPlazo;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigInteger getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(BigInteger codVendedor) {
        this.codVendedor = codVendedor;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public BigDecimal getPorcComision() {
        return porcComision;
    }

    public void setPorcComision(BigDecimal porcComision) {
        this.porcComision = porcComision;
    }

    public Double getValorDocumento() {
        return valorDocumento;
    }

    public void setValorDocumento(Double valorDocumento) {
        this.valorDocumento = valorDocumento;
    }

    public Double getSaldoDocumento() {
        if(Objects.nonNull(saldoDocumento))
            return Round.round(saldoDocumento, 2);
        return Round.round(0, 2);
    }

    public void setSaldoDocumento(Double saldoDocumento) {
        this.saldoDocumento = saldoDocumento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    public int getNroPagos() {
        return nroPagos;
    }

    public void setNroPagos(int nroPagos) {
        this.nroPagos = nroPagos;
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
        hash += (cxcDocCobrarPK != null ? cxcDocCobrarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcDocCobrar)) {
            return false;
        }
        CxcDocCobrar other = (CxcDocCobrar) object;
        if ((this.cxcDocCobrarPK == null && other.cxcDocCobrarPK != null) || (this.cxcDocCobrarPK != null && !this.cxcDocCobrarPK.equals(other.cxcDocCobrarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcDocCobrar[ cxcDocCobrarPK=" + cxcDocCobrarPK + " ]";
    }

}
