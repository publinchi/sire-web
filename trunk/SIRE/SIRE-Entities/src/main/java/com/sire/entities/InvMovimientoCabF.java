/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "INV_MOVIMIENTO_CAB_F")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvMovimientoCabF.findAll", query = "SELECT i FROM InvMovimientoCabF i"),
    @NamedQuery(name = "InvMovimientoCabF.findByCodEmpresa", query = "SELECT i FROM InvMovimientoCabF i WHERE i.invMovimientoCabFPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvMovimientoCabF.findByCodDocumento", query = "SELECT i FROM InvMovimientoCabF i WHERE i.invMovimientoCabFPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "InvMovimientoCabF.findByNumDocumento", query = "SELECT i FROM InvMovimientoCabF i WHERE i.invMovimientoCabFPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "InvMovimientoCabF.findByFechaDocumento", query = "SELECT i FROM InvMovimientoCabF i WHERE i.fechaDocumento = :fechaDocumento"),
    @NamedQuery(name = "InvMovimientoCabF.findByCodDivisa", query = "SELECT i FROM InvMovimientoCabF i WHERE i.codDivisa = :codDivisa"),
    @NamedQuery(name = "InvMovimientoCabF.findByReferencia", query = "SELECT i FROM InvMovimientoCabF i WHERE i.referencia = :referencia"),
    @NamedQuery(name = "InvMovimientoCabF.findByDetalle", query = "SELECT i FROM InvMovimientoCabF i WHERE i.detalle = :detalle"),
    @NamedQuery(name = "InvMovimientoCabF.findByEstado", query = "SELECT i FROM InvMovimientoCabF i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvMovimientoCabF.findByCodDestino", query = "SELECT i FROM InvMovimientoCabF i WHERE i.codDestino = :codDestino"),
    @NamedQuery(name = "InvMovimientoCabF.findByFechaEstado", query = "SELECT i FROM InvMovimientoCabF i WHERE i.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "InvMovimientoCabF.findByNumDestino", query = "SELECT i FROM InvMovimientoCabF i WHERE i.numDestino = :numDestino")})
public class InvMovimientoCabF implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvMovimientoCabFPK invMovimientoCabFPK;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "COD_DIVISA")
    private String codDivisa;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "COD_DESTINO")
    private String codDestino;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Column(name = "NUM_DESTINO")
    private Integer numDestino;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_CLIENTE", referencedColumnName = "COD_CLIENTE")})
    @ManyToOne(optional = false)
    private CxcCliente cxcCliente;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_PROVEEDOR", referencedColumnName = "COD_PROVEEDOR")})
    @ManyToOne(optional = false)
    private InvProveedor invProveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invMovimientoCabF")
    private List<InvMovimientoDtllF> invMovimientoDtllFList;

    public InvMovimientoCabF() {
    }

    public InvMovimientoCabF(InvMovimientoCabFPK invMovimientoCabFPK) {
        this.invMovimientoCabFPK = invMovimientoCabFPK;
    }

    public InvMovimientoCabF(String codEmpresa, String codDocumento, int numDocumento) {
        this.invMovimientoCabFPK = new InvMovimientoCabFPK(codEmpresa, codDocumento, numDocumento);
    }

    public InvMovimientoCabFPK getInvMovimientoCabFPK() {
        return invMovimientoCabFPK;
    }

    public void setInvMovimientoCabFPK(InvMovimientoCabFPK invMovimientoCabFPK) {
        this.invMovimientoCabFPK = invMovimientoCabFPK;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getCodDivisa() {
        return codDivisa;
    }

    public void setCodDivisa(String codDivisa) {
        this.codDivisa = codDivisa;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodDestino() {
        return codDestino;
    }

    public void setCodDestino(String codDestino) {
        this.codDestino = codDestino;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public Integer getNumDestino() {
        return numDestino;
    }

    public void setNumDestino(Integer numDestino) {
        this.numDestino = numDestino;
    }

    public CxcCliente getCxcCliente() {
        return cxcCliente;
    }

    public void setCxcCliente(CxcCliente cxcCliente) {
        this.cxcCliente = cxcCliente;
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

    @XmlTransient
    public List<InvMovimientoDtllF> getInvMovimientoDtllFList() {
        return invMovimientoDtllFList;
    }

    public void setInvMovimientoDtllFList(List<InvMovimientoDtllF> invMovimientoDtllFList) {
        this.invMovimientoDtllFList = invMovimientoDtllFList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invMovimientoCabFPK != null ? invMovimientoCabFPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvMovimientoCabF)) {
            return false;
        }
        InvMovimientoCabF other = (InvMovimientoCabF) object;
        if ((this.invMovimientoCabFPK == null && other.invMovimientoCabFPK != null) || (this.invMovimientoCabFPK != null && !this.invMovimientoCabFPK.equals(other.invMovimientoCabFPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvMovimientoCabF[ invMovimientoCabFPK=" + invMovimientoCabFPK + " ]";
    }
    
}
