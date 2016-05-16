/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.CajFacturaEnviada;
import com.sire.entities.CajFacturaEnviadaPK;
import com.sire.errorhandling.AppException;
import com.sire.errorhandling.ErrorMessage;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author publio
 */
@Stateless
@Path("com.sire.entities.cajfacturaenviada")
public class CajFacturaEnviadaFacadeREST extends AbstractFacade<CajFacturaEnviada> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private CajFacturaEnviadaPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codProyecto=codProyectoValue;codSubproyecto=codSubproyectoValue;codSupervisor=codSupervisorValue;rucCiProveedor=rucCiProveedorValue;numDocumento=numDocumentoValue;secuencial=secuencialValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.CajFacturaEnviadaPK key = new com.sire.entities.CajFacturaEnviadaPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codProyecto = map.get("codProyecto");
        if (codProyecto != null && !codProyecto.isEmpty()) {
            key.setCodProyecto(new java.lang.Integer(codProyecto.get(0)));
        }
        java.util.List<String> codSubproyecto = map.get("codSubproyecto");
        if (codSubproyecto != null && !codSubproyecto.isEmpty()) {
            key.setCodSubproyecto(new java.lang.Integer(codSubproyecto.get(0)));
        }
        java.util.List<String> codSupervisor = map.get("codSupervisor");
        if (codSupervisor != null && !codSupervisor.isEmpty()) {
            key.setCodSupervisor(new java.lang.Integer(codSupervisor.get(0)));
        }
        java.util.List<String> rucCiProveedor = map.get("rucCiProveedor");
        if (rucCiProveedor != null && !rucCiProveedor.isEmpty()) {
            key.setRucCiProveedor(rucCiProveedor.get(0));
        }
        java.util.List<String> numDocumento = map.get("numDocumento");
        if (numDocumento != null && !numDocumento.isEmpty()) {
            key.setNumDocumento(numDocumento.get(0));
        }
        java.util.List<String> secuencial = map.get("secuencial");
        if (secuencial != null && !secuencial.isEmpty()) {
            key.setSecuencial(new java.lang.Integer(secuencial.get(0)));
        }
        return key;
    }

    public CajFacturaEnviadaFacadeREST() {
        super(CajFacturaEnviada.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CajFacturaEnviada entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, CajFacturaEnviada entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.CajFacturaEnviadaPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CajFacturaEnviada find(@PathParam("id") PathSegment id) {
        com.sire.entities.CajFacturaEnviadaPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CajFacturaEnviada> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CajFacturaEnviada> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @PUT
    @Path("save")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response save(CajFacturaEnviada entity) {
        // TODO https://rodrigouchoa.wordpress.com/2014/10/22/cdi-ejb-sending-asynchronous-mail-on-transaction-success/
        // TODO: sendEmail(pago);
        try {
            validarFactura(entity);
            super.create(entity);

            return Response.ok().build();
        } catch (AppException ex) {
            Logger.getLogger(CajFacturaEnviadaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);

            return Response.status(ex.getStatus())
                    .entity(new ErrorMessage(ex))
                    .type(MediaType.APPLICATION_JSON).
                    build();
        }
    }

    private void validarFactura(CajFacturaEnviada entity) throws AppException {
        TypedQuery<CajFacturaEnviada> query = em.createNamedQuery("CajFacturaEnviada.findByRucCiProveedorNumDocumento", CajFacturaEnviada.class);
        query.setParameter("rucCiProveedor", entity.getCajFacturaEnviadaPK().getRucCiProveedor());
        query.setParameter("numDocumento", entity.getCajFacturaEnviadaPK().getNumDocumento());
        List<CajFacturaEnviada> retorno = query.getResultList();

        if (!retorno.isEmpty()) {
            AppException appException = new AppException();
            appException.setStatus(404);
            appException.setCode(404);
            appException.setDeveloperMessage("La factura enviada ya ha sido registrada anteriormente.");
            throw appException; //To change body of generated methods, choose Tools | Templates.
        }
    }
}
