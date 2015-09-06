/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.CxcCliente;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface CxcClienteFacadeRemote {

    void create(CxcCliente cxcCliente);

    void edit(CxcCliente cxcCliente);

    void remove(CxcCliente cxcCliente);

    CxcCliente find(Object id);

    List<CxcCliente> findAll();

    List<CxcCliente> findRange(int[] range);

    int count();
    
}
