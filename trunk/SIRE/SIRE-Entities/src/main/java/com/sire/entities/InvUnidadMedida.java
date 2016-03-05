/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "INV_UNIDAD_MEDIDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvUnidadMedida.findAll", query = "SELECT i FROM InvUnidadMedida i"),
    @NamedQuery(name = "InvUnidadMedida.findByCodUnidad", query = "SELECT i FROM InvUnidadMedida i WHERE i.codUnidad = :codUnidad"),
    @NamedQuery(name = "InvUnidadMedida.findByCodEmpresa", query = "SELECT i FROM InvUnidadMedida i WHERE i.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvUnidadMedida.findByDescUnidad", query = "SELECT i FROM InvUnidadMedida i WHERE i.descUnidad = :descUnidad"),
    @NamedQuery(name = "InvUnidadMedida.findByAliasUnidad", query = "SELECT i FROM InvUnidadMedida i WHERE i.aliasUnidad = :aliasUnidad"),
    @NamedQuery(name = "InvUnidadMedida.findByFechaCreacion", query = "SELECT i FROM InvUnidadMedida i WHERE i.fechaCreacion = :fechaCreacion")})
public class InvUnidadMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_UNIDAD")
    private String codUnidad;
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "DESC_UNIDAD")
    private String descUnidad;
    @Column(name = "ALIAS_UNIDAD")
    private String aliasUnidad;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codUnidad")
    private List<InvArticulo> invArticuloList;

    public InvUnidadMedida() {
    }

    public InvUnidadMedida(String codUnidad) {
        this.codUnidad = codUnidad;
    }

    public InvUnidadMedida(String codUnidad, String codEmpresa, String descUnidad) {
        this.codUnidad = codUnidad;
        this.codEmpresa = codEmpresa;
        this.descUnidad = descUnidad;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public void setCodUnidad(String codUnidad) {
        this.codUnidad = codUnidad;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getDescUnidad() {
        return descUnidad;
    }

    public void setDescUnidad(String descUnidad) {
        this.descUnidad = descUnidad;
    }

    public String getAliasUnidad() {
        return aliasUnidad;
    }

    public void setAliasUnidad(String aliasUnidad) {
        this.aliasUnidad = aliasUnidad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<InvArticulo> getInvArticuloList() {
        return invArticuloList;
    }

    public void setInvArticuloList(List<InvArticulo> invArticuloList) {
        this.invArticuloList = invArticuloList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUnidad != null ? codUnidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvUnidadMedida)) {
            return false;
        }
        InvUnidadMedida other = (InvUnidadMedida) object;
        if ((this.codUnidad == null && other.codUnidad != null) || (this.codUnidad != null && !this.codUnidad.equals(other.codUnidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvUnidadMedida[ codUnidad=" + codUnidad + " ]";
    }
    
}
