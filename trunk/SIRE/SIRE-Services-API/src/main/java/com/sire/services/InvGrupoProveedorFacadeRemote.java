/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvGrupoProveedor;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface InvGrupoProveedorFacadeRemote {

    void create(InvGrupoProveedor invGrupoProveedor);

    void edit(InvGrupoProveedor invGrupoProveedor);

    void remove(InvGrupoProveedor invGrupoProveedor);

    InvGrupoProveedor find(Object id);

    List<InvGrupoProveedor> findAll();

    List<InvGrupoProveedor> findRange(int[] range);

    int count();
    
}
