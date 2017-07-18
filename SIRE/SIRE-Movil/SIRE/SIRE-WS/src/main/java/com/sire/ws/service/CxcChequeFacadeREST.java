/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.CxcCheque;
import com.sire.entities.CxcChequePK;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author publio
 */
@Stateless
@Path("com.sire.entities.cxccheque")
public class CxcChequeFacadeREST extends AbstractFacade<CxcCheque> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private CxcChequePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codDocumento=codDocumentoValue;numDocumento=numDocumentoValue;auxiliar=auxiliarValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.CxcChequePK key = new com.sire.entities.CxcChequePK();
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
        java.util.List<String> auxiliar = map.get("auxiliar");
        if (auxiliar != null && !auxiliar.isEmpty()) {
            key.setAuxiliar(new java.lang.Integer(auxiliar.get(0)));
        }
        return key;
    }

    public CxcChequeFacadeREST() {
        super(CxcCheque.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CxcCheque entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, CxcCheque entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.CxcChequePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CxcCheque find(@PathParam("id") PathSegment id) {
        com.sire.entities.CxcChequePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CxcCheque> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CxcCheque> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("/findByCodClienteCodEmpresaMes/{codCliente}/{codEmpresa}/{fechaFac}")
    @Produces({"application/json"})
    public List<CxcCheque> findByCodClienteCodEmpresaMes(@PathParam("codCliente") String codCliente, @PathParam("codEmpresa") String codEmpresa, @PathParam("fechaRecepcion") String fechaRecepcion) {
        TypedQuery<CxcCheque> query = em.createNamedQuery("CxcCheque.findByCodClienteCodEmpresaMes", CxcCheque.class);
        query.setParameter("codCliente", new BigInteger(codCliente));
        query.setParameter("codEmpresa", codEmpresa);
        query.setParameter("fechaRecepcion", fechaRecepcion);

        return query.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
