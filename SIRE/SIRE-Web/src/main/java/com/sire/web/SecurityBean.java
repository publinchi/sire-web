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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */
@ManagedBean(name = "securityMB")
@RequestScoped
public class SecurityBean {

    private static final Logger LOGGER = Logger.getLogger(SecurityBean.class.getName());
    @Getter
    @Setter
    @ManagedProperty(value = "#{user}")
    private UserManager userManager;
    String loginURL = "/ui/login.xhtml";

    public void checkLogIn() {
        if (userManager == null || !userManager.isLoggedIn()) {
            try {
                String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + loginURL);
                LOGGER.log(Level.INFO, "No Autorizado");
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        } else {
            LOGGER.log(Level.INFO, "Autorizado");
        }
    }
}
