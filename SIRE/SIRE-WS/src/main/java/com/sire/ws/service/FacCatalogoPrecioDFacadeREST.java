/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.*;

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
 * @author pestupinan
 */
@Stateless
@Path("com.sire.entities.faccatalogopreciod")
public class FacCatalogoPrecioDFacadeREST extends AbstractFacade<FacCatalogoPrecioD> {

    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private FacCatalogoPrecioDPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codCatalogo=codCatalogoValue;codArticulo=codArticuloValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.FacCatalogoPrecioDPK key = new com.sire.entities.FacCatalogoPrecioDPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> codEmpresa = map.get("codEmpresa");
        if (codEmpresa != null && !codEmpresa.isEmpty()) {
            key.setCodEmpresa(codEmpresa.get(0));
        }
        java.util.List<String> codCatalogo = map.get("codCatalogo");
        if (codCatalogo != null && !codCatalogo.isEmpty()) {
            key.setCodCatalogo(codCatalogo.get(0));
        }
        java.util.List<String> codArticulo = map.get("codArticulo");
        if (codArticulo != null && !codArticulo.isEmpty()) {
            key.setCodArticulo(new java.lang.Integer(codArticulo.get(0)));
        }
        return key;
    }

    public FacCatalogoPrecioDFacadeREST() {
        super(FacCatalogoPrecioD.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(FacCatalogoPrecioD entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, FacCatalogoPrecioD entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.FacCatalogoPrecioDPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public FacCatalogoPrecioD find(@PathParam("id") PathSegment id) {
        com.sire.entities.FacCatalogoPrecioDPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Path("{codEmpresa}/{codCatalogo}/{from}/{to}")
    @Produces({"application/json"})
    public List<FacCatalogoPrecioD> findAllByCodEmpresaCodCatalogo(@PathParam("codEmpresa") String codEmpresa,
                                                                   @PathParam("codCatalogo") String codCatalogo,
                                                                   @PathParam("from") Integer from,
                                                                   @PathParam("to") Integer to) {
        int[] range = new int[]{from, to};
        List<FacCatalogoPrecioD> facCatalogoPrecioDS = new ArrayList<>();
        TypedQuery<FacCatalogoPrecioD> query = em.createNamedQuery(
                "FacCatalogoPrecioD.findByAnteriorCodEmpresaCodCatalogo", FacCatalogoPrecioD.class);
        query.setParameter("codEmpresa", codEmpresa);
        query.setParameter("codCatalogo", codCatalogo);
        query.setMaxResults(range[1] - range[0] + 1);
        query.setFirstResult(range[0]);
        List<FacCatalogoPrecioD> retorno = query.getResultList();
        for (FacCatalogoPrecioD facCatalogoPrecioD : retorno) {
            FacCatalogoPrecioD facCatalogoPrecioD1 = new FacCatalogoPrecioD();
            facCatalogoPrecioD1.setAnteriorFechaPrecio(facCatalogoPrecioD.getAnteriorFechaPrecio());
            facCatalogoPrecioD1.setAnteriorPrecioVenta(facCatalogoPrecioD.getAnteriorPrecioVenta());
            facCatalogoPrecioD1.setAnteriorUsuarioPrecio(facCatalogoPrecioD.getAnteriorUsuarioPrecio());
            facCatalogoPrecioD1.setAuxPrecio(facCatalogoPrecioD.getAuxPrecio());
            facCatalogoPrecioD1.setCantidadRequerida(facCatalogoPrecioD.getCantidadRequerida());
            facCatalogoPrecioD1.setCodDivisa1(facCatalogoPrecioD.getCodDivisa1());
            facCatalogoPrecioD1.setCodDivisa2(facCatalogoPrecioD.getCodDivisa2());
            facCatalogoPrecioD1.setCodDivisa3(facCatalogoPrecioD.getCodDivisa3());
            facCatalogoPrecioD1.setCodUnidad(facCatalogoPrecioD.getCodUnidad());
            facCatalogoPrecioD1.setComentario(facCatalogoPrecioD.getComentario());
            facCatalogoPrecioD1.setFacCatalogoPrecioC(new FacCatalogoPrecioC(facCatalogoPrecioD.getFacCatalogoPrecioC()
                    .getFacCatalogoPrecioCPK()));
            facCatalogoPrecioD1.setFacCatalogoPrecioDPK(facCatalogoPrecioD.getFacCatalogoPrecioDPK());
            facCatalogoPrecioD1.setFactor(facCatalogoPrecioD.getFactor());
            facCatalogoPrecioD1.setFechaModPrecio1(facCatalogoPrecioD.getFechaModPrecio1());
            facCatalogoPrecioD1.setFechaModPrecio2(facCatalogoPrecioD.getFechaModPrecio2());
            facCatalogoPrecioD1.setFechaModPrecio3(facCatalogoPrecioD.getFechaModPrecio3());
            facCatalogoPrecioD1.setOperador(facCatalogoPrecioD.getOperador());
            facCatalogoPrecioD1.setPorcDescuento(facCatalogoPrecioD.getPorcDescuento());
            facCatalogoPrecioD1.setPrecioVenta1(facCatalogoPrecioD.getPrecioVenta1());
            facCatalogoPrecioD1.setPrecioVenta2(facCatalogoPrecioD.getPrecioVenta2());
            facCatalogoPrecioD1.setPrecioVenta3(facCatalogoPrecioD.getPrecioVenta3());
            facCatalogoPrecioD1.setUsuario(new GnrUsuarios(facCatalogoPrecioD.getUsuario().getNombreUsuario()));

            facCatalogoPrecioDS.add(facCatalogoPrecioD1);
        }
        return facCatalogoPrecioDS;
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<FacCatalogoPrecioD> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<FacCatalogoPrecioD> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
