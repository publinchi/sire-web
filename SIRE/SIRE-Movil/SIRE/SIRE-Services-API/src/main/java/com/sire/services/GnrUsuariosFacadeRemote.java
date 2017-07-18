/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.GnrUsuarios;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface GnrUsuariosFacadeRemote {

    void create(GnrUsuarios gnrUsuarios);

    void edit(GnrUsuarios gnrUsuarios);

    void remove(GnrUsuarios gnrUsuarios);

    GnrUsuarios find(Object id);

    List<GnrUsuarios> findAll();

    List<GnrUsuarios> findRange(int[] range);

    int count();
    
}
