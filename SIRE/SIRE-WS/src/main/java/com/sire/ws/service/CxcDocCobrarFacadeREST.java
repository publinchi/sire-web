/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.ws.service;

import com.sire.entities.CxcCheque;
import com.sire.entities.CxcDocCobrar;
import com.sire.entities.CxcDocCobrarPK;
import com.sire.entities.Pago;
import com.sire.event.MailEvent;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
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
@Path("com.sire.entities.cxcdoccobrar")
public class CxcDocCobrarFacadeREST extends AbstractFacade<CxcDocCobrar> {

    @Inject
    private Event<MailEvent> eventProducer;
    @PersistenceContext(unitName = "com.sire_SIRE-WS_war_1.0.0PU")
    private EntityManager em;

    private CxcDocCobrarPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;codEmpresa=codEmpresaValue;codDocumento=codDocumentoValue;numDocumento=numDocumentoValue;numeroCuota=numeroCuotaValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.sire.entities.CxcDocCobrarPK key = new com.sire.entities.CxcDocCobrarPK();
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
            key.setNumDocumento(new java.math.BigInteger(numDocumento.get(0)));
        }
        java.util.List<String> numeroCuota = map.get("numeroCuota");
        if (numeroCuota != null && !numeroCuota.isEmpty()) {
            key.setNumeroCuota(new java.math.BigInteger(numeroCuota.get(0)));
        }
        return key;
    }

    public CxcDocCobrarFacadeREST() {
        super(CxcDocCobrar.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CxcDocCobrar entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, CxcDocCobrar entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.sire.entities.CxcDocCobrarPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CxcDocCobrar find(@PathParam("id") PathSegment id) {
        com.sire.entities.CxcDocCobrarPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CxcDocCobrar> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CxcDocCobrar> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @GET
    @Path("/findByCodCliente/{codCliente}")
    @Produces({"application/json"})
    public List<CxcDocCobrar> findByCodCliente(@PathParam("codCliente") String codCliente) {
        TypedQuery<CxcDocCobrar> query = em.createNamedQuery("CxcDocCobrar.findByCodCliente", CxcDocCobrar.class);
        query.setParameter("codCliente", new BigInteger(codCliente));
        List<CxcDocCobrar> retorno = query.getResultList();
        return retorno;
    }

    @GET
    @Path("/findByCodClienteCodVendedor/{codCliente}/{codVendedor}")
    @Produces({"application/json"})
    public List<CxcDocCobrar> findByCodClienteCodVendedor(@PathParam("codCliente") String codCliente,
                                                          @PathParam("codVendedor") String codVendedor) {
        TypedQuery<CxcDocCobrar> query = em.createNamedQuery("CxcDocCobrar.findByCodClienteCodVendedor", CxcDocCobrar.class);
        query.setParameter("codCliente", new BigInteger(codCliente));
        query.setParameter("codVendedor", new BigInteger(codVendedor));

        List<CxcDocCobrar> tmpCxcDocCobrars = new ArrayList<>();

        for (CxcDocCobrar cxcDocCobrar : query.getResultList()) {
            CxcDocCobrar tmpCxcDocCobrar = new CxcDocCobrar(
                    cxcDocCobrar.getCxcDocCobrarPK(),
                    cxcDocCobrar.getDiasPlazo(),
                    cxcDocCobrar.getPorcComision(),
                    cxcDocCobrar.getValorDocumento(),
                    cxcDocCobrar.getSaldoDocumento(),
                    cxcDocCobrar.getNroPagos()
            );
            tmpCxcDocCobrars.add(tmpCxcDocCobrar);
        }

        return tmpCxcDocCobrars;
    }

    @GET
    @Path("/sumSaldoDocumentoByCodClienteCodEmpresaFechaFac/{codCliente}/{codEmpresa}/{fechaFac}")
    @Produces({"application/json"})
    public String sumSaldoDocumentoByCodClienteCodEmpresaFechaFac(@PathParam("codCliente") String codCliente, @PathParam("codEmpresa") String codEmpresa, @PathParam("fechaFac") String fechaFac) {
        TypedQuery<Double> query = em.createNamedQuery("CxcDocCobrar.sumSaldoDocumentoByCodClienteCodEmpresaFechaFac", Double.class);
        query.setParameter("codCliente", new BigInteger(codCliente));
        query.setParameter("codEmpresa", codEmpresa);
        query.setParameter("fechaFac", fechaFac);

        Double sum = query.getSingleResult();
        if (sum != null) {
            return sum.toString();
        } else {
            return "";
        }
    }

    @GET
    @Path("/sumCapitalByCodClienteCodEmpresaFechaRecepcion/{codCliente}/{codEmpresa}/{fechaFac}")
    @Produces({"application/json"})
    public String sumCapitalByCodClienteCodEmpresaFechaRecepcion(@PathParam("codCliente") String codCliente, @PathParam("codEmpresa") String codEmpresa, @PathParam("fechaRecepcion") String fechaRecepcion) {
        TypedQuery<CxcCheque> q = em.createNamedQuery("CxcCheque.findByCodClienteCodEmpresaMes", CxcCheque.class);
        q.setParameter("codCliente", new BigInteger(codCliente));
        q.setParameter("codEmpresa", codEmpresa);
        q.setParameter("fechaRecepcion", fechaRecepcion);

        List<CxcCheque> cxcCheques = q.getResultList();

        Double total = 0.0;
        for (CxcCheque cxcCheque : cxcCheques) {
            TypedQuery<Double> query = em.createNamedQuery("CxcDocCobrar.sumCapitalByCodClienteCodEmpresaFechaEmision", Double.class);
            query.setParameter("codEmpresa", codEmpresa);
            query.setParameter("codDocumento", cxcCheque.getCxcChequePK().getCodDocumento());
            query.setParameter("numDocumento", cxcCheque.getCxcChequePK().getNumDocumento());
            query.setParameter("fechaEmision", fechaRecepcion);

            Double sum = query.getSingleResult();
            if (sum != null) {
                total += sum;
            }
        }
        return total.toString();
    }

    @PUT
    @Path("save")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response save(Pago pago) {
        getEntityManager().persist(pago.getGnrLogHistorico());

        for (CxcDocCobrar cxcDocCobrar : pago.getCxcDocCobrarList()) {
            super.edit(cxcDocCobrar);
        }

        pago.getCxcAbonoC().setCxcAbonoDList(pago.getCxcAbonoDList());

        getEntityManager().persist(pago.getCxcAbonoC());

        getEntityManager().persist(pago.getCxcPagoContado());

        for (CxcCheque cxcCheque : pago.getCxcChequeList()) {
            getEntityManager().persist(cxcCheque);
        }

        // TODO https://rodrigouchoa.wordpress.com/2014/10/22/cdi-ejb-sending-asynchronous-mail-on-transaction-success/
        // TODO: sendEmail(pago);
        return Response.ok().build();
    }

    private void sendEmail(Pago pago) {
        MailEvent event = new MailEvent();
        event.setTo(pago.getClientMail());
        event.setSubject("Cobro.");

        String saltoLinea = System.getProperty("line.separator");
        StringBuilder msg = new StringBuilder();
        msg.append("Documento Nº: ");
        msg.append(pago.getGnrLogHistorico().getGnrLogHistoricoPK().getNumDocumento());
        msg.append(saltoLinea);
        msg.append("Monto: ");
        msg.append(pago.getCxcAbonoC().getTotalCapital());
        msg.append(saltoLinea);
        msg.append("Ret. Fuente: ");
        msg.append(pago.getCxcPagoContado().getRetencion());
        msg.append(saltoLinea);
        msg.append("Dev. Descuento: ");
        msg.append("");
        msg.append(saltoLinea);
        msg.append("Cheques: ");
        msg.append(saltoLinea);
        for (CxcCheque cxcCheque : pago.getCxcChequeList()) {
            msg.append("Banco: ");
            msg.append(cxcCheque.getCodBanco());
            msg.append(saltoLinea);
            msg.append("Cheque Nº: ");
            msg.append(cxcCheque.getNumCheque());
            msg.append(saltoLinea);
            msg.append("Valor: ");
            msg.append(cxcCheque.getValorCheque());
        }

        event.setMessage(msg.toString());

        eventProducer.fire(event); //firing event!
    }
}
