/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.GnrEmpresa;
import com.sire.rs.client.GnrEmpresaFacadeREST;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "empresasBean")
@SessionScoped
public class EmpresasBean {

    private final GnrEmpresaFacadeREST gnrEmpresaFacadeREST;
    private final Gson gson;

    public EmpresasBean() {
        this.gnrEmpresaFacadeREST = new GnrEmpresaFacadeREST();
        GsonBuilder builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public List<GnrEmpresa> getGnrEmpresas() {
        String string = gnrEmpresaFacadeREST.findAll_JSON(String.class);

        List<GnrEmpresa> gnrEmpresas = gson.fromJson(string, new TypeToken<java.util.List<GnrEmpresa>>() {
        }.getType());
        return gnrEmpresas;
    }

}
