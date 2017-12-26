/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pestupinan
 */
public class Comprobante {

    private List<String> comprobante;

    public Comprobante() {
        this.comprobante = new ArrayList<>();
    }

    public List<String> getComprobante() {
        return comprobante;
    }

    public void setComprobante(List<String> comprobante) {
        this.comprobante = comprobante;
    }

}
