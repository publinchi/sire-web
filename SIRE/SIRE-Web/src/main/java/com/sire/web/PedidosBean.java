/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.sire.entities.FacTmpFactC;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author root
 */
@ManagedBean(name = "pedidosBean")
@SessionScoped
@Getter
@Setter
public class PedidosBean {

    private Date fechaInicio;
    private Date fechaFin;

    public void consultarPedidos() {
        System.out.println("consultarPedidos");
    }
}
