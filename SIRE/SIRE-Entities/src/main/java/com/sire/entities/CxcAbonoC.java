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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author publio
 */
@Entity
@Table(name = "CXC_ABONO_C")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcAbonoC.findAll", query = "SELECT c FROM CxcAbonoC c"),
    @NamedQuery(name = "CxcAbonoC.findByCodEmpresa", query = "SELECT c FROM CxcAbonoC c WHERE c.cxcAbonoCPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcAbonoC.findByCodDocumento", query = "SELECT c FROM CxcAbonoC c WHERE c.cxcAbonoCPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "CxcAbonoC.findByNumAbono", query = "SELECT c FROM CxcAbonoC c WHERE c.cxcAbonoCPK.numAbono = :numAbono"),
    @NamedQuery(name = "CxcAbonoC.findByCodVendedor", query = "SELECT c FROM CxcAbonoC c WHERE c.codVendedor = :codVendedor"),
    @NamedQuery(name = "CxcAbonoC.findByCodMotivo", query = "SELECT c FROM CxcAbonoC c WHERE c.codMotivo = :codMotivo"),
    @NamedQuery(name = "CxcAbonoC.findByCodDivisa", query = "SELECT c FROM CxcAbonoC c WHERE c.codDivisa = :codDivisa"),
    @NamedQuery(name = "CxcAbonoC.findByFechaAbono", query = "SELECT c FROM CxcAbonoC c WHERE c.fechaAbono = :fechaAbono"),
    @NamedQuery(name = "CxcAbonoC.findByDetalle", query = "SELECT c FROM CxcAbonoC c WHERE c.detalle = :detalle"),
    @NamedQuery(name = "CxcAbonoC.findByTotalCapital", query = "SELECT c FROM CxcAbonoC c WHERE c.totalCapital = :totalCapital"),
    @NamedQuery(name = "CxcAbonoC.findByTotalMora", query = "SELECT c FROM CxcAbonoC c WHERE c.totalMora = :totalMora"),
    @NamedQuery(name = "CxcAbonoC.findByEstado", query = "SELECT c FROM CxcAbonoC c WHERE c.estado = :estado"),
    @NamedQuery(name = "CxcAbonoC.findByFechaEstado", query = "SELECT c FROM CxcAbonoC c WHERE c.fechaEstado = :fechaEstado")})
public class CxcAbonoC implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcAbonoCPK cxcAbonoCPK;
    @Basic(optional = false)
    @Column(name = "COD_VENDEDOR")
    private BigInteger codVendedor;
    @Column(name = "COD_MOTIVO")
    private String codMotivo;
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Column(name = "FECHA_ABONO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAbono;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "TOTAL_CAPITAL")
    private Double totalCapital;
    @Column(name = "TOTAL_MORA")
    private BigInteger totalMora;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_CLIENTE", referencedColumnName = "COD_CLIENTE")})
    @ManyToOne(optional = false)
    private CxcCliente cxcCliente;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_INFORME", referencedColumnName = "COD_INFORME"),
        @JoinColumn(name = "NUM_INFORME", referencedColumnName = "NUM_INFORME")})
    @ManyToOne(optional = false)
    private CxcInforme cxcInforme;
    @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO")
    @ManyToOne
    private GnrUsuarios nombreUsuario;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cxcAbonoC")
    private List<CxcAbonoD> cxcAbonoDList;

    public CxcAbonoC() {
    }

    public CxcAbonoC(CxcAbonoCPK cxcAbonoCPK) {
        this.cxcAbonoCPK = cxcAbonoCPK;
    }

    public CxcAbonoC(CxcAbonoCPK cxcAbonoCPK, BigInteger codVendedor) {
        this.cxcAbonoCPK = cxcAbonoCPK;
        this.codVendedor = codVendedor;
    }

    public CxcAbonoC(String codEmpresa, String codDocumento, BigInteger numAbono) {
        this.cxcAbonoCPK = new CxcAbonoCPK(codEmpresa, codDocumento, numAbono);
    }

    public CxcAbonoCPK getCxcAbonoCPK() {
        return cxcAbonoCPK;
    }

    public void setCxcAbonoCPK(CxcAbonoCPK cxcAbonoCPK) {
        this.cxcAbonoCPK = cxcAbonoCPK;
    }

    public BigInteger getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(BigInteger codVendedor) {
        this.codVendedor = codVendedor;
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

    public Date getFechaAbono() {
        return fechaAbono;
    }

    public void setFechaAbono(Date fechaAbono) {
        this.fechaAbono = fechaAbono;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(Double totalCapital) {
        this.totalCapital = totalCapital;
    }

    public BigInteger getTotalMora() {
        return totalMora;
    }

    public void setTotalMora(BigInteger totalMora) {
        this.totalMora = totalMora;
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

    public CxcCliente getCxcCliente() {
        return cxcCliente;
    }

    public void setCxcCliente(CxcCliente cxcCliente) {
        this.cxcCliente = cxcCliente;
    }

    public CxcInforme getCxcInforme() {
        return cxcInforme;
    }

    public void setCxcInforme(CxcInforme cxcInforme) {
        this.cxcInforme = cxcInforme;
    }

    public GnrUsuarios getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(GnrUsuarios nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<CxcAbonoD> getCxcAbonoDList() {
        return cxcAbonoDList;
    }

    public void setCxcAbonoDList(List<CxcAbonoD> cxcAbonoDList) {
        this.cxcAbonoDList = cxcAbonoDList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcAbonoCPK != null ? cxcAbonoCPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcAbonoC)) {
            return false;
        }
        CxcAbonoC other = (CxcAbonoC) object;
        if ((this.cxcAbonoCPK == null && other.cxcAbonoCPK != null) || (this.cxcAbonoCPK != null && !this.cxcAbonoCPK.equals(other.cxcAbonoCPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcAbonoC[ cxcAbonoCPK=" + cxcAbonoCPK + " ]";
    }

}
