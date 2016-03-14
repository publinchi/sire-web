/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author publio
 */
@ManagedBean
@SessionScoped
public class MapaBean {

    @Getter
    @Setter
    private String ciudad, direccion;
    @Getter
    @Setter
    private MapModel emptyModel;

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }

    public void seleccionarLocalizacion() {
        System.out.println(emptyModel.getMarkers().size());
        ciudad = "Quito";
        direccion = "Mariscal Sucre";
    }

}
