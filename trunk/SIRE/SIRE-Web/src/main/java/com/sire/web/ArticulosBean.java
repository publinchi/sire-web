/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.CxcCliente;
import com.sire.entities.FacCatalogoPrecioD;
import com.sire.entities.FacDescVol;
import com.sire.entities.FacDescVolPK;
import com.sire.entities.GnrDivisa;
import com.sire.entities.InvArticulo;
import com.sire.entities.InvBodegaArt;
import com.sire.entities.InvBodegaArtPK;
import com.sire.entities.InvInventario;
import com.sire.entities.InvIva;
import com.sire.entities.InvMovimientoCab;
import com.sire.entities.InvMovimientoCabPK;
import com.sire.entities.InvMovimientoDtll;
import com.sire.entities.InvMovimientoDtllPK;
import com.sire.entities.InvTransacciones;
import com.sire.entities.InvUnidadAlternativa;
import com.sire.entities.VCliente;
import com.sire.exception.ClienteException;
import com.sire.exception.EmptyException;
import com.sire.rs.client.FacCatalogoPrecioDFacadeREST;
import com.sire.rs.client.FacDescVolFacadeREST;
import com.sire.rs.client.GnrContadorDocFacadeREST;
import com.sire.rs.client.InvArticuloFacadeREST;
import com.sire.rs.client.InvBodegaArtFacadeREST;
import com.sire.rs.client.InvIvaFacadeREST;
import com.sire.rs.client.InvMovimientoCabFacadeREST;
import com.sire.rs.client.InvUnidadAlternativaFacadeREST;
import com.sire.rs.client.VClienteFacadeREST;
import com.sire.utils.Round;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ClientErrorException;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.mobile.event.SwipeEvent;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "articulosBean")
@SessionScoped
@Getter
@Setter
public class ArticulosBean {

    private static final Logger logger = Logger.getLogger(ArticulosBean.class.getName());

    private final InvArticuloFacadeREST invArticuloFacadeREST;
    private final VClienteFacadeREST vClienteFacadeREST;
    private final FacDescVolFacadeREST facDescVolFacadeREST;
    private final InvMovimientoCabFacadeREST invMovimientoCabFacadeREST;
    private final InvIvaFacadeREST invIvaFacadeREST;
    private final InvUnidadAlternativaFacadeREST invUnidadAlternativaFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    private String input;
    private List<InvArticulo> articulos;
    private List<InvMovimientoDtll> invMovimientoDtlls;
    private InvMovimientoCab invMovimientoCab;
    private InvMovimientoDtll invMovimientoDtllSeleccionado;

    //
    private Double existencia;
    private boolean agregarBloqueado;

    // Atributos de articulo a ser agregado a la lista
    private String codInventario;
    private int codArticulo;
    private InvArticulo invArticuloSeleccionado;
    private Double totalIVA;
    private String ubicacion;

    private FacCatalogoPrecioD facCatalogoPrecioD;

    @ManagedProperty(value = "#{user}")
    private UserManager userManager;
    @ManagedProperty("#{cliente}")
    private CustomerBean cliente;
    @ManagedProperty("#{customers}")
    private CustomersBean clientes;

    //Resumen
    Double subTotal, iva, total;

    //Mensaje
    private String cantidadExedida;

    public ArticulosBean() {
        codInventario = "01";
        invMovimientoDtlls = new ArrayList<>();
        invMovimientoCab = new InvMovimientoCab();
        invMovimientoCab.setInvMovimientoDtllList(invMovimientoDtlls);
        invArticuloFacadeREST = new InvArticuloFacadeREST();
        vClienteFacadeREST = new VClienteFacadeREST();
        facDescVolFacadeREST = new FacDescVolFacadeREST();
        invMovimientoCabFacadeREST = new InvMovimientoCabFacadeREST();
        invIvaFacadeREST = new InvIvaFacadeREST();
        invUnidadAlternativaFacadeREST = new InvUnidadAlternativaFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    // Medotos del negocio
    public void findArticulos() {
        logger.info("Invocando findArticulos: " + input);

        String articulosString;
        String codEmpresa = userManager.getGnrEmpresa().getCodEmpresa();
        try {
            articulosString = invArticuloFacadeREST.findParaVenta(String.class, input.toUpperCase().replace("%AC", "$WC"), codEmpresa);
            articulos = gson.fromJson(articulosString, new TypeToken<java.util.List<InvArticulo>>() {
            }.getType());
            System.out.println("# articulos: " + articulos.size());
        } catch (ClientErrorException cee) {
            articulos = null;
        }
    }

    public void tapArticulo(SelectEvent event) {
        invArticuloSeleccionado = ((InvArticulo) event.getObject());
        System.out.println("Articulo seleccionado: " + invArticuloSeleccionado.getNombreArticulo());
        logger.info("codUnidad: " + invArticuloSeleccionado.getCodUnidad().getCodUnidad());
        setCodArticulo(invArticuloSeleccionado.getInvArticuloPK().getCodArticulo());

        InvMovimientoDtll invMovimientoDtll = new InvMovimientoDtll();

        // TODO cargar esta bodega
        InvBodegaArt invBodegaArt = new InvBodegaArt();
        InvBodegaArtPK invBodegaArtPK = new InvBodegaArtPK();

        invBodegaArtPK.setCodEmpresa(userManager.getGnrEmpresa().getCodEmpresa());

        invBodegaArtPK.setCodArticulo(invArticuloSeleccionado.getInvArticuloPK().getCodArticulo());

        invBodegaArt.setInvBodegaArtPK(invBodegaArtPK);
        invBodegaArt.setExistencia(BigDecimal.ZERO);
        invBodegaArt.setExistPendEnt(BigDecimal.ZERO);
        invBodegaArt.setExistPendSal(BigDecimal.ZERO);
        InvMovimientoDtllPK invMovimientoDtllPK = new InvMovimientoDtllPK();
        invMovimientoDtllPK.setCodEmpresa(userManager.getGnrEmpresa().getCodEmpresa());
        invMovimientoDtllPK.setCodDocumento("SAI");
        int count = invMovimientoDtlls.size() + 1;
        invMovimientoDtll.setPosicion(count);
        invMovimientoDtllPK.setNumLinea(new Short(String.valueOf(count)));
        invMovimientoDtllPK.setNumDocumento(0);

        invMovimientoDtll.setInvMovimientoDtllPK(invMovimientoDtllPK);

        invMovimientoDtll.setInvBodegaArt(invBodegaArt);
        invMovimientoDtll.setInvArticulo(invArticuloSeleccionado);
        invMovimientoDtll.setCodUnidad(invArticuloSeleccionado.getCodUnidad().getCodUnidad());

        if (invArticuloSeleccionado.getExistencia().intValue() > 0) {
            logger.info("Pos: " + invMovimientoDtll.getPosicion());
            logger.info("CodEmpresa:" + invMovimientoDtll.getInvMovimientoDtllPK().getCodEmpresa());
            logger.info("CodDocumento:" + invMovimientoDtll.getInvMovimientoDtllPK().getCodDocumento());
            logger.info("NumDocumento: " + invMovimientoDtll.getInvMovimientoDtllPK().getNumDocumento());
            logger.info("NumLinea: " + invMovimientoDtll.getInvMovimientoDtllPK().getNumLinea());

            invMovimientoDtlls.add(invMovimientoDtll);
        }
    }

    public void tapArticuloFinal(SelectEvent event) {
        setInvMovimientoDtllSeleccionado(((InvMovimientoDtll) event.getObject()));
        InvMovimientoDtll invMovimientoDtll = obtenerMovimientoSeleccionado();

        String codArticulo = String.valueOf(invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo());
        logger.log(Level.INFO, "Articulo seleccionado final: {0}", codArticulo);

        String codEmpresa = userManager.getGnrEmpresa().getCodEmpresa();

        StringBuilder id = new StringBuilder();

        id.append("find;codEmpresa=");
        id.append(codEmpresa);
        id.append(";codCatalogo=01;codArticulo=");
        id.append(codArticulo);

        FacCatalogoPrecioDFacadeREST facCatalogoPrecioDFacadeREST = new FacCatalogoPrecioDFacadeREST();
        String response = facCatalogoPrecioDFacadeREST.find_JSON(String.class, id.toString());

        facCatalogoPrecioD = gson.fromJson(response, new TypeToken<FacCatalogoPrecioD>() {
        }.getType());

        invMovimientoDtll.setPrecioVenta(facCatalogoPrecioD.getPrecioVenta1());

        logger.info("invMovimientoDtll.getPrecioVenta(): " + invMovimientoDtll.getPrecioVenta());

        if (invArticuloSeleccionado.getDescuento() == null) {
            invArticuloSeleccionado.setDescuento(buscarDescuento());
        }

        invArticuloSeleccionado.setIva(findIva());

        loadPrecioUnitarioByUnidadMedida();
    }

    public void swipeleft(SwipeEvent event) {
        setInvMovimientoDtllSeleccionado(((InvMovimientoDtll) event.getData()));
        InvMovimientoDtll invMovimientoDtll = obtenerMovimientoSeleccionado();

        logger.info("Posicion: " + invMovimientoDtll.getPosicion());

        InvMovimientoDtll forDelete = null;
        for (InvMovimientoDtll invMovimientoDtll1 : invMovimientoDtlls) {
            if (invMovimientoDtll1.getPosicion() == invMovimientoDtll.getPosicion()) {
                forDelete = invMovimientoDtll1;
                break;
            }
        }

        invMovimientoDtlls.remove(forDelete);
        logger.log(Level.INFO, "Articulo removido: {0}", forDelete.getPosicion());

        if (invMovimientoDtll.getCantidad() != null) {
            calcularResumen();
        }
    }

    public void loadInventariosByBodega() {
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:inv");
    }

    public void calcularTotalRegistro() {
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalRegistro");
    }

    public void agregarArticulo() {
        InvMovimientoDtll movimientoSeleccionado = obtenerMovimientoSeleccionado();
        String codBodega = movimientoSeleccionado.getInvBodegaArt().getInvBodegaArtPK().getCodBodega();
        String codUnidad = movimientoSeleccionado.getCodUnidad();
        BigDecimal cantidad = movimientoSeleccionado.getCantidad();

        logger.info("Articulo a ser agregado: ");
        logger.log(Level.INFO, "codBodega: {0}", codBodega);
        logger.log(Level.INFO, "codInventario: {0}", codInventario);
        logger.log(Level.INFO, "codUnidad: {0}", codUnidad);
        logger.log(Level.INFO, "cantidad: {0}", cantidad);
        logger.log(Level.INFO, "precioUnitario: {0}", movimientoSeleccionado.getCostoUnitario());
        logger.log(Level.INFO, "descuento: {0}", invArticuloSeleccionado.getDescArticulo());
        logger.log(Level.INFO, "total: {0}", movimientoSeleccionado.getCostoTotal());
        logger.log(Level.INFO, "iva: {0}", invArticuloSeleccionado.getCodIva());
        logger.log(Level.INFO, "totalIVA: {0}", totalIVA);

        // codBodega
        movimientoSeleccionado.getInvBodegaArt().getInvBodegaArtPK().setCodBodega(codBodega);
        // codInventario
        movimientoSeleccionado.getInvBodegaArt().getInvBodegaArtPK().setCodInventario(codInventario);
        // codUnidad
        movimientoSeleccionado.setCodUnidad(codUnidad);
        // cantidad
        movimientoSeleccionado.setCantidad(cantidad);
        // descuento
        movimientoSeleccionado.setPorcDesc1(invArticuloSeleccionado.getDescuento());
        // estado
        movimientoSeleccionado.setEstado("G");
        // fecha_estado
        movimientoSeleccionado.setFechaEstado(Calendar.getInstance().getTime());
        // total
        movimientoSeleccionado.setCostoTotal(movimientoSeleccionado.getCostoTotal());
        // precio unitario
        movimientoSeleccionado.setCostoUnitario(movimientoSeleccionado.getCostoUnitario());
        // iva
        // total iva
        // auxCantidad
        int auxCantidad = 0;
        switch (facCatalogoPrecioD.getCodUnidad()) {
            case "CA":
                if (facCatalogoPrecioD.getOperador().endsWith("/")) {
                    logger.info("1:::");
                    auxCantidad = cantidad.intValue() * facCatalogoPrecioD.getFactor().intValue();
                }
                break;
            case "FU":
                if (facCatalogoPrecioD.getOperador().endsWith("*")) {
                    logger.info("2:::");
                    auxCantidad = cantidad.intValue() / facCatalogoPrecioD.getFactor().intValue();
                }
                break;
        }
        logger.log(Level.INFO, "5::: {0}", auxCantidad);
        movimientoSeleccionado.setAuxCantidad(BigInteger.valueOf(auxCantidad));
        movimientoSeleccionado.setValorCompra(BigInteger.ZERO);

        movimientoSeleccionado.setPorcDesc1(movimientoSeleccionado.getPorcDesc1());
        movimientoSeleccionado.setPorcDesc2(BigDecimal.ZERO);
        movimientoSeleccionado.setPorcDesc3(BigDecimal.ZERO);
        movimientoSeleccionado.setDescuento(invArticuloSeleccionado.getDescuento());
        movimientoSeleccionado.setPorcentajeIva(invArticuloSeleccionado.getIva());

        InvInventario invInventario = new InvInventario(userManager.getGnrEmpresa().getCodEmpresa(), codBodega, codInventario);
        movimientoSeleccionado.getInvBodegaArt().setInvInventario(invInventario);

        calcularResumen();

        // TODO terminar el mapeo
        RequestContext
                .getCurrentInstance().update("pedido:accordionPanel:formTablaArticulos:tablaArticulos");

    }

    public void loadPrecioTotalByCantidad() {
        logger.info("precio venta: " + invArticuloSeleccionado.getPrecio());

        InvMovimientoDtll movimientoSeleccionado = obtenerMovimientoSeleccionado();

        logger.info("invArticuloSeleccionado.getExistencia().intValue(): " + invArticuloSeleccionado.getExistencia().intValue());
        logger.info("movimientoSeleccionado.getCantidad().intValue(): " + movimientoSeleccionado.getCantidad().intValue());

        if (getExistencia().intValue() >= movimientoSeleccionado.getCantidad().intValue()) {
            Double precioTotal;
            Double descuento = 0.0;
            if (invArticuloSeleccionado.getDescuento() != null) {
                descuento = (movimientoSeleccionado.getCostoUnitario() * movimientoSeleccionado.getCantidad().intValue() * invArticuloSeleccionado.getDescuento().intValue()) / 100;
            }

            logger.info("descuento: " + descuento);

            precioTotal = (movimientoSeleccionado.getCostoUnitario() * movimientoSeleccionado.getCantidad().intValue()) - descuento;

            precioTotal = Round.round(precioTotal, 2);

            logger.info("precioTotal: " + precioTotal);
            movimientoSeleccionado.setCostoTotal(precioTotal);

            Double totalPlusIVA = precioTotal * (1 + invArticuloSeleccionado.getIva().doubleValue() / 100);
            logger.info("totalPlusIVA: " + totalPlusIVA);
            invArticuloSeleccionado.setTotalPlusIVA(totalPlusIVA);

            agregarBloqueado = false;

            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalRegistro");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalIva");
            cantidadExedida = "";
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:cantidadLabel");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:botonAgregar");

        } else {
            cantidadExedida = "Cantidad Excedida";

            movimientoSeleccionado.setCantidad(null);
            movimientoSeleccionado.getInvBodegaArt().getInvBodegaArtPK().setCodBodega(null);
            movimientoSeleccionado.setCostoTotal(null);
            invArticuloSeleccionado.setTotalPlusIVA(null);

            agregarBloqueado = true;

            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalRegistro");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalIva");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:cantidadLabel");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:botonAgregar");
        }
    }

    public void loadPrecioUnitarioByUnidadMedida() {
        InvMovimientoDtll invMovimientoDtll = obtenerMovimientoSeleccionado();
        String codUnidad;
        if (invMovimientoDtll.getCodUnidad() != null) {
            codUnidad = invMovimientoDtll.getCodUnidad();
        } else {
            codUnidad = obtenerMovimientoSeleccionado().getCodUnidad();
        }
        String codEmpresa = userManager.getGnrEmpresa().getCodEmpresa();
        int codArticulo = invMovimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo();

        StringBuilder id = new StringBuilder();

        id.append("find;codEmpresa=");
        id.append(codEmpresa);
        id.append(";");
        id.append("codUnidad=");
        id.append(codUnidad);
        id.append(";");
        id.append("codArticulo=");
        id.append(codArticulo);
        InvUnidadAlternativa invUnidadAlternativa = invUnidadAlternativaFacadeREST.find_JSON(InvUnidadAlternativa.class, id.toString());

        BigDecimal auxPrecio = BigDecimal.ZERO;
        BigDecimal factor = BigDecimal.ZERO;
        String operador = "";

        if (invUnidadAlternativa != null) {
            auxPrecio = facCatalogoPrecioD.getAuxPrecio();
            factor = invUnidadAlternativa.getFactor();
            operador = invUnidadAlternativa.getOperador();
        }

        logger.info("auxPrecio: " + auxPrecio);
        logger.info("factor: " + factor);
        logger.info("operador: " + operador);

        Double precio = 0.0;
        switch (operador) {
            case "+":
                precio = auxPrecio.doubleValue() + factor.doubleValue();
                existencia = Round.round(invArticuloSeleccionado.getExistencia().doubleValue() + factor.doubleValue(), 4);
                break;
            case "-":
                precio = auxPrecio.doubleValue() - factor.doubleValue();
                existencia = Round.round(invArticuloSeleccionado.getExistencia().doubleValue() - factor.doubleValue(), 4);
                break;
            case "*":
                precio = auxPrecio.doubleValue() / factor.doubleValue();
                existencia = Round.round(invArticuloSeleccionado.getExistencia().doubleValue() * factor.doubleValue(), 4);
                break;
            case "/":
                precio = auxPrecio.doubleValue() * factor.doubleValue();
                existencia = Round.round(invArticuloSeleccionado.getExistencia().doubleValue() / factor.doubleValue(), 4);
                break;
            default:
                precio = auxPrecio.doubleValue();
        }

        invMovimientoDtll.setCostoUnitario(Round.round(precio, 4));

        logger.info("$$$$$$$$$$ precio venta: " + invMovimientoDtll.getCostoUnitario());

        if (invMovimientoDtll.getCantidad() != null) {
            loadPrecioTotalByCantidad();
        }

        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:precioUnitario");
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:existencia");
    }

    public String enviar() {
        try {
            CxcCliente cxcCliente = obtenerCliente();
            invMovimientoCab.setCxcCliente(cxcCliente);

            if (invMovimientoDtlls.isEmpty()) {
                throw new EmptyException();
            }

            GnrContadorDocFacadeREST gnrContadorDocFacadeREST = new GnrContadorDocFacadeREST();
            BigDecimal numDocumentoResp = gnrContadorDocFacadeREST.numDocumento(BigDecimal.class, "01", "03", "SAI");

            InvMovimientoCabPK invMovimientoCabPK = new InvMovimientoCabPK();
            invMovimientoCabPK.setCodEmpresa(userManager.getGnrEmpresa().getCodEmpresa());
            invMovimientoCabPK.setCodDocumento("SAI");
            invMovimientoCabPK.setNumDocumento(numDocumentoResp.longValue());
            invMovimientoCab.setInvMovimientoCabPK(invMovimientoCabPK);
            invMovimientoCab.setInvProveedor(null);

            invMovimientoCab.setNumRetencion(null);
            invMovimientoCab.setFechaEmision(Calendar.getInstance().getTime());
            GnrDivisa gnrDivisa = new GnrDivisa("01", userManager.getGnrEmpresa().getCodEmpresa());
            invMovimientoCab.setGnrDivisa(gnrDivisa);
            invMovimientoCab.setReferencia("FAC");
            InvTransacciones invTransacciones = new InvTransacciones(userManager.getGnrEmpresa().getCodEmpresa(), "20");
            invMovimientoCab.setInvTransacciones(invTransacciones);
            invMovimientoCab.setDetalle(new StringBuilder("FAC ").append(cliente.getCliente().getRazonSocial()).toString());
            invMovimientoCab.setDigitadoPor(userManager.getUserName());
            invMovimientoCab.setEstado("G");
            invMovimientoCab.setTotalDocumento(BigInteger.ZERO);
            invMovimientoCab.setDescuentos(BigInteger.ZERO);
            invMovimientoCab.setFechaEstado(Calendar.getInstance().getTime());
            invMovimientoCab.setOtrDescuentos(BigInteger.ZERO);
            invMovimientoCab.setIva(BigInteger.ZERO);
            invMovimientoCab.setRecargos(BigInteger.ZERO);
            invMovimientoCab.setFletes(BigInteger.ZERO);
            invMovimientoCab.setOtrCargos(BigInteger.ZERO);
            invMovimientoCab.setRetencion(BigInteger.ZERO);
            invMovimientoCab.setDiasPlazo(1);
            invMovimientoCab.setFechaVencimiento(null);
            invMovimientoCab.setNroCuotas(null);
            invMovimientoCab.setTConIva(null);
            invMovimientoCab.setTSinIva(null);
            invMovimientoCab.setAutoContDoc(null);
            invMovimientoCab.setAutoContImprDoc(null);
            invMovimientoCab.setFechEmisDoc(null);
            invMovimientoCab.setFechCaduDoc(null);
            invMovimientoCab.setNumFactRete(null);

            logger.log(Level.INFO, "enviar ::::::::::::::: {0} articulos", invMovimientoCab.getInvMovimientoDtllList().size());

            Double subtotal = 0.0;
            for (InvMovimientoDtll invMovimientoDtll : invMovimientoDtlls) {
                InvBodegaArt invBodegaArt = invMovimientoDtll.getInvBodegaArt();

                String codBodega = obtenerCodBodega(invMovimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo());

                logger.info("codBodega recuperado: " + codBodega);

                invBodegaArt.getInvBodegaArtPK().setCodBodega(codBodega);

                subtotal = subtotal + invMovimientoDtll.getCostoTotal();

                InvMovimientoDtllPK invMovimientoDtllPK = new InvMovimientoDtllPK(userManager.getGnrEmpresa().getCodEmpresa(), "SAI", new Long(numDocumentoResp.longValue()), Short.valueOf(String.valueOf(invMovimientoDtlls.indexOf(invMovimientoDtll) + 1)));
                invMovimientoDtll.setInvMovimientoDtllPK(invMovimientoDtllPK);

                if (invMovimientoDtll.getInvMovimientoDtllPK() != null) {
                    logger.log(Level.INFO, "InvMovimientoDtllPK: {0}", invMovimientoDtll.getInvMovimientoDtllPK().toString());
                } else {
                    logger.info("InvMovimientoDtllPK: null");
                }
            }

            invMovimientoCab.setSubtotal(subtotal);

            logger.info("Enviando Documento ...");
            invMovimientoCabFacadeREST.create_JSON(invMovimientoCab);
            logger.info("Documento Enviado.");

            limpiar();

            addMessage("Pedido enviado exitosamente.", "Num. Pedido: " + numDocumentoResp, FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "index?faces-redirect=true";
        } catch (ClienteException ex) {
            Logger.getLogger(ArticulosBean.class.getName()).log(Level.WARNING, "Por favor seleccione el cliente.");
            addMessage("Advertencia", "Por favor seleccione el cliente.", FacesMessage.SEVERITY_INFO);
            return "pedido?faces-redirect=true";
        } catch (EmptyException ex) {
            Logger.getLogger(ArticulosBean.class.getName()).log(Level.WARNING, "Por favor seleccione al menos un artículo.");
            addMessage("Advertencia", "Por favor seleccione al menos un artículo.", FacesMessage.SEVERITY_INFO);
            return "pedido?faces-redirect=true";
        } catch (NullPointerException ex) {
            Logger.getLogger(ArticulosBean.class.getName()).log(Level.WARNING, "Por favor validar valor de registro(s).");
            addMessage("Advertencia", "Por favor validar valor de registro(s).", FacesMessage.SEVERITY_INFO);
            return "pedido?faces-redirect=true";
        }
    }

    public String getUbicacion() {
        if (ubicacion == null) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            String command = "geoip " + httpServletRequest.getRemoteAddr();
            logger.log(Level.INFO, "Command: {0}", command);
            ubicacion = executeCommand(command);
            logger.log(Level.INFO, "Ciudad:{0}", ubicacion);
        }
        return this.ubicacion;

    }

    private String executeCommand(String command) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitVal = p.waitFor();
            logger.log(Level.INFO, "Exited with error code {0}", exitVal);
            if (output.toString().isEmpty()) {
                return "Check geoip.";
            }
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "{0}", e);
        }

        return output.toString();

    }

    private void addMessage(String summary, String detail, Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void limpiar() {
        codArticulo = 0;
        invArticuloSeleccionado = null;
        totalIVA = null;
        input = null;

        iva = null;
        subTotal = null;
        total = null;

        invMovimientoCab = null;
        invMovimientoCab = new InvMovimientoCab();
        invMovimientoDtlls.clear();
        invMovimientoCab.setInvMovimientoDtllList(invMovimientoDtlls);

        articulos.clear();
        invMovimientoDtllSeleccionado = null;

        clientes.limpiar();
        cliente.limpiar();
    }

    private CxcCliente obtenerCliente() throws ClienteException {
        if (cliente.getCliente() == null) {
            throw new ClienteException();
        }

        return new CxcCliente(userManager.getGnrEmpresa().getCodEmpresa(), cliente.getCliente().getCodCliente());
    }

    private BigDecimal buscarDescuento() {
        String articulosString = invArticuloFacadeREST.findByArticuloEmpresa(String.class, String.valueOf(invArticuloSeleccionado.getInvArticuloPK().getCodArticulo()), userManager.getGnrEmpresa().getCodEmpresa());
        List<InvArticulo> listaArticulos = gson.fromJson(articulosString, new TypeToken<java.util.List<InvArticulo>>() {
        }.getType());

        String clientesString = vClienteFacadeREST.findByClienteEmpresa(String.class, String.valueOf(cliente.getCliente().getCodCliente()), userManager.getGnrEmpresa().getCodEmpresa());
        List<VCliente> listaClientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
        }.getType());

        FacDescVolPK facDescVolPK = new FacDescVolPK();
        facDescVolPK.setCodEmpresa(userManager.getGnrEmpresa().getCodEmpresa());
        facDescVolPK.setCodGrupo(listaClientes.get(0).getCodGrupo());
        facDescVolPK.setCodTipo(listaClientes.get(0).getCodTipo());
        facDescVolPK.setCodGrupo1(listaArticulos.get(0).getInvGrupo3().getInvGrupo3PK().getCodGrupo1());
        facDescVolPK.setCodGrupo2(listaArticulos.get(0).getInvGrupo3().getInvGrupo3PK().getCodGrupo2());
        facDescVolPK.setCodGrupo3(listaArticulos.get(0).getInvGrupo3().getInvGrupo3PK().getCodGrupo3());

        StringBuilder id = new StringBuilder();

        id.append("find;codEmpresa=");
        id.append(facDescVolPK.getCodEmpresa());
        id.append(";");
        id.append("codGrupo=");
        id.append(facDescVolPK.getCodGrupo());
        id.append(";");
        id.append("codTipo=");
        id.append(facDescVolPK.getCodTipo());
        id.append(";");
        id.append("codGrupo1=");
        id.append(facDescVolPK.getCodGrupo1());
        id.append(";");
        id.append("codGrupo2=");
        id.append(facDescVolPK.getCodGrupo2());
        id.append(";");
        id.append("codGrupo3=");
        id.append(facDescVolPK.getCodGrupo3());

        FacDescVol facDescVol = facDescVolFacadeREST.find_JSON(FacDescVol.class, id.toString());

        if (facDescVol != null) {
            invArticuloSeleccionado.setDescuento(facDescVol.getPorcDescuento());
        }

        return invArticuloSeleccionado.getDescuento();
    }

    private BigDecimal findIva() {
        StringBuilder id = new StringBuilder();

        id.append("find;codEmpresa=");
        id.append(userManager.getGnrEmpresa().getCodEmpresa());
        id.append(";");
        id.append("codIva=");
        id.append(invArticuloSeleccionado.getCodIva());

        InvIva invIva = invIvaFacadeREST.find_JSON(InvIva.class, id.toString());

        return invIva.getValor();
    }

    private void calcularSubTotal() {

    }

    private List<InvMovimientoDtll> obtenerMovimientos() {
        return invMovimientoDtlls;
    }

    private InvMovimientoDtll obtenerMovimientoSeleccionado() {
        return getInvMovimientoDtllSeleccionado();
    }

    private void calcularResumen() {
        subTotal = 0.0;
        iva = 0.0;
        total = 0.0;
        for (InvMovimientoDtll invMovimientoDtll1 : obtenerMovimientos()) {
            Double descuento = 0.0;
            if (invMovimientoDtll1.getDescuento() != null) {
                descuento = (invMovimientoDtll1.getCostoUnitario() * invMovimientoDtll1.getCantidad().intValue() * invMovimientoDtll1.getDescuento().intValue()) / 100;
            }

            Double _subTotal = Round.round(invMovimientoDtll1.getCostoUnitario() * (invMovimientoDtll1.getCantidad().intValue()) - descuento, 2);
            Double _iva = invMovimientoDtll1.getCostoUnitario() * (invMovimientoDtll1.getPorcentajeIva().doubleValue() / 100);

            subTotal += Round.round(_subTotal, 2);
            iva += Round.round(_iva, 2);
            total += Round.round(_subTotal + _iva, 2);
        }

    }

    private String obtenerCodBodega(Integer codArticulo) {

        Map<Integer, String> map = new HashMap< Integer, String>();

        List tmp = new ArrayList();
        List<InvBodegaArt> lista = obtenerBodegasPorCodArticulo(codArticulo);
        for (InvBodegaArt invBodegaArt : lista) {

            if (invBodegaArt.getExistencia().intValue() > 0) {
                logger.info("Codigo Bodega: " + invBodegaArt.getInvBodegaArtPK().getCodBodega());
                logger.info("Existencia: " + invBodegaArt.getExistencia());
                logger.info("----------");
                map.put(invBodegaArt.getExistencia().intValue(), invBodegaArt.getInvBodegaArtPK().getCodBodega());
            }
        }

        for (Integer existencia : map.keySet()) {
            tmp.add(existencia);
        }

        Collections.sort(tmp, new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });

        return map.get(tmp.get(0));
    }

    private List<InvBodegaArt> obtenerBodegasPorCodArticulo(Integer codArticulo) {
        InvBodegaArtFacadeREST invBodegaArtFacadeREST = new InvBodegaArtFacadeREST();
        String result = invBodegaArtFacadeREST.findByCodArticulo_JSON(String.class, String.valueOf(codArticulo));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setDateFormat("yyyy-MM-dd").create();
        List<InvBodegaArt> invBodegaArts = gson.fromJson(result, new TypeToken<List<InvBodegaArt>>() {
        }.getType());
        return invBodegaArts;
    }

    private Integer obtenerExistenciaPorCodArticulo() {

        return null;
    }
}
