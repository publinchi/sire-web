/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "securityMB")
@RequestScoped
public class SecurityBean {

    @ManagedProperty(value = "#{user}")
    private UserManager userManager;
    String loginURL = "/SIRE-Web/ui/login.xhtml";

    public void checkLogIn() {
        if (userManager == null || !userManager.isLoggedIn()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(loginURL);
                Logger.getLogger(SecurityBean.class.getName()).log(Level.INFO, "No Autorizado");
            } catch (IOException ex) {
                Logger.getLogger(SecurityBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
        Logger.getLogger(SecurityBean.class.getName()).log(Level.INFO, "Autorizado");
        }
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
