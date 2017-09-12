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
 * @author pestupinan
 */
@Entity
@Table(name = "INV_UNIDAD_ALTERNATIVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvUnidadAlternativa.findAll", query = "SELECT i FROM InvUnidadAlternativa i"),
    @NamedQuery(name = "InvUnidadAlternativa.findByCodEmpresa", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.invUnidadAlternativaPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvUnidadAlternativa.findByCodArticulo", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.invUnidadAlternativaPK.codArticulo = :codArticulo AND i.estado = :estado"),
    @NamedQuery(name = "InvUnidadAlternativa.findByFactor", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.factor = :factor"),
    @NamedQuery(name = "InvUnidadAlternativa.findByCodUnidad", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.invUnidadAlternativaPK.codUnidad = :codUnidad"),
    @NamedQuery(name = "InvUnidadAlternativa.findByOperador", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.operador = :operador"),
    @NamedQuery(name = "InvUnidadAlternativa.findByPeso", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.peso = :peso"),
    @NamedQuery(name = "InvUnidadAlternativa.findByVolumen", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.volumen = :volumen"),
    @NamedQuery(name = "InvUnidadAlternativa.findByEstado", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvUnidadAlternativa.findByFechaEstado", query = "SELECT i FROM InvUnidadAlternativa i WHERE i.fechaEstado = :fechaEstado")})
public class InvUnidadAlternativa implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invUnidadAlternativa")
    private List<FacTmpFactD> facTmpFactDList;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvUnidadAlternativaPK invUnidadAlternativaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "FACTOR")
    private BigDecimal factor;
    @Column(name = "OPERADOR")
    private String operador;
    @Basic(optional = false)
    @Column(name = "PESO")
    private BigInteger peso;
    @Basic(optional = false)
    @Column(name = "VOLUMEN")
    private BigInteger volumen;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;

    public InvUnidadAlternativa() {
    }

    public InvUnidadAlternativa(InvUnidadAlternativaPK invUnidadAlternativaPK) {
        this.invUnidadAlternativaPK = invUnidadAlternativaPK;
    }

    public InvUnidadAlternativa(InvUnidadAlternativaPK invUnidadAlternativaPK, BigDecimal factor, BigInteger peso, BigInteger volumen) {
        this.invUnidadAlternativaPK = invUnidadAlternativaPK;
        this.factor = factor;
        this.peso = peso;
        this.volumen = volumen;
    }

    public InvUnidadAlternativa(String codEmpresa, int codArticulo, String codUnidad) {
        this.invUnidadAlternativaPK = new InvUnidadAlternativaPK(codEmpresa, codArticulo, codUnidad);
    }

    public InvUnidadAlternativaPK getInvUnidadAlternativaPK() {
        return invUnidadAlternativaPK;
    }

    public void setInvUnidadAlternativaPK(InvUnidadAlternativaPK invUnidadAlternativaPK) {
        this.invUnidadAlternativaPK = invUnidadAlternativaPK;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public BigInteger getPeso() {
        return peso;
    }

    public void setPeso(BigInteger peso) {
        this.peso = peso;
    }

    public BigInteger getVolumen() {
        return volumen;
    }

    public void setVolumen(BigInteger volumen) {
        this.volumen = volumen;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invUnidadAlternativaPK != null ? invUnidadAlternativaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvUnidadAlternativa)) {
            return false;
        }
        InvUnidadAlternativa other = (InvUnidadAlternativa) object;
        if ((this.invUnidadAlternativaPK == null && other.invUnidadAlternativaPK != null) || (this.invUnidadAlternativaPK != null && !this.invUnidadAlternativaPK.equals(other.invUnidadAlternativaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvUnidadAlternativa[ invUnidadAlternativaPK=" + invUnidadAlternativaPK + " ]";
    }

    @XmlTransient
    public List<FacTmpFactD> getFacTmpFactDList() {
        return facTmpFactDList;
    }

    public void setFacTmpFactDList(List<FacTmpFactD> facTmpFactDList) {
        this.facTmpFactDList = facTmpFactDList;
    }
    
}
