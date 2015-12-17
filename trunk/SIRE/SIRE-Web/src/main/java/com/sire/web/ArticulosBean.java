/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.InvArticulo;
import com.sire.entities.InvBodegaArt;
import com.sire.entities.InvBodegaArtPK;
import com.sire.entities.InvInventario;
import com.sire.entities.InvMovimientoDtll;
import com.sire.rs.client.InvArticuloFacadeREST;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.ClientErrorException;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "articulosBean")
@SessionScoped
@Getter
@Setter
public class ArticulosBean {

    private static final Logger logger = Logger.getLogger(ArticulosBean.class.getName());

    private final InvArticuloFacadeREST invArticuloFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    private String input;
    private List<InvArticulo> articulos;
    private List<InvMovimientoDtll> invMovimientoDtlls;
    private InvMovimientoDtll invMovimientoDtllSeleccionado;
    private List<InvInventario> invInventarios;

    // Atributos de articulo a ser agregado a la lista
    private int codBodega;
    private int codInventario;
    private String codUnidad;
    private int codArticulo;
    private InvArticulo invArticuloSeleccionado;
    private int cantidad;
    private Double totalRegistro;
    private Double totalIVA;

    public ArticulosBean() {
        invMovimientoDtlls = new ArrayList<>();
        invArticuloFacadeREST = new InvArticuloFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    // Medotos del negocio
    public void findArticulos() {
        System.out.println("Invocando findArticulos.");
        String articulosString = null;
        try {
            articulosString = invArticuloFacadeREST.findByNombreArticulo(String.class, input);
            articulos = gson.fromJson(articulosString, new TypeToken<java.util.List<InvArticulo>>() {
            }.getType());
            System.out.println("# articulos: " + articulos.size());
        } catch (ClientErrorException cee) {
            articulos = null;
        }
    }

    public void tapArticulo(SelectEvent event) {
        invArticuloSeleccionado = ((InvArticulo) event.getObject());
        System.out.println("Articulo seleccionado: " + invArticuloSeleccionado.getNombreArticulo());
        setCodArticulo(invArticuloSeleccionado.getInvArticuloPK().getCodArticulo());

        InvMovimientoDtll invMovimientoDtll = new InvMovimientoDtll();
        InvBodegaArt invBodegaArt = new InvBodegaArt();
        InvBodegaArtPK invBodegaArtPK = new InvBodegaArtPK();

        invBodegaArtPK.setCodArticulo(invArticuloSeleccionado.getInvArticuloPK().getCodArticulo());

        invBodegaArt.setInvBodegaArtPK(invBodegaArtPK);
        invMovimientoDtll.setInvBodegaArt(invBodegaArt);
        invMovimientoDtlls.add(invMovimientoDtll);
    }

    public void tapArticuloFinal(SelectEvent event) {
        setInvMovimientoDtllSeleccionado(((InvMovimientoDtll) event.getObject()));
        InvMovimientoDtll invMovimientoDtll = getInvMovimientoDtllSeleccionado();
        System.out.println("Articulo seleccionado: "
                + invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo());

//        codBodega = Integer.parseInt(invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodBodega());
//        codInventario = Integer.parseInt(invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodInventario());
//        codUnidad = invMovimientoDtll.getCodUnidad();
//        cantidad = invMovimientoDtll.getCantidad().intValue();

        // TODO terminar el mapeo
    }

    public void loadInventariosByBodega() {
        Logger.getLogger(ArticulosBean.class.getName()).log(Level.INFO, "codBodega: {0}", codBodega);
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:inv");
    }

    public void calcularTotalRegistro() {
        Logger.getLogger(ArticulosBean.class.getName()).log(Level.INFO, "cantidad: {0}", cantidad);
//        totalRegistro = cantidad * invArticuloSeleccionado.getCostoPromedio();
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalRegistro");
    }

    public void agregarArticulo() {
        logger.info("Articulo a ser agregado: ");
        logger.log(Level.INFO, "codBodega: {0}", codBodega);
        logger.log(Level.INFO, "codInventario: {0}", codInventario);
        logger.log(Level.INFO, "codUnidad: {0}", codUnidad);
        logger.log(Level.INFO, "cantidad: {0}", cantidad);
        logger.log(Level.INFO, "precioUnitario: {0}", invArticuloSeleccionado.getCostoPromedio());
        logger.log(Level.INFO, "descuento: {0}", invArticuloSeleccionado.getDescArticulo());
        logger.log(Level.INFO, "total: {0}", totalRegistro);
        logger.log(Level.INFO, "iva: {0}", invArticuloSeleccionado.getCodIva());
        logger.log(Level.INFO, "totalIVA: {0}", totalIVA);

        InvMovimientoDtll invMovimientoDtll = getInvMovimientoDtllSeleccionado();
        // codBodega
        invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().setCodBodega(String.valueOf(codBodega));
        // codInventario
        invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().setCodInventario(String.valueOf(codInventario));
        // codUnidad
        invMovimientoDtll.setCodUnidad(codUnidad);
        // cantidad
        invMovimientoDtll.setCantidad(new BigDecimal(cantidad));
        // precio unitario
        invMovimientoDtll.setCostoUnitario(invArticuloSeleccionado.getCostoPromedio());
        // descuento
        if (invArticuloSeleccionado.getDescArticulo() != null) {
            invMovimientoDtll.setDescuento(new BigInteger(invArticuloSeleccionado.getDescArticulo()));
        }
        // total
        if (totalRegistro != null) {
            invMovimientoDtll.setCostoTotal(new BigInteger(totalRegistro.toString()));
        }
        // iva
        // total iva

        // TODO terminar el mapeo
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formTablaArticulos:tablaArticulos");

    }
}
