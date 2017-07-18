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
@Table(name = "BAN_CTA_CTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BanCtaCte.findAll", query = "SELECT b FROM BanCtaCte b"),
    @NamedQuery(name = "BanCtaCte.findByCodEmpresa", query = "SELECT b FROM BanCtaCte b WHERE b.banCtaCtePK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "BanCtaCte.findByCtaCorriente", query = "SELECT b FROM BanCtaCte b WHERE b.banCtaCtePK.ctaCorriente = :ctaCorriente"),
    @NamedQuery(name = "BanCtaCte.findByCodigoCuentaB", query = "SELECT b FROM BanCtaCte b WHERE b.codigoCuentaB = :codigoCuentaB"),
    @NamedQuery(name = "BanCtaCte.findByDescCuenta", query = "SELECT b FROM BanCtaCte b WHERE b.descCuenta = :descCuenta"),
    @NamedQuery(name = "BanCtaCte.findByDescBanco", query = "SELECT b FROM BanCtaCte b WHERE b.descBanco = :descBanco"),
    @NamedQuery(name = "BanCtaCte.findByFechaProceso", query = "SELECT b FROM BanCtaCte b WHERE b.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "BanCtaCte.findByCodigoCuentaP", query = "SELECT b FROM BanCtaCte b WHERE b.codigoCuentaP = :codigoCuentaP"),
    @NamedQuery(name = "BanCtaCte.findByEstado", query = "SELECT b FROM BanCtaCte b WHERE b.estado = :estado"),
    @NamedQuery(name = "BanCtaCte.findByFechaEstado", query = "SELECT b FROM BanCtaCte b WHERE b.fechaEstado = :fechaEstado")})
public class BanCtaCte implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BanCtaCtePK banCtaCtePK;
    @Column(name = "CODIGO_CUENTA_B")
    private String codigoCuentaB;
    @Column(name = "DESC_CUENTA")
    private String descCuenta;
    @Column(name = "DESC_BANCO")
    private String descBanco;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "CODIGO_CUENTA_P")
    private String codigoCuentaP;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;

    public BanCtaCte() {
    }

    public BanCtaCte(BanCtaCtePK banCtaCtePK) {
        this.banCtaCtePK = banCtaCtePK;
    }

    public BanCtaCte(String codEmpresa, String ctaCorriente) {
        this.banCtaCtePK = new BanCtaCtePK(codEmpresa, ctaCorriente);
    }

    public BanCtaCtePK getBanCtaCtePK() {
        return banCtaCtePK;
    }

    public void setBanCtaCtePK(BanCtaCtePK banCtaCtePK) {
        this.banCtaCtePK = banCtaCtePK;
    }

    public String getCodigoCuentaB() {
        return codigoCuentaB;
    }

    public void setCodigoCuentaB(String codigoCuentaB) {
        this.codigoCuentaB = codigoCuentaB;
    }

    public String getDescCuenta() {
        return descCuenta;
    }

    public void setDescCuenta(String descCuenta) {
        this.descCuenta = descCuenta;
    }

    public String getDescBanco() {
        return descBanco;
    }

    public void setDescBanco(String descBanco) {
        this.descBanco = descBanco;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getCodigoCuentaP() {
        return codigoCuentaP;
    }

    public void setCodigoCuentaP(String codigoCuentaP) {
        this.codigoCuentaP = codigoCuentaP;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (banCtaCtePK != null ? banCtaCtePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BanCtaCte)) {
            return false;
        }
        BanCtaCte other = (BanCtaCte) object;
        if ((this.banCtaCtePK == null && other.banCtaCtePK != null) || (this.banCtaCtePK != null && !this.banCtaCtePK.equals(other.banCtaCtePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.BanCtaCte[ banCtaCtePK=" + banCtaCtePK + " ]";
    }
    
}
