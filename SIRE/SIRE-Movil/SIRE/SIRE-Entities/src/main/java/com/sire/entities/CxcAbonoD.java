/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author publio
 */
@Entity
@Table(name = "CXC_ABONO_D")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcAbonoD.findAll", query = "SELECT c FROM CxcAbonoD c"),
    @NamedQuery(name = "CxcAbonoD.findByCodEmpresa", query = "SELECT c FROM CxcAbonoD c WHERE c.cxcAbonoDPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcAbonoD.findByCodDocumento", query = "SELECT c FROM CxcAbonoD c WHERE c.cxcAbonoDPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "CxcAbonoD.findByNumAbono", query = "SELECT c FROM CxcAbonoD c WHERE c.cxcAbonoDPK.numAbono = :numAbono"),
    @NamedQuery(name = "CxcAbonoD.findByAuxiliar", query = "SELECT c FROM CxcAbonoD c WHERE c.cxcAbonoDPK.auxiliar = :auxiliar"),
    @NamedQuery(name = "CxcAbonoD.findByCodAbono", query = "SELECT c FROM CxcAbonoD c WHERE c.codAbono = :codAbono"),
    @NamedQuery(name = "CxcAbonoD.findByNumDocumento", query = "SELECT c FROM CxcAbonoD c WHERE c.numDocumento = :numDocumento"),
    @NamedQuery(name = "CxcAbonoD.findByDias", query = "SELECT c FROM CxcAbonoD c WHERE c.dias = :dias"),
    @NamedQuery(name = "CxcAbonoD.findByCapital", query = "SELECT c FROM CxcAbonoD c WHERE c.capital = :capital"),
    @NamedQuery(name = "CxcAbonoD.findByValorMora", query = "SELECT c FROM CxcAbonoD c WHERE c.valorMora = :valorMora"),
    @NamedQuery(name = "CxcAbonoD.findByPorcComision", query = "SELECT c FROM CxcAbonoD c WHERE c.porcComision = :porcComision"),
    @NamedQuery(name = "CxcAbonoD.findByNumeroCuota", query = "SELECT c FROM CxcAbonoD c WHERE c.numeroCuota = :numeroCuota")})
public class CxcAbonoD implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcAbonoDPK cxcAbonoDPK;
    @Column(name = "COD_ABONO")
    private String codAbono;
    @Basic(optional = false)
    @Column(name = "NUM_DOCUMENTO")
    private int numDocumento;
    @Column(name = "DIAS")
    private Integer dias;
    @Basic(optional = false)
    @Column(name = "CAPITAL")
    private Double capital;
    @Basic(optional = false)
    @Column(name = "VALOR_MORA")
    private BigInteger valorMora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PORC_COMISION")
    private BigDecimal porcComision;
    @Column(name = "NUMERO_CUOTA")
    private Integer numeroCuota;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "NUM_ABONO", referencedColumnName = "NUM_ABONO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private CxcAbonoC cxcAbonoC;

    public CxcAbonoD() {
    }

    public CxcAbonoD(CxcAbonoDPK cxcAbonoDPK) {
        this.cxcAbonoDPK = cxcAbonoDPK;
    }

    public CxcAbonoD(CxcAbonoDPK cxcAbonoDPK, int numDocumento, Double capital, BigInteger valorMora, BigDecimal porcComision) {
        this.cxcAbonoDPK = cxcAbonoDPK;
        this.numDocumento = numDocumento;
        this.capital = capital;
        this.valorMora = valorMora;
        this.porcComision = porcComision;
    }

    public CxcAbonoD(String codEmpresa, String codDocumento, int numAbono, int auxiliar) {
        this.cxcAbonoDPK = new CxcAbonoDPK(codEmpresa, codDocumento, numAbono, auxiliar);
    }

    public CxcAbonoDPK getCxcAbonoDPK() {
        return cxcAbonoDPK;
    }

    public void setCxcAbonoDPK(CxcAbonoDPK cxcAbonoDPK) {
        this.cxcAbonoDPK = cxcAbonoDPK;
    }

    public String getCodAbono() {
        return codAbono;
    }

    public void setCodAbono(String codAbono) {
        this.codAbono = codAbono;
    }

    public int getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(int numDocumento) {
        this.numDocumento = numDocumento;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public BigInteger getValorMora() {
        return valorMora;
    }

    public void setValorMora(BigInteger valorMora) {
        this.valorMora = valorMora;
    }

    public BigDecimal getPorcComision() {
        return porcComision;
    }

    public void setPorcComision(BigDecimal porcComision) {
        this.porcComision = porcComision;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public CxcAbonoC getCxcAbonoC() {
        return cxcAbonoC;
    }

    public void setCxcAbonoC(CxcAbonoC cxcAbonoC) {
        this.cxcAbonoC = cxcAbonoC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcAbonoDPK != null ? cxcAbonoDPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcAbonoD)) {
            return false;
        }
        CxcAbonoD other = (CxcAbonoD) object;
        if ((this.cxcAbonoDPK == null && other.cxcAbonoDPK != null) || (this.cxcAbonoDPK != null && !this.cxcAbonoDPK.equals(other.cxcAbonoDPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcAbonoD[ cxcAbonoDPK=" + cxcAbonoDPK + " ]";
    }

}
