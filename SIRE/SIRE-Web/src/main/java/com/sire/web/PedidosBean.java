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
import com.sire.entities.FacTmpFactD;
import com.sire.entities.InvArticulo;
import com.sire.entities.Pedido;
import com.sire.exception.VendedorException;
import com.sire.rs.client.FacParametrosFacadeREST;
import com.sire.rs.client.FacTmpFactCFacadeREST;
import com.sire.rs.client.FacTmpFactDFacadeREST;
import com.sire.rs.client.InvArticuloFacadeREST;
import com.sire.utils.Round;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
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

    private static final Logger LOGGER = Logger.getLogger(PedidosBean.class.getName());
    @Getter
    @Setter
    private Date fechaInicio = Calendar.getInstance().getTime(), fechaFin = Calendar.getInstance().getTime();
    private final FacTmpFactCFacadeREST facTmpFactCFacadeREST;
    private final FacTmpFactDFacadeREST facTmpFactDFacadeREST;
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
    @Getter
    @Setter
    private List<InvArticulo> invArticulos;
    private Pedido pedidoSeleccionado;
    private final InvArticuloFacadeREST invArticuloFacadeREST;
    @Getter
    private Double pagoTotal;

    public PedidosBean() {
        invArticuloFacadeREST = new InvArticuloFacadeREST();
        facTmpFactCFacadeREST = new FacTmpFactCFacadeREST();
        facTmpFactDFacadeREST = new FacTmpFactDFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        facParametrosFacadeREST = new FacParametrosFacadeREST();
    }

    public void consultarPedidos() {
        invArticulos = null;
        try {
            LOGGER.info("consultarPedidos");
            pedidos = gson.fromJson(facTmpFactCFacadeREST.
                    findByFechas_JSON(String.class, fechaInicio,
                            fechaFin, obtenerEmpresa(), obtenerVendedor()), new TypeToken<java.util.List<Pedido>>() {
                    }.getType()
            );
            LOGGER.log(Level.INFO, "pedidos: {0}", pedidos.size());
            pagoTotal = 0.0;
            for (Pedido pedido : pedidos) {
                if (pedido.getFacTmpFactC().getTotalFactura() != null) {
                    pagoTotal += pedido.getFacTmpFactC().getTotalFactura().doubleValue();
                }
            }
            pagoTotal = Round.round(pagoTotal, 2);
        } catch (VendedorException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    public void tapPedido(SelectEvent event) {
        LOGGER.log(Level.INFO, "\u00b7\u00b7 tapPedido \u00b7\u00b7 {0}", event.getObject());
        invArticulos = new ArrayList<>();
        pedidoSeleccionado = ((Pedido) event.getObject());
        LOGGER.log(Level.INFO, "# EgresoInv Pedido seleccionado: {0}",
                pedidoSeleccionado.getFacTmpFactC().getFacTmpFactCPK().getEgresoInv());
        try {
            detallesPedido = gson.fromJson(facTmpFactDFacadeREST.findByFacTmpFactC_JSON(String.class, obtenerEmpresa(),
                    URLEncoder.encode(String.valueOf(pedidoSeleccionado.getFacTmpFactC().getFacTmpFactCPK().getEgresoInv()), "UTF-8"),
                    pedidoSeleccionado.getFacTmpFactC().getFacTmpFactCPK().getEi()), new TypeToken<java.util.List<FacTmpFactD>>() {
            }.getType());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PedidosBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (FacTmpFactD facTmpFactD : detallesPedido) {
            InvArticulo invArticulo = invArticuloFacadeREST.find_JSON(
                    InvArticulo.class, "id;codEmpresa=" + facTmpFactD.getInvUnidadAlternativa().getInvUnidadAlternativaPK().getCodEmpresa() + ";codArticulo=" + facTmpFactD.getInvUnidadAlternativa().getInvUnidadAlternativaPK().getCodArticulo()
            );
            invArticulos.add(invArticulo);
        }

        LOGGER.log(Level.INFO, "# Articulos: {0}", invArticulos.size());
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

        LOGGER.log(Level.INFO, "codVendedor: {0}", defCodVendedor);
        return defCodVendedor;
    }

    private FacParametros obtenerFacParametros() {
        FacParametros facParametros = facParametrosFacadeREST.find_JSON(
                FacParametros.class, "id;codEmpresa=" + obtenerEmpresa()
                + ";nombreUsuario=" + userManager.getCurrent().getNombreUsuario());
        return facParametros;
    }
}
