/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.InvUnidadAlternativa;
import com.sire.entities.InvUnidadAlternativaPK;

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
@Path("com.sire.entities.invunidadalternativa")
public class InvUnidadAlternativaFacadeREST extends AbstractFacade<InvUnidadAlternativa> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private InvUnidadAlternativaPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codArticulo=codArticuloValue;codUnidad=codUnidadValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.InvUnidadAlternativaPK key = new com.sire.entities.InvUnidadAlternativaPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codArticulo = map.get("codArticulo");
        if (codArticulo != null && !codArticulo.isEmpty()) {
            key.setCodArticulo(new java.lang.Integer(codArticulo.get(0)));
        }
        java.util.List<String> codUnidad = map.get("codUnidad");
        if (codUnidad != null && !codUnidad.isEmpty()) {
            key.setCodUnidad(codUnidad.get(0));
        }
        return key;
    }

    public InvUnidadAlternativaFacadeREST() {
        super(InvUnidadAlternativa.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(InvUnidadAlternativa entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, InvUnidadAlternativa entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.InvUnidadAlternativaPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public InvUnidadAlternativa find(@PathParam("id") PathSegment id) {
        com.sire.entities.InvUnidadAlternativaPK key = getPrimaryKey(id);
        InvUnidadAlternativa invUnidadAlternativa = super.find(key);
        InvUnidadAlternativa tmpInvUnidadAlternativa = new InvUnidadAlternativa(
                invUnidadAlternativa.getInvUnidadAlternativaPK()
        );
        tmpInvUnidadAlternativa.setOperador(invUnidadAlternativa.getOperador());
        tmpInvUnidadAlternativa.setFactor(invUnidadAlternativa.getFactor());
        tmpInvUnidadAlternativa.setEstado(invUnidadAlternativa.getEstado());
        tmpInvUnidadAlternativa.setFechaEstado(tmpInvUnidadAlternativa.getFechaEstado());
        tmpInvUnidadAlternativa.setPeso(tmpInvUnidadAlternativa.getPeso());
        tmpInvUnidadAlternativa.setVolumen(tmpInvUnidadAlternativa.getVolumen());

        return tmpInvUnidadAlternativa;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<InvUnidadAlternativa> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<InvUnidadAlternativa> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("/findByCodArticulo/{codArticulo}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<InvUnidadAlternativa> findByCodArticulo(@PathParam("codArticulo") String codArticulo) {
        TypedQuery<InvUnidadAlternativa> query = em.createNamedQuery("InvUnidadAlternativa.findByCodArticulo", InvUnidadAlternativa.class);
        query.setParameter("codArticulo", Integer.parseInt(codArticulo));
        query.setParameter("estado", "A");
        List<InvUnidadAlternativa> invUnidadAlternativas = query.getResultList();

        List<InvUnidadAlternativa> tmpInvUnidadAlternativas = new ArrayList<>();

        for (InvUnidadAlternativa invUnidadAlternativa : invUnidadAlternativas) {
            tmpInvUnidadAlternativas.add(new InvUnidadAlternativa(invUnidadAlternativa.getInvUnidadAlternativaPK()));
        }

        return tmpInvUnidadAlternativas;
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
