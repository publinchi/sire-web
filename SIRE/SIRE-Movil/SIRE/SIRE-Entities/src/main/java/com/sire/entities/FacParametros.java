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
@Table(name = "FAC_PARAMETROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacParametros.findAll", query = "SELECT f FROM FacParametros f"),
    @NamedQuery(name = "FacParametros.findByNombreUsuario", query = "SELECT f FROM FacParametros f WHERE f.facParametrosPK.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "FacParametros.findByCodEmpresa", query = "SELECT f FROM FacParametros f WHERE f.facParametrosPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "FacParametros.findByTipoFacturacion", query = "SELECT f FROM FacParametros f WHERE f.tipoFacturacion = :tipoFacturacion"),
    @NamedQuery(name = "FacParametros.findByCodPuntoVenta", query = "SELECT f FROM FacParametros f WHERE f.codPuntoVenta = :codPuntoVenta"),
    @NamedQuery(name = "FacParametros.findByTipoFactura", query = "SELECT f FROM FacParametros f WHERE f.tipoFactura = :tipoFactura"),
    @NamedQuery(name = "FacParametros.findBySecuencial", query = "SELECT f FROM FacParametros f WHERE f.secuencial = :secuencial"),
    @NamedQuery(name = "FacParametros.findByDefTipoFactura", query = "SELECT f FROM FacParametros f WHERE f.defTipoFactura = :defTipoFactura"),
    @NamedQuery(name = "FacParametros.findByNumComprobante", query = "SELECT f FROM FacParametros f WHERE f.numComprobante = :numComprobante"),
    @NamedQuery(name = "FacParametros.findByFechaFactura", query = "SELECT f FROM FacParametros f WHERE f.fechaFactura = :fechaFactura"),
    @NamedQuery(name = "FacParametros.findByFormaPago", query = "SELECT f FROM FacParametros f WHERE f.formaPago = :formaPago"),
    @NamedQuery(name = "FacParametros.findByDefFormaPago", query = "SELECT f FROM FacParametros f WHERE f.defFormaPago = :defFormaPago"),
    @NamedQuery(name = "FacParametros.findByCodigoCliente", query = "SELECT f FROM FacParametros f WHERE f.codigoCliente = :codigoCliente"),
    @NamedQuery(name = "FacParametros.findByDefCodigoCliente", query = "SELECT f FROM FacParametros f WHERE f.defCodigoCliente = :defCodigoCliente"),
    @NamedQuery(name = "FacParametros.findByTipoMoneda", query = "SELECT f FROM FacParametros f WHERE f.tipoMoneda = :tipoMoneda"),
    @NamedQuery(name = "FacParametros.findByDefTipoMoneda", query = "SELECT f FROM FacParametros f WHERE f.defTipoMoneda = :defTipoMoneda"),
    @NamedQuery(name = "FacParametros.findByDesplegarPend", query = "SELECT f FROM FacParametros f WHERE f.desplegarPend = :desplegarPend"),
    @NamedQuery(name = "FacParametros.findByCodVendedor", query = "SELECT f FROM FacParametros f WHERE f.codVendedor = :codVendedor"),
    @NamedQuery(name = "FacParametros.findByDefCodVendedor", query = "SELECT f FROM FacParametros f WHERE f.defCodVendedor = :defCodVendedor"),
    @NamedQuery(name = "FacParametros.findByComisionVendedor", query = "SELECT f FROM FacParametros f WHERE f.comisionVendedor = :comisionVendedor"),
    @NamedQuery(name = "FacParametros.findByDetalleArticulo", query = "SELECT f FROM FacParametros f WHERE f.detalleArticulo = :detalleArticulo"),
    @NamedQuery(name = "FacParametros.findByNroItems", query = "SELECT f FROM FacParametros f WHERE f.nroItems = :nroItems"),
    @NamedQuery(name = "FacParametros.findByEntPend", query = "SELECT f FROM FacParametros f WHERE f.entPend = :entPend"),
    @NamedQuery(name = "FacParametros.findByBodega", query = "SELECT f FROM FacParametros f WHERE f.bodega = :bodega"),
    @NamedQuery(name = "FacParametros.findByDefBodega", query = "SELECT f FROM FacParametros f WHERE f.defBodega = :defBodega"),
    @NamedQuery(name = "FacParametros.findByCantidad", query = "SELECT f FROM FacParametros f WHERE f.cantidad = :cantidad"),
    @NamedQuery(name = "FacParametros.findByInventario", query = "SELECT f FROM FacParametros f WHERE f.inventario = :inventario"),
    @NamedQuery(name = "FacParametros.findByDefInventario", query = "SELECT f FROM FacParametros f WHERE f.defInventario = :defInventario"),
    @NamedQuery(name = "FacParametros.findBySerie", query = "SELECT f FROM FacParametros f WHERE f.serie = :serie"),
    @NamedQuery(name = "FacParametros.findByUnidadMedida", query = "SELECT f FROM FacParametros f WHERE f.unidadMedida = :unidadMedida"),
    @NamedQuery(name = "FacParametros.findByListaPrecio", query = "SELECT f FROM FacParametros f WHERE f.listaPrecio = :listaPrecio"),
    @NamedQuery(name = "FacParametros.findByPrecioVenta", query = "SELECT f FROM FacParametros f WHERE f.precioVenta = :precioVenta"),
    @NamedQuery(name = "FacParametros.findByDefPrecioVenta", query = "SELECT f FROM FacParametros f WHERE f.defPrecioVenta = :defPrecioVenta"),
    @NamedQuery(name = "FacParametros.findByDescVolumen", query = "SELECT f FROM FacParametros f WHERE f.descVolumen = :descVolumen"),
    @NamedQuery(name = "FacParametros.findByExistNeg", query = "SELECT f FROM FacParametros f WHERE f.existNeg = :existNeg"),
    @NamedQuery(name = "FacParametros.findByDescPago", query = "SELECT f FROM FacParametros f WHERE f.descPago = :descPago"),
    @NamedQuery(name = "FacParametros.findByFormaPrecio", query = "SELECT f FROM FacParametros f WHERE f.formaPrecio = :formaPrecio"),
    @NamedQuery(name = "FacParametros.findByFechaVencimiento", query = "SELECT f FROM FacParametros f WHERE f.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "FacParametros.findByDiasPlazo", query = "SELECT f FROM FacParametros f WHERE f.diasPlazo = :diasPlazo"),
    @NamedQuery(name = "FacParametros.findByDefDiasPlazo", query = "SELECT f FROM FacParametros f WHERE f.defDiasPlazo = :defDiasPlazo"),
    @NamedQuery(name = "FacParametros.findByNumeroLetras", query = "SELECT f FROM FacParametros f WHERE f.numeroLetras = :numeroLetras"),
    @NamedQuery(name = "FacParametros.findByDefNumeroLetras", query = "SELECT f FROM FacParametros f WHERE f.defNumeroLetras = :defNumeroLetras"),
    @NamedQuery(name = "FacParametros.findByDescuentos", query = "SELECT f FROM FacParametros f WHERE f.descuentos = :descuentos"),
    @NamedQuery(name = "FacParametros.findByRecargos", query = "SELECT f FROM FacParametros f WHERE f.recargos = :recargos"),
    @NamedQuery(name = "FacParametros.findByIvaFactura", query = "SELECT f FROM FacParametros f WHERE f.ivaFactura = :ivaFactura"),
    @NamedQuery(name = "FacParametros.findByIvaNotaVenta", query = "SELECT f FROM FacParametros f WHERE f.ivaNotaVenta = :ivaNotaVenta"),
    @NamedQuery(name = "FacParametros.findByRetencion", query = "SELECT f FROM FacParametros f WHERE f.retencion = :retencion"),
    @NamedQuery(name = "FacParametros.findByRetencionIva", query = "SELECT f FROM FacParametros f WHERE f.retencionIva = :retencionIva"),
    @NamedQuery(name = "FacParametros.findByEfectivo", query = "SELECT f FROM FacParametros f WHERE f.efectivo = :efectivo"),
    @NamedQuery(name = "FacParametros.findByCheques", query = "SELECT f FROM FacParametros f WHERE f.cheques = :cheques"),
    @NamedQuery(name = "FacParametros.findByTarjetaCredito", query = "SELECT f FROM FacParametros f WHERE f.tarjetaCredito = :tarjetaCredito"),
    @NamedQuery(name = "FacParametros.findByDeposito", query = "SELECT f FROM FacParametros f WHERE f.deposito = :deposito"),
    @NamedQuery(name = "FacParametros.findByDetalle", query = "SELECT f FROM FacParametros f WHERE f.detalle = :detalle"),
    @NamedQuery(name = "FacParametros.findByOtros", query = "SELECT f FROM FacParametros f WHERE f.otros = :otros"),
    @NamedQuery(name = "FacParametros.findByControlaCambio", query = "SELECT f FROM FacParametros f WHERE f.controlaCambio = :controlaCambio"),
    @NamedQuery(name = "FacParametros.findByImprimirFactura", query = "SELECT f FROM FacParametros f WHERE f.imprimirFactura = :imprimirFactura"),
    @NamedQuery(name = "FacParametros.findByMenuGrabacion", query = "SELECT f FROM FacParametros f WHERE f.menuGrabacion = :menuGrabacion"),
    @NamedQuery(name = "FacParametros.findByCodArticuloComodin", query = "SELECT f FROM FacParametros f WHERE f.codArticuloComodin = :codArticuloComodin"),
    @NamedQuery(name = "FacParametros.findByDescPromocion", query = "SELECT f FROM FacParametros f WHERE f.descPromocion = :descPromocion")})
public class FacParametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacParametrosPK facParametrosPK;
    @Column(name = "TIPO_FACTURACION")
    private String tipoFacturacion;
    @Column(name = "COD_PUNTO_VENTA")
    private Integer codPuntoVenta;
    @Column(name = "TIPO_FACTURA")
    private String tipoFactura;
    @Column(name = "SECUENCIAL")
    private Integer secuencial;
    @Column(name = "DEF_TIPO_FACTURA")
    private String defTipoFactura;
    @Column(name = "NUM_COMPROBANTE")
    private String numComprobante;
    @Column(name = "FECHA_FACTURA")
    private String fechaFactura;
    @Column(name = "FORMA_PAGO")
    private String formaPago;
    @Column(name = "DEF_FORMA_PAGO")
    private String defFormaPago;
    @Column(name = "CODIGO_CLIENTE")
    private String codigoCliente;
    @Column(name = "DEF_CODIGO_CLIENTE")
    private Long defCodigoCliente;
    @Column(name = "TIPO_MONEDA")
    private String tipoMoneda;
    @Column(name = "DEF_TIPO_MONEDA")
    private String defTipoMoneda;
    @Column(name = "DESPLEGAR_PEND")
    private String desplegarPend;
    @Column(name = "COD_VENDEDOR")
    private String codVendedor;
    @Column(name = "DEF_COD_VENDEDOR")
    private Integer defCodVendedor;
    @Column(name = "COMISION_VENDEDOR")
    private String comisionVendedor;
    @Column(name = "DETALLE_ARTICULO")
    private String detalleArticulo;
    @Column(name = "NRO_ITEMS")
    private Short nroItems;
    @Column(name = "ENT_PEND")
    private String entPend;
    @Column(name = "BODEGA")
    private String bodega;
    @Column(name = "DEF_BODEGA")
    private String defBodega;
    @Column(name = "CANTIDAD")
    private String cantidad;
    @Column(name = "INVENTARIO")
    private String inventario;
    @Column(name = "DEF_INVENTARIO")
    private String defInventario;
    @Column(name = "SERIE")
    private String serie;
    @Column(name = "UNIDAD_MEDIDA")
    private String unidadMedida;
    @Column(name = "LISTA_PRECIO")
    private String listaPrecio;
    @Column(name = "PRECIO_VENTA")
    private String precioVenta;
    @Column(name = "DEF_PRECIO_VENTA")
    private Integer defPrecioVenta;
    @Column(name = "DESC_VOLUMEN")
    private String descVolumen;
    @Column(name = "EXIST_NEG")
    private String existNeg;
    @Column(name = "DESC_PAGO")
    private String descPago;
    @Column(name = "FORMA_PRECIO")
    private String formaPrecio;
    @Column(name = "FECHA_VENCIMIENTO")
    private String fechaVencimiento;
    @Column(name = "DIAS_PLAZO")
    private String diasPlazo;
    @Column(name = "DEF_DIAS_PLAZO")
    private Short defDiasPlazo;
    @Column(name = "NUMERO_LETRAS")
    private String numeroLetras;
    @Column(name = "DEF_NUMERO_LETRAS")
    private Integer defNumeroLetras;
    @Column(name = "DESCUENTOS")
    private String descuentos;
    @Column(name = "RECARGOS")
    private String recargos;
    @Column(name = "IVA_FACTURA")
    private String ivaFactura;
    @Column(name = "IVA_NOTA_VENTA")
    private String ivaNotaVenta;
    @Column(name = "RETENCION")
    private String retencion;
    @Column(name = "RETENCION_IVA")
    private String retencionIva;
    @Column(name = "EFECTIVO")
    private String efectivo;
    @Column(name = "CHEQUES")
    private String cheques;
    @Column(name = "TARJETA_CREDITO")
    private String tarjetaCredito;
    @Column(name = "DEPOSITO")
    private String deposito;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "OTROS")
    private String otros;
    @Column(name = "CONTROLA_CAMBIO")
    private String controlaCambio;
    @Column(name = "IMPRIMIR_FACTURA")
    private String imprimirFactura;
    @Column(name = "MENU_GRABACION")
    private String menuGrabacion;
    @Column(name = "COD_ARTICULO_COMODIN")
    private Integer codArticuloComodin;
    @Column(name = "DESC_PROMOCION")
    private String descPromocion;
    @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrUsuarios gnrUsuarios;

    public FacParametros() {
    }

    public FacParametros(FacParametrosPK facParametrosPK) {
        this.facParametrosPK = facParametrosPK;
    }

    public FacParametros(String nombreUsuario, String codEmpresa) {
        this.facParametrosPK = new FacParametrosPK(nombreUsuario, codEmpresa);
    }

    public FacParametrosPK getFacParametrosPK() {
        return facParametrosPK;
    }

    public void setFacParametrosPK(FacParametrosPK facParametrosPK) {
        this.facParametrosPK = facParametrosPK;
    }

    public String getTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(String tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public Integer getCodPuntoVenta() {
        return codPuntoVenta;
    }

    public void setCodPuntoVenta(Integer codPuntoVenta) {
        this.codPuntoVenta = codPuntoVenta;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public String getDefTipoFactura() {
        return defTipoFactura;
    }

    public void setDefTipoFactura(String defTipoFactura) {
        this.defTipoFactura = defTipoFactura;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getDefFormaPago() {
        return defFormaPago;
    }

    public void setDefFormaPago(String defFormaPago) {
        this.defFormaPago = defFormaPago;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Long getDefCodigoCliente() {
        return defCodigoCliente;
    }

    public void setDefCodigoCliente(Long defCodigoCliente) {
        this.defCodigoCliente = defCodigoCliente;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getDefTipoMoneda() {
        return defTipoMoneda;
    }

    public void setDefTipoMoneda(String defTipoMoneda) {
        this.defTipoMoneda = defTipoMoneda;
    }

    public String getDesplegarPend() {
        return desplegarPend;
    }

    public void setDesplegarPend(String desplegarPend) {
        this.desplegarPend = desplegarPend;
    }

    public String getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(String codVendedor) {
        this.codVendedor = codVendedor;
    }

    public Integer getDefCodVendedor() {
        return defCodVendedor;
    }

    public void setDefCodVendedor(Integer defCodVendedor) {
        this.defCodVendedor = defCodVendedor;
    }

    public String getComisionVendedor() {
        return comisionVendedor;
    }

    public void setComisionVendedor(String comisionVendedor) {
        this.comisionVendedor = comisionVendedor;
    }

    public String getDetalleArticulo() {
        return detalleArticulo;
    }

    public void setDetalleArticulo(String detalleArticulo) {
        this.detalleArticulo = detalleArticulo;
    }

    public Short getNroItems() {
        return nroItems;
    }

    public void setNroItems(Short nroItems) {
        this.nroItems = nroItems;
    }

    public String getEntPend() {
        return entPend;
    }

    public void setEntPend(String entPend) {
        this.entPend = entPend;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getDefBodega() {
        return defBodega;
    }

    public void setDefBodega(String defBodega) {
        this.defBodega = defBodega;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getInventario() {
        return inventario;
    }

    public void setInventario(String inventario) {
        this.inventario = inventario;
    }

    public String getDefInventario() {
        return defInventario;
    }

    public void setDefInventario(String defInventario) {
        this.defInventario = defInventario;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(String listaPrecio) {
        this.listaPrecio = listaPrecio;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getDefPrecioVenta() {
        return defPrecioVenta;
    }

    public void setDefPrecioVenta(Integer defPrecioVenta) {
        this.defPrecioVenta = defPrecioVenta;
    }

    public String getDescVolumen() {
        return descVolumen;
    }

    public void setDescVolumen(String descVolumen) {
        this.descVolumen = descVolumen;
    }

    public String getExistNeg() {
        return existNeg;
    }

    public void setExistNeg(String existNeg) {
        this.existNeg = existNeg;
    }

    public String getDescPago() {
        return descPago;
    }

    public void setDescPago(String descPago) {
        this.descPago = descPago;
    }

    public String getFormaPrecio() {
        return formaPrecio;
    }

    public void setFormaPrecio(String formaPrecio) {
        this.formaPrecio = formaPrecio;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDiasPlazo() {
        return diasPlazo;
    }

    public void setDiasPlazo(String diasPlazo) {
        this.diasPlazo = diasPlazo;
    }

    public Short getDefDiasPlazo() {
        return defDiasPlazo;
    }

    public void setDefDiasPlazo(Short defDiasPlazo) {
        this.defDiasPlazo = defDiasPlazo;
    }

    public String getNumeroLetras() {
        return numeroLetras;
    }

    public void setNumeroLetras(String numeroLetras) {
        this.numeroLetras = numeroLetras;
    }

    public Integer getDefNumeroLetras() {
        return defNumeroLetras;
    }

    public void setDefNumeroLetras(Integer defNumeroLetras) {
        this.defNumeroLetras = defNumeroLetras;
    }

    public String getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(String descuentos) {
        this.descuentos = descuentos;
    }

    public String getRecargos() {
        return recargos;
    }

    public void setRecargos(String recargos) {
        this.recargos = recargos;
    }

    public String getIvaFactura() {
        return ivaFactura;
    }

    public void setIvaFactura(String ivaFactura) {
        this.ivaFactura = ivaFactura;
    }

    public String getIvaNotaVenta() {
        return ivaNotaVenta;
    }

    public void setIvaNotaVenta(String ivaNotaVenta) {
        this.ivaNotaVenta = ivaNotaVenta;
    }

    public String getRetencion() {
        return retencion;
    }

    public void setRetencion(String retencion) {
        this.retencion = retencion;
    }

    public String getRetencionIva() {
        return retencionIva;
    }

    public void setRetencionIva(String retencionIva) {
        this.retencionIva = retencionIva;
    }

    public String getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(String efectivo) {
        this.efectivo = efectivo;
    }

    public String getCheques() {
        return cheques;
    }

    public void setCheques(String cheques) {
        this.cheques = cheques;
    }

    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    public void setTarjetaCredito(String tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    public String getDeposito() {
        return deposito;
    }

    public void setDeposito(String deposito) {
        this.deposito = deposito;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getControlaCambio() {
        return controlaCambio;
    }

    public void setControlaCambio(String controlaCambio) {
        this.controlaCambio = controlaCambio;
    }

    public String getImprimirFactura() {
        return imprimirFactura;
    }

    public void setImprimirFactura(String imprimirFactura) {
        this.imprimirFactura = imprimirFactura;
    }

    public String getMenuGrabacion() {
        return menuGrabacion;
    }

    public void setMenuGrabacion(String menuGrabacion) {
        this.menuGrabacion = menuGrabacion;
    }

    public Integer getCodArticuloComodin() {
        return codArticuloComodin;
    }

    public void setCodArticuloComodin(Integer codArticuloComodin) {
        this.codArticuloComodin = codArticuloComodin;
    }

    public String getDescPromocion() {
        return descPromocion;
    }

    public void setDescPromocion(String descPromocion) {
        this.descPromocion = descPromocion;
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
        hash += (facParametrosPK != null ? facParametrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacParametros)) {
            return false;
        }
        FacParametros other = (FacParametros) object;
        if ((this.facParametrosPK == null && other.facParametrosPK != null) || (this.facParametrosPK != null && !this.facParametrosPK.equals(other.facParametrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.FacParametros[ facParametrosPK=" + facParametrosPK + " ]";
    }
    
}
