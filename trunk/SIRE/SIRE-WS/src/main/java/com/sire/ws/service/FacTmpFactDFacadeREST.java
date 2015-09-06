/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.FacTmpFactD;
import com.sire.entities.FacTmpFactDPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Administrator
 */
@Stateless
@Path("com.sire.entities.factmpfactd")
public class FacTmpFactDFacadeREST extends AbstractFacade<FacTmpFactD> {
    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private FacTmpFactDPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;ei=eiValue;egresoInv=egresoInvValue;auxiliar=auxiliarValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.FacTmpFactDPK key = new com.sire.entities.FacTmpFactDPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> ei = map.get("ei");
        if (ei != null && !ei.isEmpty()) {
            key.setEi(ei.get(0));
        }
        java.util.List<String> egresoInv = map.get("egresoInv");
        if (egresoInv != null && !egresoInv.isEmpty()) {
            key.setEgresoInv(new java.lang.Integer(egresoInv.get(0)));
        }
        java.util.List<String> auxiliar = map.get("auxiliar");
        if (auxiliar != null && !auxiliar.isEmpty()) {
            key.setAuxiliar(new java.lang.Long(auxiliar.get(0)));
        }
        return key;
    }

    public FacTmpFactDFacadeREST() {
        super(FacTmpFactD.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(FacTmpFactD entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, FacTmpFactD entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.FacTmpFactDPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public FacTmpFactD find(@PathParam("id") PathSegment id) {
        com.sire.entities.FacTmpFactDPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<FacTmpFactD> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<FacTmpFactD> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
