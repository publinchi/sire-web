/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CXC_CLASE_CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcClaseCliente.findAll", query = "SELECT c FROM CxcClaseCliente c"),
    @NamedQuery(name = "CxcClaseCliente.findByDescripcion", query = "SELECT c FROM CxcClaseCliente c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CxcClaseCliente.findByEstado", query = "SELECT c FROM CxcClaseCliente c WHERE c.estado = :estado"),
    @NamedQuery(name = "CxcClaseCliente.findByCodigo", query = "SELECT c FROM CxcClaseCliente c WHERE c.codigo = :codigo")})
public class CxcClaseCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(mappedBy = "claseCliete")
    private List<CxcCliente> cxcClienteList;

    public CxcClaseCliente() {
    }

    public CxcClaseCliente(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<CxcCliente> getCxcClienteList() {
        return cxcClienteList;
    }

    public void setCxcClienteList(List<CxcCliente> cxcClienteList) {
        this.cxcClienteList = cxcClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcClaseCliente)) {
            return false;
        }
        CxcClaseCliente other = (CxcClaseCliente) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcClaseCliente[ codigo=" + codigo + " ]";
    }
    
}
