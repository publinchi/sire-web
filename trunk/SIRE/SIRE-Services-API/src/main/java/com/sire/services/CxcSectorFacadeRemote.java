/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.CxcSector;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface CxcSectorFacadeRemote {

    void create(CxcSector cxcSector);

    void edit(CxcSector cxcSector);

    void remove(CxcSector cxcSector);

    CxcSector find(Object id);

    List<CxcSector> findAll();

    List<CxcSector> findRange(int[] range);

    int count();
    
}
