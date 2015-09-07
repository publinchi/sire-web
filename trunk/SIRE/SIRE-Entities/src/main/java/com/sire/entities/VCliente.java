/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "V_CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VCliente.findAll", query = "SELECT v FROM VCliente v"),
    @NamedQuery(name = "VCliente.findByCodEmpresa", query = "SELECT v FROM VCliente v WHERE v.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "VCliente.findByCodCatalogo", query = "SELECT v FROM VCliente v WHERE v.codCatalogo = :codCatalogo"),
    @NamedQuery(name = "VCliente.findByCodGrupo", query = "SELECT v FROM VCliente v WHERE v.codGrupo = :codGrupo"),
    @NamedQuery(name = "VCliente.findByCodTipo", query = "SELECT v FROM VCliente v WHERE v.codTipo = :codTipo"),
    @NamedQuery(name = "VCliente.findByCodPersona", query = "SELECT v FROM VCliente v WHERE v.codPersona = :codPersona"),
    @NamedQuery(name = "VCliente.findByCodCliente", query = "SELECT v FROM VCliente v WHERE v.codCliente = :codCliente"),
    @NamedQuery(name = "VCliente.findByCodZona", query = "SELECT v FROM VCliente v WHERE v.codZona = :codZona"),
    @NamedQuery(name = "VCliente.findByCodSector", query = "SELECT v FROM VCliente v WHERE v.codSector = :codSector"),
    @NamedQuery(name = "VCliente.findByFechaCreacion", query = "SELECT v FROM VCliente v WHERE v.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "VCliente.findByLimiteFactura", query = "SELECT v FROM VCliente v WHERE v.limiteFactura = :limiteFactura"),
    @NamedQuery(name = "VCliente.findByIndicador", query = "SELECT v FROM VCliente v WHERE v.indicador = :indicador"),
    @NamedQuery(name = "VCliente.findByPagaIva", query = "SELECT v FROM VCliente v WHERE v.pagaIva = :pagaIva"),
    @NamedQuery(name = "VCliente.findByEstado", query = "SELECT v FROM VCliente v WHERE v.estado = :estado"),
    @NamedQuery(name = "VCliente.findByFechaEstado", query = "SELECT v FROM VCliente v WHERE v.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "VCliente.findByObservaciones", query = "SELECT v FROM VCliente v WHERE v.observaciones = :observaciones"),
    @NamedQuery(name = "VCliente.findByCodPago", query = "SELECT v FROM VCliente v WHERE v.codPago = :codPago"),
    @NamedQuery(name = "VCliente.findByComoPaga", query = "SELECT v FROM VCliente v WHERE v.comoPaga = :comoPaga"),
    @NamedQuery(name = "VCliente.findByTipoPersona", query = "SELECT v FROM VCliente v WHERE v.tipoPersona = :tipoPersona"),
    @NamedQuery(name = "VCliente.findByDocumento", query = "SELECT v FROM VCliente v WHERE v.documento = :documento"),
    @NamedQuery(name = "VCliente.findByApellidos", query = "SELECT v FROM VCliente v WHERE v.apellidos like :apellidos"),
    @NamedQuery(name = "VCliente.findByCodDocumento", query = "SELECT v FROM VCliente v WHERE v.codDocumento = :codDocumento"),
    @NamedQuery(name = "VCliente.findByNombres", query = "SELECT v FROM VCliente v WHERE v.nombres = :nombres"),
    @NamedQuery(name = "VCliente.findBySexo", query = "SELECT v FROM VCliente v WHERE v.sexo = :sexo"),
    @NamedQuery(name = "VCliente.findByFechaNacimiento", query = "SELECT v FROM VCliente v WHERE v.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "VCliente.findByLugarNacimiento", query = "SELECT v FROM VCliente v WHERE v.lugarNacimiento = :lugarNacimiento"),
    @NamedQuery(name = "VCliente.findByEstadoCivil", query = "SELECT v FROM VCliente v WHERE v.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "VCliente.findByFax", query = "SELECT v FROM VCliente v WHERE v.fax = :fax"),
    @NamedQuery(name = "VCliente.findByMail", query = "SELECT v FROM VCliente v WHERE v.mail = :mail"),
    @NamedQuery(name = "VCliente.findByRazonSocial", query = "SELECT v FROM VCliente v WHERE v.razonSocial = :razonSocial"),
    @NamedQuery(name = "VCliente.findByRepresentante", query = "SELECT v FROM VCliente v WHERE v.representante = :representante"),
    @NamedQuery(name = "VCliente.findByDireccion", query = "SELECT v FROM VCliente v WHERE v.direccion = :direccion"),
    @NamedQuery(name = "VCliente.findByCodContacto", query = "SELECT v FROM VCliente v WHERE v.codContacto = :codContacto"),
    @NamedQuery(name = "VCliente.findByTelefono", query = "SELECT v FROM VCliente v WHERE v.telefono = :telefono"),
    @NamedQuery(name = "VCliente.findByDireccion2", query = "SELECT v FROM VCliente v WHERE v.direccion2 = :direccion2"),
    @NamedQuery(name = "VCliente.findByCodEntrega", query = "SELECT v FROM VCliente v WHERE v.codEntrega = :codEntrega"),
    @NamedQuery(name = "VCliente.findByTelefono2", query = "SELECT v FROM VCliente v WHERE v.telefono2 = :telefono2"),
    @NamedQuery(name = "VCliente.findByCodVendedor", query = "SELECT v FROM VCliente v WHERE v.codVendedor = :codVendedor"),
    @NamedQuery(name = "VCliente.findByVendedor", query = "SELECT v FROM VCliente v WHERE v.vendedor = :vendedor"),
    @NamedQuery(name = "VCliente.findByCiudadEntrega", query = "SELECT v FROM VCliente v WHERE v.ciudadEntrega = :ciudadEntrega"),
    @NamedQuery(name = "VCliente.findByCiudadContacto", query = "SELECT v FROM VCliente v WHERE v.ciudadContacto = :ciudadContacto"),
    @NamedQuery(name = "VCliente.findByCodigoAnterior", query = "SELECT v FROM VCliente v WHERE v.codigoAnterior = :codigoAnterior"),
    @NamedQuery(name = "VCliente.findByClaseCliete", query = "SELECT v FROM VCliente v WHERE v.claseCliete = :claseCliete"),
    @NamedQuery(name = "VCliente.findByLocalidad", query = "SELECT v FROM VCliente v WHERE v.localidad = :localidad")})
public class VCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Column(name = "COD_CATALOGO")
    private String codCatalogo;
    @Column(name = "COD_GRUPO")
    private String codGrupo;
    @Column(name = "COD_TIPO")
    private String codTipo;
    @Column(name = "COD_PERSONA")
    private Integer codPersona;
    @Basic(optional = false)
    @Column(name = "COD_CLIENTE")
    @Id
    private BigInteger codCliente;
    @Column(name = "COD_ZONA")
    private String codZona;
    @Column(name = "COD_SECTOR")
    private String codSector;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "LIMITE_FACTURA")
    private BigInteger limiteFactura;
    @Column(name = "INDICADOR")
    private String indicador;
    @Column(name = "PAGA_IVA")
    private String pagaIva;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "COD_PAGO")
    private String codPago;
    @Column(name = "COMO_PAGA")
    private String comoPaga;
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
    @Column(name = "FAX")
    private String fax;
    @Column(name = "MAIL")
    private String mail;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "REPRESENTANTE")
    private String representante;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "COD_CONTACTO")
    private String codContacto;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DIRECCION2")
    private String direccion2;
    @Column(name = "COD_ENTREGA")
    private String codEntrega;
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Basic(optional = false)
    @Column(name = "COD_VENDEDOR")
    private int codVendedor;
    @Column(name = "VENDEDOR")
    private String vendedor;
    @Column(name = "CIUDAD_ENTREGA")
    private String ciudadEntrega;
    @Column(name = "CIUDAD_CONTACTO")
    private String ciudadContacto;
    @Column(name = "CODIGO_ANTERIOR")
    private BigInteger codigoAnterior;
    @Column(name = "CLASE_CLIETE")
    private String claseCliete;
    @Column(name = "LOCALIDAD")
    private String localidad;

    public VCliente() {
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodCatalogo() {
        return codCatalogo;
    }

    public void setCodCatalogo(String codCatalogo) {
        this.codCatalogo = codCatalogo;
    }

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public Integer getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Integer codPersona) {
        this.codPersona = codPersona;
    }

    public BigInteger getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(BigInteger codCliente) {
        this.codCliente = codCliente;
    }

    public String getCodZona() {
        return codZona;
    }

    public void setCodZona(String codZona) {
        this.codZona = codZona;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigInteger getLimiteFactura() {
        return limiteFactura;
    }

    public void setLimiteFactura(BigInteger limiteFactura) {
        this.limiteFactura = limiteFactura;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getPagaIva() {
        return pagaIva;
    }

    public void setPagaIva(String pagaIva) {
        this.pagaIva = pagaIva;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCodPago() {
        return codPago;
    }

    public void setCodPago(String codPago) {
        this.codPago = codPago;
    }

    public String getComoPaga() {
        return comoPaga;
    }

    public void setComoPaga(String comoPaga) {
        this.comoPaga = comoPaga;
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

    public String getCodContacto() {
        return codContacto;
    }

    public void setCodContacto(String codContacto) {
        this.codContacto = codContacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getCodEntrega() {
        return codEntrega;
    }

    public void setCodEntrega(String codEntrega) {
        this.codEntrega = codEntrega;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public int getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
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

    public BigInteger getCodigoAnterior() {
        return codigoAnterior;
    }

    public void setCodigoAnterior(BigInteger codigoAnterior) {
        this.codigoAnterior = codigoAnterior;
    }

    public String getClaseCliete() {
        return claseCliete;
    }

    public void setClaseCliete(String claseCliete) {
        this.claseCliete = claseCliete;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
}
