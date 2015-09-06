/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvMovimientoDtll;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvMovimientoDtllFacadeRemote {

    void create(InvMovimientoDtll invMovimientoDtll);

    void edit(InvMovimientoDtll invMovimientoDtll);

    void remove(InvMovimientoDtll invMovimientoDtll);

    InvMovimientoDtll find(Object id);

    List<InvMovimientoDtll> findAll();

    List<InvMovimientoDtll> findRange(int[] range);

    int count();
    
}
