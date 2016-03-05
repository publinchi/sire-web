/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.InvMovimientoCab;
import com.sire.entities.InvMovimientoCabPK;
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
@Path("com.sire.entities.invmovimientocab")
public class InvMovimientoCabFacadeREST extends AbstractFacade<InvMovimientoCab> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private InvMovimientoCabPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codDocumento=codDocumentoValue;numDocumento=numDocumentoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.InvMovimientoCabPK key = new com.sire.entities.InvMovimientoCabPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codDocumento = map.get("codDocumento");
        if (codDocumento != null && !codDocumento.isEmpty()) {
            key.setCodDocumento(codDocumento.get(0));
        }
        java.util.List<String> numDocumento = map.get("numDocumento");
        if (numDocumento != null && !numDocumento.isEmpty()) {
            key.setNumDocumento(new java.lang.Long(numDocumento.get(0)));
        }
        return key;
    }

    public InvMovimientoCabFacadeREST() {
        super(InvMovimientoCab.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(InvMovimientoCab entity) {
//        em.getTransaction().begin();
        super.create(entity);

//        FacTmpFactC facTmpFactC = new FacTmpFactC();
//        facTmpFactC.setCodCliente(entity.getCxcCliente().getCxcClientePK().getCodCliente().longValue());
//        facTmpFactC.setCodDivisa("01");
//        facTmpFactC.setCodDocumento("FAC");
//        facTmpFactC.setCodPago(entity.getFormaPago());
//        // TODO setCodVendedor
//        facTmpFactC.setCodVendedor(1);
//        facTmpFactC.setContCred("1");
//        facTmpFactC.setDescuentos(BigInteger.ZERO);
//        facTmpFactC.setEstado("G");
//        FacTmpFactCPK facTmpFactCPK= new FacTmpFactCPK(entity.getInvMovimientoCabPK().getCodEmpresa(), 0, null);
//        facTmpFactC.setFacTmpFactCPK(null);
//        em.persist(facTmpFactC);
//        em.getTransaction().commit();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, InvMovimientoCab entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.InvMovimientoCabPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public InvMovimientoCab find(@PathParam("id") PathSegment id) {
        com.sire.entities.InvMovimientoCabPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<InvMovimientoCab> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<InvMovimientoCab> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
