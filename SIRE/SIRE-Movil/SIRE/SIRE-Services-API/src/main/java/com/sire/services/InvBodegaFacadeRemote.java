/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvBodega;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvBodegaFacadeRemote {

    void create(InvBodega invBodega);

    void edit(InvBodega invBodega);

    void remove(InvBodega invBodega);

    InvBodega find(Object id);

    List<InvBodega> findAll();

    List<InvBodega> findRange(int[] range);

    int count();
    
}
