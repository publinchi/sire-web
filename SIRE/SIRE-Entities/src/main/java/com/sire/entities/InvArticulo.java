/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "INV_ARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvArticulo.findAll", query = "SELECT i FROM InvArticulo i"),
    @NamedQuery(name = "InvArticulo.findByCodEmpresa", query = "SELECT i FROM InvArticulo i WHERE i.invArticuloPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvArticulo.findByCodArticulo", query = "SELECT i FROM InvArticulo i WHERE i.invArticuloPK.codArticulo = :codArticulo"),
    @NamedQuery(name = "InvArticulo.findByCodProveedor", query = "SELECT i FROM InvArticulo i WHERE i.codProveedor = :codProveedor"),
    @NamedQuery(name = "InvArticulo.findByCodigoFabrica", query = "SELECT i FROM InvArticulo i WHERE i.codigoFabrica = :codigoFabrica"),
    @NamedQuery(name = "InvArticulo.findByNombreArticulo", query = "SELECT i FROM InvArticulo i WHERE i.nombreArticulo like :nombreArticulo"),
    @NamedQuery(name = "InvArticulo.findByUnidadCon", query = "SELECT i FROM InvArticulo i WHERE i.unidadCon = :unidadCon"),
    @NamedQuery(name = "InvArticulo.findByPagaIva", query = "SELECT i FROM InvArticulo i WHERE i.pagaIva = :pagaIva"),
    @NamedQuery(name = "InvArticulo.findByUnidadIng", query = "SELECT i FROM InvArticulo i WHERE i.unidadIng = :unidadIng"),
    @NamedQuery(name = "InvArticulo.findByUnidadEgr", query = "SELECT i FROM InvArticulo i WHERE i.unidadEgr = :unidadEgr"),
    @NamedQuery(name = "InvArticulo.findByCodigoBarras", query = "SELECT i FROM InvArticulo i WHERE i.codigoBarras = :codigoBarras"),
    @NamedQuery(name = "InvArticulo.findByCodIva", query = "SELECT i FROM InvArticulo i WHERE i.codIva = :codIva"),
    @NamedQuery(name = "InvArticulo.findByPesoArticulo", query = "SELECT i FROM InvArticulo i WHERE i.pesoArticulo = :pesoArticulo"),
    @NamedQuery(name = "InvArticulo.findByVolumenArticulo", query = "SELECT i FROM InvArticulo i WHERE i.volumenArticulo = :volumenArticulo"),
    @NamedQuery(name = "InvArticulo.findByCostoPromedio", query = "SELECT i FROM InvArticulo i WHERE i.costoPromedio = :costoPromedio"),
    @NamedQuery(name = "InvArticulo.findByFechaCreacion", query = "SELECT i FROM InvArticulo i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "InvArticulo.findByCostoUltimaCompra", query = "SELECT i FROM InvArticulo i WHERE i.costoUltimaCompra = :costoUltimaCompra"),
    @NamedQuery(name = "InvArticulo.findByFechaUltimaCompra", query = "SELECT i FROM InvArticulo i WHERE i.fechaUltimaCompra = :fechaUltimaCompra"),
    @NamedQuery(name = "InvArticulo.findByEstado", query = "SELECT i FROM InvArticulo i WHERE i.estado = :estado"),
    @NamedQuery(name = "InvArticulo.findByFechaEstado", query = "SELECT i FROM InvArticulo i WHERE i.fechaEstado = :fechaEstado"),
    @NamedQuery(name = "InvArticulo.findByDescArticulo", query = "SELECT i FROM InvArticulo i WHERE i.descArticulo = :descArticulo"),
    @NamedQuery(name = "InvArticulo.findByPorcentajeUtilidad", query = "SELECT i FROM InvArticulo i WHERE i.porcentajeUtilidad = :porcentajeUtilidad"),
    @NamedQuery(name = "InvArticulo.findByStockMin", query = "SELECT i FROM InvArticulo i WHERE i.stockMin = :stockMin"),
    @NamedQuery(name = "InvArticulo.findByStockMax", query = "SELECT i FROM InvArticulo i WHERE i.stockMax = :stockMax"),
    @NamedQuery(name = "InvArticulo.findByPorcentajeUtilidad2", query = "SELECT i FROM InvArticulo i WHERE i.porcentajeUtilidad2 = :porcentajeUtilidad2"),
    @NamedQuery(name = "InvArticulo.findByPorcentajeUtilidad3", query = "SELECT i FROM InvArticulo i WHERE i.porcentajeUtilidad3 = :porcentajeUtilidad3"),
    @NamedQuery(name = "InvArticulo.findByTipoArticulo", query = "SELECT i FROM InvArticulo i WHERE i.tipoArticulo = :tipoArticulo"),
    @NamedQuery(name = "InvArticulo.findByNombreArticuloEstado", query = "SELECT i FROM InvArticulo i WHERE i.nombreArticulo like :nombreArticulo AND i.estado = :estado ORDER BY i.nombreArticulo"),
    @NamedQuery(name = "InvArticulo.findParaVenta", query = "SELECT a, c, SUM(b.existencia) FROM InvArticulo a, InvBodegaArt b, FacCatalogoPrecioD c WHERE a.invArticuloPK.codEmpresa = :codEmpresa AND a.invArticuloPK.codEmpresa = b.invBodegaArtPK.codEmpresa AND a.invArticuloPK.codArticulo = b.invBodegaArtPK.codArticulo AND b.invBodegaArtPK.codInventario = '01' AND a.invArticuloPK.codEmpresa = c.facCatalogoPrecioDPK.codEmpresa AND a.invArticuloPK.codArticulo = c.facCatalogoPrecioDPK.codArticulo AND c.facCatalogoPrecioDPK.codCatalogo = '01' AND a.estado = :estado AND a.nombreArticulo like :nombreArticulo GROUP BY a, c ORDER BY a.nombreArticulo"),
    @NamedQuery(name = "InvArticulo.findByArticuloEmpresa", query = "SELECT i FROM InvArticulo i WHERE i.invArticuloPK.codArticulo = :codArticulo AND i.invArticuloPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "InvArticulo.findByCodigo", query = "SELECT a, c, SUM(b.existencia) FROM InvArticulo a, InvBodegaArt b, FacCatalogoPrecioD c WHERE a.invArticuloPK.codEmpresa = :codEmpresa AND a.invArticuloPK.codEmpresa = b.invBodegaArtPK.codEmpresa AND a.invArticuloPK.codArticulo = b.invBodegaArtPK.codArticulo AND a.invArticuloPK.codArticulo = :codArticulo AND b.invBodegaArtPK.codInventario = '01' AND a.invArticuloPK.codEmpresa = c.facCatalogoPrecioDPK.codEmpresa AND a.invArticuloPK.codArticulo = c.facCatalogoPrecioDPK.codArticulo AND c.facCatalogoPrecioDPK.codCatalogo = '01' AND a.estado = :estado GROUP BY a, c ORDER BY a.nombreArticulo")})
public class InvArticulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InvArticuloPK invArticuloPK;
    @Column(name = "COD_PROVEEDOR")
    private Integer codProveedor;
    @Column(name = "CODIGO_FABRICA")
    private String codigoFabrica;
    @Basic(optional = false)
    @Column(name = "NOMBRE_ARTICULO")
    private String nombreArticulo;
    @Column(name = "UNIDAD_CON")
    private String unidadCon;
    @Basic(optional = false)
    @Column(name = "PAGA_IVA")
    private String pagaIva;
    @Basic(optional = false)
    @Column(name = "UNIDAD_ING")
    private String unidadIng;
    @Basic(optional = false)
    @Column(name = "UNIDAD_EGR")
    private String unidadEgr;
    @Column(name = "CODIGO_BARRAS")
    private String codigoBarras;
    @Column(name = "COD_IVA")
    private String codIva;
    @Basic(optional = false)
    @Column(name = "PESO_ARTICULO")
    private BigInteger pesoArticulo;
    @Basic(optional = false)
    @Column(name = "VOLUMEN_ARTICULO")
    private BigInteger volumenArticulo;
    @Basic(optional = false)
    @Column(name = "COSTO_PROMEDIO")
    private BigInteger costoPromedio;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "COSTO_ULTIMA_COMPRA")
    private BigInteger costoUltimaCompra;
    @Column(name = "FECHA_ULTIMA_COMPRA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaCompra;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @Column(name = "DESC_ARTICULO")
    private String descArticulo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PORCENTAJE_UTILIDAD")
    private BigDecimal porcentajeUtilidad;
    @Column(name = "STOCK_MIN")
    private BigDecimal stockMin;
    @Column(name = "STOCK_MAX")
    private BigDecimal stockMax;
    @Column(name = "PORCENTAJE_UTILIDAD_2")
    private BigDecimal porcentajeUtilidad2;
    @Column(name = "PORCENTAJE_UTILIDAD_3")
    private BigDecimal porcentajeUtilidad3;
    @Column(name = "TIPO_ARTICULO")
    private String tipoArticulo;
    @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private GnrEmpresa gnrEmpresa;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_GRUPO1", referencedColumnName = "COD_GRUPO1"),
        @JoinColumn(name = "COD_GRUPO2", referencedColumnName = "COD_GRUPO2"),
        @JoinColumn(name = "COD_GRUPO3", referencedColumnName = "COD_GRUPO3")})
    @ManyToOne(optional = false)
    private InvGrupo3 invGrupo3;
    @JoinColumns({
        @JoinColumn(name = "COD_EMPRESA", referencedColumnName = "COD_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "COD_MARCA", referencedColumnName = "COD_MARCA")})
    @ManyToOne(optional = false)
    private InvMarcas invMarcas;
    @JoinColumn(name = "COD_UNIDAD", referencedColumnName = "COD_UNIDAD")
    @ManyToOne(optional = false)
    private InvUnidadMedida codUnidad;

    // Inicio Transientes
    @Getter
    @Setter
    @Transient
    private Double precio, totalPlusIVA;

    @Getter
    @Setter
    @Transient
    private String unidad;

    @Getter
    @Setter
    @Transient
    private BigDecimal existencia, descuento, iva, auxPrecio;
    // Fin Transientes

    public InvArticulo() {
    }

    public InvArticulo(InvArticuloPK invArticuloPK) {
        this.invArticuloPK = invArticuloPK;
    }

    public InvArticulo(InvArticuloPK invArticuloPK, String nombreArticulo, String pagaIva, String unidadIng, String unidadEgr, BigInteger pesoArticulo, BigInteger volumenArticulo, BigInteger costoPromedio, Date fechaCreacion, BigInteger costoUltimaCompra) {
        this.invArticuloPK = invArticuloPK;
        this.nombreArticulo = nombreArticulo;
        this.pagaIva = pagaIva;
        this.unidadIng = unidadIng;
        this.unidadEgr = unidadEgr;
        this.pesoArticulo = pesoArticulo;
        this.volumenArticulo = volumenArticulo;
        this.costoPromedio = costoPromedio;
        this.fechaCreacion = fechaCreacion;
        this.costoUltimaCompra = costoUltimaCompra;
    }

    public InvArticulo(String codEmpresa, int codArticulo) {
        this.invArticuloPK = new InvArticuloPK(codEmpresa, codArticulo);
    }

    public InvArticuloPK getInvArticuloPK() {
        return invArticuloPK;
    }

    public void setInvArticuloPK(InvArticuloPK invArticuloPK) {
        this.invArticuloPK = invArticuloPK;
    }

    public Integer getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(Integer codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getCodigoFabrica() {
        return codigoFabrica;
    }

    public void setCodigoFabrica(String codigoFabrica) {
        this.codigoFabrica = codigoFabrica;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getUnidadCon() {
        return unidadCon;
    }

    public void setUnidadCon(String unidadCon) {
        this.unidadCon = unidadCon;
    }

    public String getPagaIva() {
        return pagaIva;
    }

    public void setPagaIva(String pagaIva) {
        this.pagaIva = pagaIva;
    }

    public String getUnidadIng() {
        return unidadIng;
    }

    public void setUnidadIng(String unidadIng) {
        this.unidadIng = unidadIng;
    }

    public String getUnidadEgr() {
        return unidadEgr;
    }

    public void setUnidadEgr(String unidadEgr) {
        this.unidadEgr = unidadEgr;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodIva() {
        return codIva;
    }

    public void setCodIva(String codIva) {
        this.codIva = codIva;
    }

    public BigInteger getPesoArticulo() {
        return pesoArticulo;
    }

    public void setPesoArticulo(BigInteger pesoArticulo) {
        this.pesoArticulo = pesoArticulo;
    }

    public BigInteger getVolumenArticulo() {
        return volumenArticulo;
    }

    public void setVolumenArticulo(BigInteger volumenArticulo) {
        this.volumenArticulo = volumenArticulo;
    }

    public BigInteger getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(BigInteger costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigInteger getCostoUltimaCompra() {
        return costoUltimaCompra;
    }

    public void setCostoUltimaCompra(BigInteger costoUltimaCompra) {
        this.costoUltimaCompra = costoUltimaCompra;
    }

    public Date getFechaUltimaCompra() {
        return fechaUltimaCompra;
    }

    public void setFechaUltimaCompra(Date fechaUltimaCompra) {
        this.fechaUltimaCompra = fechaUltimaCompra;
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

    public String getDescArticulo() {
        return descArticulo;
    }

    public void setDescArticulo(String descArticulo) {
        this.descArticulo = descArticulo;
    }

    public BigDecimal getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(BigDecimal porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

    public BigDecimal getStockMin() {
        return stockMin;
    }

    public void setStockMin(BigDecimal stockMin) {
        this.stockMin = stockMin;
    }

    public BigDecimal getStockMax() {
        return stockMax;
    }

    public void setStockMax(BigDecimal stockMax) {
        this.stockMax = stockMax;
    }

    public BigDecimal getPorcentajeUtilidad2() {
        return porcentajeUtilidad2;
    }

    public void setPorcentajeUtilidad2(BigDecimal porcentajeUtilidad2) {
        this.porcentajeUtilidad2 = porcentajeUtilidad2;
    }

    public BigDecimal getPorcentajeUtilidad3() {
        return porcentajeUtilidad3;
    }

    public void setPorcentajeUtilidad3(BigDecimal porcentajeUtilidad3) {
        this.porcentajeUtilidad3 = porcentajeUtilidad3;
    }

    public String getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(String tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public GnrEmpresa getGnrEmpresa() {
        return gnrEmpresa;
    }

    public void setGnrEmpresa(GnrEmpresa gnrEmpresa) {
        this.gnrEmpresa = gnrEmpresa;
    }

    public InvGrupo3 getInvGrupo3() {
        return invGrupo3;
    }

    public void setInvGrupo3(InvGrupo3 invGrupo3) {
        this.invGrupo3 = invGrupo3;
    }

    public InvMarcas getInvMarcas() {
        return invMarcas;
    }

    public void setInvMarcas(InvMarcas invMarcas) {
        this.invMarcas = invMarcas;
    }

    public InvUnidadMedida getCodUnidad() {
        return codUnidad;
    }

    public void setCodUnidad(InvUnidadMedida codUnidad) {
        this.codUnidad = codUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invArticuloPK != null ? invArticuloPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvArticulo)) {
            return false;
        }
        InvArticulo other = (InvArticulo) object;
        if ((this.invArticuloPK == null && other.invArticuloPK != null) || (this.invArticuloPK != null && !this.invArticuloPK.equals(other.invArticuloPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.InvArticulo[ invArticuloPK=" + invArticuloPK + " ]";
    }

}
