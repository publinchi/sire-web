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
@Table(name = "CXC_CHEQUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcCheque.findAll", query = "SELECT c FROM CxcCheque c"),
    @NamedQuery(name = "CxcCheque.findByCodEmpresa", query = "SELECT c FROM CxcCheque c WHERE c.cxcChequePK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcCheque.findByCodDocumento", query = "SELECT c FROM CxcCheque c WHERE c.cxcChequePK.codDocumento = :codDocumento"),
    @NamedQuery(name = "CxcCheque.findByNumDocumento", query = "SELECT c FROM CxcCheque c WHERE c.cxcChequePK.numDocumento = :numDocumento"),
    @NamedQuery(name = "CxcCheque.findByNumCheque", query = "SELECT c FROM CxcCheque c WHERE c.numCheque = :numCheque"),
    @NamedQuery(name = "CxcCheque.findByCodDivisa", query = "SELECT c FROM CxcCheque c WHERE c.codDivisa = :codDivisa"),
    @NamedQuery(name = "CxcCheque.findByAuxiliar", query = "SELECT c FROM CxcCheque c WHERE c.cxcChequePK.auxiliar = :auxiliar"),
    @NamedQuery(name = "CxcCheque.findByCodBanco", query = "SELECT c FROM CxcCheque c WHERE c.codBanco = :codBanco"),
    @NamedQuery(name = "CxcCheque.findByFechaRecepcion", query = "SELECT c FROM CxcCheque c WHERE c.fechaRecepcion = :fechaRecepcion"),
    @NamedQuery(name = "CxcCheque.findByNumCuenta", query = "SELECT c FROM CxcCheque c WHERE c.numCuenta = :numCuenta"),
    @NamedQuery(name = "CxcCheque.findByFechaCheque", query = "SELECT c FROM CxcCheque c WHERE c.fechaCheque = :fechaCheque"),
    @NamedQuery(name = "CxcCheque.findByValorCheque", query = "SELECT c FROM CxcCheque c WHERE c.valorCheque = :valorCheque"),
    @NamedQuery(name = "CxcCheque.findByReferencia", query = "SELECT c FROM CxcCheque c WHERE c.referencia = :referencia"),
    @NamedQuery(name = "CxcCheque.findByCodDeposito", query = "SELECT c FROM CxcCheque c WHERE c.codDeposito = :codDeposito"),
    @NamedQuery(name = "CxcCheque.findByNumDeposito", query = "SELECT c FROM CxcCheque c WHERE c.numDeposito = :numDeposito"),
    @NamedQuery(name = "CxcCheque.findByEstado", query = "SELECT c FROM CxcCheque c WHERE c.estado = :estado"),
    @NamedQuery(name = "CxcCheque.findByDetalle", query = "SELECT c FROM CxcCheque c WHERE c.detalle = :detalle"),
    @NamedQuery(name = "CxcCheque.findByFechaEstado", query = "SELECT c FROM CxcCheque c WHERE c.fechaEstado = :fechaEstado")})
public class CxcCheque implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcChequePK cxcChequePK;
    @Basic(optional = false)
    @Column(name = "NUM_CHEQUE")
    private BigInteger numCheque;
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Column(name = "COD_BANCO")
    private String codBanco;
    @Column(name = "FECHA_RECEPCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;
    @Column(name = "NUM_CUENTA")
    private String numCuenta;
    @Column(name = "FECHA_CHEQUE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCheque;
    @Basic(optional = false)
    @Column(name = "VALOR_CHEQUE")
    private Double valorCheque;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "COD_DEPOSITO")
    private String codDeposito;
    @Basic(optional = false)
    @Column(name = "NUM_DEPOSITO")
    private long numDeposito;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_CLIENTE", referencedColumnName = "COD_CLIENTE")})
    @ManyToOne(optional = false)
    private CxcCliente cxcCliente;

    public CxcCheque() {
    }

    public CxcCheque(CxcChequePK cxcChequePK) {
        this.cxcChequePK = cxcChequePK;
    }

    public CxcCheque(CxcChequePK cxcChequePK, BigInteger numCheque, Double valorCheque, long numDeposito) {
        this.cxcChequePK = cxcChequePK;
        this.numCheque = numCheque;
        this.valorCheque = valorCheque;
        this.numDeposito = numDeposito;
    }

    public CxcCheque(String codEmpresa, String codDocumento, long numDocumento, int auxiliar) {
        this.cxcChequePK = new CxcChequePK(codEmpresa, codDocumento, numDocumento, auxiliar);
    }

    public CxcChequePK getCxcChequePK() {
        return cxcChequePK;
    }

    public void setCxcChequePK(CxcChequePK cxcChequePK) {
        this.cxcChequePK = cxcChequePK;
    }

    public BigInteger getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(BigInteger numCheque) {
        this.numCheque = numCheque;
    }

    public String getCodDivisa() {
        return codDivisa;
    }

    public void setCodDivisa(String codDivisa) {
        this.codDivisa = codDivisa;
    }

    public String getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(String codBanco) {
        this.codBanco = codBanco;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Date getFechaCheque() {
        return fechaCheque;
    }

    public void setFechaCheque(Date fechaCheque) {
        this.fechaCheque = fechaCheque;
    }

    public Double getValorCheque() {
        return valorCheque;
    }

    public void setValorCheque(Double valorCheque) {
        this.valorCheque = valorCheque;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCodDeposito() {
        return codDeposito;
    }

    public void setCodDeposito(String codDeposito) {
        this.codDeposito = codDeposito;
    }

    public long getNumDeposito() {
        return numDeposito;
    }

    public void setNumDeposito(long numDeposito) {
        this.numDeposito = numDeposito;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
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
        hash += (cxcChequePK != null ? cxcChequePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcCheque)) {
            return false;
        }
        CxcCheque other = (CxcCheque) object;
        if ((this.cxcChequePK == null && other.cxcChequePK != null) || (this.cxcChequePK != null && !this.cxcChequePK.equals(other.cxcChequePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcCheque[ cxcChequePK=" + cxcChequePK + " ]";
    }
    
}
