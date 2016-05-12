/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author publio
 */
@Embeddable
public class CajFacturaEnviadaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @Column(name = "COD_PROYECTO")
    private int codProyecto;
    @Basic(optional = false)
    @Column(name = "COD_SUBPROYECTO")
    private int codSubproyecto;
    @Basic(optional = false)
    @Column(name = "COD_SUPERVISOR")
    private int codSupervisor;
    @Basic(optional = false)
    @Column(name = "RUC_CI_PROVEEDOR")
    private String rucCiProveedor;
    @Basic(optional = false)
    @Column(name = "NUM_DOCUMENTO")
    private String numDocumento;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SeqGen")
    @SequenceGenerator(name = "SeqGen", sequenceName = "SECUENCIAL_SEQUENCE", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "SECUENCIAL")
    private Integer secuencial;

    public CajFacturaEnviadaPK() {
    }

    public CajFacturaEnviadaPK(String codEmpresa, int codProyecto, int codSubproyecto, int codSupervisor, String rucCiProveedor, String numDocumento, Integer secuencial) {
        this.codEmpresa = codEmpresa;
        this.codProyecto = codProyecto;
        this.codSubproyecto = codSubproyecto;
        this.codSupervisor = codSupervisor;
        this.rucCiProveedor = rucCiProveedor;
        this.numDocumento = numDocumento;
        this.secuencial = secuencial;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(int codProyecto) {
        this.codProyecto = codProyecto;
    }

    public int getCodSubproyecto() {
        return codSubproyecto;
    }

    public void setCodSubproyecto(int codSubproyecto) {
        this.codSubproyecto = codSubproyecto;
    }

    public int getCodSupervisor() {
        return codSupervisor;
    }

    public void setCodSupervisor(int codSupervisor) {
        this.codSupervisor = codSupervisor;
    }

    public String getRucCiProveedor() {
        return rucCiProveedor;
    }

    public void setRucCiProveedor(String rucCiProveedor) {
        this.rucCiProveedor = rucCiProveedor;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        hash += (int) codProyecto;
        hash += (int) codSubproyecto;
        hash += (int) codSupervisor;
        hash += (rucCiProveedor != null ? rucCiProveedor.hashCode() : 0);
        hash += (numDocumento != null ? numDocumento.hashCode() : 0);
        hash += (int) secuencial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CajFacturaEnviadaPK)) {
            return false;
        }
        CajFacturaEnviadaPK other = (CajFacturaEnviadaPK) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        if (this.codProyecto != other.codProyecto) {
            return false;
        }
        if (this.codSubproyecto != other.codSubproyecto) {
            return false;
        }
        if (this.codSupervisor != other.codSupervisor) {
            return false;
        }
        if ((this.rucCiProveedor == null && other.rucCiProveedor != null) || (this.rucCiProveedor != null && !this.rucCiProveedor.equals(other.rucCiProveedor))) {
            return false;
        }
        if ((this.numDocumento == null && other.numDocumento != null) || (this.numDocumento != null && !this.numDocumento.equals(other.numDocumento))) {
            return false;
        }
        if (this.secuencial != other.secuencial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sire.entities.CajFacturaEnviadaPK[ codEmpresa=" + codEmpresa + ", codProyecto=" + codProyecto + ", codSubproyecto=" + codSubproyecto + ", codSupervisor=" + codSupervisor + ", rucCiProveedor=" + rucCiProveedor + ", numDocumento=" + numDocumento + ", secuencial=" + secuencial + " ]";
    }

}
