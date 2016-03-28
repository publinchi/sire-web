/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author publio
 */
@Entity
@Table(name = "CXC_FORMA_PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcFormaPago.findAll", query = "SELECT c FROM CxcFormaPago c"),
    @NamedQuery(name = "CxcFormaPago.findByCodEmpresa", query = "SELECT c FROM CxcFormaPago c WHERE c.cxcFormaPagoPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcFormaPago.findByCodPago", query = "SELECT c FROM CxcFormaPago c WHERE c.cxcFormaPagoPK.codPago = :codPago"),
    @NamedQuery(name = "CxcFormaPago.findByDescripcion", query = "SELECT c FROM CxcFormaPago c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CxcFormaPago.findByNumDias", query = "SELECT c FROM CxcFormaPago c WHERE c.numDias = :numDias"),
    @NamedQuery(name = "CxcFormaPago.findByPorcentajeInteres", query = "SELECT c FROM CxcFormaPago c WHERE c.porcentajeInteres = :porcentajeInteres")})
public class CxcFormaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcFormaPagoPK cxcFormaPagoPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "NUM_DIAS")
    private long numDias;
    @Column(name = "PORCENTAJE_INTERES")
    private Short porcentajeInteres;

    public CxcFormaPago() {
    }

    public CxcFormaPago(CxcFormaPagoPK cxcFormaPagoPK) {
        this.cxcFormaPagoPK = cxcFormaPagoPK;
    }

    public CxcFormaPago(CxcFormaPagoPK cxcFormaPagoPK, long numDias) {
        this.cxcFormaPagoPK = cxcFormaPagoPK;
        this.numDias = numDias;
    }

    public CxcFormaPago(String codEmpresa, String codPago) {
        this.cxcFormaPagoPK = new CxcFormaPagoPK(codEmpresa, codPago);
    }

    public CxcFormaPagoPK getCxcFormaPagoPK() {
        return cxcFormaPagoPK;
    }

    public void setCxcFormaPagoPK(CxcFormaPagoPK cxcFormaPagoPK) {
        this.cxcFormaPagoPK = cxcFormaPagoPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getNumDias() {
        return numDias;
    }

    public void setNumDias(long numDias) {
        this.numDias = numDias;
    }

    public Short getPorcentajeInteres() {
        return porcentajeInteres;
    }

    public void setPorcentajeInteres(Short porcentajeInteres) {
        this.porcentajeInteres = porcentajeInteres;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcFormaPagoPK != null ? cxcFormaPagoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcFormaPago)) {
            return false;
        }
        CxcFormaPago other = (CxcFormaPago) object;
        if ((this.cxcFormaPagoPK == null && other.cxcFormaPagoPK != null) || (this.cxcFormaPagoPK != null && !this.cxcFormaPagoPK.equals(other.cxcFormaPagoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcFormaPago[ cxcFormaPagoPK=" + cxcFormaPagoPK + " ]";
    }
    
}
