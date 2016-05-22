/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CAJ_RUBRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CajRubro.findAll", query = "SELECT c FROM CajRubro c"),
    @NamedQuery(name = "CajRubro.findByCodEmpresa", query = "SELECT c FROM CajRubro c WHERE c.cajRubroPK.codEmpresa = :codEmpresa and c.estado <> 'I'"),
    @NamedQuery(name = "CajRubro.findByCodRubro", query = "SELECT c FROM CajRubro c WHERE c.cajRubroPK.codRubro = :codRubro"),
    @NamedQuery(name = "CajRubro.findByDescripcionRubro", query = "SELECT c FROM CajRubro c WHERE c.descripcionRubro = :descripcionRubro"),
    @NamedQuery(name = "CajRubro.findByMontoRubro", query = "SELECT c FROM CajRubro c WHERE c.montoRubro = :montoRubro"),
    @NamedQuery(name = "CajRubro.findByEstado", query = "SELECT c FROM CajRubro c WHERE c.estado = :estado"),
    @NamedQuery(name = "CajRubro.findByFechaEstado", query = "SELECT c FROM CajRubro c WHERE c.fechaEstado = :fechaEstado")})
public class CajRubro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CajRubroPK cajRubroPK;
    @Column(name = "DESCRIPCION_RUBRO")
    private String descripcionRubro;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MONTO_RUBRO")
    private BigDecimal montoRubro;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO")
    @ManyToOne
    private GnrUsuarios nombreUsuario;

    public CajRubro() {
    }

    public CajRubro(CajRubroPK cajRubroPK) {
        this.cajRubroPK = cajRubroPK;
    }

    public CajRubro(String codEmpresa, int codRubro) {
        this.cajRubroPK = new CajRubroPK(codEmpresa, codRubro);
    }

    public CajRubroPK getCajRubroPK() {
        return cajRubroPK;
    }

    public void setCajRubroPK(CajRubroPK cajRubroPK) {
        this.cajRubroPK = cajRubroPK;
    }

    public String getDescripcionRubro() {
        return descripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        this.descripcionRubro = descripcionRubro;
    }

    public BigDecimal getMontoRubro() {
        return montoRubro;
    }

    public void setMontoRubro(BigDecimal montoRubro) {
        this.montoRubro = montoRubro;
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

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    public GnrUsuarios getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(GnrUsuarios nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cajRubroPK != null ? cajRubroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajRubro)) {
            return false;
        }
        CajRubro other = (CajRubro) object;
        if ((this.cajRubroPK == null && other.cajRubroPK != null) || (this.cajRubroPK != null && !this.cajRubroPK.equals(other.cajRubroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CajRubro[ cajRubroPK=" + cajRubroPK + " ]";
    }
    
}
