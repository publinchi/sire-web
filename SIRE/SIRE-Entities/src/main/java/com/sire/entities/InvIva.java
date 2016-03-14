/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "INV_IVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvIva.findAll", query = "SELECT i FROM InvIva i"),
    @NamedQuery(name = "InvIva.findByCodEmpresa", query = "SELECT i FROM InvIva i WHERE i.invIvaPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvIva.findByCodIva", query = "SELECT i FROM InvIva i WHERE i.invIvaPK.codIva = :codIva"),
    @NamedQuery(name = "InvIva.findByValor", query = "SELECT i FROM InvIva i WHERE i.valor = :valor"),
    @NamedQuery(name = "InvIva.findByEstado", query = "SELECT i FROM InvIva i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvIva.findByFechaVigencia", query = "SELECT i FROM InvIva i WHERE i.fechaVigencia = :fechaVigencia")})
public class InvIva implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvIvaPK invIvaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVigencia;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;

    public InvIva() {
    }

    public InvIva(InvIvaPK invIvaPK) {
        this.invIvaPK = invIvaPK;
    }

    public InvIva(String codEmpresa, String codIva) {
        this.invIvaPK = new InvIvaPK(codEmpresa, codIva);
    }

    public InvIvaPK getInvIvaPK() {
        return invIvaPK;
    }

    public void setInvIvaPK(InvIvaPK invIvaPK) {
        this.invIvaPK = invIvaPK;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invIvaPK != null ? invIvaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvIva)) {
            return false;
        }
        InvIva other = (InvIva) object;
        if ((this.invIvaPK == null && other.invIvaPK != null) || (this.invIvaPK != null && !this.invIvaPK.equals(other.invIvaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvIva[ invIvaPK=" + invIvaPK + " ]";
    }
    
}
