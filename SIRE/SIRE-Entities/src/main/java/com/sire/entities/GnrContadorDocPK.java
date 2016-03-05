/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author pestupinan
 */
@Embeddable
public class GnrContadorDocPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_MODULO")
    private String codModulo;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "NUM_CONTADOR")
    private BigDecimal numContador;

    public GnrContadorDocPK() {
    }

    public GnrContadorDocPK(String codEmpresa, String codModulo, String codDocumento, BigDecimal numContador) {
        this.codEmpresa = codEmpresa;
        this.codModulo = codModulo;
        this.codDocumento = codDocumento;
        this.numContador = numContador;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
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

    public BigDecimal getNumContador() {
        return numContador;
    }

    public void setNumContador(BigDecimal numContador) {
        this.numContador = numContador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (codModulo != null ? codModulo.hashCode() : 0);
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (numContador != null ? numContador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrContadorDocPK)) {
            return false;
        }
        GnrContadorDocPK other = (GnrContadorDocPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if ((this.codModulo == null && other.codModulo != null) || (this.codModulo != null && !this.codModulo.equals(other.codModulo))) {
            return false;
        }
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if ((this.numContador == null && other.numContador != null) || (this.numContador != null && !this.numContador.equals(other.numContador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrContadorDocPK[ codEmpresa=" + codEmpresa + ", codModulo=" + codModulo + ", codDocumento=" + codDocumento + ", numContador=" + numContador + " ]";
    }
    
}
