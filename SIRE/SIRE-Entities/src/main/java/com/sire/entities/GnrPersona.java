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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "GNR_PERSONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GnrPersona.findAll", query = "SELECT g FROM GnrPersona g"),
    @NamedQuery(name = "GnrPersona.findByCodEmpresa", query = "SELECT g FROM GnrPersona g WHERE g.gnrPersonaPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "GnrPersona.findByCodPersona", query = "SELECT g FROM GnrPersona g WHERE g.gnrPersonaPK.codPersona = :codPersona"),
    @NamedQuery(name = "GnrPersona.findByTipoPersona", query = "SELECT g FROM GnrPersona g WHERE g.tipoPersona = :tipoPersona"),
    @NamedQuery(name = "GnrPersona.findByDocumento", query = "SELECT g FROM GnrPersona g WHERE g.documento = :documento"),
    @NamedQuery(name = "GnrPersona.findByApellidos", query = "SELECT g FROM GnrPersona g WHERE g.apellidos = :apellidos"),
    @NamedQuery(name = "GnrPersona.findByCodDocumento", query = "SELECT g FROM GnrPersona g WHERE g.codDocumento = :codDocumento"),
    @NamedQuery(name = "GnrPersona.findByNombres", query = "SELECT g FROM GnrPersona g WHERE g.nombres = :nombres"),
    @NamedQuery(name = "GnrPersona.findBySexo", query = "SELECT g FROM GnrPersona g WHERE g.sexo = :sexo"),
    @NamedQuery(name = "GnrPersona.findByFechaNacimiento", query = "SELECT g FROM GnrPersona g WHERE g.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "GnrPersona.findByLugarNacimiento", query = "SELECT g FROM GnrPersona g WHERE g.lugarNacimiento = :lugarNacimiento"),
    @NamedQuery(name = "GnrPersona.findByEstadoCivil", query = "SELECT g FROM GnrPersona g WHERE g.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "GnrPersona.findByRazonSocial", query = "SELECT g FROM GnrPersona g WHERE g.razonSocial = :razonSocial"),
    @NamedQuery(name = "GnrPersona.findByRepresentante", query = "SELECT g FROM GnrPersona g WHERE g.representante = :representante"),
    @NamedQuery(name = "GnrPersona.findByDireccion", query = "SELECT g FROM GnrPersona g WHERE g.direccion = :direccion"),
    @NamedQuery(name = "GnrPersona.findByDireccion2", query = "SELECT g FROM GnrPersona g WHERE g.direccion2 = :direccion2"),
    @NamedQuery(name = "GnrPersona.findByTelefono", query = "SELECT g FROM GnrPersona g WHERE g.telefono = :telefono"),
    @NamedQuery(name = "GnrPersona.findByTelefono2", query = "SELECT g FROM GnrPersona g WHERE g.telefono2 = :telefono2"),
    @NamedQuery(name = "GnrPersona.findByFax", query = "SELECT g FROM GnrPersona g WHERE g.fax = :fax"),
    @NamedQuery(name = "GnrPersona.findByMail", query = "SELECT g FROM GnrPersona g WHERE g.mail = :mail"),
    @NamedQuery(name = "GnrPersona.findByCodEntrega", query = "SELECT g FROM GnrPersona g WHERE g.codEntrega = :codEntrega"),
    @NamedQuery(name = "GnrPersona.findByCodContacto", query = "SELECT g FROM GnrPersona g WHERE g.codContacto = :codContacto"),
    @NamedQuery(name = "GnrPersona.findByCiudadEntrega", query = "SELECT g FROM GnrPersona g WHERE g.ciudadEntrega = :ciudadEntrega"),
    @NamedQuery(name = "GnrPersona.findByCiudadContacto", query = "SELECT g FROM GnrPersona g WHERE g.ciudadContacto = :ciudadContacto")})
public class GnrPersona implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GnrPersonaPK gnrPersonaPK;
    @Basic(optional = false)
    @Column(name = "TIPO_PERSONA")
    private String tipoPersona;
    @Column(name = "DOCUMENTO")
    private String documento;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private short codDocumento;
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "LUGAR_NACIMIENTO")
    private String lugarNacimiento;
    @Basic(optional = false)
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "REPRESENTANTE")
    private String representante;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "DIRECCION2")
    private String direccion2;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Column(name = "FAX")
    private String fax;
    @Column(name = "MAIL")
    private String mail;
    @Column(name = "COD_ENTREGA")
    private String codEntrega;
    @Column(name = "COD_CONTACTO")
    private String codContacto;
    @Column(name = "CIUDAD_ENTREGA")
    private String ciudadEntrega;
    @Column(name = "CIUDAD_CONTACTO")
    private String ciudadContacto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrPersona")
    private List<InvProveedor> invProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gnrPersona")
    private List<FacVendedor> facVendedorList;
    
    public GnrPersona() {
    }

    public GnrPersona(GnrPersonaPK gnrPersonaPK) {
        this.gnrPersonaPK = gnrPersonaPK;
    }

    public GnrPersona(GnrPersonaPK gnrPersonaPK, String tipoPersona, short codDocumento, String sexo, String estadoCivil) {
        this.gnrPersonaPK = gnrPersonaPK;
        this.tipoPersona = tipoPersona;
        this.codDocumento = codDocumento;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
    }

    public GnrPersona(String codEmpresa, int codPersona) {
        this.gnrPersonaPK = new GnrPersonaPK(codEmpresa, codPersona);
    }

    public GnrPersonaPK getGnrPersonaPK() {
        return gnrPersonaPK;
    }

    public void setGnrPersonaPK(GnrPersonaPK gnrPersonaPK) {
        this.gnrPersonaPK = gnrPersonaPK;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public short getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(short codDocumento) {
        this.codDocumento = codDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCodEntrega() {
        return codEntrega;
    }

    public void setCodEntrega(String codEntrega) {
        this.codEntrega = codEntrega;
    }

    public String getCodContacto() {
        return codContacto;
    }

    public void setCodContacto(String codContacto) {
        this.codContacto = codContacto;
    }

    public String getCiudadEntrega() {
        return ciudadEntrega;
    }

    public void setCiudadEntrega(String ciudadEntrega) {
        this.ciudadEntrega = ciudadEntrega;
    }

    public String getCiudadContacto() {
        return ciudadContacto;
    }

    public void setCiudadContacto(String ciudadContacto) {
        this.ciudadContacto = ciudadContacto;
    }

    @XmlTransient
    public List<InvProveedor> getInvProveedorList() {
        return invProveedorList;
    }

    public void setInvProveedorList(List<InvProveedor> invProveedorList) {
        this.invProveedorList = invProveedorList;
    }
    
    @XmlTransient
    public List<FacVendedor> getFacVendedorList() {
        return facVendedorList;
    }

    public void setFacVendedorList(List<FacVendedor> facVendedorList) {
        this.facVendedorList = facVendedorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gnrPersonaPK != null ? gnrPersonaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GnrPersona)) {
            return false;
        }
        GnrPersona other = (GnrPersona) object;
        if ((this.gnrPersonaPK == null && other.gnrPersonaPK != null) || (this.gnrPersonaPK != null && !this.gnrPersonaPK.equals(other.gnrPersonaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.GnrPersona[ gnrPersonaPK=" + gnrPersonaPK + " ]";
    }

}
