/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.GnrUsuarios;
import com.sire.rs.client.GnrUsuarioFacade;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "user")
@SessionScoped
public class UserManager {

    private String userName;
    private String password;
    private final Gson gson;
    private GnrUsuarios current;
    private int activeindex;

    public UserManager() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public int getActiveindex() {
        return activeindex;
    }

    public void setActiveindex(int activeindex) {
        this.activeindex = activeindex;
    }

    public void dirige(int a) {
        setActiveindex(a);
        Logger.getLogger(SecurityBean.class.getName()).log(Level.INFO, String.valueOf(a));
    }

    public void login() {
        System.out.println("User: " + userName);
        System.out.println("Password: " + password);
        GnrUsuarioFacade gnrUsuarioFacadeREST = new GnrUsuarioFacade();
        List<GnrUsuarios> gnrUsuarios = gson.fromJson(gnrUsuarioFacadeREST.findAll_JSON(String.class),
                new TypeToken<java.util.List<GnrUsuarios>>() {
                }.getType());
        System.out.println(gnrUsuarios.size());
        for (GnrUsuarios gnrUsuario : gnrUsuarios) {
            if (gnrUsuario.getNombreUsuario().toUpperCase().equals(userName.toUpperCase())
                    && gnrUsuario.getClave().toUpperCase().equals(password.toUpperCase())) {
                current = gnrUsuario;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Exitoso", "Bienvenido " + userName + "."));
                try {
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
    }

    public boolean isLoggedIn() {
        Logger.getLogger(SecurityBean.class.getName()).log(Level.INFO, "Cheking logged in");
        return current != null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GnrUsuarios getCurrent() {
        return current;
    }

    public void setCurrent(GnrUsuarios current) {
        this.current = current;
    }

}
