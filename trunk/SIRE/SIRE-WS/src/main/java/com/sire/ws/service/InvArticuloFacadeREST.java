/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.FacCatalogoPrecioD;
import com.sire.entities.InvArticulo;
import com.sire.entities.InvArticuloPK;
import java.math.BigDecimal;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Administrator
 */
@Stateless
@Path("com.sire.entities.invarticulo")
public class InvArticuloFacadeREST extends AbstractFacade<InvArticulo> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private InvArticuloPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codArticulo=codArticuloValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.InvArticuloPK key = new com.sire.entities.InvArticuloPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codArticulo = map.get("codArticulo");
        if (codArticulo != null && !codArticulo.isEmpty()) {
            key.setCodArticulo(new java.lang.Integer(codArticulo.get(0)));
        }
        return key;
    }

    public InvArticuloFacadeREST() {
        super(InvArticulo.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(InvArticulo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, InvArticulo entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.InvArticuloPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public InvArticulo find(@PathParam("id") PathSegment id) {
        com.sire.entities.InvArticuloPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<InvArticulo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<InvArticulo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("/findByNombreArticulo/{nombreArticulo}")
    @Produces({"application/json"})
    public List<InvArticulo> findByNombreArticulo(@PathParam("nombreArticulo") String nombreArticulo) {
        TypedQuery<InvArticulo> query = em.createNamedQuery("InvArticulo.findByNombreArticuloEstado", InvArticulo.class);
        query.setParameter("nombreArticulo", nombreArticulo + "%");
        query.setParameter("estado", "A");
        List<InvArticulo> retorno = query.getResultList();
        return retorno;
    }

    @GET
    @Path("/findParaVenta/{nombreArticulo}/{codEmpresa}")
    @Produces({"application/json"})
    public List<InvArticulo> findParaVenta(@PathParam("nombreArticulo") String nombreArticulo, @PathParam("codEmpresa") String codEmpresa) {
        TypedQuery<Object[]> query = em.createNamedQuery("InvArticulo.findParaVenta", Object[].class);
        query.setParameter("nombreArticulo", nombreArticulo.replace("$WC", "%AC"));
        query.setParameter("codEmpresa", codEmpresa);
        query.setParameter("estado", "A");
        List<Object[]> retorno = query.getResultList();

        List<InvArticulo> list = new ArrayList<>();
        for (Object[] objects : retorno) {
            ((InvArticulo) objects[0]).setPrecio(((FacCatalogoPrecioD) objects[1]).getPrecioVenta1());
            ((InvArticulo) objects[0]).setUnidad(((FacCatalogoPrecioD) objects[1]).getCodUnidad());
            ((InvArticulo) objects[0]).setExistencia((BigDecimal) objects[2]);
            list.add((InvArticulo) objects[0]);
        }

        return list;
    }

    @GET
    @Path("/findByArticuloEmpresa/{codArticulo}/{codEmpresa}")
    @Produces({"application/json"})
    public List<InvArticulo> findByArticuloEmpresa(@PathParam("codArticulo") String codArticulo, @PathParam("codEmpresa") String codEmpresa) {
        TypedQuery<InvArticulo> query = em.createNamedQuery("InvArticulo.findByArticuloEmpresa", InvArticulo.class);
        query.setParameter("codArticulo", Integer.valueOf(codArticulo));
        query.setParameter("codEmpresa", codEmpresa);
        List<InvArticulo> retorno = query.getResultList();

        return retorno;
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
