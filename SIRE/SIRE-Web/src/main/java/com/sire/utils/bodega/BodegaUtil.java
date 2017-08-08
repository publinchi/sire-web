/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.utils.bodega;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.InvBodegaArt;
import com.sire.rs.client.InvBodegaArtFacadeREST;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author publio
 */
public class BodegaUtil {

    private static final Logger logger = Logger.getLogger(BodegaUtil.class.getName());

    private final Gson gson;
    private final GsonBuilder builder;

    public BodegaUtil() {
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    private List<InvBodegaArt> obtenerBodegasPorCodArticulo(Integer codArticulo) {
        InvBodegaArtFacadeREST invBodegaArtFacadeREST = new InvBodegaArtFacadeREST();
        String result = invBodegaArtFacadeREST.findByCodArticulo_JSON(String.class, String.valueOf(codArticulo));
        List<InvBodegaArt> invBodegaArts = gson.fromJson(result, new TypeToken<List<InvBodegaArt>>() {
        }.getType());
        return invBodegaArts;
    }

    public String obtenerCodBodega(Integer codArticulo) {

        Map<Integer, String> map = new HashMap<>();

        List tmp = new ArrayList();
        List<InvBodegaArt> lista = obtenerBodegasPorCodArticulo(codArticulo);
        for (InvBodegaArt invBodegaArt : lista) {

            if (invBodegaArt.getExistencia().intValue() > 0) {
                logger.info("Codigo Bodega: " + invBodegaArt.getInvBodegaArtPK().getCodBodega()
                        + ", Existencia: " + invBodegaArt.getExistencia());
                map.put(invBodegaArt.getExistencia().intValue(), invBodegaArt.getInvBodegaArtPK().getCodBodega());
            }
        }

        for (Integer exist : map.keySet()) {
            tmp.add(exist);
        }

        Collections.sort(tmp, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        if (!tmp.isEmpty()) {
            return map.get(tmp.get(0));
        } else {
            return "01";
        }
    }
}
