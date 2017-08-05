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

    private static final Logger LOGGER = Logger.getLogger(InventariosBean.class.getName());
    private List<InvInventario> invInventarios;
    private List<InvUnidadAlternativa> invUnidadAlternativas;
    @ManagedProperty(value = "#{articulosBean}")
    private ArticulosBean articulosBean;

    @Setter
    private InvUnidadAlternativa invUnidadAlternativa;

    public List<InvInventario> getInvInventarios() {
        InvMovimientoDtll invMovimientoDtll = articulosBean.getInvMovimientoDtllSeleccionado();

        if (invMovimientoDtll != null) {
            String codBod = invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodBodega();
            if (codBod != null) {
                int codBodega = Integer.valueOf(codBod);

                InvInventarioFacadeREST invInventarioFacadeREST = new InvInventarioFacadeREST();
                String result = invInventarioFacadeREST.findByCodBodega(String.class, String.valueOf(codBodega));
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
                invInventarios = gson.fromJson(result, new TypeToken<List<InvInventario>>() {
                }.getType());

                LOGGER.info("codBodega: " + String.valueOf(codBodega) + ", # Inventarios: " + invInventarios.size());
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

        if (invMovimientoDtll != null) {
            int codArticulo = invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo();
            InvUnidadAlternativaFacadeREST invUnidadAlternativaFacadeREST = new InvUnidadAlternativaFacadeREST();
            String result = invUnidadAlternativaFacadeREST.findByCodArticulo(String.class, String.valueOf(codArticulo));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
            invUnidadAlternativas = gson.fromJson(result, new TypeToken<List<InvUnidadAlternativa>>() {
            }.getType());

            LOGGER.info("codArticulo: " + codArticulo + ", # InvUnidadAlternativas: " + invUnidadAlternativas.size());
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
