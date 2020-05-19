/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.CajRubro;
import com.sire.entities.CajRubroPK;

import java.util.ArrayList;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Administrator
 */
@Stateless
@Path("com.sire.entities.cajrubro")
public class CajRubroFacadeREST extends AbstractFacade<CajRubro> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private CajRubroPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codRubro=codRubroValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.CajRubroPK key = new com.sire.entities.CajRubroPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codRubro = map.get("codRubro");
        if (codRubro != null && !codRubro.isEmpty()) {
            key.setCodRubro(new java.lang.Integer(codRubro.get(0)));
        }
        return key;
    }

    public CajRubroFacadeREST() {
        super(CajRubro.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CajRubro entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, CajRubro entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.CajRubroPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CajRubro find(@PathParam("id") PathSegment id) {
        com.sire.entities.CajRubroPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CajRubro> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CajRubro> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("/empresa/{codEmpresa}")
    @Produces({"application/json"})
    public List<CajRubro> cajRubroByCodEmpresa(@PathParam("codEmpresa") String codEmpresa) {
        TypedQuery<CajRubro> query = em.createNamedQuery("CajRubro.findByCodEmpresa", CajRubro.class);
        query.setParameter("codEmpresa", codEmpresa);

        List<CajRubro> tmpCajRubros = new ArrayList<>();

        for (CajRubro cajRubro : query.getResultList()) {
            tmpCajRubros.add(new CajRubro(
                    cajRubro.getCajRubroPK().getCodEmpresa(), cajRubro.getCajRubroPK().getCodRubro()
            ));
        }

        try {
            return tmpCajRubros;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
