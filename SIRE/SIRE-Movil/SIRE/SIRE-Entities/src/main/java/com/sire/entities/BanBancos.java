/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
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
@Table(name = "BAN_BANCOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BanBancos.findAll", query = "SELECT b FROM BanBancos b"),
    @NamedQuery(name = "BanBancos.findByCodEmpresa", query = "SELECT b FROM BanBancos b WHERE b.banBancosPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "BanBancos.findByCodBanco", query = "SELECT b FROM BanBancos b WHERE b.banBancosPK.codBanco = :codBanco"),
    @NamedQuery(name = "BanBancos.findByDescripcion", query = "SELECT b FROM BanBancos b WHERE b.descripcion = :descripcion")})
public class BanBancos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BanBancosPK banBancosPK;
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public BanBancos() {
    }

    public BanBancos(BanBancosPK banBancosPK) {
        this.banBancosPK = banBancosPK;
    }

    public BanBancos(String codEmpresa, String codBanco) {
        this.banBancosPK = new BanBancosPK(codEmpresa, codBanco);
    }

    public BanBancosPK getBanBancosPK() {
        return banBancosPK;
    }

    public void setBanBancosPK(BanBancosPK banBancosPK) {
        this.banBancosPK = banBancosPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (banBancosPK != null ? banBancosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BanBancos)) {
            return false;
        }
        BanBancos other = (BanBancos) object;
        if ((this.banBancosPK == null && other.banBancosPK != null) || (this.banBancosPK != null && !this.banBancosPK.equals(other.banBancosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.BanBancos[ banBancosPK=" + banBancosPK + " ]";
    }
    
}
