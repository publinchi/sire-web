/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "CXC_CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcCliente.findAll", query = "SELECT c FROM CxcCliente c"),
    @NamedQuery(name = "CxcCliente.findByCodEmpresa", query = "SELECT c FROM CxcCliente c WHERE c.cxcClientePK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcCliente.findByCodCatalogo", query = "SELECT c FROM CxcCliente c WHERE c.codCatalogo = :codCatalogo"),
    @NamedQuery(name = "CxcCliente.findByCodGrupo", query = "SELECT c FROM CxcCliente c WHERE c.codGrupo = :codGrupo"),
    @NamedQuery(name = "CxcCliente.findByCodPersona", query = "SELECT c FROM CxcCliente c WHERE c.codPersona = :codPersona"),
    @NamedQuery(name = "CxcCliente.findByCodTipo", query = "SELECT c FROM CxcCliente c WHERE c.codTipo = :codTipo"),
    @NamedQuery(name = "CxcCliente.findByCodVendedor", query = "SELECT c FROM CxcCliente c WHERE c.codVendedor = :codVendedor"),
    @NamedQuery(name = "CxcCliente.findByCodCliente", query = "SELECT c FROM CxcCliente c WHERE c.cxcClientePK.codCliente = :codCliente"),
    @NamedQuery(name = "CxcCliente.findByFechaCreacion", query = "SELECT c FROM CxcCliente c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "CxcCliente.findByLimiteFactura", query = "SELECT c FROM CxcCliente c WHERE c.limiteFactura = :limiteFactura"),
    @NamedQuery(name = "CxcCliente.findByIndicador", query = "SELECT c FROM CxcCliente c WHERE c.indicador = :indicador"),
    @NamedQuery(name = "CxcCliente.findByPagaIva", query = "SELECT c FROM CxcCliente c WHERE c.pagaIva = :pagaIva"),
    @NamedQuery(name = "CxcCliente.findByEstado", query = "SELECT c FROM CxcCliente c WHERE c.estado = :estado"),
    @NamedQuery(name = "CxcCliente.findByFechaEstado", query = "SELECT c FROM CxcCliente c WHERE c.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "CxcCliente.findByObservaciones", query = "SELECT c FROM CxcCliente c WHERE c.observaciones = :observaciones"),
    @NamedQuery(name = "CxcCliente.findByCodPago", query = "SELECT c FROM CxcCliente c WHERE c.codPago = :codPago"),
    @NamedQuery(name = "CxcCliente.findByComoPaga", query = "SELECT c FROM CxcCliente c WHERE c.comoPaga = :comoPaga"),
    @NamedQuery(name = "CxcCliente.findByCodigoAnterior", query = "SELECT c FROM CxcCliente c WHERE c.codigoAnterior = :codigoAnterior"),
    @NamedQuery(name = "CxcCliente.findByLocalidad", query = "SELECT c FROM CxcCliente c WHERE c.localidad = :localidad"),
    @NamedQuery(name = "CxcCliente.findByCalificacion", query = "SELECT c FROM CxcCliente c WHERE c.calificacion = :calificacion")})
public class CxcCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcClientePK cxcClientePK;
    @Column(name = "COD_CATALOGO")
    private String codCatalogo;
    @Column(name = "COD_GRUPO")
    private String codGrupo;
    @Column(name = "COD_PERSONA")
    private Integer codPersona;
    @Column(name = "COD_TIPO")
    private String codTipo;
    @Basic(optional = false)
    @Column(name = "COD_VENDEDOR")
    private int codVendedor;
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
    @Column(name = "CODIGO_ANTERIOR")
    private BigInteger codigoAnterior;
    @Column(name = "LOCALIDAD")
    private String localidad;
    @Column(name = "CALIFICACION")
    private String calificacion;
    @JoinColumn(name = "CLASE_CLIETE", referencedColumnName = "CODIGO")
    @ManyToOne
    private CxcClaseCliente claseCliete;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_ZONA", referencedColumnName = "COD_ZONA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_SECTOR", referencedColumnName = "COD_SECTOR")})
    @ManyToOne(optional = false)
    private CxcSector cxcSector;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_ZONA", referencedColumnName = "COD_ZONA")})
    @ManyToOne(optional = false)
    private CxcZona cxcZona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cxcCliente")
    private List<InvMovimientoCab> invMovimientoCabList;

    public CxcCliente() {
    }

    public CxcCliente(CxcClientePK cxcClientePK) {
        this.cxcClientePK = cxcClientePK;
    }

    public CxcCliente(CxcClientePK cxcClientePK, int codVendedor, BigInteger limiteFactura) {
        this.cxcClientePK = cxcClientePK;
        this.codVendedor = codVendedor;
        this.limiteFactura = limiteFactura;
    }

    public CxcCliente(String codEmpresa, BigInteger codCliente) {
        this.cxcClientePK = new CxcClientePK(codEmpresa, codCliente);
    }

    public CxcClientePK getCxcClientePK() {
        return cxcClientePK;
    }

    public void setCxcClientePK(CxcClientePK cxcClientePK) {
        this.cxcClientePK = cxcClientePK;
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

    public Integer getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Integer codPersona) {
        this.codPersona = codPersona;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public int getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
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

    public BigInteger getCodigoAnterior() {
        return codigoAnterior;
    }

    public void setCodigoAnterior(BigInteger codigoAnterior) {
        this.codigoAnterior = codigoAnterior;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public CxcClaseCliente getClaseCliete() {
        return claseCliete;
    }

    public void setClaseCliete(CxcClaseCliente claseCliete) {
        this.claseCliete = claseCliete;
    }

    public CxcSector getCxcSector() {
        return cxcSector;
    }

    public void setCxcSector(CxcSector cxcSector) {
        this.cxcSector = cxcSector;
    }

    public CxcZona getCxcZona() {
        return cxcZona;
    }

    public void setCxcZona(CxcZona cxcZona) {
        this.cxcZona = cxcZona;
    }

    @XmlTransient
    public List<InvMovimientoCab> getInvMovimientoCabList() {
        return invMovimientoCabList;
    }

    public void setInvMovimientoCabList(List<InvMovimientoCab> invMovimientoCabList) {
        this.invMovimientoCabList = invMovimientoCabList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcClientePK != null ? cxcClientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcCliente)) {
            return false;
        }
        CxcCliente other = (CxcCliente) object;
        if ((this.cxcClientePK == null && other.cxcClientePK != null) || (this.cxcClientePK != null && !this.cxcClientePK.equals(other.cxcClientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcCliente[ cxcClientePK=" + cxcClientePK + " ]";
    }
    
}
