/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.BanBancos;
import com.sire.rs.client.BanBancosFacadeREST;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Setter;

/**
 *
 * @author publio
 */
@ManagedBean(name = "bancosBean")
@SessionScoped
public class BancosBean {

    @Setter
    List<BanBancos> bancos;

    public List<BanBancos> getBancos() {
        if (bancos == null) {
            obtenerBancos();
        }
        return bancos;
    }

    private void obtenerBancos() {
        BanBancosFacadeREST banBancosFacadeREST = new BanBancosFacadeREST();
        String banBancosString = banBancosFacadeREST.findAll_JSON(String.class);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        bancos = gson.fromJson(banBancosString, new TypeToken<List<BanBancos>>() {
        }.getType());
    }
}
