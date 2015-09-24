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
import com.sire.entities.InvMovimientoDtll;
import com.sire.rs.client.InvArticuloFacadeREST;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.ClientErrorException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "articulosBean")
@SessionScoped
public class ArticulosBean {

    private final InvArticuloFacadeREST invArticuloFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    private String input;
    private List<InvArticulo> articulos;
//    private List<InvArticulo> articulosSeleccionados;
    private List<InvMovimientoDtll> invMovimientoDtlls;
    private InvMovimientoDtll invMovimientoDtllSeleccionado;

    public ArticulosBean() {
//        articulosSeleccionados = new ArrayList<>();
        invMovimientoDtlls = new ArrayList<>();
        invArticuloFacadeREST = new InvArticuloFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

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
        InvArticulo invArticulo = ((InvArticulo) event.getObject());
        System.out.println("Articulo seleccionado: " + invArticulo.getNombreArticulo());

        InvMovimientoDtll invMovimientoDtll = new InvMovimientoDtll();
        InvBodegaArt invBodegaArt = new InvBodegaArt();
        InvBodegaArtPK invBodegaArtPK = new InvBodegaArtPK();

        invBodegaArtPK.setCodArticulo(invArticulo.getInvArticuloPK().getCodArticulo());

        invBodegaArt.setInvBodegaArtPK(invBodegaArtPK);
        invMovimientoDtll.setInvBodegaArt(invBodegaArt);
        invMovimientoDtlls.add(invMovimientoDtll);
    }
    
     public void tapArticuloFinal(SelectEvent event) {
        InvMovimientoDtll invMovimientoDtll = ((InvMovimientoDtll) event.getObject());
        System.out.println("Articulo seleccionado: " + invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo());
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<InvArticulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<InvArticulo> articulos) {
        this.articulos = articulos;
    }

//    public List<InvArticulo> getArticulosSeleccionados() {
//        return articulosSeleccionados;
//    }
//
//    public void setArticulosSeleccionados(List<InvArticulo> articulosSeleccionados) {
//        this.articulosSeleccionados = articulosSeleccionados;
//    }
    public List<InvMovimientoDtll> getInvMovimientoDtlls() {
        return invMovimientoDtlls;
    }

    public void setInvMovimientoDtlls(List<InvMovimientoDtll> invMovimientoDtlls) {
        this.invMovimientoDtlls = invMovimientoDtlls;
    }

    public InvMovimientoDtll getInvMovimientoDtllSeleccionado() {
        return invMovimientoDtllSeleccionado;
    }

    public void setInvMovimientoDtllSeleccionado(InvMovimientoDtll invMovimientoDtllSeleccionado) {
        this.invMovimientoDtllSeleccionado = invMovimientoDtllSeleccionado;
    }

}
