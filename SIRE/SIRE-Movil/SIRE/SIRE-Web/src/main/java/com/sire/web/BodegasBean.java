/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.InvBodegaArt;
import com.sire.entities.InvMovimientoDtll;
import com.sire.rs.client.InvBodegaArtFacadeREST;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "bodegasBean")
@SessionScoped
public class BodegasBean {

    private List<InvBodegaArt> invBodegaArts;
    @ManagedProperty(value = "#{articulosBean}")
    private ArticulosBean articulosBean;

    public List<InvBodegaArt> getInvBodegaArts() {
        InvMovimientoDtll invMovimientoDtll = articulosBean.getInvMovimientoDtllSeleccionado();
        Logger.getLogger(BodegasBean.class.getName()).info("Invocando getInvBodegaArts");
        if (invMovimientoDtll != null) {
            int codArticulo = invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo();
            Logger.getLogger(BodegasBean.class.getName()).log(Level.INFO, "codArticulo: {0}", String.valueOf(codArticulo));
            InvBodegaArtFacadeREST invBodegaArtFacadeREST = new InvBodegaArtFacadeREST();
            String result = invBodegaArtFacadeREST.findByCodArticulo_JSON(String.class, String.valueOf(codArticulo));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
            invBodegaArts = gson.fromJson(result, new TypeToken<List<InvBodegaArt>>() {
            }.getType());
        }
        return invBodegaArts;
    }

    public void setInvBodegaArts(List<InvBodegaArt> invBodegaArts) {
        this.invBodegaArts = invBodegaArts;
    }

    public ArticulosBean getArticulosBean() {
        return articulosBean;
    }

    public void setArticulosBean(ArticulosBean articulosBean) {
        this.articulosBean = articulosBean;
    }

}
