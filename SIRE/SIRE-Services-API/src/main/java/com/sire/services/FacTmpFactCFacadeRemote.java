/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.FacTmpFactC;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface FacTmpFactCFacadeRemote {

    void create(FacTmpFactC facTmpFactC);

    void edit(FacTmpFactC facTmpFactC);

    void remove(FacTmpFactC facTmpFactC);

    FacTmpFactC find(Object id);

    List<FacTmpFactC> findAll();

    List<FacTmpFactC> findRange(int[] range);

    int count();
    
}
