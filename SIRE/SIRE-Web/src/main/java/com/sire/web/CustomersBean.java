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
import com.sire.entities.FacParametros;
import com.sire.entities.VCliente;
import com.sire.exception.FacParametrosException;
import com.sire.exception.VendedorException;
import com.sire.rs.client.FacParametrosFacadeREST;
import com.sire.rs.client.VClienteFacadeREST;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    private final FacParametrosFacadeREST facParametrosFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    @Getter
    @Setter
    private String input, modo = "c";
    private static final Logger logger = Logger.getLogger(CustomersBean.class.getName());

    public CustomersBean() {

        vClienteFacadeREST = new VClienteFacadeREST();
        facParametrosFacadeREST = new FacParametrosFacadeREST();
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
        try {
            vendedorBuscarClientes();
        } catch (VendedorException | FacParametrosException ex) {
            entregadorBuscarClientes();
            logger.log(Level.SEVERE, "Por favor validar registro(s).", ex);
            addMessage("Advertencia", ex.getMessage(), FacesMessage.SEVERITY_WARN);
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
        logger.log(Level.INFO, "Cliente seleccionado: {0} {1} / codVendedor: {2}", new Object[]{vCliente.getApellidos(), vCliente.getNombres(), vCliente.getCodVendedor()});
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

    private Integer obtenerVendedor() throws VendedorException, FacParametrosException {
        FacParametros facParametros = obtenerFacParametros();

        if (facParametros == null) {
            throw new FacParametrosException("No se pudo obtener parámetros.");
        }

        Integer defCodVendedor = facParametros.getDefCodVendedor();

        if (defCodVendedor == null) {
            throw new VendedorException("Vendedor no asociado a facturación.");
        }

        logger.log(Level.INFO, "codVendedor: {0}", defCodVendedor);
        return defCodVendedor;
    }

    private FacParametros obtenerFacParametros() {
        String facParametrosString = facParametrosFacadeREST.findAll_JSON(String.class);
        List<FacParametros> listaFacParametros = gson.fromJson(facParametrosString, new TypeToken<java.util.List<FacParametros>>() {
        }.getType());

        logger.log(Level.INFO, "Current user: {0}", userManager.getCurrent().getNombreUsuario().toLowerCase());

        for (FacParametros facParametros : listaFacParametros) {
            if (facParametros.getFacParametrosPK().getNombreUsuario().toLowerCase().
                    equals(userManager.getCurrent().getNombreUsuario().toLowerCase())
                    && facParametros.getFacParametrosPK().getCodEmpresa().
                            equals(obtenerEmpresa())) {
                logger.log(Level.INFO, "Usuario *: {0}", facParametros.getFacParametrosPK().getNombreUsuario().toLowerCase());
                logger.log(Level.INFO, "facParametros: {0}", facParametros);
                return facParametros;
            }
        }
        return null;
    }

    private void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void vendedorBuscarClientes() throws VendedorException, FacParametrosException {
        String clientesString = null;
        try {
            if (modo.equals("r") && !input.isEmpty()) {
                clientesString = vClienteFacadeREST.findByRazonSocialEmpresaVendedor(String.class, URLEncoder.encode(input, "UTF-8"), obtenerEmpresa(), obtenerVendedor());
                clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
                }.getType());
            } else if (modo.equals("n") && !input.isEmpty()) {
                clientesString = vClienteFacadeREST.findByNombresApellidosEmpresaVendedor(String.class, URLEncoder.encode(input, "UTF-8"), obtenerEmpresa(), obtenerVendedor());
                clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
                }.getType());
            } else if (modo.equals("c") && !input.isEmpty()) {
                try {
                    clientesString = vClienteFacadeREST.findByClienteEmpresaVendedor(String.class, input, obtenerEmpresa(), obtenerVendedor());
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
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private void entregadorBuscarClientes() {
        String clientesString = null;
        try {
            if (modo.equals("r") && !input.isEmpty()) {
                clientesString = vClienteFacadeREST.findByRazonSocialEmpresa(String.class, URLEncoder.encode(input, "UTF-8"), obtenerEmpresa());
                clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
                }.getType());
            } else if (modo.equals("n") && !input.isEmpty()) {
                clientesString = vClienteFacadeREST.findByNombresApellidosEmpresa(String.class, URLEncoder.encode(input, "UTF-8"), obtenerEmpresa());
                clientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
                }.getType());
            } else if (modo.equals("c") && !input.isEmpty()) {
                try {
                    clientesString = vClienteFacadeREST.findByClienteEmpresa(String.class, input, obtenerEmpresa());
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
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
}
