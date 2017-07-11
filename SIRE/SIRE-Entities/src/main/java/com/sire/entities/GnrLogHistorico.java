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
@Table(name = "GNR_LOG_HISTORICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrLogHistorico.findAll", query = "SELECT g FROM GnrLogHistorico g"),
    @NamedQuery(name = "GnrLogHistorico.findByCodEmpresa", query = "SELECT g FROM GnrLogHistorico g WHERE g.gnrLogHistoricoPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "GnrLogHistorico.findByCodDocumento", query = "SELECT g FROM GnrLogHistorico g WHERE g.gnrLogHistoricoPK.codDocumento = :codDocumento"),
    @NamedQuery(name = "GnrLogHistorico.findByNumDocumento", query = "SELECT g FROM GnrLogHistorico g WHERE g.gnrLogHistoricoPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "GnrLogHistorico.findByFechaDocumento", query = "SELECT g FROM GnrLogHistorico g WHERE g.fechaDocumento = :fechaDocumento"),
    @NamedQuery(name = "GnrLogHistorico.findByUbicacionGeografica", query = "SELECT g FROM GnrLogHistorico g WHERE g.ubicacionGeografica = :ubicacionGeografica"),
    @NamedQuery(name = "GnrLogHistorico.findByDispositivo", query = "SELECT g FROM GnrLogHistorico g WHERE g.dispositivo = :dispositivo"),
    @NamedQuery(name = "GnrLogHistorico.findByNombreUsuario", query = "SELECT g FROM GnrLogHistorico g WHERE g.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "GnrLogHistorico.findByEstado", query = "SELECT g FROM GnrLogHistorico g WHERE g.estado = :estado"),
    @NamedQuery(name = "GnrLogHistorico.findByFechaEstado", query = "SELECT g FROM GnrLogHistorico g WHERE g.fechaEstado = :fechaEstado")})
public class GnrLogHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GnrLogHistoricoPK gnrLogHistoricoPK;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "UBICACION_GEOGRAFICA")
    private String ubicacionGeografica;
    @Column(name = "DISPOSITIVO")
    private String dispositivo;
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "LATITUD")
    private String latitud;
    @Column(name = "LONGITUD")
    private String longitud;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;

    public GnrLogHistorico() {
    }

    public GnrLogHistorico(GnrLogHistoricoPK gnrLogHistoricoPK) {
        this.gnrLogHistoricoPK = gnrLogHistoricoPK;
    }

    public GnrLogHistorico(String codEmpresa, String codDocumento, int numDocumento) {
        this.gnrLogHistoricoPK = new GnrLogHistoricoPK(codEmpresa, codDocumento, numDocumento);
    }

    public GnrLogHistoricoPK getGnrLogHistoricoPK() {
        return gnrLogHistoricoPK;
    }

    public void setGnrLogHistoricoPK(GnrLogHistoricoPK gnrLogHistoricoPK) {
        this.gnrLogHistoricoPK = gnrLogHistoricoPK;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    public void setUbicacionGeografica(String ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
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
        hash += (gnrLogHistoricoPK != null ? gnrLogHistoricoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrLogHistorico)) {
            return false;
        }
        GnrLogHistorico other = (GnrLogHistorico) object;
        if ((this.gnrLogHistoricoPK == null && other.gnrLogHistoricoPK != null) || (this.gnrLogHistoricoPK != null && !this.gnrLogHistoricoPK.equals(other.gnrLogHistoricoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrLogHistorico[ gnrLogHistoricoPK=" + gnrLogHistoricoPK + " ]";
    }

}
