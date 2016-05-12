/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author publio
 */
@Entity
@Table(name = "CAJ_FACTURA_ENVIADA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CajFacturaEnviada.findAll", query = "SELECT c FROM CajFacturaEnviada c"),
    @NamedQuery(name = "CajFacturaEnviada.findByCodEmpresa", query = "SELECT c FROM CajFacturaEnviada c WHERE c.cajFacturaEnviadaPK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CajFacturaEnviada.findByCodProyecto", query = "SELECT c FROM CajFacturaEnviada c WHERE c.cajFacturaEnviadaPK.codProyecto = :codProyecto"),
    @NamedQuery(name = "CajFacturaEnviada.findByCodSubproyecto", query = "SELECT c FROM CajFacturaEnviada c WHERE c.cajFacturaEnviadaPK.codSubproyecto = :codSubproyecto"),
    @NamedQuery(name = "CajFacturaEnviada.findByCodSupervisor", query = "SELECT c FROM CajFacturaEnviada c WHERE c.cajFacturaEnviadaPK.codSupervisor = :codSupervisor"),
    @NamedQuery(name = "CajFacturaEnviada.findByRucCiProveedor", query = "SELECT c FROM CajFacturaEnviada c WHERE c.cajFacturaEnviadaPK.rucCiProveedor = :rucCiProveedor"),
    @NamedQuery(name = "CajFacturaEnviada.findByNumDocumento", query = "SELECT c FROM CajFacturaEnviada c WHERE c.cajFacturaEnviadaPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "CajFacturaEnviada.findBySecuencial", query = "SELECT c FROM CajFacturaEnviada c WHERE c.cajFacturaEnviadaPK.secuencial = :secuencial"),
    @NamedQuery(name = "CajFacturaEnviada.findByNumLiquidacion", query = "SELECT c FROM CajFacturaEnviada c WHERE c.numLiquidacion = :numLiquidacion"),
    @NamedQuery(name = "CajFacturaEnviada.findByFechaDocumento", query = "SELECT c FROM CajFacturaEnviada c WHERE c.fechaDocumento = :fechaDocumento"),
    @NamedQuery(name = "CajFacturaEnviada.findByCodRubro", query = "SELECT c FROM CajFacturaEnviada c WHERE c.codRubro = :codRubro"),
    @NamedQuery(name = "CajFacturaEnviada.findByIdFoto", query = "SELECT c FROM CajFacturaEnviada c WHERE c.idFoto = :idFoto"),
    @NamedQuery(name = "CajFacturaEnviada.findByTotalConIva", query = "SELECT c FROM CajFacturaEnviada c WHERE c.totalConIva = :totalConIva"),
    @NamedQuery(name = "CajFacturaEnviada.findByTotalSinIva", query = "SELECT c FROM CajFacturaEnviada c WHERE c.totalSinIva = :totalSinIva"),
    @NamedQuery(name = "CajFacturaEnviada.findByIvaDocumento", query = "SELECT c FROM CajFacturaEnviada c WHERE c.ivaDocumento = :ivaDocumento"),
    @NamedQuery(name = "CajFacturaEnviada.findByTotalDocumento", query = "SELECT c FROM CajFacturaEnviada c WHERE c.totalDocumento = :totalDocumento"),
    @NamedQuery(name = "CajFacturaEnviada.findByLugarTransaccion", query = "SELECT c FROM CajFacturaEnviada c WHERE c.lugarTransaccion = :lugarTransaccion"),
    @NamedQuery(name = "CajFacturaEnviada.findByEstado", query = "SELECT c FROM CajFacturaEnviada c WHERE c.estado = :estado"),
    @NamedQuery(name = "CajFacturaEnviada.findByFechaEstado", query = "SELECT c FROM CajFacturaEnviada c WHERE c.fechaEstado = :fechaEstado")})
public class CajFacturaEnviada implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CajFacturaEnviadaPK cajFacturaEnviadaPK;
    @Column(name = "NUM_LIQUIDACION")
    private BigInteger numLiquidacion;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "COD_RUBRO")
    private Integer codRubro;
    @Column(name = "ID_FOTO")
    private String idFoto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL_CON_IVA")
    private Double totalConIva;
    @Column(name = "TOTAL_SIN_IVA")
    private Double totalSinIva;
    @Column(name = "IVA_DOCUMENTO")
    private Double ivaDocumento;
    @Column(name = "TOTAL_DOCUMENTO")
    private Double totalDocumento;
    @Column(name = "LUGAR_TRANSACCION")
    private String lugarTransaccion;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @JoinColumn(name = "NOMBRE_USUARIO", referencedColumnName = "NOMBRE_USUARIO")
    @ManyToOne
    private GnrUsuarios nombreUsuario;

    public CajFacturaEnviada() {
    }

    public CajFacturaEnviada(CajFacturaEnviadaPK cajFacturaEnviadaPK) {
        this.cajFacturaEnviadaPK = cajFacturaEnviadaPK;
    }

    public CajFacturaEnviada(String codEmpresa, int codProyecto, int codSubproyecto, int codSupervisor, String rucCiProveedor, String numDocumento, int secuencial) {
        this.cajFacturaEnviadaPK = new CajFacturaEnviadaPK(codEmpresa, codProyecto, codSubproyecto, codSupervisor, rucCiProveedor, numDocumento, secuencial);
    }

    public CajFacturaEnviadaPK getCajFacturaEnviadaPK() {
        return cajFacturaEnviadaPK;
    }

    public void setCajFacturaEnviadaPK(CajFacturaEnviadaPK cajFacturaEnviadaPK) {
        this.cajFacturaEnviadaPK = cajFacturaEnviadaPK;
    }

    public BigInteger getNumLiquidacion() {
        return numLiquidacion;
    }

    public void setNumLiquidacion(BigInteger numLiquidacion) {
        this.numLiquidacion = numLiquidacion;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public Integer getCodRubro() {
        return codRubro;
    }

    public void setCodRubro(Integer codRubro) {
        this.codRubro = codRubro;
    }

    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }

    public Double getTotalConIva() {
        return totalConIva;
    }

    public void setTotalConIva(Double totalConIva) {
        this.totalConIva = totalConIva;
    }

    public Double getTotalSinIva() {
        return totalSinIva;
    }

    public void setTotalSinIva(Double totalSinIva) {
        this.totalSinIva = totalSinIva;
    }

    public Double getIvaDocumento() {
        return ivaDocumento;
    }

    public void setIvaDocumento(Double ivaDocumento) {
        this.ivaDocumento = ivaDocumento;
    }

    public Double getTotalDocumento() {
        return totalDocumento;
    }

    public void setTotalDocumento(Double totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

    public String getLugarTransaccion() {
        return lugarTransaccion;
    }

    public void setLugarTransaccion(String lugarTransaccion) {
        this.lugarTransaccion = lugarTransaccion;
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

    public GnrUsuarios getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(GnrUsuarios nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cajFacturaEnviadaPK != null ? cajFacturaEnviadaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajFacturaEnviada)) {
            return false;
        }
        CajFacturaEnviada other = (CajFacturaEnviada) object;
        if ((this.cajFacturaEnviadaPK == null && other.cajFacturaEnviadaPK != null) || (this.cajFacturaEnviadaPK != null && !this.cajFacturaEnviadaPK.equals(other.cajFacturaEnviadaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CajFacturaEnviada[ cajFacturaEnviadaPK=" + cajFacturaEnviadaPK + " ]";
    }

}
