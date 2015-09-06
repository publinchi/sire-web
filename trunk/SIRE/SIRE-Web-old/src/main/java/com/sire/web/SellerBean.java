/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author pestupinan
 */
@ManagedBean
public class SellerBean {

    private String nombre;
    private String apellido;

    public String getNombre() {
        nombre = "Publio";
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        apellido = "Estupiñán";
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
