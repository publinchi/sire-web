/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.sire.entities.InvInventario;
import com.sire.entities.InvMovimientoDtll;
import com.sire.rs.client.InvInventarioFacade;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "inventariosBean")
public class InventariosBean {

    private List<InvInventario> invInventarios;
    @ManagedProperty(value = "#{articulosBean}")
    private ArticulosBean articulosBean;

    public List<InvInventario> getInvInventarios() {
//        InvMovimientoDtll invMovimientoDtll = articulosBean.getInvMovimientoDtllSeleccionado();
//        Logger.getLogger(BodegasBean.class.getName()).info("Invocando getInvBodegaArts");
//        if (invMovimientoDtll != null) {
//            int codArticulo = invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo();
//            Logger.getLogger(BodegasBean.class.getName()).info(String.valueOf(codArticulo));
//            InvInventarioFacade invInventarioFacade = new InvInventarioFacade();
//            String result = invInventarioFacade.find_JSON(String.class, String.valueOf(codArticulo));
//            GsonBuilder builder = new GsonBuilder();
//            Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
//            invBodegaArts = gson.fromJson(result, new TypeToken<List<InvBodegaArt>>() {
//            }.getType());
//        }
        return invInventarios;
    }

    public void setInvInventarios(List<InvInventario> invInventarios) {
        this.invInventarios = invInventarios;
    }

}
