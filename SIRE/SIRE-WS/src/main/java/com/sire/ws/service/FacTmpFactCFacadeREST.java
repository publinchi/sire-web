/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.FacTmpFactC;
import com.sire.entities.FacTmpFactCPK;
import com.sire.entities.Pedido;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author root
 */
@Stateless
@Path("com.sire.entities.factmpfactc")
public class FacTmpFactCFacadeREST extends AbstractFacade<FacTmpFactC> {
    
    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;
    
    private FacTmpFactCPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;egresoInv=egresoInvValue;ei=eiValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.FacTmpFactCPK key = new com.sire.entities.FacTmpFactCPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> egresoInv = map.get("egresoInv");
        if (egresoInv != null && !egresoInv.isEmpty()) {
            key.setEgresoInv(new java.lang.Integer(egresoInv.get(0)));
        }
        java.util.List<String> ei = map.get("ei");
        if (ei != null && !ei.isEmpty()) {
            key.setEi(ei.get(0));
        }
        return key;
    }
    
    public FacTmpFactCFacadeREST() {
        super(FacTmpFactC.class);
    }
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(FacTmpFactC entity) {
        super.create(entity);
    }
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, FacTmpFactC entity) {
        super.edit(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.FacTmpFactCPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FacTmpFactC find(@PathParam("id") PathSegment id) {
        com.sire.entities.FacTmpFactCPK key = getPrimaryKey(id);
        return super.find(key);
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FacTmpFactC> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FacTmpFactC> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("/findByFechas/{fechaInicio}/{fechaFin}/{codEmpresa}/{codVendedor}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Pedido> findByFechas(@PathParam("fechaInicio") String fechaInicio,
            @PathParam("fechaFin") String fechaFin, @PathParam("codEmpresa") String codEmpresa,
            @PathParam("codVendedor") Integer codVendedor) {
        List<Pedido> pedidos = null;
        try {
            TypedQuery<FacTmpFactC> query = em.createNamedQuery("FacTmpFactC.findByFechas", FacTmpFactC.class);
            query.setParameter("fechaInicio", new SimpleDateFormat("dd-MM-yyyy").parse(fechaInicio));
            query.setParameter("fechaFin", new SimpleDateFormat("dd-MM-yyyy").parse(fechaFin));
            query.setParameter("codEmpresa", codEmpresa);
            query.setParameter("codVendedor", codVendedor);
            List<FacTmpFactC> retorno = query.getResultList();
            pedidos = new ArrayList<>();
            for (FacTmpFactC facTmpFactC : retorno) {
                Pedido pedido = new Pedido();
                FacTmpFactC newFacTmpFactC = new FacTmpFactC();
                FacTmpFactCPK facTmpFactCPK = new FacTmpFactCPK();
                facTmpFactCPK.setEgresoInv(facTmpFactC.getFacTmpFactCPK().getEgresoInv());
                newFacTmpFactC.setFacTmpFactCPK(facTmpFactCPK);
                newFacTmpFactC.setCodCliente(facTmpFactC.getCodCliente());
                newFacTmpFactC.setFechaFactura(facTmpFactC.getFechaFactura());
                newFacTmpFactC.setTotalConIva(facTmpFactC.getTotalConIva());
                pedido.setFacTmpFactC(newFacTmpFactC);
                pedidos.add(pedido);
            }
        } catch (ParseException ex) {
            Logger.getLogger(FacTmpFactCFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pedidos;
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
    
}
