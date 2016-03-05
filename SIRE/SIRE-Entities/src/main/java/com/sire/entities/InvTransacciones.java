/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "INV_TRANSACCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvTransacciones.findAll", query = "SELECT i FROM InvTransacciones i"),
    @NamedQuery(name = "InvTransacciones.findByCodEmpresa", query = "SELECT i FROM InvTransacciones i WHERE i.invTransaccionesPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvTransacciones.findByCodMovimiento", query = "SELECT i FROM InvTransacciones i WHERE i.invTransaccionesPK.codMovimiento = :codMovimiento"),
    @NamedQuery(name = "InvTransacciones.findByDescMovimiento", query = "SELECT i FROM InvTransacciones i WHERE i.descMovimiento = :descMovimiento"),
    @NamedQuery(name = "InvTransacciones.findByCodigoCuenta", query = "SELECT i FROM InvTransacciones i WHERE i.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "InvTransacciones.findByDescAlias", query = "SELECT i FROM InvTransacciones i WHERE i.descAlias = :descAlias"),
    @NamedQuery(name = "InvTransacciones.findByTipoTransaccion", query = "SELECT i FROM InvTransacciones i WHERE i.tipoTransaccion = :tipoTransaccion"),
    @NamedQuery(name = "InvTransacciones.findByAutorizacion", query = "SELECT i FROM InvTransacciones i WHERE i.autorizacion = :autorizacion"),
    @NamedQuery(name = "InvTransacciones.findByNroPedido", query = "SELECT i FROM InvTransacciones i WHERE i.nroPedido = :nroPedido"),
    @NamedQuery(name = "InvTransacciones.findByProvCliente", query = "SELECT i FROM InvTransacciones i WHERE i.provCliente = :provCliente"),
    @NamedQuery(name = "InvTransacciones.findByValoriza", query = "SELECT i FROM InvTransacciones i WHERE i.valoriza = :valoriza"),
    @NamedQuery(name = "InvTransacciones.findByCantidad", query = "SELECT i FROM InvTransacciones i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "InvTransacciones.findByUnidadMedida", query = "SELECT i FROM InvTransacciones i WHERE i.unidadMedida = :unidadMedida"),
    @NamedQuery(name = "InvTransacciones.findByPrecioCosto", query = "SELECT i FROM InvTransacciones i WHERE i.precioCosto = :precioCosto")})
public class InvTransacciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvTransaccionesPK invTransaccionesPK;
    @Basic(optional = false)
    @Column(name = "DESC_MOVIMIENTO")
    private String descMovimiento;
    @Basic(optional = false)
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @Basic(optional = false)
    @Column(name = "DESC_ALIAS")
    private String descAlias;
    @Basic(optional = false)
    @Column(name = "TIPO_TRANSACCION")
    private String tipoTransaccion;
    @Basic(optional = false)
    @Column(name = "AUTORIZACION")
    private String autorizacion;
    @Column(name = "NRO_PEDIDO")
    private String nroPedido;
    @Column(name = "PROV_CLIENTE")
    private String provCliente;
    @Column(name = "VALORIZA")
    private String valoriza;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private String cantidad;
    @Column(name = "UNIDAD_MEDIDA")
    private String unidadMedida;
    @Column(name = "PRECIO_COSTO")
    private String precioCosto;
    @ManyToMany(mappedBy = "invTransaccionesList")
    private List<GnrEmpresa> gnrEmpresaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invTransacciones")
    private List<InvMovimientoCab> invMovimientoCabList;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;

    public InvTransacciones() {
    }

    public InvTransacciones(InvTransaccionesPK invTransaccionesPK) {
        this.invTransaccionesPK = invTransaccionesPK;
    }

    public InvTransacciones(InvTransaccionesPK invTransaccionesPK, String descMovimiento, String codigoCuenta, String descAlias, String tipoTransaccion, String autorizacion, String cantidad) {
        this.invTransaccionesPK = invTransaccionesPK;
        this.descMovimiento = descMovimiento;
        this.codigoCuenta = codigoCuenta;
        this.descAlias = descAlias;
        this.tipoTransaccion = tipoTransaccion;
        this.autorizacion = autorizacion;
        this.cantidad = cantidad;
    }

    public InvTransacciones(String codEmpresa, String codMovimiento) {
        this.invTransaccionesPK = new InvTransaccionesPK(codEmpresa, codMovimiento);
    }

    public InvTransaccionesPK getInvTransaccionesPK() {
        return invTransaccionesPK;
    }

    public void setInvTransaccionesPK(InvTransaccionesPK invTransaccionesPK) {
        this.invTransaccionesPK = invTransaccionesPK;
    }

    public String getDescMovimiento() {
        return descMovimiento;
    }

    public void setDescMovimiento(String descMovimiento) {
        this.descMovimiento = descMovimiento;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getDescAlias() {
        return descAlias;
    }

    public void setDescAlias(String descAlias) {
        this.descAlias = descAlias;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getProvCliente() {
        return provCliente;
    }

    public void setProvCliente(String provCliente) {
        this.provCliente = provCliente;
    }

    public String getValoriza() {
        return valoriza;
    }

    public void setValoriza(String valoriza) {
        this.valoriza = valoriza;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getPrecioCosto() {
        return precioCosto;
    }

    public void setPrecioCosto(String precioCosto) {
        this.precioCosto = precioCosto;
    }

    @XmlTransient
    public List<GnrEmpresa> getGnrEmpresaList() {
        return gnrEmpresaList;
    }

    public void setGnrEmpresaList(List<GnrEmpresa> gnrEmpresaList) {
        this.gnrEmpresaList = gnrEmpresaList;
    }

    @XmlTransient
    public List<InvMovimientoCab> getInvMovimientoCabList() {
        return invMovimientoCabList;
    }

    public void setInvMovimientoCabList(List<InvMovimientoCab> invMovimientoCabList) {
        this.invMovimientoCabList = invMovimientoCabList;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invTransaccionesPK != null ? invTransaccionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvTransacciones)) {
            return false;
        }
        InvTransacciones other = (InvTransacciones) object;
        if ((this.invTransaccionesPK == null && other.invTransaccionesPK != null) || (this.invTransaccionesPK != null && !this.invTransaccionesPK.equals(other.invTransaccionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvTransacciones[ invTransaccionesPK=" + invTransaccionesPK + " ]";
    }
    
}
