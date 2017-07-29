/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.InvBodegaArt;
import com.sire.entities.InvBodegaArtPK;
import com.sire.entities.InvMovimientoCab;
import com.sire.entities.InvMovimientoCabPK;
import com.sire.entities.InvMovimientoDtll;
import com.sire.entities.Pedido;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @Consumes({"application/xml", "application/json"})
    public void create(Pedido entity) {
        try {
            getEntityManager().persist(entity.getGnrLogHistorico());

            super.create(entity.getInvMovimientoCab());
            getEntityManager().persist(entity.getFacTmpFactC());

            Logger.getLogger(InvMovimientoCabFacadeREST.class.getName()).info("Actualizando...");
            for (InvMovimientoDtll invMovimientoDtll : entity.getInvMovimientoCab().getInvMovimientoDtllList()) {
                InvBodegaArtPK invBodegaArtPK = new InvBodegaArtPK();

                Integer codArticulo = invMovimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo();
                invBodegaArtPK.setCodArticulo(codArticulo);

                String codBodega = invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodBodega();
                invBodegaArtPK.setCodBodega(codBodega);

                String codEmpresa = invMovimientoDtll.getInvMovimientoDtllPK().getCodEmpresa();
                invBodegaArtPK.setCodEmpresa(codEmpresa);

                String codInventario = invMovimientoDtll.getInvBodegaArt().getInvInventario().getInvInventarioPK().getCodInventario();
                invBodegaArtPK.setCodInventario(codInventario);

                InvBodegaArt invBodegaArt = em.find(InvBodegaArt.class, invBodegaArtPK);

                Double antiguaExistencia = invBodegaArt.getExistencia().doubleValue();

                Double nuevaExistencia = antiguaExistencia - invMovimientoDtll.getAuxCantidad();

                Logger.getLogger(InvMovimientoCabFacadeREST.class.getName())
                        .info("codArticulo: " + codArticulo.toString()
                                + ", codBodega: " + codBodega
                                + ", codEmpresa: " + codEmpresa
                                + ", codInventario: " + codInventario
                                + ", invBodegaArt: " + invBodegaArt.toString());

                Logger.getLogger(InvMovimientoCabFacadeREST.class.getName())
                        .info("Cantidad: " + invMovimientoDtll.getCantidad()
                                + ", Antigua Existencia: " + antiguaExistencia
                                + ", Nueva Existencia: " + nuevaExistencia
                        );

                if (nuevaExistencia >= 0) {
                    invBodegaArt.setExistencia(new BigDecimal(nuevaExistencia));
                    Logger.getLogger(InvMovimientoCabFacadeREST.class.getName()).log(Level.INFO, "Existencia actualizada.");
                } else {
                    throw new SQLException("Cantidad de articulo " + codArticulo + " superior a la disponible.");
                }
            }
        } catch (SQLException se) {
            Logger.getLogger(InvMovimientoCabFacadeREST.class.getName()).log(Level.SEVERE, se.getMessage());
        }

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
