/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "INV_INVENTARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvInventario.findAll", query = "SELECT i FROM InvInventario i"),
    @NamedQuery(name = "InvInventario.findByCodEmpresa", query = "SELECT i FROM InvInventario i WHERE i.invInventarioPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvInventario.findByCodBodega", query = "SELECT i FROM InvInventario i WHERE i.invInventarioPK.codBodega = :codBodega"),
    @NamedQuery(name = "InvInventario.findByCodInventario", query = "SELECT i FROM InvInventario i WHERE i.invInventarioPK.codInventario = :codInventario"),
    @NamedQuery(name = "InvInventario.findByCodigoCuenta", query = "SELECT i FROM InvInventario i WHERE i.codigoCuenta = :codigoCuenta"),
    @NamedQuery(name = "InvInventario.findByDescInventario", query = "SELECT i FROM InvInventario i WHERE i.descInventario = :descInventario"),
    @NamedQuery(name = "InvInventario.findByNombreCorto", query = "SELECT i FROM InvInventario i WHERE i.nombreCorto = :nombreCorto"),
    @NamedQuery(name = "InvInventario.findByCostoVenta", query = "SELECT i FROM InvInventario i WHERE i.costoVenta = :costoVenta"),
    @NamedQuery(name = "InvInventario.findByVentas", query = "SELECT i FROM InvInventario i WHERE i.ventas = :ventas"),
    @NamedQuery(name = "InvInventario.findByDevolucionVentas", query = "SELECT i FROM InvInventario i WHERE i.devolucionVentas = :devolucionVentas"),
    @NamedQuery(name = "InvInventario.findByDescuentoVentas", query = "SELECT i FROM InvInventario i WHERE i.descuentoVentas = :descuentoVentas")})
public class InvInventario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvInventarioPK invInventarioPK;
    @Column(name = "CODIGO_CUENTA")
    private String codigoCuenta;
    @Column(name = "DESC_INVENTARIO")
    private String descInventario;
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;
    @Column(name = "COSTO_VENTA")
    private String costoVenta;
    @Column(name = "VENTAS")
    private String ventas;
    @Column(name = "DEVOLUCION_VENTAS")
    private String devolucionVentas;
    @Column(name = "DESCUENTO_VENTAS")
    private String descuentoVentas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invInventario")
    private List<InvBodegaArt> invBodegaArtList;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_BODEGA", referencedColumnName = "COD_BODEGA", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private InvBodega invBodega;

    public InvInventario() {
    }

    public InvInventario(InvInventarioPK invInventarioPK) {
        this.invInventarioPK = invInventarioPK;
    }

    public InvInventario(String codEmpresa, String codBodega, String codInventario) {
        this.invInventarioPK = new InvInventarioPK(codEmpresa, codBodega, codInventario);
    }

    public InvInventarioPK getInvInventarioPK() {
        return invInventarioPK;
    }

    public void setInvInventarioPK(InvInventarioPK invInventarioPK) {
        this.invInventarioPK = invInventarioPK;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getDescInventario() {
        return descInventario;
    }

    public void setDescInventario(String descInventario) {
        this.descInventario = descInventario;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(String costoVenta) {
        this.costoVenta = costoVenta;
    }

    public String getVentas() {
        return ventas;
    }

    public void setVentas(String ventas) {
        this.ventas = ventas;
    }

    public String getDevolucionVentas() {
        return devolucionVentas;
    }

    public void setDevolucionVentas(String devolucionVentas) {
        this.devolucionVentas = devolucionVentas;
    }

    public String getDescuentoVentas() {
        return descuentoVentas;
    }

    public void setDescuentoVentas(String descuentoVentas) {
        this.descuentoVentas = descuentoVentas;
    }

    @XmlTransient
    public List<InvBodegaArt> getInvBodegaArtList() {
        return invBodegaArtList;
    }

    public void setInvBodegaArtList(List<InvBodegaArt> invBodegaArtList) {
        this.invBodegaArtList = invBodegaArtList;
    }

    public InvBodega getInvBodega() {
        return invBodega;
    }

    public void setInvBodega(InvBodega invBodega) {
        this.invBodega = invBodega;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invInventarioPK != null ? invInventarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvInventario)) {
            return false;
        }
        InvInventario other = (InvInventario) object;
        if ((this.invInventarioPK == null && other.invInventarioPK != null) || (this.invInventarioPK != null && !this.invInventarioPK.equals(other.invInventarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvInventario[ invInventarioPK=" + invInventarioPK + " ]";
    }
    
}
