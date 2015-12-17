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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.ClientErrorException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "customers")
@SessionScoped
public class CustomersBean {

    @ManagedProperty("#{cliente}")
    private CustomerBean cliente;
    private List<VCliente> clientes;
    private final VClienteFacadeREST vClienteFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    private String input;

    public CustomersBean() {
        vClienteFacadeREST = new VClienteFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    @PostConstruct
    private void init() {
    }

    private void loadClientes() {
        List<VCliente> list = gson.fromJson(vClienteFacadeREST.findAll_JSON(String.class), new TypeToken<java.util.List<VCliente>>() {
        }.getType());
        cleanClientes();
        setClientes(list);
        System.out.println("# clientes: " + clientes.size());
    }

    public void findClientes() {
        String clientesString = null;
        try {
            clientesString = vClienteFacadeREST.findByApellidos(String.class, input);
            clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
            }.getType());
        } catch (ClientErrorException cee) {
            clientes = null;
        }
    }

    private void cleanClientes() {
        if (clientes != null) {
            getClientes().clear();
        }
    }

    public void tapCliente(SelectEvent event) {
        VCliente vCliente = ((VCliente) event.getObject());
        System.out.println("Cliente seleccionado: " + vCliente.getApellidos() + " " + vCliente.getNombres());
        cliente.setCliente(vCliente);
    }

    public List<VCliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<VCliente> clientes) {
        this.clientes = clientes;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public CustomerBean getCliente() {
        return cliente;
    }

    public void setCliente(CustomerBean cliente) {
        this.cliente = cliente;
    }

}
