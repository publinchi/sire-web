/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.CxcClaseCliente;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface CxcClaseClienteFacadeRemote {

    void create(CxcClaseCliente cxcClaseCliente);

    void edit(CxcClaseCliente cxcClaseCliente);

    void remove(CxcClaseCliente cxcClaseCliente);

    CxcClaseCliente find(Object id);

    List<CxcClaseCliente> findAll();

    List<CxcClaseCliente> findRange(int[] range);

    int count();
    
}
