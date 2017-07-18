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
 * @author publio
 */
@Entity
@Table(name = "CXC_INFORME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CxcInforme.findAll", query = "SELECT c FROM CxcInforme c"),
    @NamedQuery(name = "CxcInforme.findByCodEmpresa", query = "SELECT c FROM CxcInforme c WHERE c.cxcInformePK.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "CxcInforme.findByCodInforme", query = "SELECT c FROM CxcInforme c WHERE c.cxcInformePK.codInforme = :codInforme"),
    @NamedQuery(name = "CxcInforme.findByNumInforme", query = "SELECT c FROM CxcInforme c WHERE c.cxcInformePK.numInforme = :numInforme"),
    @NamedQuery(name = "CxcInforme.findByFechaInforme", query = "SELECT c FROM CxcInforme c WHERE c.fechaInforme = :fechaInforme"),
    @NamedQuery(name = "CxcInforme.findByCodVendedor", query = "SELECT c FROM CxcInforme c WHERE c.codVendedor = :codVendedor"),
    @NamedQuery(name = "CxcInforme.findByEstado", query = "SELECT c FROM CxcInforme c WHERE c.estado = :estado"),
    @NamedQuery(name = "CxcInforme.findByFechaEstado", query = "SELECT c FROM CxcInforme c WHERE c.fechaEstado = :fechaEstado")})
public class CxcInforme implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxcInformePK cxcInformePK;
    @Column(name = "FECHA_INFORME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInforme;
    @Basic(optional = false)
    @Column(name = "COD_VENDEDOR")
    private BigInteger codVendedor;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ESTADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cxcInforme")
    private List<CxcAbonoC> cxcAbonoCList;

    public CxcInforme() {
    }

    public CxcInforme(CxcInformePK cxcInformePK) {
        this.cxcInformePK = cxcInformePK;
    }

    public CxcInforme(CxcInformePK cxcInformePK, BigInteger codVendedor) {
        this.cxcInformePK = cxcInformePK;
        this.codVendedor = codVendedor;
    }

    public CxcInforme(String codEmpresa, String codInforme, BigInteger numInforme) {
        this.cxcInformePK = new CxcInformePK(codEmpresa, codInforme, numInforme);
    }

    public CxcInformePK getCxcInformePK() {
        return cxcInformePK;
    }

    public void setCxcInformePK(CxcInformePK cxcInformePK) {
        this.cxcInformePK = cxcInformePK;
    }

    public Date getFechaInforme() {
        return fechaInforme;
    }

    public void setFechaInforme(Date fechaInforme) {
        this.fechaInforme = fechaInforme;
    }

    public BigInteger getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(BigInteger codVendedor) {
        this.codVendedor = codVendedor;
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
    public List<CxcAbonoC> getCxcAbonoCList() {
        return cxcAbonoCList;
    }

    public void setCxcAbonoCList(List<CxcAbonoC> cxcAbonoCList) {
        this.cxcAbonoCList = cxcAbonoCList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxcInformePK != null ? cxcInformePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxcInforme)) {
            return false;
        }
        CxcInforme other = (CxcInforme) object;
        if ((this.cxcInformePK == null && other.cxcInformePK != null) || (this.cxcInformePK != null && !this.cxcInformePK.equals(other.cxcInformePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CxcInforme[ cxcInformePK=" + cxcInformePK + " ]";
    }
    
}
