/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.VCliente;
import java.math.BigInteger;
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

/**
 *
 * @author Administrator
 */
@Stateless
@Path("com.sire.entities.vcliente")
public class VClienteFacadeREST extends AbstractFacade<VCliente> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    public VClienteFacadeREST() {
        super(VCliente.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(VCliente entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, VCliente entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public VCliente find(@PathParam("id") BigInteger id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<VCliente> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<VCliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("/findByApellidos/{apellidos}")
    @Produces({"application/json"})
    public List<VCliente> findByApellidos(@PathParam("apellidos") String apellidos) {
        TypedQuery<VCliente> query = em.createNamedQuery("VCliente.findByApellidos", VCliente.class);
        query.setParameter("apellidos", apellidos.toUpperCase() + "%");
        List<VCliente> retorno = query.getResultList();
        return retorno;
    }

    @GET
    @Path("/findByRazonSocial/{razonSocial}")
    @Produces({"application/json"})
    public List<VCliente> findByRazonSocial(@PathParam("razonSocial") String razonSocial) {
        TypedQuery<VCliente> query = em.createNamedQuery("VCliente.findByRazonSocial", VCliente.class);
        query.setParameter("razonSocial", razonSocial.toUpperCase());
        List<VCliente> retorno = query.getResultList();
        return retorno;
    }

    @GET
    @Path("/findByClienteEmpresa/{codCliente}/{codEmpresa}")
    @Produces({"application/json"})
    public List<VCliente> findByClienteEmpresa(@PathParam("codCliente") String codCliente, @PathParam("codEmpresa") String codEmpresa) {
        TypedQuery<VCliente> query = em.createNamedQuery("VCliente.findByClienteEmpresa", VCliente.class);
        query.setParameter("codCliente", Integer.valueOf(codCliente));
        query.setParameter("codEmpresa", codEmpresa);
        List<VCliente> retorno = query.getResultList();
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
