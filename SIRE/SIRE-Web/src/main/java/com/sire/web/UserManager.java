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
import com.sire.entities.GnrUsuaMod;
import com.sire.entities.GnrUsuarios;
import com.sire.rs.client.GnrUsuaModFacadeREST;
import com.sire.rs.client.GnrUsuarioFacadeREST;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "user")
@SessionScoped
public class UserManager {

    @Getter
    @Setter
    private String userName, password;
    @Getter
    @Setter
    private GnrEmpresa gnrEmpresa;
    private final Gson gson;
    @Getter
    @Setter
    private GnrUsuarios current;
    @Getter
    @Setter
    private int activeindex;
    private static Logger logger;
    @Getter
    @Setter
    private List<GnrUsuaMod> gnrUsuaMods;

    private final static String PEDIDOS_Y_DESPACHOS = "04";
    private final static String CUENTAS_POR_COBRAR = "06";
    @Getter
    private boolean pedidoVisible = false, cobroVisible = false;

    public UserManager() {
        logger = Logger.getLogger(UserManager.class.getName());
        GsonBuilder builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        gnrEmpresa = new GnrEmpresa();
    }

    public void dirige(int a) {
        setActiveindex(a);
        Logger.getLogger(SecurityBean.class.getName()).log(Level.INFO, String.valueOf(a));
    }

    public void login() {
        logger.log(Level.INFO, "User: {0}", userName);
        logger.log(Level.INFO, "Password: {0}", password);
        logger.log(Level.INFO, "Empresa: {0}", gnrEmpresa);
        GnrUsuarioFacadeREST gnrUsuarioFacadeREST = new GnrUsuarioFacadeREST();
        List<GnrUsuarios> gnrUsuarios = gson.fromJson(gnrUsuarioFacadeREST.findAll_JSON(String.class),
                new TypeToken<java.util.List<GnrUsuarios>>() {
        }.getType());
        System.out.println(gnrUsuarios.size());
        for (GnrUsuarios gnrUsuario : gnrUsuarios) {
            if (gnrUsuario.getNombreUsuario().toUpperCase().equals(userName.toUpperCase())
                    && gnrUsuario.getClave().toUpperCase().equals(password.toUpperCase())) {
                current = gnrUsuario;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Exitoso", "Bienvenido " + current.getNombreUsuario() + "."));
                context.getExternalContext().getFlash().setKeepMessages(true);
                try {
                    loadAuthorizedModules();
                    context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/ui/index.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                Logger.getLogger(SecurityBean.class.getName()).log(Level.INFO, "Login OK");
                return;
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error", "Nombre de usuario o contraseña incorrectos"));
        userName = null;
        password = null;
        RequestContext.getCurrentInstance().update("login:frmLogin:basic");
    }

    public boolean isLoggedIn() {
        Logger.getLogger(UserManager.class.getName()).log(Level.INFO, "Cheking logged in");
        return current != null;
    }

    public String logout() {
        gnrUsuaMods = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    private void loadAuthorizedModules() {
        if (gnrUsuaMods == null) {
            GnrUsuaModFacadeREST gnrUsuaModFacadeREST = new GnrUsuaModFacadeREST();
            String gnrUsuaModsString = gnrUsuaModFacadeREST.findByNombreUsuario(String.class, userName.toUpperCase());
            gnrUsuaMods = gson.fromJson(gnrUsuaModsString,
                    new TypeToken<java.util.List<GnrUsuaMod>>() {
            }.getType());

            checkPermission();
        }
    }

    private void checkPermission() {
        logger.info("gnrUsuaMods size: " + gnrUsuaMods.size());

        for (GnrUsuaMod gnrUsuaMod : gnrUsuaMods) {
            if (gnrUsuaMod.getGnrUsuaModPK().getCodModulo().equals(PEDIDOS_Y_DESPACHOS)) {
                pedidoVisible = true;
                logger.info("pedidoVisible: " + pedidoVisible);
            } else if (gnrUsuaMod.getGnrUsuaModPK().getCodModulo().equals(CUENTAS_POR_COBRAR)) {
                cobroVisible = true;
                logger.info("cobroVisible: " + cobroVisible);
            }
        }
    }
}
