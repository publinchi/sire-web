/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author publio
 */
@Getter
@Setter
public class Pago {

    private List<CxcDocCobrar> cxcDocCobrarList;
    private CxcAbonoC cxcAbonoC;
    private List<CxcAbonoD> cxcAbonoDList;
    private CxcPagoContado cxcPagoContado;
    private List<CxcCheque> cxcChequeList;
    private GnrLogHistorico gnrLogHistorico;
    private String clientMail;
    private String razonSocial;

}
