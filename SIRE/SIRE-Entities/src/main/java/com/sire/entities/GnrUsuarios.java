/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "GNR_USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrUsuarios.findAll", query = "SELECT g FROM GnrUsuarios g"),
    @NamedQuery(name = "GnrUsuarios.findByNombreUsuario", query = "SELECT g FROM GnrUsuarios g WHERE g.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "GnrUsuarios.findByFechaCreacion", query = "SELECT g FROM GnrUsuarios g WHERE g.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "GnrUsuarios.findByClave", query = "SELECT g FROM GnrUsuarios g WHERE g.clave = :clave"),
    @NamedQuery(name = "GnrUsuarios.findByFechaCaducidad", query = "SELECT g FROM GnrUsuarios g WHERE g.fechaCaducidad = :fechaCaducidad"),
    @NamedQuery(name = "GnrUsuarios.findByTipoUsuario", query = "SELECT g FROM GnrUsuarios g WHERE g.tipoUsuario = :tipoUsuario"),
    @NamedQuery(name = "GnrUsuarios.findByEstado", query = "SELECT g FROM GnrUsuarios g WHERE g.estado = :estado"),
    @NamedQuery(name = "GnrUsuarios.findByFechaEstado", query = "SELECT g FROM GnrUsuarios g WHERE g.fechaEstado = :fechaEstado")})
public class GnrUsuarios implements Serializable {

    @Basic(optional = false)
    @Column(name = "USUARIO_ID")
    private int usuarioId;
    @OneToMany(mappedBy = "nombreUsuario")
    private List<CajRubro> cajRubroList;

    @OneToMany(mappedBy = "nombreUsuario")
    private List<PryProyecto> pryProyectoList;

    @OneToMany(mappedBy = "nombreUsuario")
    private List<CajFacturaEnviada> cajFacturaEnviadaList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrUsuarios")
    private List<GnrUsuaMod> gnrUsuaModList;

    @OneToMany(mappedBy = "nombreUsuario")
    private List<CxcAbonoC> cxcAbonoCList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrUsuarios")
    private List<FacParametros> facParametrosList;
    @OneToMany(mappedBy = "nombreUsuario")
    private List<FacTmpFactC> facTmpFactCList;
    @JoinTable(name = "GNR_USUA_CONT", joinColumns = {
        @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO")}, inverseJoinColumns = {
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA"),
        @JoinColumn(name = "NUM_CONTADOR", referencedColumnName = "NUM_CONTADOR"),
        @JoinColumn(name = "COD_MODULO", referencedColumnName = "COD_MODULO"),
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO")})
    @ManyToMany
    private List<GnrContadorDoc> gnrContadorDocList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "CLAVE")
    private String clave;
    @Column(name = "FECHA_CADUCIDAD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaducidad;
    @Column(name = "TIPO_USUARIO")
    private String tipoUsuario;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @OneToMany(mappedBy = "usuario")
    private List<FacCatalogoPrecioD> facCatalogoPrecioDList;
    @OneToMany(mappedBy = "anteriorUsuarioPrecio")
    private List<FacCatalogoPrecioD> facCatalogoPrecioDList1;

    public GnrUsuarios() {
    }

    public GnrUsuarios(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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

    @XmlTransient
    public List<FacCatalogoPrecioD> getFacCatalogoPrecioDList() {
        return facCatalogoPrecioDList;
    }

    public void setFacCatalogoPrecioDList(List<FacCatalogoPrecioD> facCatalogoPrecioDList) {
        this.facCatalogoPrecioDList = facCatalogoPrecioDList;
    }

    @XmlTransient
    public List<FacCatalogoPrecioD> getFacCatalogoPrecioDList1() {
        return facCatalogoPrecioDList1;
    }

    public void setFacCatalogoPrecioDList1(List<FacCatalogoPrecioD> facCatalogoPrecioDList1) {
        this.facCatalogoPrecioDList1 = facCatalogoPrecioDList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrUsuarios)) {
            return false;
        }
        GnrUsuarios other = (GnrUsuarios) object;
        if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrUsuarios[ nombreUsuario=" + nombreUsuario + " ]";
    }

    @XmlTransient
    public List<GnrContadorDoc> getGnrContadorDocList() {
        return gnrContadorDocList;
    }

    public void setGnrContadorDocList(List<GnrContadorDoc> gnrContadorDocList) {
        this.gnrContadorDocList = gnrContadorDocList;
    }

    @XmlTransient
    public List<FacTmpFactC> getFacTmpFactCList() {
        return facTmpFactCList;
    }

    public void setFacTmpFactCList(List<FacTmpFactC> facTmpFactCList) {
        this.facTmpFactCList = facTmpFactCList;
    }

    @XmlTransient
    public List<FacParametros> getFacParametrosList() {
        return facParametrosList;
    }

    public void setFacParametrosList(List<FacParametros> facParametrosList) {
        this.facParametrosList = facParametrosList;
    }

    @XmlTransient
    public List<CxcAbonoC> getCxcAbonoCList() {
        return cxcAbonoCList;
    }

    public void setCxcAbonoCList(List<CxcAbonoC> cxcAbonoCList) {
        this.cxcAbonoCList = cxcAbonoCList;
    }

    @XmlTransient
    public List<GnrUsuaMod> getGnrUsuaModList() {
        return gnrUsuaModList;
    }

    public void setGnrUsuaModList(List<GnrUsuaMod> gnrUsuaModList) {
        this.gnrUsuaModList = gnrUsuaModList;
    }

    @XmlTransient
    public List<CajFacturaEnviada> getCajFacturaEnviadaList() {
        return cajFacturaEnviadaList;
    }

    public void setCajFacturaEnviadaList(List<CajFacturaEnviada> cajFacturaEnviadaList) {
        this.cajFacturaEnviadaList = cajFacturaEnviadaList;
    }

    @XmlTransient
    public List<PryProyecto> getPryProyectoList() {
        return pryProyectoList;
    }

    public void setPryProyectoList(List<PryProyecto> pryProyectoList) {
        this.pryProyectoList = pryProyectoList;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @XmlTransient
    public List<CajRubro> getCajRubroList() {
        return cajRubroList;
    }

    public void setCajRubroList(List<CajRubro> cajRubroList) {
        this.cajRubroList = cajRubroList;
    }
    
}
