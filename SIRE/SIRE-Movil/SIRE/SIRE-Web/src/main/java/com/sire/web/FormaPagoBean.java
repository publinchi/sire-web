/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.CxcFormaPago;
import com.sire.rs.client.CxcFormaPagoFacadeREST;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author publio
 */
@ManagedBean(name = "formaPagoBean")
public class FormaPagoBean {

    private final GsonBuilder builder;
    private final Gson gson;
    private List<CxcFormaPago> formaPagos;

    public FormaPagoBean() {
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public List<CxcFormaPago> getFormaPagos() {
        CxcFormaPagoFacadeREST cxcFormaPagoFacadeREST = new CxcFormaPagoFacadeREST();
        String formaPagosString = cxcFormaPagoFacadeREST.findAll_JSON(String.class);
        formaPagos = gson.fromJson(formaPagosString, new TypeToken<java.util.List<CxcFormaPago>>() {
        }.getType());
        return formaPagos;
    }

    public void setFormaPagos(List<CxcFormaPago> formaPagos) {
        this.formaPagos = formaPagos;
    }

}
