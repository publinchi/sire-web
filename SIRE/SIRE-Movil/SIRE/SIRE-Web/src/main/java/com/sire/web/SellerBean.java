/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.FacParametros;
import com.sire.entities.VVendedor;
import com.sire.exception.VendedorException;
import com.sire.rs.client.FacParametrosFacadeREST;
import com.sire.rs.client.VVendedorFacadeREST;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "vendedor")
public class SellerBean {

    private static final Logger LOGGER = Logger.getLogger(PedidosBean.class.getName());
    private String nombresVendedor;
    @ManagedProperty(value = "#{user}")
    @Getter
    @Setter
    private UserManager userManager;
    private final GsonBuilder builder;
    private final Gson gson;
    private final VVendedorFacadeREST vVendedorFacadeREST;

    public SellerBean() {
        vVendedorFacadeREST = new VVendedorFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public String getNombresVendedor() {
        try {
            VVendedor vVendedor = vVendedorFacadeREST.find_JSON(VVendedor.class, obtenerVendedor().toString());
            nombresVendedor = vVendedor.getNombresVendedor();
        } catch (VendedorException ex) {
            Logger.getLogger(SellerBean.class.getName()).log(Level.SEVERE, ex.getMessage());
            nombresVendedor = userManager.getCurrent().getNombreUsuario();
        }
        return nombresVendedor;
    }

    private Integer obtenerVendedor() throws VendedorException {
        FacParametros facParametros = obtenerFacParametros();

        if (facParametros == null) {
            throw new VendedorException("Vendedor no asociado a facturación.");
        }

        Integer defCodVendedor = facParametros.getDefCodVendedor();

        if (defCodVendedor == null) {
            throw new VendedorException("Vendedor no asociado a facturación.");
        }

        LOGGER.log(Level.INFO, "codVendedor: {0}", defCodVendedor);
        return defCodVendedor;
    }

    private FacParametros obtenerFacParametros() {
        FacParametrosFacadeREST facParametrosFacadeREST = new FacParametrosFacadeREST();
        String facParametrosString = facParametrosFacadeREST.findAll_JSON(String.class);
        List<FacParametros> listaFacParametros = gson.fromJson(facParametrosString, new TypeToken<java.util.List<FacParametros>>() {
        }.getType());

        LOGGER.log(Level.INFO, "Current user: {0}", userManager.getCurrent().getNombreUsuario().toLowerCase());

        for (FacParametros facParametros : listaFacParametros) {
            if (facParametros.getFacParametrosPK().getNombreUsuario().toLowerCase().
                    equals(userManager.getCurrent().getNombreUsuario().toLowerCase())
                    && facParametros.getFacParametrosPK().getCodEmpresa().
                            equals(obtenerEmpresa())) {
                LOGGER.log(Level.INFO, "Usuario *: {0}", facParametros.getFacParametrosPK().getNombreUsuario().toLowerCase());
                LOGGER.log(Level.INFO, "facParametros: {0}", facParametros);
                return facParametros;
            }
        }
        return null;
    }

    private String obtenerEmpresa() {
        return userManager.getGnrEmpresa().getCodEmpresa();
    }
}
