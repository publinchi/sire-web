/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.client;

/**
 *
 * @author Administrator
 */
public class TestClient {

    public static void main(String args[]) {
//        CxcClaseClienteFacadeREST cxcClaseClienteFacadeREST = new CxcClaseClienteFacadeREST();
//        System.out.println(cxcClaseClienteFacadeREST.countREST());

        InvMovimientoCabFacadeREST invMovimientoCabFacadeREST = new InvMovimientoCabFacadeREST();
        System.out.println(invMovimientoCabFacadeREST.countREST());
    }
}
