/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.InvInventario;
import com.sire.entities.InvMovimientoDtll;
import com.sire.entities.InvUnidadAlternativa;
import com.sire.rs.client.InvInventarioFacadeREST;
import com.sire.rs.client.InvUnidadAlternativaFacadeREST;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import lombok.Setter;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "inventariosBean")
public class InventariosBean {

    private List<InvInventario> invInventarios;
    private List<InvUnidadAlternativa> invUnidadAlternativas;
    @ManagedProperty(value = "#{articulosBean}")
    private ArticulosBean articulosBean;

    @Setter
    private InvUnidadAlternativa invUnidadAlternativa;

    public List<InvInventario> getInvInventarios() {
        InvMovimientoDtll invMovimientoDtll = articulosBean.getInvMovimientoDtllSeleccionado();
        Logger.getLogger(InventariosBean.class.getName()).info("Invocando getInvInventarios");
        if (invMovimientoDtll != null) {
            String codBod = invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodBodega();
            Logger.getLogger(InventariosBean.class.getName()).log(Level.INFO, "# CodBod: {0}", codBod);
            if (codBod != null) {
                int codBodega = Integer.valueOf(codBod);
                Logger.getLogger(InventariosBean.class.getName()).log(Level.INFO, "codBodega: {0}", String.valueOf(codBodega));
                InvInventarioFacadeREST invInventarioFacadeREST = new InvInventarioFacadeREST();
                String result = invInventarioFacadeREST.findByCodBodega(String.class, String.valueOf(codBodega));
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
                invInventarios = gson.fromJson(result, new TypeToken<List<InvInventario>>() {
                }.getType());
                Logger.getLogger(InventariosBean.class.getName()).log(Level.INFO, "# Inventarios: {0}", invInventarios.size());
            }
        }
        return invInventarios;
    }

    public void setInvInventarios(List<InvInventario> invInventarios) {
        this.invInventarios = invInventarios;
    }

    public ArticulosBean getArticulosBean() {
        return articulosBean;
    }

    public void setArticulosBean(ArticulosBean articulosBean) {
        this.articulosBean = articulosBean;
    }

    public List<InvUnidadAlternativa> getInvUnidadAlternativas() {
        InvMovimientoDtll invMovimientoDtll = articulosBean.getInvMovimientoDtllSeleccionado();
        Logger.getLogger(InventariosBean.class.getName()).info("Invocando getInvInventarios");
        if (invMovimientoDtll != null) {
            int codArticulo = invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo();
            Logger.getLogger(InventariosBean.class.getName()).log(Level.INFO, InventariosBean.class.getName() + " - codArticulo: {0}", codArticulo);
            InvUnidadAlternativaFacadeREST invUnidadAlternativaFacadeREST = new InvUnidadAlternativaFacadeREST();
            String result = invUnidadAlternativaFacadeREST.findByCodArticulo(String.class, String.valueOf(codArticulo));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
            invUnidadAlternativas = gson.fromJson(result, new TypeToken<List<InvUnidadAlternativa>>() {
            }.getType());
            Logger.getLogger(InventariosBean.class.getName()).log(Level.INFO, "# InvUnidadAlternativas: {0}", invUnidadAlternativas.size());
        }
        return invUnidadAlternativas;
    }

    public void setInvUnidadAlternativas(List<InvUnidadAlternativa> invUnidadAlternativas) {
        this.invUnidadAlternativas = invUnidadAlternativas;
    }

    public InvUnidadAlternativa getInvUnidadAlternativa() {
        return invUnidadAlternativa;
    }

}
