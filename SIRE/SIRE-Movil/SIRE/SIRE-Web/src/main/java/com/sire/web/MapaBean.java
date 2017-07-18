/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

/**
 *
 * @author publio
 */
@ManagedBean(name = "mapa")
@SessionScoped
public class MapaBean {

    private static final Logger LOGGER = Logger.getLogger(MapaBean.class.getName());
    @Getter
    @Setter
    private String direccion, lat, lng;
    @Getter
    @Setter
    private MapModel emptyModel;

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }

    public void seleccionarLocalizacion() {
        direccion = "Mariscal Sucre";
    }

    public void processLocation() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Map map = context.getExternalContext().getRequestParameterMap();
            lat = (String) map.get("lat");
            lng = (String) map.get("lng");
            LOGGER.log(Level.INFO, "lat: {0}", lat);
            LOGGER.log(Level.INFO, "lng: {0}", lng);
            GeoApiContext googleContext = new GeoApiContext().setApiKey("AIzaSyDoXgacFtGDCtWfYPQeJO4Kz7NUEQWkNAA");
            LatLng location = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
            GeocodingResult[] results = GeocodingApi.reverseGeocode(googleContext, location).await();
            direccion = results[0].formattedAddress;
            LOGGER.log(Level.INFO, "Direcci\u00f3n: {0}", direccion);
//             RequestContext.getCurrentInstance().update("pedido:accordionPanel:ubicacion");
        } catch (Exception ex) {
            Logger.getLogger(MapaBean.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    public void limpiar() {
        direccion = null;
    }
}
