/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pestupinan
 */
@Entity
@Table(name = "V_VENDEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VVendedor.findAll", query = "SELECT v FROM VVendedor v")
    , @NamedQuery(name = "VVendedor.findByCodEmpresa", query = "SELECT v FROM VVendedor v WHERE v.codEmpresa = :codEmpresa")
    , @NamedQuery(name = "VVendedor.findByCodVendedor", query = "SELECT v FROM VVendedor v WHERE v.codVendedor = :codVendedor")
    , @NamedQuery(name = "VVendedor.findByNombresVendedor", query = "SELECT v FROM VVendedor v WHERE v.nombresVendedor = :nombresVendedor")
    , @NamedQuery(name = "VVendedor.findByDireccion", query = "SELECT v FROM VVendedor v WHERE v.direccion = :direccion")})
public class VVendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "COD_EMPRESA")
    private String codEmpresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_VENDEDOR")
    @Id
    private int codVendedor;
    @Size(max = 201)
    @Column(name = "NOMBRES_VENDEDOR")
    private String nombresVendedor;
    @Size(max = 60)
    @Column(name = "DIRECCION")
    private String direccion;

    public VVendedor() {
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public int getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
    }

    public String getNombresVendedor() {
        return nombresVendedor;
    }

    public void setNombresVendedor(String nombresVendedor) {
        this.nombresVendedor = nombresVendedor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
