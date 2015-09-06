/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvBodegaArt;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvBodegaArtFacadeRemote {

    void create(InvBodegaArt invBodegaArt);

    void edit(InvBodegaArt invBodegaArt);

    void remove(InvBodegaArt invBodegaArt);

    InvBodegaArt find(Object id);

    List<InvBodegaArt> findAll();

    List<InvBodegaArt> findRange(int[] range);

    int count();
    
}
