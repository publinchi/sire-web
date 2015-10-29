/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.InvBodegaArt;
import com.sire.entities.InvBodegaArtPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
@Path("com.sire.entities.invbodegaart")
public class InvBodegaArtFacadeREST extends AbstractFacade<InvBodegaArt> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private InvBodegaArtPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codBodega=codBodegaValue;codArticulo=codArticuloValue;codInventario=codInventarioValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.InvBodegaArtPK key = new com.sire.entities.InvBodegaArtPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codBodega = map.get("codBodega");
        if (codBodega != null && !codBodega.isEmpty()) {
            key.setCodBodega(codBodega.get(0));
        }
        java.util.List<String> codArticulo = map.get("codArticulo");
        if (codArticulo != null && !codArticulo.isEmpty()) {
            key.setCodArticulo(new java.lang.Integer(codArticulo.get(0)));
        }
        java.util.List<String> codInventario = map.get("codInventario");
        if (codInventario != null && !codInventario.isEmpty()) {
            key.setCodInventario(codInventario.get(0));
        }
        return key;
    }

    public InvBodegaArtFacadeREST() {
        super(InvBodegaArt.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(InvBodegaArt entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, InvBodegaArt entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.InvBodegaArtPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public InvBodegaArt find(@PathParam("id") PathSegment id) {
        com.sire.entities.InvBodegaArtPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Path("/findByCodArticulo/{codArticulo}")
    @Produces({"application/xml", "application/json"})
    public List<InvBodegaArt> findByCodArticulo(@PathParam("codArticulo") Integer codArticulo) {
        TypedQuery<InvBodegaArt> query = em.createNamedQuery("InvBodegaArt.findByCodArticulo", InvBodegaArt.class);
        query.setParameter("codArticulo", codArticulo);
        List<InvBodegaArt> invBodegaArts = query.getResultList();
        return invBodegaArts;
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<InvBodegaArt> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<InvBodegaArt> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
