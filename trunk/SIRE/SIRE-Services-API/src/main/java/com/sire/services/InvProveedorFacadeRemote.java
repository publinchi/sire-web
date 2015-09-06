/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvProveedor;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvProveedorFacadeRemote {

    void create(InvProveedor invProveedor);

    void edit(InvProveedor invProveedor);

    void remove(InvProveedor invProveedor);

    InvProveedor find(Object id);

    List<InvProveedor> findAll();

    List<InvProveedor> findRange(int[] range);

    int count();
    
}
