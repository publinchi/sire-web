/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.entities;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author publio
 */
@Getter
@Setter
public class Pedido {

    private InvMovimientoCab invMovimientoCab;
    private FacTmpFactC facTmpFactC;
    private GnrLogHistorico gnrLogHistorico;
}
