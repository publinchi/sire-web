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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "V_COBROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VCobros.findAll", query = "SELECT v FROM VCobros v"),
    @NamedQuery(name = "VCobros.findByCodEmpresa", query = "SELECT v FROM VCobros v WHERE v.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "VCobros.findByCodDocumento", query = "SELECT v FROM VCobros v WHERE v.codDocumento = :codDocumento"),
    @NamedQuery(name = "VCobros.findByFechaAbono", query = "SELECT v FROM VCobros v WHERE v.fechaAbono = :fechaAbono"),
    @NamedQuery(name = "VCobros.findByNumAbono", query = "SELECT v FROM VCobros v WHERE v.numAbono = :numAbono"),
    @NamedQuery(name = "VCobros.findByCodVendedor", query = "SELECT v FROM VCobros v WHERE v.codVendedor = :codVendedor"),
    @NamedQuery(name = "VCobros.findByCodCliente", query = "SELECT v FROM VCobros v WHERE v.codCliente = :codCliente"),
    @NamedQuery(name = "VCobros.findByNombreUsuario", query = "SELECT v FROM VCobros v WHERE v.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "VCobros.findByNumInforme", query = "SELECT v FROM VCobros v WHERE v.numInforme = :numInforme"),
    @NamedQuery(name = "VCobros.findByCodDivisa", query = "SELECT v FROM VCobros v WHERE v.codDivisa = :codDivisa"),
    @NamedQuery(name = "VCobros.findByDetalle", query = "SELECT v FROM VCobros v WHERE v.detalle = :detalle"),
    @NamedQuery(name = "VCobros.findByAuxiliar", query = "SELECT v FROM VCobros v WHERE v.auxiliar = :auxiliar"),
    @NamedQuery(name = "VCobros.findByCodAbono", query = "SELECT v FROM VCobros v WHERE v.codAbono = :codAbono"),
    @NamedQuery(name = "VCobros.findByNumDocumento", query = "SELECT v FROM VCobros v WHERE v.numDocumento = :numDocumento"),
    @NamedQuery(name = "VCobros.findByNumeroCuota", query = "SELECT v FROM VCobros v WHERE v.numeroCuota = :numeroCuota"),
    @NamedQuery(name = "VCobros.findByCapital", query = "SELECT v FROM VCobros v WHERE v.capital = :capital"),
    @NamedQuery(name = "VCobros.findByValorMora", query = "SELECT v FROM VCobros v WHERE v.valorMora = :valorMora"),
    @NamedQuery(name = "VCobros.findByPorcComision", query = "SELECT v FROM VCobros v WHERE v.porcComision = :porcComision"),
    @NamedQuery(name = "VCobros.findByCodMotivo", query = "SELECT v FROM VCobros v WHERE v.codMotivo = :codMotivo")})
public class VCobros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Column(name = "FECHA_ABONO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAbono;
    @Basic(optional = false)
    @Column(name = "NUM_ABONO")
    private BigInteger numAbono;
    @Basic(optional = false)
    @Column(name = "COD_VENDEDOR")
    private BigInteger codVendedor;
    @Column(name = "COD_CLIENTE")
    private BigInteger codCliente;
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "NUM_INFORME")
    private BigInteger numInforme;
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Column(name = "DETALLE")
    private String detalle;
    @Basic(optional = false)
    @Column(name = "AUXILIAR")
    private int auxiliar;
    @Column(name = "COD_ABONO")
    private String codAbono;
    @Basic(optional = false)
    @Column(name = "NUM_DOCUMENTO")
    private int numDocumento;
    @Column(name = "NUMERO_CUOTA")
    private Integer numeroCuota;
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
    @Column(name = "COD_MOTIVO")
    private String codMotivo;
    @Id
    private Long id;

    public VCobros() {
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public BigInteger getNumAbono() {
        return numAbono;
    }

    public void setNumAbono(BigInteger numAbono) {
        this.numAbono = numAbono;
    }

    public BigInteger getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(BigInteger codVendedor) {
        this.codVendedor = codVendedor;
    }

    public BigInteger getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(BigInteger codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public BigInteger getNumInforme() {
        return numInforme;
    }

    public void setNumInforme(BigInteger numInforme) {
        this.numInforme = numInforme;
    }

    public String getCodDivisa() {
        return codDivisa;
    }

    public void setCodDivisa(String codDivisa) {
        this.codDivisa = codDivisa;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(int auxiliar) {
        this.auxiliar = auxiliar;
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

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
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

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
