/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvMovimientoCab;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvMovimientoCabFacadeRemote {

    void create(InvMovimientoCab invMovimientoCab);

    void edit(InvMovimientoCab invMovimientoCab);

    void remove(InvMovimientoCab invMovimientoCab);

    InvMovimientoCab find(Object id);

    List<InvMovimientoCab> findAll();

    List<InvMovimientoCab> findRange(int[] range);

    int count();
    
}
