/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

/**
 *
 * @author publio
 */
public class Pedido {

    private InvMovimientoCab invMovimientoCab;
    private FacTmpFactC facTmpFactC;

    public InvMovimientoCab getInvMovimientoCab() {
        return invMovimientoCab;
    }

    public void setInvMovimientoCab(InvMovimientoCab invMovimientoCab) {
        this.invMovimientoCab = invMovimientoCab;
    }

    public FacTmpFactC getFacTmpFactC() {
        return facTmpFactC;
    }

    public void setFacTmpFactC(FacTmpFactC facTmpFactC) {
        this.facTmpFactC = facTmpFactC;
    }
}
