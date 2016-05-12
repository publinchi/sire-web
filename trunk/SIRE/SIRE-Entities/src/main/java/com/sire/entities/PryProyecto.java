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
@Table(name = "PRY_PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PryProyecto.findAll", query = "SELECT p FROM PryProyecto p"),
    @NamedQuery(name = "PryProyecto.findByCodEmpresa", query = "SELECT p FROM PryProyecto p WHERE p.pryProyectoPK.codEmpresa = :codEmpresa and p.estadoProyecto = :estadoProyecto"),
    @NamedQuery(name = "PryProyecto.findByCodProyecto", query = "SELECT p FROM PryProyecto p WHERE p.pryProyectoPK.codProyecto = :codProyecto"),
    @NamedQuery(name = "PryProyecto.findByDescProyecto", query = "SELECT p FROM PryProyecto p WHERE p.descProyecto = :descProyecto"),
    @NamedQuery(name = "PryProyecto.findByEstadoProyecto", query = "SELECT p FROM PryProyecto p WHERE p.estadoProyecto = :estadoProyecto"),
    @NamedQuery(name = "PryProyecto.findByFechaEstado", query = "SELECT p FROM PryProyecto p WHERE p.fechaEstado = :fechaEstado")})
public class PryProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PryProyectoPK pryProyectoPK;
    @Column(name = "DESC_PROYECTO")
    private String descProyecto;
    @Column(name = "ESTADO_PROYECTO")
    private Character estadoProyecto;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO")
    @ManyToOne
    private GnrUsuarios nombreUsuario;

    public PryProyecto() {
    }

    public PryProyecto(PryProyectoPK pryProyectoPK) {
        this.pryProyectoPK = pryProyectoPK;
    }

    public PryProyecto(String codEmpresa, int codProyecto) {
        this.pryProyectoPK = new PryProyectoPK(codEmpresa, codProyecto);
    }

    public PryProyectoPK getPryProyectoPK() {
        return pryProyectoPK;
    }

    public void setPryProyectoPK(PryProyectoPK pryProyectoPK) {
        this.pryProyectoPK = pryProyectoPK;
    }

    public String getDescProyecto() {
        return descProyecto;
    }

    public void setDescProyecto(String descProyecto) {
        this.descProyecto = descProyecto;
    }

    public Character getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(Character estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public GnrUsuarios getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(GnrUsuarios nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pryProyectoPK != null ? pryProyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PryProyecto)) {
            return false;
        }
        PryProyecto other = (PryProyecto) object;
        if ((this.pryProyectoPK == null && other.pryProyectoPK != null) || (this.pryProyectoPK != null && !this.pryProyectoPK.equals(other.pryProyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.PryProyecto[ pryProyectoPK=" + pryProyectoPK + " ]";
    }

}
