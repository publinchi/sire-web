/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.FacParametros;
import com.sire.entities.FacTmpFactD;
import com.sire.entities.InvArticulo;
import com.sire.entities.Pago;
import com.sire.entities.Pedido;
import com.sire.exception.ClienteException;
import com.sire.exception.VendedorException;
import com.sire.rs.client.CxcPagoContadoFacadeREST;
import com.sire.rs.client.FacParametrosFacadeREST;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author root
 */
@ManagedBean(name = "pagosBean")
@SessionScoped
public class PagosBean {

    private static final Logger logger = Logger.getLogger(PagosBean.class.getName());
    @Getter
    @Setter
    private Date fechaInicio, fechaFin;
    private final GsonBuilder builder;
    private final Gson gson;
    private final FacParametrosFacadeREST facParametrosFacadeREST;
    @ManagedProperty(value = "#{user}")
    @Getter
    @Setter
    private UserManager userManager;
    @ManagedProperty("#{cliente}")
    @Getter
    @Setter
    private CustomerBean cliente;
    @Getter
    @Setter
    private List<Pago> pagos;
    @Getter
    @Setter
    private List<FacTmpFactD> detallesPago;
    @Getter
    @Setter
    private List<InvArticulo> invArticulos;
    private Pago pagoSeleccionado;
    private final CxcPagoContadoFacadeREST cxcPagoContadoFacadeREST;

    public PagosBean() {
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        cxcPagoContadoFacadeREST = new CxcPagoContadoFacadeREST();
        facParametrosFacadeREST = new FacParametrosFacadeREST();
    }

    public void consultarPagos() {
        logger.info("consultarPagos");
        invArticulos = null;
        try {
            if (cliente.getCliente() == null) {
                throw new ClienteException("Por favor, seleccione el cliente.");
            } else {
                pagos = gson.fromJson(cxcPagoContadoFacadeREST.findByFechas_JSON(String.class, fechaInicio,
                        fechaFin, obtenerEmpresa(), cliente.getCliente().getCodVendedor()), new TypeToken<java.util.List<Pedido>>() {
                }.getType()
                );
                logger.log(Level.INFO, "pagos: {0}", pagos.size());
            }
        } catch (ClienteException ex) {
            logger.log(Level.WARNING, null, ex);
            addMessage("Advertencia", ex.getMessage(), FacesMessage.SEVERITY_WARN);
        }

    }

    public void tapPago(SelectEvent event) {
//        logger.log(Level.INFO, "\u00b7\u00b7 tapPago \u00b7\u00b7 {0}", event.getObject());
//        invArticulos = new ArrayList<>();
//        pagoSeleccionado = ((Pago) event.getObject());
//        logger.log(Level.INFO, "# NumPago pago seleccionado: {0}",
//                pagoSeleccionado.getCxcPagoContado().getCxcPagoContadoPK().getNumPago());
//        detallesPago = gson.fromJson(facTmpFactDFacadeREST.findByFacTmpFactC_JSON(String.class, obtenerEmpresa(),
//                pagoSeleccionado.getFacTmpFactC().getFacTmpFactCPK().getEgresoInv(),
//                pagoSeleccionado.getFacTmpFactC().getFacTmpFactCPK().getEi()), new TypeToken<java.util.List<FacTmpFactD>>() {
//        }.getType());
//
//        for (FacTmpFactD facTmpFactD : detallesPago) {
//            InvArticulo invArticulo = invArticuloFacadeREST.find_JSON(
//                    InvArticulo.class, "id;codEmpresa=" + facTmpFactD.getInvUnidadAlternativa().getInvUnidadAlternativaPK().getCodEmpresa() + ";codArticulo=" + facTmpFactD.getInvUnidadAlternativa().getInvUnidadAlternativaPK().getCodArticulo()
//            );
//            invArticulos.add(invArticulo);
//        }
//
//        logger.log(Level.INFO, "# Articulos: {0}", invArticulos.size());
    }

    private String obtenerEmpresa() {
        return userManager.getGnrEmpresa().getCodEmpresa();
    }

    private Integer obtenerVendedor() throws VendedorException {
        FacParametros facParametros = obtenerFacParametros();

        if (facParametros == null) {
            throw new VendedorException("Vendedor no asociado a facturación.");
        }

        Integer defCodVendedor = facParametros.getDefCodVendedor();

        if (defCodVendedor == null) {
            throw new VendedorException("Vendedor no asociado a facturación.");
        }

        logger.log(Level.INFO, "codVendedor: {0}", defCodVendedor);
        return defCodVendedor;
    }

    private FacParametros obtenerFacParametros() {
        String facParametrosString = facParametrosFacadeREST.findAll_JSON(String.class);
        List<FacParametros> listaFacParametros = gson.fromJson(facParametrosString, new TypeToken<java.util.List<FacParametros>>() {
        }.getType());

        logger.log(Level.INFO, "Current user: {0}", userManager.getCurrent().getNombreUsuario().toLowerCase());

        for (FacParametros facParametros : listaFacParametros) {
            if (facParametros.getFacParametrosPK().getNombreUsuario().toLowerCase().
                    equals(userManager.getCurrent().getNombreUsuario().toLowerCase())
                    && facParametros.getFacParametrosPK().getCodEmpresa().
                            equals(obtenerEmpresa())) {
                logger.log(Level.INFO, "Usuario *: {0}", facParametros.getFacParametrosPK().getNombreUsuario().toLowerCase());
                logger.log(Level.INFO, "facParametros: {0}", facParametros);
                return facParametros;
            }
        }
        return null;
    }

    private void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
