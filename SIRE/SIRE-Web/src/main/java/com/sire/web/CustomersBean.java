/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.VCliente;
import com.sire.rs.client.VClienteFacadeREST;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author pestupinan
 */
@ManagedBean
@ApplicationScoped
public class CustomersBean {

    private List<VCliente> clientes;
    private final VClienteFacadeREST vClienteFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;

    public CustomersBean() {
        vClienteFacadeREST = new VClienteFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    @PostConstruct
    private void loadClientes() {
        List<VCliente> list = gson.fromJson(vClienteFacadeREST.findAll_JSON(String.class), new TypeToken<java.util.List<VCliente>>() {
        }.getType());
        cleanClientes();
        setClientes(list);
        System.out.println("# clientes: " + clientes.size());
    }

    private void cleanClientes() {
        if (clientes != null) {
            getClientes().clear();
        }
    }

    public List<VCliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<VCliente> clientes) {
        this.clientes = clientes;
    }

}
