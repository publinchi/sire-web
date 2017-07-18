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
@Table(name = "GNR_USUA_MOD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrUsuaMod.findAll", query = "SELECT g FROM GnrUsuaMod g"),
    @NamedQuery(name = "GnrUsuaMod.findByCodModulo", query = "SELECT g FROM GnrUsuaMod g WHERE g.gnrUsuaModPK.codModulo = :codModulo"),
    @NamedQuery(name = "GnrUsuaMod.findByNombreUsuario", query = "SELECT g FROM GnrUsuaMod g WHERE g.gnrUsuaModPK.nombreUsuario = :nombreUsuario and g.tipoModulo = :tipoModulo"),
    @NamedQuery(name = "GnrUsuaMod.findByCodEmpresa", query = "SELECT g FROM GnrUsuaMod g WHERE g.gnrUsuaModPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "GnrUsuaMod.findByTipoModulo", query = "SELECT g FROM GnrUsuaMod g WHERE g.tipoModulo = :tipoModulo"),
    @NamedQuery(name = "GnrUsuaMod.findByPermiso", query = "SELECT g FROM GnrUsuaMod g WHERE g.permiso = :permiso")})
public class GnrUsuaMod implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GnrUsuaModPK gnrUsuaModPK;
    @Column(name = "TIPO_MODULO")
    private String tipoModulo;
    @Column(name = "PERMISO")
    private String permiso;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrUsuarios gnrUsuarios;

    public GnrUsuaMod() {
    }

    public GnrUsuaMod(GnrUsuaModPK gnrUsuaModPK) {
        this.gnrUsuaModPK = gnrUsuaModPK;
    }

    public GnrUsuaMod(String codModulo, String nombreUsuario, String codEmpresa) {
        this.gnrUsuaModPK = new GnrUsuaModPK(codModulo, nombreUsuario, codEmpresa);
    }

    public GnrUsuaModPK getGnrUsuaModPK() {
        return gnrUsuaModPK;
    }

    public void setGnrUsuaModPK(GnrUsuaModPK gnrUsuaModPK) {
        this.gnrUsuaModPK = gnrUsuaModPK;
    }

    public String getTipoModulo() {
        return tipoModulo;
    }

    public void setTipoModulo(String tipoModulo) {
        this.tipoModulo = tipoModulo;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    public GnrUsuarios getGnrUsuarios() {
        return gnrUsuarios;
    }

    public void setGnrUsuarios(GnrUsuarios gnrUsuarios) {
        this.gnrUsuarios = gnrUsuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnrUsuaModPK != null ? gnrUsuaModPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrUsuaMod)) {
            return false;
        }
        GnrUsuaMod other = (GnrUsuaMod) object;
        if ((this.gnrUsuaModPK == null && other.gnrUsuaModPK != null) || (this.gnrUsuaModPK != null && !this.gnrUsuaModPK.equals(other.gnrUsuaModPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrUsuaMod[ gnrUsuaModPK=" + gnrUsuaModPK + " ]";
    }
    
}
