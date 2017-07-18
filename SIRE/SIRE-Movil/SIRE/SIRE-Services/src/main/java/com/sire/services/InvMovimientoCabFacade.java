/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.services;

import com.sire.entities.InvMovimientoCab;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class InvMovimientoCabFacade extends AbstractFacade<InvMovimientoCab> implements InvMovimientoCabFacadeRemote {
    @PersistenceContext(unitName = "com.sire_SIRE-Services_ejb_1.0.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvMovimientoCabFacade() {
        super(InvMovimientoCab.class);
    }
    
}
