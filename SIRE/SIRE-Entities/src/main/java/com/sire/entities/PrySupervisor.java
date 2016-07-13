/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "PRY_SUPERVISOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrySupervisor.findAll", query = "SELECT p FROM PrySupervisor p"),
    @NamedQuery(name = "PrySupervisor.findByCodEmpresa", query = "SELECT p FROM PrySupervisor p WHERE p.prySupervisorPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "PrySupervisor.findByFechaEstado", query = "SELECT p FROM PrySupervisor p WHERE p.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "PrySupervisor.findByEstado", query = "SELECT p FROM PrySupervisor p WHERE p.estado = :estado"),
    @NamedQuery(name = "PrySupervisor.findByMail", query = "SELECT p FROM PrySupervisor p WHERE p.mail = :mail"),
    @NamedQuery(name = "PrySupervisor.findByMovil", query = "SELECT p FROM PrySupervisor p WHERE p.movil = :movil"),
    @NamedQuery(name = "PrySupervisor.findByTelefono", query = "SELECT p FROM PrySupervisor p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "PrySupervisor.findByDireccion", query = "SELECT p FROM PrySupervisor p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "PrySupervisor.findByNombres", query = "SELECT p FROM PrySupervisor p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "PrySupervisor.findByCodSupervisor", query = "SELECT p FROM PrySupervisor p WHERE p.prySupervisorPK.codSupervisor = :codSupervisor"),
    @NamedQuery(name = "PrySupervisor.findByCodigoCuenta", query = "SELECT p FROM PrySupervisor p WHERE p.codigoCuenta = :codigoCuenta")})
public class PrySupervisor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrySupervisorPK prySupervisorPK;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "MAIL")
    private String mail;
    @Column(name = "MOVIL")
    private String movil;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prySupervisor")
    private List<CajFacturaEnviada> cajFacturaEnviadaList;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_PROVEEDOR", referencedColumnName = "COD_PROVEEDOR")})
    @ManyToOne(optional = false)
    private InvProveedor invProveedor;

    public PrySupervisor() {
    }

    public PrySupervisor(PrySupervisorPK prySupervisorPK) {
        this.prySupervisorPK = prySupervisorPK;
    }

    public PrySupervisor(String codEmpresa, int codSupervisor) {
        this.prySupervisorPK = new PrySupervisorPK(codEmpresa, codSupervisor);
    }

    public PrySupervisorPK getPrySupervisorPK() {
        return prySupervisorPK;
    }

    public void setPrySupervisorPK(PrySupervisorPK prySupervisorPK) {
        this.prySupervisorPK = prySupervisorPK;
    }

    public Date getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(Date fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    @XmlTransient
    public List<CajFacturaEnviada> getCajFacturaEnviadaList() {
        return cajFacturaEnviadaList;
    }

    public void setCajFacturaEnviadaList(List<CajFacturaEnviada> cajFacturaEnviadaList) {
        this.cajFacturaEnviadaList = cajFacturaEnviadaList;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    public InvProveedor getInvProveedor() {
        return invProveedor;
    }

    public void setInvProveedor(InvProveedor invProveedor) {
        this.invProveedor = invProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prySupervisorPK != null ? prySupervisorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrySupervisor)) {
            return false;
        }
        PrySupervisor other = (PrySupervisor) object;
        if ((this.prySupervisorPK == null && other.prySupervisorPK != null) || (this.prySupervisorPK != null && !this.prySupervisorPK.equals(other.prySupervisorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.PrySupervisor[ prySupervisorPK=" + prySupervisorPK + " ]";
    }
    
}
