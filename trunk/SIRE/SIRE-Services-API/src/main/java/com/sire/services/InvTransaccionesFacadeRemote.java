/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvTransacciones;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvTransaccionesFacadeRemote {

    void create(InvTransacciones invTransacciones);

    void edit(InvTransacciones invTransacciones);

    void remove(InvTransacciones invTransacciones);

    InvTransacciones find(Object id);

    List<InvTransacciones> findAll();

    List<InvTransacciones> findRange(int[] range);

    int count();
    
}
