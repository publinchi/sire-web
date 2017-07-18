/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class InvBodegaArtPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_BODEGA")
    private String codBodega;
    @Basic(optional = false)
    @Column(name = "COD_ARTICULO")
    private int codArticulo;
    @Basic(optional = false)
    @Column(name = "COD_INVENTARIO")
    private String codInventario;

    public InvBodegaArtPK() {
    }

    public InvBodegaArtPK(String codEmpresa, String codBodega, int codArticulo, String codInventario) {
        this.codEmpresa = codEmpresa;
        this.codBodega = codBodega;
        this.codArticulo = codArticulo;
        this.codInventario = codInventario;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodBodega() {
        return codBodega;
    }

    public void setCodBodega(String codBodega) {
        this.codBodega = codBodega;
    }

    public int getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(int codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getCodInventario() {
        return codInventario;
    }

    public void setCodInventario(String codInventario) {
        this.codInventario = codInventario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codBodega != null ? codBodega.hashCode() : 0);
        hash += (int) codArticulo;
        hash += (codInventario != null ? codInventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvBodegaArtPK)) {
            return false;
        }
        InvBodegaArtPK other = (InvBodegaArtPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codBodega == null && other.codBodega != null) || (this.codBodega != null && !this.codBodega.equals(other.codBodega))) {
            return false;
        }
        if (this.codArticulo != other.codArticulo) {
            return false;
        }
        if ((this.codInventario == null && other.codInventario != null) || (this.codInventario != null && !this.codInventario.equals(other.codInventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvBodegaArtPK[ codEmpresa=" + codEmpresa + ", codBodega=" + codBodega + ", codArticulo=" + codArticulo + ", codInventario=" + codInventario + " ]";
    }
    
}
