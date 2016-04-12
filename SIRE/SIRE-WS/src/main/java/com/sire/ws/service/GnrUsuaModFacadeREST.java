/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.GnrUsuaMod;
import com.sire.entities.GnrUsuaModPK;
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
 * @author publio
 */
@Stateless
@Path("com.sire.entities.gnrusuamod")
public class GnrUsuaModFacadeREST extends AbstractFacade<GnrUsuaMod> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private GnrUsuaModPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codModulo=codModuloValue;nombreUsuario=nombreUsuarioValue;codEmpresa=codEmpresaValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.GnrUsuaModPK key = new com.sire.entities.GnrUsuaModPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codModulo = map.get("codModulo");
        if (codModulo != null && !codModulo.isEmpty()) {
            key.setCodModulo(codModulo.get(0));
        }
        java.util.List<String> nombreUsuario = map.get("nombreUsuario");
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            key.setNombreUsuario(nombreUsuario.get(0));
        }
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        return key;
    }

    public GnrUsuaModFacadeREST() {
        super(GnrUsuaMod.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(GnrUsuaMod entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, GnrUsuaMod entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.GnrUsuaModPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public GnrUsuaMod find(@PathParam("id") PathSegment id) {
        com.sire.entities.GnrUsuaModPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GnrUsuaMod> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GnrUsuaMod> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("/findByNombreUsuario/{nombreUsuario}")
    @Produces({"application/json"})
    public List<GnrUsuaMod> findByNombreUsuario(@PathParam("nombreUsuario") String nombreUsuario) {
        TypedQuery<GnrUsuaMod> query = em.createNamedQuery("GnrUsuaMod.findByNombreUsuario", GnrUsuaMod.class);
        query.setParameter("nombreUsuario", nombreUsuario);
        query.setParameter("tipoModulo", "M");
        List<GnrUsuaMod> retorno = query.getResultList();
        return retorno;
    }

}
