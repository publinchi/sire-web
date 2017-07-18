/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvUnidadAlternativa;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvUnidadAlternativaFacadeRemote {

    void create(InvUnidadAlternativa invUnidadAlternativa);

    void edit(InvUnidadAlternativa invUnidadAlternativa);

    void remove(InvUnidadAlternativa invUnidadAlternativa);

    InvUnidadAlternativa find(Object id);

    List<InvUnidadAlternativa> findAll();

    List<InvUnidadAlternativa> findRange(int[] range);

    int count();
    
}
