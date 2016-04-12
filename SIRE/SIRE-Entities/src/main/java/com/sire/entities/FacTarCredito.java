/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author publio
 */
@Entity
@Table(name = "FAC_TAR_CREDITO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacTarCredito.findAll", query = "SELECT f FROM FacTarCredito f"),
    @NamedQuery(name = "FacTarCredito.findByCodEmpresa", query = "SELECT f FROM FacTarCredito f WHERE f.facTarCreditoPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacTarCredito.findByCodTarjeta", query = "SELECT f FROM FacTarCredito f WHERE f.facTarCreditoPK.codTarjeta = :codTarjeta"),
    @NamedQuery(name = "FacTarCredito.findByCodigoCuenta", query = "SELECT f FROM FacTarCredito f WHERE f.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "FacTarCredito.findByDescripcion", query = "SELECT f FROM FacTarCredito f WHERE f.descripcion = :descripcion"),
    @NamedQuery(name = "FacTarCredito.findByPorcComision", query = "SELECT f FROM FacTarCredito f WHERE f.porcComision = :porcComision")})
public class FacTarCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacTarCreditoPK facTarCreditoPK;
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PORC_COMISION")
    private BigDecimal porcComision;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;

    public FacTarCredito() {
    }

    public FacTarCredito(FacTarCreditoPK facTarCreditoPK) {
        this.facTarCreditoPK = facTarCreditoPK;
    }

    public FacTarCredito(String codEmpresa, String codTarjeta) {
        this.facTarCreditoPK = new FacTarCreditoPK(codEmpresa, codTarjeta);
    }

    public FacTarCreditoPK getFacTarCreditoPK() {
        return facTarCreditoPK;
    }

    public void setFacTarCreditoPK(FacTarCreditoPK facTarCreditoPK) {
        this.facTarCreditoPK = facTarCreditoPK;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPorcComision() {
        return porcComision;
    }

    public void setPorcComision(BigDecimal porcComision) {
        this.porcComision = porcComision;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facTarCreditoPK != null ? facTarCreditoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacTarCredito)) {
            return false;
        }
        FacTarCredito other = (FacTarCredito) object;
        if ((this.facTarCreditoPK == null && other.facTarCreditoPK != null) || (this.facTarCreditoPK != null && !this.facTarCreditoPK.equals(other.facTarCreditoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacTarCredito[ facTarCreditoPK=" + facTarCreditoPK + " ]";
    }
    
}
