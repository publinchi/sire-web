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
public class CustomerBean {

    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;

    public String getNombre() {
        nombre = "Victor";
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        apellido = "Romero";
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        direccion = "Eloy Alfaro y Granados";
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        telefono = "022805656";
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
