/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.FacTmpFactC;
import com.sire.rs.client.FacTmpFactCFacadeREST;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author root
 */
@ManagedBean(name = "pedidosBean")
@SessionScoped
public class PedidosBean {

    @Getter
    @Setter
    private Date fechaInicio, fechaFin;
    private FacTmpFactCFacadeREST facTmpFactCFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;

    public PedidosBean() {
        facTmpFactCFacadeREST = new FacTmpFactCFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public void consultarPedidos() {
        System.out.println("consultarPedidos");
        List<FacTmpFactC> list = gson.fromJson(facTmpFactCFacadeREST.
                findByFechas_JSON(String.class, fechaInicio,
                        fechaFin), new TypeToken<java.util.List<FacTmpFactC>>() {
        }.getType());
        System.out.println("list: " + list.size());
    }
}
