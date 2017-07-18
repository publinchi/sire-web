/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.GnrDivisa;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface GnrDivisaFacadeRemote {

    void create(GnrDivisa gnrDivisa);

    void edit(GnrDivisa gnrDivisa);

    void remove(GnrDivisa gnrDivisa);

    GnrDivisa find(Object id);

    List<GnrDivisa> findAll();

    List<GnrDivisa> findRange(int[] range);

    int count();
    
}
