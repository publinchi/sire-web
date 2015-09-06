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
public class InvProveedorPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_PROVEEDOR")
    private int codProveedor;

    public InvProveedorPK() {
    }

    public InvProveedorPK(String codEmpresa, int codProveedor) {
        this.codEmpresa = codEmpresa;
        this.codProveedor = codProveedor;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(int codProveedor) {
        this.codProveedor = codProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) codProveedor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvProveedorPK)) {
            return false;
        }
        InvProveedorPK other = (InvProveedorPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.codProveedor != other.codProveedor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvProveedorPK[ codEmpresa=" + codEmpresa + ", codProveedor=" + codProveedor + " ]";
    }
    
}
