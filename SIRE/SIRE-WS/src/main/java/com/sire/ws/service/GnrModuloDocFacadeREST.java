/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.GnrModuloDoc;
import com.sire.entities.GnrModuloDocPK;
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
 * @author pestupinan
 */
@Stateless
@Path("com.sire.entities.gnrmodulodoc")
public class GnrModuloDocFacadeREST extends AbstractFacade<GnrModuloDoc> {
    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private GnrModuloDocPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codModulo=codModuloValue;codDocumento=codDocumentoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.GnrModuloDocPK key = new com.sire.entities.GnrModuloDocPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codModulo = map.get("codModulo");
        if (codModulo != null && !codModulo.isEmpty()) {
            key.setCodModulo(codModulo.get(0));
        }
        java.util.List<String> codDocumento = map.get("codDocumento");
        if (codDocumento != null && !codDocumento.isEmpty()) {
            key.setCodDocumento(codDocumento.get(0));
        }
        return key;
    }

    public GnrModuloDocFacadeREST() {
        super(GnrModuloDoc.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(GnrModuloDoc entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, GnrModuloDoc entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.GnrModuloDocPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GnrModuloDoc find(@PathParam("id") PathSegment id) {
        com.sire.entities.GnrModuloDocPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<GnrModuloDoc> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GnrModuloDoc> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
