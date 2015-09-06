/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.FacTmpFactD;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface FacTmpFactDFacadeRemote {

    void create(FacTmpFactD facTmpFactD);

    void edit(FacTmpFactD facTmpFactD);

    void remove(FacTmpFactD facTmpFactD);

    FacTmpFactD find(Object id);

    List<FacTmpFactD> findAll();

    List<FacTmpFactD> findRange(int[] range);

    int count();
    
}
