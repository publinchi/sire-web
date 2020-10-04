/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.CxcCliente;
import com.sire.entities.CxcPagoContado;
import com.sire.entities.CxcPagoContadoPK;
import com.sire.entities.Pago;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
@Path("com.sire.entities.cxcpagocontado")
public class CxcPagoContadoFacadeREST extends AbstractFacade<CxcPagoContado> {
    
    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(FacTmpFactCFacadeREST.class.getName());
    
    private CxcPagoContadoPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codDocumento=codDocumentoValue;numPago=numPagoValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.CxcPagoContadoPK key = new com.sire.entities.CxcPagoContadoPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codDocumento = map.get("codDocumento");
        if (codDocumento != null && !codDocumento.isEmpty()) {
            key.setCodDocumento(codDocumento.get(0));
        }
        java.util.List<String> numPago = map.get("numPago");
        if (numPago != null && !numPago.isEmpty()) {
            key.setNumPago(new java.math.BigInteger(numPago.get(0)));
        }
        return key;
    }
    
    public CxcPagoContadoFacadeREST() {
        super(CxcPagoContado.class);
    }
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CxcPagoContado entity) {
        super.create(entity);
    }
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, CxcPagoContado entity) {
        super.edit(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.CxcPagoContadoPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CxcPagoContado find(@PathParam("id") PathSegment id) {
        com.sire.entities.CxcPagoContadoPK key = getPrimaryKey(id);
        return super.find(key);
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CxcPagoContado> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CxcPagoContado> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("/findByFechas/{fechaInicio}/{fechaFin}/{codEmpresa}/{codVendedor}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Pago> findByFechas(@PathParam("fechaInicio") String fechaInicio,
            @PathParam("fechaFin") String fechaFin, @PathParam("codEmpresa") String codEmpresa,
            @PathParam("codVendedor") Integer codVendedor) {
        List<Pago> pagos = null;
        try {
            TypedQuery<Object[]> query = em.createNamedQuery("CxcPagoContado.findByFechas", Object[].class);
            query.setParameter("fechaInicio", new SimpleDateFormat("dd-MM-yyyy").parse(fechaInicio));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(fechaFin));
            c.add(Calendar.DATE, 1);
            query.setParameter("fechaFin", c.getTime());
            query.setParameter("codEmpresa", codEmpresa);
            query.setParameter("codVendedor", codVendedor);
            List<Object[]> retorno = query.getResultList();
            pagos = new ArrayList<>();
            for (Object[] object : retorno) {
                CxcPagoContado cxcPagoContado = (CxcPagoContado) object[0];
                Pago pago = new Pago();
                CxcPagoContado newCxcPagoContado = new CxcPagoContado();
                CxcPagoContadoPK cxcPagoContadoPK = new CxcPagoContadoPK();
                cxcPagoContadoPK.setCodDocumento(cxcPagoContado.getCxcPagoContadoPK().getCodDocumento());
                cxcPagoContadoPK.setCodEmpresa(cxcPagoContado.getCxcPagoContadoPK().getCodEmpresa());
                cxcPagoContadoPK.setNumPago(cxcPagoContado.getCxcPagoContadoPK().getNumPago());
                newCxcPagoContado.setCxcPagoContadoPK(cxcPagoContadoPK);
                newCxcPagoContado.setCodVendedor(cxcPagoContado.getCodVendedor());
                newCxcPagoContado.setFechaDocumento(cxcPagoContado.getFechaDocumento());
                newCxcPagoContado.setNombreUsuario(cxcPagoContado.getNombreUsuario());
                newCxcPagoContado.setCxcCliente(new CxcCliente(cxcPagoContado.getCxcCliente().getCxcClientePK()));
                newCxcPagoContado.setPagoTotal(cxcPagoContado.getPagoTotal());
                pago.setCxcPagoContado(newCxcPagoContado);
                pago.setRazonSocial((String) object[1]);
                pagos.add(pago);
            }
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return pagos;
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
