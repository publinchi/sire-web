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
 * @author pestupinan
 */
@Embeddable
public class GnrModuloDocPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_MODULO")
    private String codModulo;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;

    public GnrModuloDocPK() {
    }

    public GnrModuloDocPK(String codModulo, String codDocumento) {
        this.codModulo = codModulo;
        this.codDocumento = codDocumento;
    }

    public String getCodModulo() {
        return codModulo;
    }

    public void setCodModulo(String codModulo) {
        this.codModulo = codModulo;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codModulo != null ? codModulo.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrModuloDocPK)) {
            return false;
        }
        GnrModuloDocPK other = (GnrModuloDocPK) object;
        if ((this.codModulo == null && other.codModulo != null) || (this.codModulo != null && !this.codModulo.equals(other.codModulo))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrModuloDocPK[ codModulo=" + codModulo + ", codDocumento=" + codDocumento + " ]";
    }
    
}
