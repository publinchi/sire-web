/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.CxcZona;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface CxcZonaFacadeRemote {

    void create(CxcZona cxcZona);

    void edit(CxcZona cxcZona);

    void remove(CxcZona cxcZona);

    CxcZona find(Object id);

    List<CxcZona> findAll();

    List<CxcZona> findRange(int[] range);

    int count();
    
}
