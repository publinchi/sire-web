/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.entities;

import ec.gob.sri.comprobantes.modelo.factura.Factura;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author pestupinan
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(propOrder = {"claveAcceso", "ruc", "comprobantes"})
public class Lote {

    @XmlAttribute
    protected String version;
    private Comprobante comprobantes;
//    private List<ComprobanteXml> comprobantes;
    private String claveAcceso;
    private String ruc;
    @XmlTransient
    private List<Factura> facturas;

    public Lote() {
        this.comprobantes = new Comprobante();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

//    public List<ComprobanteXml> getComprobantes() {
//        return this.comprobantes;
//    }
//
//    public void setComprobantes(List<ComprobanteXml> comprobantes) {
//        this.comprobantes = comprobantes;
//    }
    public Comprobante getComprobantes() {
        return this.comprobantes;
    }

    public void setComprobantes(Comprobante comprobantes) {
        this.comprobantes = comprobantes;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

}
