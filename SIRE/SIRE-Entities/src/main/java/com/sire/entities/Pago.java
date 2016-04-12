/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.util.List;

/**
 *
 * @author publio
 */
public class Pago {

    private List<CxcDocCobrar> cxcDocCobrarList;
    private CxcAbonoC cxcAbonoC;
    private CxcPagoContado cxcPagoContado;
    private List<CxcCheque> cxcChequeList;

    public List<CxcDocCobrar> getCxcDocCobrarList() {
        return cxcDocCobrarList;
    }

    public void setCxcDocCobrarList(List<CxcDocCobrar> cxcDocCobrarList) {
        this.cxcDocCobrarList = cxcDocCobrarList;
    }

    public CxcAbonoC getCxcAbonoC() {
        return cxcAbonoC;
    }

    public void setCxcAbonoC(CxcAbonoC cxcAbonoC) {
        this.cxcAbonoC = cxcAbonoC;
    }

    public CxcPagoContado getCxcPagoContado() {
        return cxcPagoContado;
    }

    public void setCxcPagoContado(CxcPagoContado cxcPagoContado) {
        this.cxcPagoContado = cxcPagoContado;
    }

    public List<CxcCheque> getCxcChequeList() {
        return cxcChequeList;
    }

    public void setCxcChequeList(List<CxcCheque> cxcChequeList) {
        this.cxcChequeList = cxcChequeList;
    }

}
