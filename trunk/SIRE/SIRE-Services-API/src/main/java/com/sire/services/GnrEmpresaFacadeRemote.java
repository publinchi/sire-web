/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.GnrEmpresa;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface GnrEmpresaFacadeRemote {

    void create(GnrEmpresa gnrEmpresa);

    void edit(GnrEmpresa gnrEmpresa);

    void remove(GnrEmpresa gnrEmpresa);

    GnrEmpresa find(Object id);

    List<GnrEmpresa> findAll();

    List<GnrEmpresa> findRange(int[] range);

    int count();
    
}
