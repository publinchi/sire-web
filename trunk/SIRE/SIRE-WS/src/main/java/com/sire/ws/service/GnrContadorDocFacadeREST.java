/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.GnrContadorDoc;
import com.sire.entities.GnrContadorDocPK;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
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
 * @author pestupinan
 */
@Stateless
@Path("com.sire.entities.gnrcontadordoc")
public class GnrContadorDocFacadeREST extends AbstractFacade<GnrContadorDoc> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private GnrContadorDocPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codModulo=codModuloValue;codDocumento=codDocumentoValue;numContador=numContadorValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.GnrContadorDocPK key = new com.sire.entities.GnrContadorDocPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codModulo = map.get("codModulo");
        if (codModulo != null && !codModulo.isEmpty()) {
            key.setCodModulo(codModulo.get(0));
        }
        java.util.List<String> codDocumento = map.get("codDocumento");
        if (codDocumento != null && !codDocumento.isEmpty()) {
            key.setCodDocumento(codDocumento.get(0));
        }
        java.util.List<String> numContador = map.get("numContador");
        if (numContador != null && !numContador.isEmpty()) {
            key.setNumContador(new java.math.BigDecimal(numContador.get(0)));
        }
        return key;
    }

    public GnrContadorDocFacadeREST() {
        super(GnrContadorDoc.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(GnrContadorDoc entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, GnrContadorDoc entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.GnrContadorDocPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GnrContadorDoc find(@PathParam("id") PathSegment id) {
        com.sire.entities.GnrContadorDocPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<GnrContadorDoc> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<GnrContadorDoc> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("{wempresa}/{wcod_modulo}/{wcod_documento}")
    @Produces("application/json")
    public BigDecimal numDocumento(@PathParam("wempresa") String wempresa, @PathParam("wcod_modulo") String wcod_modulo, @PathParam("wcod_documento") String wcod_documento) {
        StoredProcedureQuery query = this.em.createNamedStoredProcedureQuery("PRC_NUM_COMPROBANTE");
        query.setParameter("wempresa", wempresa);
        query.setParameter("wcod_modulo", wcod_modulo);
        query.setParameter("wcod_documento", wcod_documento);
        query.execute();
        BigDecimal wnum_documento = (BigDecimal) query.getOutputParameterValue(4);
//        String mensaje = (String) query.getOutputParameterValue(5);

        return wnum_documento;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
