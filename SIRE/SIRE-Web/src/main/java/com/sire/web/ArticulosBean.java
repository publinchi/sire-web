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
import com.sire.entities.GnrDivisa;
import com.sire.entities.InvArticulo;
import com.sire.entities.InvBodegaArt;
import com.sire.entities.InvBodegaArtPK;
import com.sire.entities.InvInventario;
import com.sire.entities.InvMovimientoCab;
import com.sire.entities.InvMovimientoCabPK;
import com.sire.entities.InvMovimientoDtll;
import com.sire.entities.InvMovimientoDtllPK;
import com.sire.entities.InvTransacciones;
import com.sire.exception.ClienteException;
import com.sire.exception.EmptyException;
import com.sire.rs.client.FacCatalogoPrecioDFacadeREST;
import com.sire.rs.client.GnrContadorDocFacadeREST;
import com.sire.rs.client.InvArticuloFacadeREST;
import com.sire.rs.client.InvMovimientoCabFacadeREST;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    private final InvMovimientoCabFacadeREST invMovimientoCabFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;
    private String input;
    private List<InvArticulo> articulos;
    private List<InvMovimientoDtll> invMovimientoDtlls;
    private InvMovimientoCab invMovimientoCab;
    private InvMovimientoDtll invMovimientoDtllSeleccionado;

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

    public ArticulosBean() {
        codInventario = "01";
        invMovimientoDtlls = new ArrayList<>();
        invMovimientoCab = new InvMovimientoCab();
        invMovimientoCab.setInvMovimientoDtllList(invMovimientoDtlls);
        invArticuloFacadeREST = new InvArticuloFacadeREST();
        invMovimientoCabFacadeREST = new InvMovimientoCabFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    // Medotos del negocio
    public void findArticulos() {
        System.out.println("Invocando findArticulos.");
        String articulosString;
        String codEmpresa = userManager.getGnrEmpresa().getCodEmpresa();
        try {
            articulosString = invArticuloFacadeREST.findParaVenta(String.class, input, codEmpresa);
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

        invMovimientoDtll.setInvBodegaArt(invBodegaArt);
        invMovimientoDtll.setInvArticulo(invArticuloSeleccionado);

        invMovimientoDtlls.add(invMovimientoDtll);
    }

    public void tapArticuloFinal(SelectEvent event) {
        setInvMovimientoDtllSeleccionado(((InvMovimientoDtll) event.getObject()));
        InvMovimientoDtll invMovimientoDtll = getInvMovimientoDtllSeleccionado();

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

        loadPrecioUnitarioByUnidadMedida();

        invMovimientoDtll.setPrecioVenta(facCatalogoPrecioD.getPrecioVenta1());

    }

    public void swipeleft(SwipeEvent event) {
        setInvMovimientoDtllSeleccionado(((InvMovimientoDtll) event.getData()));
        InvMovimientoDtll invMovimientoDtll = getInvMovimientoDtllSeleccionado();
        invMovimientoDtlls.remove(invMovimientoDtll);
        logger.log(Level.INFO, "Articulo removido: {0}", invMovimientoDtll);
    }

    public void loadInventariosByBodega() {
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:inv");
    }

    public void calcularTotalRegistro() {
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalRegistro");
    }

    public void agregarArticulo() {
        String codBodega = getInvMovimientoDtllSeleccionado().getInvBodegaArt().getInvBodegaArtPK().getCodBodega();
        String codUnidad = getInvMovimientoDtllSeleccionado().getCodUnidad();
        BigDecimal cantidad = getInvMovimientoDtllSeleccionado().getCantidad();

        logger.info("Articulo a ser agregado: ");
        logger.log(Level.INFO, "codBodega: {0}", codBodega);
        logger.log(Level.INFO, "codInventario: {0}", codInventario);
        logger.log(Level.INFO, "codUnidad: {0}", codUnidad);
        logger.log(Level.INFO, "cantidad: {0}", cantidad);
        logger.log(Level.INFO, "precioUnitario: {0}", getInvMovimientoDtllSeleccionado().getCostoUnitario());
        logger.log(Level.INFO, "descuento: {0}", invArticuloSeleccionado.getDescArticulo());
        logger.log(Level.INFO, "total: {0}", getInvMovimientoDtllSeleccionado().getCostoTotal());
        logger.log(Level.INFO, "iva: {0}", invArticuloSeleccionado.getCodIva());
        logger.log(Level.INFO, "totalIVA: {0}", totalIVA);

        InvMovimientoDtll invMovimientoDtll = getInvMovimientoDtllSeleccionado();
        // codBodega
        invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().setCodBodega(codBodega);
        // codInventario
        invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().setCodInventario(codInventario);
        // codUnidad
        invMovimientoDtll.setCodUnidad(codUnidad);
        // cantidad
        invMovimientoDtll.setCantidad(cantidad);
        // descuento
        if (invArticuloSeleccionado.getDescArticulo() != null) {
            invMovimientoDtll.setDescuento(new BigInteger(invArticuloSeleccionado.getDescArticulo()));
        }
        // estado
        invMovimientoDtll.setEstado("G");
        // fecha_estado
        invMovimientoDtll.setFechaEstado(Calendar.getInstance().getTime());
        // total
        invMovimientoDtll.setCostoTotal(getInvMovimientoDtllSeleccionado().getCostoTotal());
        // precio unitario
        invMovimientoDtll.setCostoUnitario(getInvMovimientoDtllSeleccionado().getCostoUnitario());
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
        invMovimientoDtll.setAuxCantidad(BigInteger.valueOf(auxCantidad));
        invMovimientoDtll.setValorCompra(BigInteger.ZERO);

        invMovimientoDtll.setPorcDesc1(BigDecimal.ZERO);
        invMovimientoDtll.setPorcDesc2(BigDecimal.ZERO);
        invMovimientoDtll.setPorcDesc3(BigDecimal.ZERO);
        invMovimientoDtll.setPorcentajeIva(BigDecimal.ZERO);
        invMovimientoDtll.setDescuento(BigInteger.ZERO);

        InvInventario invInventario = new InvInventario(userManager.getGnrEmpresa().getCodEmpresa(), codBodega, codInventario);
        invMovimientoDtll.getInvBodegaArt().setInvInventario(invInventario);

        // TODO terminar el mapeo
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formTablaArticulos:tablaArticulos");

    }

    public void loadPrecioTotalByCantidad() {
        InvMovimientoDtll invMovimientoDtll = getInvMovimientoDtllSeleccionado();

        Double precioTotal;
        precioTotal = invMovimientoDtll.getPrecioVenta() * invMovimientoDtll.getCantidad().intValue();
        invMovimientoDtll.setCostoTotal(precioTotal);
    }

    public void loadPrecioUnitarioByUnidadMedida() {
        InvMovimientoDtll invMovimientoDtll = getInvMovimientoDtllSeleccionado();

        Double previoVenta1 = facCatalogoPrecioD.getPrecioVenta1();
        Double precioUnitario = null;

        // TODO arreglar esta logica
        if (facCatalogoPrecioD.getCodUnidad().equals(invMovimientoDtll.getCodUnidad())) {
            logger.info("A:::");
            logger.info("previoVenta1: " + previoVenta1);
            logger.info("auxCantidad: " + invMovimientoDtll.getAuxCantidad());

            if (invMovimientoDtll.getAuxCantidad() != null) {
                precioUnitario = previoVenta1 / invMovimientoDtll.getAuxCantidad().intValue();
            } else {
                precioUnitario = previoVenta1;
            }
        } else if (facCatalogoPrecioD.getCodUnidad().equals("CA")) {
            logger.info("B:::");
            if (facCatalogoPrecioD.getOperador().endsWith("/")) {
                precioUnitario = previoVenta1 / facCatalogoPrecioD.getFactor().intValue();
            }
        } else if (facCatalogoPrecioD.getCodUnidad().equals("FU")) {
            logger.info("C:::");
            precioUnitario = previoVenta1;
        }

        invMovimientoDtll.setCostoUnitario(precioUnitario);

        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:precioUnitario");
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
}
