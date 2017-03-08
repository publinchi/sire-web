/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.VCliente;
import com.sire.rs.client.VClienteFacadeREST;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.ClientErrorException;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "customers")
@SessionScoped
public class CustomersBean {

    @ManagedProperty("#{cliente}")
    @Getter
    @Setter
    private CustomerBean cliente;
    @ManagedProperty(value = "#{user}")
    @Getter
    @Setter
    private UserManager userManager;
    @ManagedProperty(value = "#{cxcDocCobrarBean}")
    @Getter
    @Setter
    private CxcDocCobrarBean cxcDocCobrarBean;
    @Getter
    @Setter
    private List<VCliente> clientes;
    private final VClienteFacadeREST vClienteFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    @Getter
    @Setter
    private String input, modo = "c";
    private static final Logger logger = Logger.getLogger(CustomersBean.class.getName());

    public CustomersBean() {

        vClienteFacadeREST = new VClienteFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    @PostConstruct
    private void init() {
    }

    private void loadClientes() {
        logger.info("loadClientes");
        List<VCliente> list = gson.fromJson(vClienteFacadeREST.findAll_JSON(String.class), new TypeToken<java.util.List<VCliente>>() {
        }.getType());
        cleanClientes();
        setClientes(list);
        logger.log(Level.INFO, "# clientes: {0}", clientes.size());
    }

    public void findClientes() {
        logger.info("findClientes");
        String clientesString = null;
        try {
            if (modo.equals("r") && !input.isEmpty()) {
                clientesString = vClienteFacadeREST.findByRazonSocialEmpresa(String.class, input, obtenerEmpresa());
                clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
                }.getType());
            } else if (modo.equals("n") && !input.isEmpty()) {
                clientesString = vClienteFacadeREST.findByNombresApellidosEmpresa(String.class, input, obtenerEmpresa());
                clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
                }.getType());
            } else if (modo.equals("c") && !input.isEmpty()) {
                try {
                    clientesString = vClienteFacadeREST.find_JSON(String.class, input);
                    clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
                    }.getType());
                } catch (JsonSyntaxException jse) {
                    VCliente vcliente = gson.fromJson(clientesString, VCliente.class);
                    clientes = new ArrayList<>();
                    clientes.add(vcliente);
                }
            }
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
        logger.info("tapCliente");
        VCliente vCliente = ((VCliente) event.getObject());
        logger.log(Level.INFO, "Cliente seleccionado: {0} {1}", new Object[]{vCliente.getApellidos(), vCliente.getNombres()});
        cliente.setCliente(vCliente);

        limpiar();
        cxcDocCobrarBean.limpiarFormaPago();
        modo = "c";
    }

    public void cambioModo() {
        limpiar();
    }

    public void limpiar() {
        if (clientes != null) {
            clientes.clear();
        }
        input = null;
    }

    private String obtenerEmpresa() {
        return userManager.getGnrEmpresa().getCodEmpresa();
    }
}
