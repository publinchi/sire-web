/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvInventario;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvInventarioFacadeRemote {

    void create(InvInventario invInventario);

    void edit(InvInventario invInventario);

    void remove(InvInventario invInventario);

    InvInventario find(Object id);

    List<InvInventario> findAll();

    List<InvInventario> findRange(int[] range);

    int count();
    
}
