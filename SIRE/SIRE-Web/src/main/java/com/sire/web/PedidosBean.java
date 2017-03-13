/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.FacParametros;
import com.sire.entities.FacTmpFactC;
import com.sire.entities.FacTmpFactD;
import com.sire.entities.Pedido;
import com.sire.exception.VendedorException;
import com.sire.rs.client.FacParametrosFacadeREST;
import com.sire.rs.client.FacTmpFactCFacadeREST;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author root
 */
@ManagedBean(name = "pedidosBean")
@SessionScoped
public class PedidosBean {

    private static final Logger logger = Logger.getLogger(PedidosBean.class.getName());
    @Getter
    @Setter
    private Date fechaInicio, fechaFin;
    private final FacTmpFactCFacadeREST facTmpFactCFacadeREST;
    private final FacParametrosFacadeREST facParametrosFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    @ManagedProperty(value = "#{user}")
    @Getter
    @Setter
    private UserManager userManager;
    @Getter
    @Setter
    private List<Pedido> pedidos;
    @Getter
    @Setter
    private List<FacTmpFactD> detallesPedido;
    private Pedido pedidoSeleccionado;

    public PedidosBean() {
        facTmpFactCFacadeREST = new FacTmpFactCFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        facParametrosFacadeREST = new FacParametrosFacadeREST();
    }

    public void consultarPedidos() {
        try {
            logger.info("consultarPedidos");
            pedidos = gson.fromJson(facTmpFactCFacadeREST.
                    findByFechas_JSON(String.class, fechaInicio,
                            fechaFin, obtenerEmpresa(), obtenerVendedor()), new TypeToken<java.util.List<Pedido>>() {
                    }.getType()
            );
            logger.log(Level.INFO, "pedidos: {0}", pedidos.size());
        } catch (VendedorException ex) {
            logger.log(Level.SEVERE, "Por favor validar registro(s).", ex);
        }
    }

    public void tapPedido(SelectEvent event) {
        logger.log(Level.INFO, "\u00b7\u00b7 tapPedido \u00b7\u00b7 {0}", event.getObject());

        pedidoSeleccionado = ((Pedido) event.getObject());
        logger.log(Level.INFO, "# EgresoInv Pedido seleccionado: {0}",
                pedidoSeleccionado.getFacTmpFactC().getFacTmpFactCPK().getEgresoInv());
    }

    private String obtenerEmpresa() {
        return userManager.getGnrEmpresa().getCodEmpresa();
    }

    private Integer obtenerVendedor() throws VendedorException {
        FacParametros facParametros = obtenerFacParametros();

        if (facParametros == null) {
            throw new VendedorException("Vendedor no asociado a facturación.");
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
}
