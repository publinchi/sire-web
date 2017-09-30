/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.datamodel.lazy.LazyInvArticuloDataModel;
import com.sire.entities.CxcCliente;
import com.sire.entities.FacCatalogoPrecioD;
import com.sire.entities.FacDescVol;
import com.sire.entities.FacDescVolPK;
import com.sire.entities.FacParametros;
import com.sire.entities.FacTmpFactC;
import com.sire.entities.FacTmpFactCPK;
import com.sire.entities.FacTmpFactD;
import com.sire.entities.FacTmpFactDPK;
import com.sire.entities.GnrDivisa;
import com.sire.entities.GnrLogHistorico;
import com.sire.entities.GnrLogHistoricoPK;
import com.sire.entities.GnrUsuarios;
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
import com.sire.entities.Pedido;
import com.sire.entities.VCliente;
import com.sire.exception.ClienteException;
import com.sire.exception.EmptyException;
import com.sire.exception.GPSException;
import com.sire.exception.LimitException;
import com.sire.exception.PayWayException;
import com.sire.exception.VendedorException;
import com.sire.rs.client.CxcDocCobrarFacadeREST;
import com.sire.rs.client.FacCatalogoPrecioDFacadeREST;
import com.sire.rs.client.FacDescVolFacadeREST;
import com.sire.rs.client.FacParametrosFacadeREST;
import com.sire.rs.client.GnrContadorDocFacadeREST;
import com.sire.rs.client.InvArticuloFacadeREST;
import com.sire.rs.client.InvIvaFacadeREST;
import com.sire.rs.client.InvMovimientoCabFacadeREST;
import com.sire.rs.client.InvUnidadAlternativaFacadeREST;
import com.sire.rs.client.VClienteFacadeREST;
import com.sire.utils.Round;
import com.sire.utils.bodega.BodegaUtil;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import javax.ws.rs.ClientErrorException;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.mobile.event.SwipeEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "articulosBean")
@SessionScoped
@Getter
@Setter
public class ArticulosBean {

    private static final Logger LOGGER = Logger.getLogger(ArticulosBean.class.getName());

    private final InvArticuloFacadeREST invArticuloFacadeREST;
    private final VClienteFacadeREST vClienteFacadeREST;
    private final FacDescVolFacadeREST facDescVolFacadeREST;
    private InvMovimientoCabFacadeREST invMovimientoCabFacadeREST;
    private final InvIvaFacadeREST invIvaFacadeREST;
    private final InvUnidadAlternativaFacadeREST invUnidadAlternativaFacadeREST;
    private final FacParametrosFacadeREST facParametrosFacadeREST;
    private final CxcDocCobrarFacadeREST cxcDocCobrarFacadeREST;

    private final GsonBuilder builder;
    private final Gson gson;
    private String input, modo = "c";
    private List<InvArticulo> articulos;
    private List<InvMovimientoDtll> invMovimientoDtlls;
    private FacTmpFactC facTmpFactC;
    private List<FacTmpFactD> facTmpFactDs;
    private InvMovimientoCab invMovimientoCab;
    private InvMovimientoDtll invMovimientoDtllSeleccionado;
    private String formaPago;

    //
    private Double existencia, maxPorcDescuento;
    private boolean agregarBloqueado = true, maxPorcDescuentoDisabled = true;

    // Atributos de articulo a ser agregado a la lista
    private String codInventario;
    private int codArticulo;
    private InvArticulo invArticuloSeleccionado;
    private Double totalIVA;

    private FacCatalogoPrecioD facCatalogoPrecioD;

    @ManagedProperty(value = "#{user}")
    private UserManager userManager;
    @ManagedProperty("#{cliente}")
    private CustomerBean cliente;
    @ManagedProperty("#{customers}")
    private CustomersBean clientes;
    @ManagedProperty("#{mapa}")
    private MapaBean mapa;
    private LazyDataModel<InvArticulo> lazyModel;

    //Resumen
    Double subTotal, iva, total, totalSinIva, totalConIva, limiteFactura;

    //Mensaje
    private String cantidadExcedida, colorCantidadExcedida = "black", observacion;

    private boolean articulosEncontradosCollapsed = true;

    public ArticulosBean() {
        codInventario = "01";
        facTmpFactC = new FacTmpFactC();
        facTmpFactDs = new ArrayList<>();
        facTmpFactC.setFacTmpFactDList(facTmpFactDs);
        invMovimientoDtlls = new ArrayList<>();
        invMovimientoCab = new InvMovimientoCab();
        invMovimientoCab.setInvMovimientoDtllList(invMovimientoDtlls);
        observacion = new String();
        invArticuloFacadeREST = new InvArticuloFacadeREST();
        vClienteFacadeREST = new VClienteFacadeREST();
        facDescVolFacadeREST = new FacDescVolFacadeREST();
        invIvaFacadeREST = new InvIvaFacadeREST();
        invUnidadAlternativaFacadeREST = new InvUnidadAlternativaFacadeREST();
        facParametrosFacadeREST = new FacParametrosFacadeREST();
        cxcDocCobrarFacadeREST = new CxcDocCobrarFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    // Medotos del negocio
    public void findArticulos() {
        LOGGER.log(Level.INFO, "Invocando findArticulos: {0}", input);

        String articulosString;
        String codEmpresa = obtenerEmpresa();
        try {
            if (modo.equals("c") && !input.isEmpty()) {
                articulosString = invArticuloFacadeREST.findByCodigo(String.class, URLEncoder.encode(input, "UTF-8"), codEmpresa);
                articulos = gson.fromJson(articulosString, new TypeToken<java.util.List<InvArticulo>>() {
                }.getType());
            } else if (modo.equals("n") && !input.isEmpty()) {
                articulosString = invArticuloFacadeREST.findParaVenta(String.class, URLEncoder.encode(input, "UTF-8"), codEmpresa);
                articulos = gson.fromJson(articulosString, new TypeToken<java.util.List<InvArticulo>>() {
                }.getType());
            }
            if (articulos != null && !articulos.isEmpty()) {
                LOGGER.log(Level.INFO, "# articulos: {0}", articulos.size());
                lazyModel = new LazyInvArticuloDataModel(articulos);
                articulosEncontradosCollapsed = false;
            } else {
                articulosEncontradosCollapsed = true;
            }
        } catch (ClientErrorException cee) {
            LOGGER.log(Level.SEVERE, cee.getMessage());
            articulos = null;
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    public void tapArticulo(SelectEvent event) {
        LOGGER.log(Level.INFO, "\u00b7\u00b7 tapArticulo \u00b7\u00b7 {0}", event.getObject());

        invArticuloSeleccionado = ((InvArticulo) event.getObject());
        LOGGER.info("Nombre artículo: " + invArticuloSeleccionado.getNombreArticulo()
                + ", Cod Unidad: " + invArticuloSeleccionado.getCodUnidad().getCodUnidad()
        );
        setCodArticulo(invArticuloSeleccionado.getInvArticuloPK().getCodArticulo());

        InvMovimientoDtll invMovimientoDtll = new InvMovimientoDtll();

        // TODO cargar esta bodega
        InvBodegaArt invBodegaArt = new InvBodegaArt();
        InvBodegaArtPK invBodegaArtPK = new InvBodegaArtPK();

        invBodegaArtPK.setCodEmpresa(obtenerEmpresa());

        invBodegaArtPK.setCodArticulo(invArticuloSeleccionado.getInvArticuloPK().getCodArticulo());

        invBodegaArt.setInvBodegaArtPK(invBodegaArtPK);
        invBodegaArt.setExistencia(BigDecimal.ZERO);
        invBodegaArt.setExistPendEnt(BigDecimal.ZERO);
        invBodegaArt.setExistPendSal(BigDecimal.ZERO);
        InvMovimientoDtllPK invMovimientoDtllPK = new InvMovimientoDtllPK();
        invMovimientoDtllPK.setCodEmpresa(obtenerEmpresa());
        invMovimientoDtllPK.setCodDocumento("SAI");
        int count = invMovimientoDtlls.size() + 1;
        invMovimientoDtll.setPosicion(count);
        invMovimientoDtllPK.setNumLinea(Short.valueOf(String.valueOf(count)));
        invMovimientoDtllPK.setNumDocumento(0);

        invMovimientoDtll.setInvMovimientoDtllPK(invMovimientoDtllPK);

        invMovimientoDtll.setInvBodegaArt(invBodegaArt);
        invMovimientoDtll.setInvArticulo(invArticuloSeleccionado);
        invMovimientoDtll.setCodUnidad(invArticuloSeleccionado.getCodUnidad().getCodUnidad());

        if (invArticuloSeleccionado.getExistencia().doubleValue() > 0 || "S".equals(obtenerFacParametros().getExistNeg())) {
            invMovimientoDtlls.add(invMovimientoDtll);
            agregarValores(invMovimientoDtll);
            input = null;
            articulos.clear();
            articulosEncontradosCollapsed = true;
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticles:buscar");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticles:dataListArticulo");
        } else {
            addMessage("Advertencia", "Producto no disponible", FacesMessage.SEVERITY_INFO);
        }
    }

    public void tapArticuloFinal(SelectEvent event) {
        agregarValores((InvMovimientoDtll) event.getObject());
    }

    public void swipeleft(SwipeEvent event) {
        InvMovimientoDtll invMovimientoDtll = obtenerMovimientoSeleccionado((InvMovimientoDtll) event.getData());

        LOGGER.log(Level.INFO, "Posicion: {0}", invMovimientoDtll.getPosicion());

        InvMovimientoDtll forDelete;
        for (InvMovimientoDtll invMovimientoDtll1 : invMovimientoDtlls) {
            if (invMovimientoDtll1.getPosicion() == invMovimientoDtll.getPosicion()) {
                forDelete = invMovimientoDtll1;
                invMovimientoDtlls.remove(forDelete);
                LOGGER.log(Level.INFO, "Articulo removido: {0}", forDelete.getPosicion());

                break;
            }
        }

        if (invMovimientoDtll.getCantidad() != null) {
            calcularResumen();
        }
    }

    public void cambioModo() {
        limpiarBusquedaArticulos();
    }

    public void limpiarBusquedaArticulos() {
        if (articulos != null) {
            articulos.clear();
        }
        input = null;
    }

    public void loadInventariosByBodega() {
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:inv");
    }

    public void calcularTotalRegistro() {
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalRegistro");
    }

    public void agregarArticulo(InvMovimientoDtll invMovimientoDtll) {
        InvMovimientoDtll movimientoSeleccionado = obtenerMovimientoSeleccionado(invMovimientoDtll);

//        obtenerPromocion(movimientoSeleccionado);
        String codBodega = movimientoSeleccionado.getInvBodegaArt().getInvBodegaArtPK().getCodBodega();
        String codUnidad = movimientoSeleccionado.getCodUnidad();
        BigDecimal cantidad = movimientoSeleccionado.getCantidad();

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

        InvUnidadAlternativa invUnidadAlternativa = obtenerInvUnidadAlternativa(movimientoSeleccionado);
        BigDecimal factor = BigDecimal.ZERO;
        String operador = "";

        if (invUnidadAlternativa != null) {
            factor = invUnidadAlternativa.getFactor();
            operador = invUnidadAlternativa.getOperador();
        }

        Double auxCantidad = 0.0;
        switch (operador) {
            case "+":
                auxCantidad = cantidad.doubleValue() + factor.doubleValue();
                break;
            case "-":
                auxCantidad = cantidad.doubleValue() - factor.doubleValue();
                break;
            case "*":
                auxCantidad = cantidad.doubleValue() / factor.doubleValue();
                break;
            case "/":
                auxCantidad = cantidad.doubleValue() * factor.doubleValue();
                break;
            default:
                break;
        }
        LOGGER.log(Level.INFO, "auxCantidad: {0}", auxCantidad);
        movimientoSeleccionado.setAuxCantidad(auxCantidad);
        movimientoSeleccionado.setDescuento(invArticuloSeleccionado.getDescuento());
        movimientoSeleccionado.setFactor(movimientoSeleccionado.getFactor());
        movimientoSeleccionado.setOperador(operador);

        BigDecimal porcDesc1 = movimientoSeleccionado.getPorcDesc1();
        if (porcDesc1 != null) {
            movimientoSeleccionado.setPorcDesc1(porcDesc1);
        } else {
            movimientoSeleccionado.setPorcDesc1(BigDecimal.ZERO);
        }
        movimientoSeleccionado.setPorcDesc2(BigDecimal.ZERO);
        movimientoSeleccionado.setPorcDesc3(BigDecimal.ZERO);

        movimientoSeleccionado.setValorCompra(BigInteger.ZERO);

        InvInventario invInventario = new InvInventario(obtenerEmpresa(), codBodega, codInventario);
        movimientoSeleccionado.getInvBodegaArt().setInvInventario(invInventario);

        LOGGER.info("Articulo a ser agregado, codBodega: " + codBodega
                + ", codInventario: " + codInventario
                + ", codUnidad: " + codUnidad + ", cantidad: " + cantidad
                + ", costoUnitario: " + movimientoSeleccionado.getCostoUnitario()
                + ", descArticulo: " + invArticuloSeleccionado.getDescArticulo()
                + ", descuento: " + invArticuloSeleccionado.getDescuento()
                + ", total: " + movimientoSeleccionado.getCostoTotal()
                + ", iva: " + movimientoSeleccionado.getPorcentajeIva()
                + ", totalIVA: " + totalIVA);

        calcularResumen();

        // TODO terminar el mapeo
        RequestContext
                .getCurrentInstance().update("pedido:accordionPanel:formTablaArticulos");

        if (FacesContext.getCurrentInstance().getMessageList().isEmpty()) {
            RequestContext
                    .getCurrentInstance().execute("PF('dlg3').hide();");
        } else {
            agregarBloqueado = true;
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:botonAgregar");
        }
    }

    public void loadPrecioTotalByCantidad(InvMovimientoDtll invMovimientoDtll) {

        InvMovimientoDtll movimientoSeleccionado = obtenerMovimientoSeleccionado(invMovimientoDtll);

        Double existence = this.existencia;

        BigDecimal cantidad = movimientoSeleccionado.getCantidad();
        if (cantidad == null) {
            cantidad = BigDecimal.ZERO;
        }

        LOGGER.info("existence: " + existence);
        LOGGER.info("cantidad: " + cantidad);
        LOGGER.info("obtenerFacParametros(): " + obtenerFacParametros());

        if (existence >= cantidad.doubleValue() || "S".equals(obtenerFacParametros().getExistNeg())) {

            Double precioTotal;
            Double descuento = 0.0;
            if (invArticuloSeleccionado.getDescuento() != null) {
                descuento = (movimientoSeleccionado.getCostoUnitario() * cantidad.doubleValue() * invArticuloSeleccionado.getDescuento().doubleValue()) / 100;
            }

            precioTotal = (movimientoSeleccionado.getCostoUnitario() * cantidad.doubleValue()) - descuento;

            precioTotal = Round.round(precioTotal, 2);

            movimientoSeleccionado.setCostoTotal(precioTotal);

            Double totalPlusIVA = precioTotal * (1 + movimientoSeleccionado.getPorcentajeIva().doubleValue() / 100);
            LOGGER.info("$ descuento: " + descuento
                    + ", precioTotal: " + precioTotal
                    + ", totalPlusIVA: " + totalPlusIVA);
            invArticuloSeleccionado.setTotalPlusIVA(Round.round(totalPlusIVA, 2));

            agregarBloqueado = false;

            if (invArticuloSeleccionado.getDescuento() == null) {
                invArticuloSeleccionado.setDescuento(new BigDecimal(0.0));
            }

            if (maxPorcDescuento != null && maxPorcDescuento < invArticuloSeleccionado.getDescuento().doubleValue()) {
                agregarBloqueado = true;
            }

            cantidadExcedida = "";
            colorCantidadExcedida = "black";

            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:bloqueC:totalRegistro");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:totalIva");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:cantidadLabel");
            RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:botonAgregar");

        } else {
            cantidadExcedida = "Excedida";
            colorCantidadExcedida = "red";

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

        LOGGER.info("Precio venta: " + invArticuloSeleccionado.getPrecio()
                + ", Existencia: " + existence
                + ", Cantidad: " + cantidad.doubleValue()
                + ", agregarBloqueado: " + agregarBloqueado);
    }

    public void loadPrecioUnitarioByUnidadMedida(InvMovimientoDtll invMovimientoDtll) {
        InvMovimientoDtll movimientoDtll = obtenerMovimientoSeleccionado(invMovimientoDtll);
        String codUnidad;
        if (movimientoDtll.getCodUnidad() != null) {
            codUnidad = movimientoDtll.getCodUnidad();
        } else {
            codUnidad = movimientoDtll.getCodUnidad();
        }
        String codEmpresa = obtenerEmpresa();
        int codArticle = movimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo();

        StringBuilder id = new StringBuilder();

        id.append("find;codEmpresa=");
        id.append(codEmpresa);
        id.append(";");
        id.append("codUnidad=");
        id.append(codUnidad);
        id.append(";");
        id.append("codArticulo=");
        id.append(codArticle);
        InvUnidadAlternativa invUnidadAlternativa = invUnidadAlternativaFacadeREST.find_JSON(InvUnidadAlternativa.class,
                id.toString());
        movimientoDtll.setInvUnidadAlternativa(invUnidadAlternativa);
        BigDecimal auxPrecio = BigDecimal.ZERO;
        BigDecimal factor = BigDecimal.ZERO;
        String operador = "";

        if (invUnidadAlternativa != null) {
            auxPrecio = facCatalogoPrecioD.getAuxPrecio();
            factor = invUnidadAlternativa.getFactor();
            operador = invUnidadAlternativa.getOperador();
        }

        Double precio;
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

        movimientoDtll.setCostoUnitario(Round.round(precio, 4));
        movimientoDtll.setFactor(factor.toBigInteger());

        if (movimientoDtll.getCantidad() != null) {
            loadPrecioTotalByCantidad(movimientoDtll);
        }

        LOGGER.info("precio: " + precio + ", auxPrecio: " + auxPrecio + ", factor: " + factor
                + ", operador: " + operador + ", costoUnitario: " + movimientoDtll.getCostoUnitario());

        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:bloqueB:precioUnitario");
        RequestContext.getCurrentInstance().update("pedido:accordionPanel:formArticulo:existencia");
    }

    public String enviar() {
        try {
            if (invMovimientoCab.getFormaPago() == null) {
                throw new PayWayException("Por favor seleccione forma de pago.");
            }

            if (facturacionLimitada()) {
                throw new LimitException("No se puede facturar, cliente pasó el limite de facturación que es de : " + limiteFactura);
            }

            if (!contenidoValido()) {
                throw new EmptyException("No se puede enviar pedido, faltan datos.");
            }

            GnrContadorDocFacadeREST gnrContadorDocFacadeREST = new GnrContadorDocFacadeREST();
            BigDecimal numDocumentoResp = gnrContadorDocFacadeREST.numDocumento(BigDecimal.class,
                    "01", "03", "SAI", userManager.getCurrent().getNombreUsuario());
            prepararInvMovimientoCab(numDocumentoResp);
            prepararInvMovimientoDtlls(numDocumentoResp);

            prepararFacTmpFactC();
            prepararFacTmpFactDs(numDocumentoResp);

            Pedido pedido = new Pedido();

            pedido.setFacTmpFactC(facTmpFactC);
            pedido.setInvMovimientoCab(invMovimientoCab);
            agregarLog(pedido);

            LOGGER.info("Enviando Documento ...");
            invMovimientoCabFacadeREST = new InvMovimientoCabFacadeREST();
            invMovimientoCabFacadeREST.create_JSON(pedido);
            LOGGER.info("Documento Enviado.");

            limpiar();

            addMessage("Pedido enviado exitosamente.", "Num. Pedido: " + numDocumentoResp, FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "index?faces-redirect=true";
        } catch (NullPointerException | PayWayException | GPSException | EmptyException | LimitException | ClienteException | VendedorException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            addMessage("Advertencia", ex.getMessage(), FacesMessage.SEVERITY_WARN);
            return "pedido?faces-redirect=true";
        } finally {
            if (invMovimientoCabFacadeREST != null) {
                invMovimientoCabFacadeREST.close();
            }
        }
    }

    public void loadTipoPago() {
        invMovimientoCab.setFormaPago(formaPago);
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
        mapa.setDireccion("");

        iva = null;
        subTotal = null;
        total = null;

        invMovimientoCab = null;
        invMovimientoCab = new InvMovimientoCab();
        invMovimientoDtlls.clear();
        invMovimientoCab.setInvMovimientoDtllList(invMovimientoDtlls);

        facTmpFactC = null;
        facTmpFactC = new FacTmpFactC();
        facTmpFactDs.clear();
        facTmpFactC.setFacTmpFactDList(facTmpFactDs);

        articulos.clear();
        invMovimientoDtllSeleccionado = null;

        formaPago = null;

        observacion = new String();

        clientes.limpiar();
        cliente.limpiar();
    }

    private CxcCliente obtenerCliente() throws ClienteException {
        if (cliente.getCliente() == null) {
            throw new ClienteException("Por favor seleccione el cliente.");
        }

        return new CxcCliente(obtenerEmpresa(), cliente.getCliente().getCodCliente());
    }

    private VCliente obtenerVCliente() throws ClienteException {
        if (cliente.getCliente() == null) {
            throw new ClienteException("Por favor seleccione el cliente.");
        }

        return cliente.getCliente();

    }

    private void buscarDescuento(InvMovimientoDtll invMovimientoDtll) throws ClienteException {
        String articulosString = invArticuloFacadeREST.findByArticuloEmpresa(String.class,
                String.valueOf(invMovimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo()), obtenerEmpresa());
        List<InvArticulo> listaArticulos = gson.fromJson(articulosString, new TypeToken<java.util.List<InvArticulo>>() {
        }.getType());

        String clientesString = vClienteFacadeREST.findByClienteEmpresa(String.class,
                String.valueOf(obtenerVCliente().getCodCliente()), obtenerEmpresa());

        List<VCliente> listaClientes = gson.fromJson(clientesString, new TypeToken<java.util.List<VCliente>>() {
        }.getType());

        FacDescVolPK facDescVolPK = new FacDescVolPK();

        facDescVolPK.setCodEmpresa(obtenerEmpresa());
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

        LOGGER.info("codEmpresa: " + facDescVolPK.getCodEmpresa()
                + ", codGrupo: " + facDescVolPK.getCodGrupo()
                + ", codTipo: " + facDescVolPK.getCodTipo()
                + ", codGrupo1: " + facDescVolPK.getCodGrupo1()
                + ", codGrupo2: " + facDescVolPK.getCodGrupo2()
                + ", codGrupo3: " + facDescVolPK.getCodGrupo3()
        );

        FacDescVol facDescVol = facDescVolFacadeREST.find_JSON(FacDescVol.class, id.toString());

        if (facDescVol
                != null) {
            if (facDescVol.getMaxPorcDescuento() != null) {
                maxPorcDescuento = facDescVol.getMaxPorcDescuento().doubleValue();
                maxPorcDescuentoDisabled = false;
            } else {
                maxPorcDescuentoDisabled = true;
            }
            invMovimientoDtll.setDescuento(facDescVol.getPorcDescuento());

            LOGGER.info("% descuento: " + invArticuloSeleccionado.getDescuento()
                    + ", maxPorcDescuento: " + maxPorcDescuento
                    + ", maxPorcDescuentoDisabled: " + maxPorcDescuentoDisabled);
        }

    }

    private void findIva(InvMovimientoDtll invMovimientoDtll) {
        StringBuilder id = new StringBuilder();

        id.append("find;codEmpresa=");
        id.append(obtenerEmpresa());
        id.append(";");
        id.append("codIva=");
        id.append(invMovimientoDtll.getInvArticulo().getCodIva());

        InvIva invIva = invIvaFacadeREST.find_JSON(InvIva.class,
                id.toString());

        invMovimientoDtll.setPorcentajeIva(invIva.getValor());
    }

    private List<InvMovimientoDtll> obtenerMovimientos() {
        return invMovimientoDtlls;
    }

    private InvMovimientoDtll obtenerMovimientoSeleccionado(InvMovimientoDtll invMovimientoDtll) {
        return invMovimientoDtlls.get(invMovimientoDtlls.indexOf(invMovimientoDtll));
    }

    private void calcularResumen() {
        subTotal = 0.0;
        iva = 0.0;
        total = 0.0;
        totalSinIva = 0.0;
        totalConIva = 0.0;
        for (InvMovimientoDtll invMovimientoDtll1 : obtenerMovimientos()) {
            BigDecimal cantidad = BigDecimal.ZERO;
            if (invMovimientoDtll1.getCantidad() != null) {
                cantidad = invMovimientoDtll1.getCantidad();
            }

            Double descuento = 0.0;
            if (invMovimientoDtll1.getDescuento() != null) {
                descuento = (invMovimientoDtll1.getCostoUnitario() * cantidad.doubleValue() * invMovimientoDtll1.getDescuento().doubleValue()) / 100;
            } else {
                invMovimientoDtll1.setDescuento(new BigDecimal(0));
            }

            if (invMovimientoDtll1.getCantidad() == null) {
                invMovimientoDtll1.setCantidad(new BigDecimal(0));
            }

            if (invMovimientoDtll1.getCostoUnitario() == null) {
                invMovimientoDtll1.setCostoUnitario(0.0);
            }

            Double _subTotal = Round.round((invMovimientoDtll1.getCostoUnitario() * (invMovimientoDtll1.getCantidad().intValue())) - descuento, 2);

            if (invMovimientoDtll1.getPorcentajeIva() == null) {
                invMovimientoDtll1.setPorcentajeIva(new BigDecimal(0));
            }

            Double _iva = (_subTotal) * (invMovimientoDtll1.getPorcentajeIva().doubleValue() / 100);

            subTotal += Round.round(_subTotal, 2);

            iva += Round.round(_iva, 2);

            total += Round.round((_subTotal + _iva), 2);

            LOGGER.info("CANTIDAD: " + cantidad.toString()
                    + ", DESCUENTO: " + descuento.toString()
                    + ", _subTotal: " + _subTotal
                    + ", _iva: " + _iva
                    + ", subTotal: " + subTotal
                    + ", iva: " + iva
                    + ", total: " + total);
        }
        total = Round.round(total, 2);
        subTotal = Round.round(subTotal, 2);
    }

    private String obtenerEmpresa() {
        return userManager.getGnrEmpresa().getCodEmpresa();
    }

    private void prepararFacTmpFactC() {
        Calendar c = Calendar.getInstance();
        facTmpFactC.setCodCliente(invMovimientoCab.getCxcCliente().getCxcClientePK().getCodCliente().longValue());
        facTmpFactC.setCodDivisa("01");
        facTmpFactC.setCodDocumento("FAC");
        facTmpFactC.setCodPago(invMovimientoCab.getFormaPago());
        facTmpFactC.setCodVendedor(invMovimientoCab.getCodVendedor());
        facTmpFactC.setContCred(invMovimientoCab.getDiasPlazo().toString());
        facTmpFactC.setDescuentos(invMovimientoCab.getDescuentos());
        facTmpFactC.setEstado("G");
        facTmpFactC.setFechaEstado(c.getTime());
        facTmpFactC.setFechaFactura(c.getTime());
        facTmpFactC.setGnrEmpresa(invMovimientoCab.getGnrEmpresa());
        facTmpFactC.setIva(invMovimientoCab.getIva());
        facTmpFactC.setNombreUsuario(invMovimientoCab.getNombreUsuario());
        facTmpFactC.setNumFactura(null);
        facTmpFactC.setOtrDescuentos(BigInteger.ZERO);
        facTmpFactC.setPorcComision(BigDecimal.ZERO);
        facTmpFactC.setRazonSocial(invMovimientoCab.getRazonSocial());
        facTmpFactC.setRecargos(BigInteger.ZERO);
        facTmpFactC.setTipoFactura("A");
        facTmpFactC.setTotalConIva(invMovimientoCab.getTConIva().longValue());
        facTmpFactC.setTotalFactura(new BigDecimal(invMovimientoCab.getTotalDocumento()));
        facTmpFactC.setTotalSinIva(invMovimientoCab.getTSinIva());
        facTmpFactC.setValorDivisa(BigInteger.ONE);
        FacTmpFactCPK facTmpFactCPK = new FacTmpFactCPK(invMovimientoCab.getInvMovimientoCabPK().getCodEmpresa(), Integer.parseInt(String.valueOf(invMovimientoCab.getInvMovimientoCabPK().getNumDocumento())), "SAI");
        facTmpFactC.setFacTmpFactCPK(facTmpFactCPK);

        facTmpFactC.setObservacion(observacion);
    }

    private void prepararFacTmpFactDs(BigDecimal numDocumentoResp) throws EmptyException {

        for (InvMovimientoDtll invMovimientoDtll : invMovimientoDtlls) {
            FacTmpFactDPK facTmpFactDPK = new FacTmpFactDPK();
            facTmpFactDPK.setAuxiliar(invMovimientoDtll.getInvMovimientoDtllPK().getNumLinea());
            facTmpFactDPK.setCodEmpresa(obtenerEmpresa());
            facTmpFactDPK.setEgresoInv(numDocumentoResp.intValue());
            facTmpFactDPK.setEi("SAI");

            FacTmpFactD facTmpFactD = new FacTmpFactD();
            facTmpFactD.setAuxCantidad(invMovimientoDtll.getAuxCantidad());
            facTmpFactD.setCantidad(invMovimientoDtll.getCantidad().doubleValue());
            facTmpFactD.setCantidadDevuelta(BigInteger.ZERO); // TODO
            facTmpFactD.setCodBodega(invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodBodega());
            facTmpFactD.setCodInventario(codInventario);
            facTmpFactD.setDetalle("Web Channel"); // TODO
            facTmpFactD.setEntregado(null); //TODO
            facTmpFactD.setFacTmpFactDPK(facTmpFactDPK);
            facTmpFactD.setFactor(new BigDecimal(invMovimientoDtll.getFactor()));
            facTmpFactD.setInvUnidadAlternativa(invMovimientoDtll.getInvUnidadAlternativa());
            facTmpFactD.setOperador(invMovimientoDtll.getOperador());
            facTmpFactD.setPorcDescPago(BigDecimal.ZERO); //TODO
            facTmpFactD.setPorcDescProm(BigDecimal.ZERO); //TODO
            facTmpFactD.setPorcDescVol(invMovimientoDtll.getPorcDesc1()); //TODO
            facTmpFactD.setPorcentajeIva(invMovimientoDtll.getPorcentajeIva());
            LOGGER.log(Level.INFO, "CostoUnitario: {0}", invMovimientoDtll.getCostoUnitario());
            facTmpFactD.setPrecioUnitario(invMovimientoDtll.getCostoUnitario()); //TODO
            facTmpFactD.setPromocion(null); //TODO
            facTmpFactD.setSerie(null); //TODO
            facTmpFactD.setTotalReg(invMovimientoDtll.getCostoTotal()); //TODO

            facTmpFactDs.add(facTmpFactD);
        }
    }

    private void prepararInvMovimientoCab(BigDecimal numDocumentoResp) throws PayWayException, GPSException, ClienteException, VendedorException {
        CxcCliente cxcCliente = obtenerCliente();
        invMovimientoCab.setCxcCliente(cxcCliente);

        if (invMovimientoCab.getFormaPago() == null) {
            throw new PayWayException("Por favor seleccione la forma de pago.");
        }

        if (mapa.getDireccion() == null) {
            throw new GPSException("Por favor active el GPS y seleccione Geolocalizar.");
        }

        InvMovimientoCabPK invMovimientoCabPK = new InvMovimientoCabPK();
        invMovimientoCabPK.setCodEmpresa(obtenerEmpresa());
        invMovimientoCabPK.setCodDocumento("SAI");
        invMovimientoCabPK.setNumDocumento(numDocumentoResp.longValue());
        invMovimientoCab.setAutoContDoc(null);
        invMovimientoCab.setAutoContImprDoc(null);
        invMovimientoCab.setDescuentos(BigInteger.ZERO);
        invMovimientoCab.setDetalle(new StringBuilder("FAC ").append(obtenerVCliente().getRazonSocial()).toString());
        invMovimientoCab.setDigitadoPor(userManager.getUserName());
        invMovimientoCab.setDiasPlazo(1);
        invMovimientoCab.setEstado("G");
        invMovimientoCab.setFechEmisDoc(null);
        invMovimientoCab.setFechCaduDoc(null);
        invMovimientoCab.setFechaVencimiento(null);
        invMovimientoCab.setFechaEmision(Calendar.getInstance().getTime());
        invMovimientoCab.setFechaEstado(Calendar.getInstance().getTime());
        invMovimientoCab.setFletes(BigInteger.ZERO);

        GnrDivisa gnrDivisa = new GnrDivisa("01", obtenerEmpresa());
        invMovimientoCab.setGnrDivisa(gnrDivisa);
        InvTransacciones invTransacciones = new InvTransacciones(obtenerEmpresa(), "20");
        invMovimientoCab.setInvTransacciones(invTransacciones);
        invMovimientoCab.setInvMovimientoCabPK(invMovimientoCabPK);
        invMovimientoCab.setInvProveedor(null);
        invMovimientoCab.setIva(BigInteger.ZERO);
        invMovimientoCab.setNumRetencion(null);
        invMovimientoCab.setNumFactRete(null);
        invMovimientoCab.setNroCuotas(null);
        invMovimientoCab.setReferencia("FAC");
        invMovimientoCab.setOtrDescuentos(BigInteger.ZERO);
        invMovimientoCab.setOtrCargos(BigInteger.ZERO);
        invMovimientoCab.setRetencion(BigInteger.ZERO);
        invMovimientoCab.setRecargos(BigInteger.ZERO);
        invMovimientoCab.setTConIva(totalConIva);
        invMovimientoCab.setTSinIva(totalSinIva);
        invMovimientoCab.setTotalDocumento(total);

        invMovimientoCab.setCodVendedor(obtenerVendedor());
        invMovimientoCab.setNombreUsuario(obtenerUsuario());
        invMovimientoCab.setRazonSocial(cliente.getCliente().getRazonSocial());

        LOGGER.log(Level.INFO, "enviar ::::::::::::::: {0} articulos", invMovimientoCab.getInvMovimientoDtllList().size());
    }

    private void prepararInvMovimientoDtlls(BigDecimal numDocumentoResp) throws EmptyException {
        if (invMovimientoDtlls.isEmpty()) {
            throw new EmptyException("Por favor seleccione al menos un artículo.");
        }

        Double subtotal = 0.0;
        for (InvMovimientoDtll invMovimientoDtll : invMovimientoDtlls) {
            InvBodegaArt invBodegaArt = invMovimientoDtll.getInvBodegaArt();

            String codBodega = new BodegaUtil().obtenerCodBodega(invMovimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo());

            LOGGER.log(Level.INFO, "codBodega recuperado: {0}", codBodega);

            invBodegaArt.getInvBodegaArtPK().setCodBodega(codBodega);

            subtotal = subtotal + invMovimientoDtll.getCostoTotal();

            InvMovimientoDtllPK invMovimientoDtllPK = new InvMovimientoDtllPK(obtenerEmpresa(), "SAI", numDocumentoResp.longValue(), Short.valueOf(String.valueOf(invMovimientoDtlls.indexOf(invMovimientoDtll) + 1)));
            invMovimientoDtll.setInvMovimientoDtllPK(invMovimientoDtllPK);

            if (invMovimientoDtll.getDescuento() == null) {
                invMovimientoDtll.setDescuento(BigDecimal.ZERO);
            }

            if (invMovimientoDtll.getInvMovimientoDtllPK() != null) {
                LOGGER.log(Level.INFO, "InvMovimientoDtllPK: {0}", invMovimientoDtll.getInvMovimientoDtllPK().toString());
            } else {
                LOGGER.info("InvMovimientoDtllPK: null");
            }
        }

        invMovimientoCab.setSubtotal(subtotal);

    }

    private FacParametros obtenerFacParametros() {
        FacParametros facParametros = facParametrosFacadeREST.find_JSON(
                FacParametros.class, "id;codEmpresa=" + obtenerEmpresa()
                + ";nombreUsuario=" + userManager.getCurrent().getNombreUsuario());
        return facParametros;
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

        return defCodVendedor;
    }

    private GnrUsuarios obtenerUsuario() {
        GnrUsuarios gnrUsuarios = obtenerFacParametros().getGnrUsuarios();
        LOGGER.log(Level.INFO, "gnrUsuarios: {0}", gnrUsuarios);
        return gnrUsuarios;
    }

    private InvUnidadAlternativa obtenerInvUnidadAlternativa(InvMovimientoDtll invMovimientoDtll) {
        String codUnidad;
        if (invMovimientoDtll.getCodUnidad() != null) {
            codUnidad = invMovimientoDtll.getCodUnidad();
        } else {
            codUnidad = invMovimientoDtll.getCodUnidad();
        }
        String codEmpresa = obtenerEmpresa();
        int codArticle = invMovimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo();

        StringBuilder id = new StringBuilder();

        id.append("find;codEmpresa=");
        id.append(codEmpresa);
        id.append(";");
        id.append("codUnidad=");
        id.append(codUnidad);
        id.append(";");
        id.append("codArticulo=");
        id.append(codArticle);

        InvUnidadAlternativa invUnidadAlternativa = invUnidadAlternativaFacadeREST.find_JSON(InvUnidadAlternativa.class,
                id.toString());
        invMovimientoDtll.setInvUnidadAlternativa(invUnidadAlternativa);

        return invUnidadAlternativa;
    }

    private boolean facturacionLimitada() {
        if (invMovimientoCab.getFormaPago().equals("1")) {
            Double sumSaldoDocumento = obtenerSumSaldoDocumento();
            LOGGER.log(Level.INFO, "sumSaldoDocumento: {0}", sumSaldoDocumento);
            Double sumCapital = obtenerSumCapital();
            LOGGER.log(Level.INFO, "sumCapital: {0}", sumCapital);
            limiteFactura = obtenerLimiteFactura();
            LOGGER.log(Level.INFO, "limiteFactura: {0}", limiteFactura);

            Double saldoActual = sumSaldoDocumento + sumCapital;

            return ((saldoActual + total) > limiteFactura);
        } else {
            return false;
        }

    }

    private Double obtenerLimiteFactura() {
        Double limiteFact = cliente.getCliente().getLimiteFactura();
        LOGGER.log(Level.INFO, "limiteFactura: {0}", limiteFact);
        return limiteFact;
    }

    private Double obtenerSumSaldoDocumento() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMyyyy");
        String formatted = format.format(c.getTime());
        String sum = cxcDocCobrarFacadeREST.sumSaldoDocumentoByCodClienteCodEmpresaFechaFac(cliente.getCliente().getCodCliente().toString(), obtenerEmpresa(), formatted);
        if (sum.isEmpty()) {
            return 0.0;
        } else {
            return new Double(sum);
        }
    }

    private Double obtenerSumCapital() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMyyyy");
        String formatted = format.format(c.getTime());
        String sum = cxcDocCobrarFacadeREST.sumCapitalByCodClienteCodEmpresaFechaRecepcion(cliente.getCliente().getCodCliente().toString(), obtenerEmpresa(), formatted);
        if (sum.isEmpty()) {
            return 0.0;
        } else {
            return new Double(sum);
        }
    }

    private void agregarLog(Pedido pedido) throws VendedorException {
        GnrLogHistorico gnrLogHistorico = new GnrLogHistorico();
        gnrLogHistorico.setDispositivo("Tablet");
        gnrLogHistorico.setEstado("G");
        gnrLogHistorico.setFechaDocumento(Calendar.getInstance().getTime());
        gnrLogHistorico.setFechaEstado(Calendar.getInstance().getTime());
        GnrLogHistoricoPK gnrLogHistoricoPK = new GnrLogHistoricoPK();
        gnrLogHistoricoPK.setCodDocumento(pedido.getInvMovimientoCab().getInvMovimientoCabPK().getCodDocumento());
        gnrLogHistoricoPK.setCodEmpresa(obtenerEmpresa());
        gnrLogHistoricoPK.setNumDocumento(new Long(pedido.getInvMovimientoCab().getInvMovimientoCabPK().getNumDocumento()).intValue());
        gnrLogHistorico.setGnrLogHistoricoPK(gnrLogHistoricoPK);
        gnrLogHistorico.setNombreUsuario(userManager.getCurrent().getNombreUsuario());
        gnrLogHistorico.setUbicacionGeografica(mapa.getDireccion());
        gnrLogHistorico.setLatitud(Double.valueOf(mapa.getLat()));
        gnrLogHistorico.setLongitud(Double.valueOf(mapa.getLng()));
        pedido.setGnrLogHistorico(gnrLogHistorico);
    }

    private boolean contenidoValido() {
        for (InvMovimientoDtll invMovimientoDtll : invMovimientoDtlls) {
            if (invMovimientoDtll.getCantidad() == null || invMovimientoDtll.getCostoTotal() == null) {
                return false;
            }
        }
        return true;
    }

    private void agregarValores(InvMovimientoDtll invMovimientoDtll) {
        setInvMovimientoDtllSeleccionado(invMovimientoDtll);
        try {

            String codArticle = String.valueOf(invMovimientoDtll.getInvBodegaArt().getInvBodegaArtPK().getCodArticulo());

            String codEmpresa = obtenerEmpresa();

            StringBuilder id = new StringBuilder();

            id.append("find;codEmpresa=");
            id.append(codEmpresa);
            id.append(";codCatalogo=01;codArticulo=");
            id.append(codArticle);

            FacCatalogoPrecioDFacadeREST facCatalogoPrecioDFacadeREST = new FacCatalogoPrecioDFacadeREST();
            String response = facCatalogoPrecioDFacadeREST.find_JSON(String.class,
                    id.toString());

            facCatalogoPrecioD = gson.fromJson(response, new TypeToken<FacCatalogoPrecioD>() {
            }.getType());

            invMovimientoDtll.setPrecioVenta(facCatalogoPrecioD.getPrecioVenta1());

            LOGGER.info("Cod Articulo: " + codArticle
                    + ", Precio Venta: " + invMovimientoDtll.getPrecioVenta());

            if (invMovimientoDtll.getDescuento() == null) {
                if (obtenerCliente() != null) {
                    buscarDescuento(invMovimientoDtll);
                }
            }

            findIva(invMovimientoDtll);
            LOGGER.info("PorcentajeIva: " + invMovimientoDtll.getPorcentajeIva());

            loadPrecioUnitarioByUnidadMedida(invMovimientoDtll);
        } catch (ClienteException ex) {
            addMessage("Advertencia", ex.getMessage(), FacesMessage.SEVERITY_INFO);
        }
    }

    private void obtenerPromocion(InvMovimientoDtll invMovimientoDtll) {
        InvMovimientoDtll promocion = invArticuloFacadeREST.findPromocion(InvMovimientoDtll.class, obtenerEmpresa(),
                "02", String.valueOf(invMovimientoDtll.getInvArticulo().getInvArticuloPK().getCodArticulo()),
                invMovimientoDtll.getCantidad().intValue(), invMovimientoDtll.getAuxCantidad().intValue());
        if (promocion != null && promocion.getMensaje().equals("OK")
                && promocion.getInvArticulo() != null
                && promocion.getInvArticulo().getInvArticuloPK() != null
                && promocion.getInvArticulo().getInvArticuloPK().getCodArticulo() != 0) {
            invMovimientoDtlls.add(promocion);
        } else if (promocion != null && promocion.getMensaje().equals("OK")
                && promocion.getInvArticulo() != null
                && promocion.getInvArticulo().getInvArticuloPK() != null
                && promocion.getInvArticulo().getInvArticuloPK().getCodArticulo() == 0
                && promocion.getPorcDesc1() != null) {
            invMovimientoDtll.setPorcDesc1(promocion.getPorcDesc1());
        }
    }
}
