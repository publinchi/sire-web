/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.GnrPersona;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface GnrPersonaFacadeRemote {

    void create(GnrPersona gnrPersona);

    void edit(GnrPersona gnrPersona);

    void remove(GnrPersona gnrPersona);

    GnrPersona find(Object id);

    List<GnrPersona> findAll();

    List<GnrPersona> findRange(int[] range);

    int count();
    
}
