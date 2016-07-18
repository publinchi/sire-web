/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.Date;
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
 * @author Administrator
 */
@Entity
@Table(name = "PRY_SUPERVISOR_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrySupervisorUsuario.findAll", query = "SELECT p FROM PrySupervisorUsuario p"),
    @NamedQuery(name = "PrySupervisorUsuario.findByCodEmpresa", query = "SELECT p FROM PrySupervisorUsuario p WHERE p.prySupervisorUsuarioPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "PrySupervisorUsuario.findByCodSupervisor", query = "SELECT p FROM PrySupervisorUsuario p WHERE p.prySupervisorUsuarioPK.codSupervisor = :codSupervisor"),
    @NamedQuery(name = "PrySupervisorUsuario.findByNombreUsuario", query = "SELECT p FROM PrySupervisorUsuario p WHERE p.prySupervisorUsuarioPK.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "PrySupervisorUsuario.findByEstado", query = "SELECT p FROM PrySupervisorUsuario p WHERE p.estado = :estado"),
    @NamedQuery(name = "PrySupervisorUsuario.findByFechaEstado", query = "SELECT p FROM PrySupervisorUsuario p WHERE p.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "PrySupervisorUsuario.findByCodEmpresaNombreUsuario", query = "SELECT p FROM PrySupervisorUsuario p WHERE p.prySupervisorUsuarioPK.codEmpresa = :codEmpresa and p.prySupervisorUsuarioPK.nombreUsuario = :nombreUsuario"),})
public class PrySupervisorUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrySupervisorUsuarioPK prySupervisorUsuarioPK;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_SUPERVISOR", referencedColumnName = "COD_SUPERVISOR", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private PrySupervisor prySupervisor;

    public PrySupervisorUsuario() {
    }

    public PrySupervisorUsuario(PrySupervisorUsuarioPK prySupervisorUsuarioPK) {
        this.prySupervisorUsuarioPK = prySupervisorUsuarioPK;
    }

    public PrySupervisorUsuario(String codEmpresa, int codSupervisor, String nombreUsuario) {
        this.prySupervisorUsuarioPK = new PrySupervisorUsuarioPK(codEmpresa, codSupervisor, nombreUsuario);
    }

    public PrySupervisorUsuarioPK getPrySupervisorUsuarioPK() {
        return prySupervisorUsuarioPK;
    }

    public void setPrySupervisorUsuarioPK(PrySupervisorUsuarioPK prySupervisorUsuarioPK) {
        this.prySupervisorUsuarioPK = prySupervisorUsuarioPK;
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

    public PrySupervisor getPrySupervisor() {
        return prySupervisor;
    }

    public void setPrySupervisor(PrySupervisor prySupervisor) {
        this.prySupervisor = prySupervisor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prySupervisorUsuarioPK != null ? prySupervisorUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrySupervisorUsuario)) {
            return false;
        }
        PrySupervisorUsuario other = (PrySupervisorUsuario) object;
        if ((this.prySupervisorUsuarioPK == null && other.prySupervisorUsuarioPK != null) || (this.prySupervisorUsuarioPK != null && !this.prySupervisorUsuarioPK.equals(other.prySupervisorUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.PrySupervisorUsuario[ prySupervisorUsuarioPK=" + prySupervisorUsuarioPK + " ]";
    }
    
}
