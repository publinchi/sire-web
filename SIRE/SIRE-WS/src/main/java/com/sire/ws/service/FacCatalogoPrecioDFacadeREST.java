/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.FacCatalogoPrecioD;
import com.sire.entities.FacCatalogoPrecioDPK;
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
@Path("com.sire.entities.faccatalogopreciod")
public class FacCatalogoPrecioDFacadeREST extends AbstractFacade<FacCatalogoPrecioD> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private FacCatalogoPrecioDPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codCatalogo=codCatalogoValue;codArticulo=codArticuloValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.FacCatalogoPrecioDPK key = new com.sire.entities.FacCatalogoPrecioDPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codCatalogo = map.get("codCatalogo");
        if (codCatalogo != null && !codCatalogo.isEmpty()) {
            key.setCodCatalogo(codCatalogo.get(0));
        }
        java.util.List<String> codArticulo = map.get("codArticulo");
        if (codArticulo != null && !codArticulo.isEmpty()) {
            key.setCodArticulo(new java.lang.Integer(codArticulo.get(0)));
        }
        return key;
    }

    public FacCatalogoPrecioDFacadeREST() {
        super(FacCatalogoPrecioD.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(FacCatalogoPrecioD entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, FacCatalogoPrecioD entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.FacCatalogoPrecioDPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public FacCatalogoPrecioD find(@PathParam("id") PathSegment id) {
        com.sire.entities.FacCatalogoPrecioDPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<FacCatalogoPrecioD> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<FacCatalogoPrecioD> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
