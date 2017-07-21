/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sire.entities.ComVisitaCliente;
import com.sire.entities.ComVisitaClientePK;
import com.sire.entities.CxcCliente;
import com.sire.entities.FacParametros;
import com.sire.entities.FacVendedor;
import com.sire.entities.GnrEmpresa;
import com.sire.entities.GnrLogHistorico;
import com.sire.entities.GnrLogHistoricoPK;
import com.sire.entities.GnrUsuarios;
import com.sire.entities.VCliente;
import com.sire.exception.ClienteException;
import com.sire.exception.EmptyException;
import com.sire.exception.GPSException;
import com.sire.exception.RestException;
import com.sire.exception.VendedorException;
import com.sire.rs.client.ComVisitaClienteFacadeREST;
import com.sire.rs.client.FacParametrosFacadeREST;
import com.sire.rs.client.GnrContadorDocFacadeREST;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author pestupinan
 */
@ManagedBean(name = "visitasBean")
@SessionScoped
@Getter
@Setter
public class VisitasBean {

    private static final Logger LOGGER = Logger.getLogger(VisitasBean.class.getName());

    private final ComVisitaClienteFacadeREST comVisitaClienteFacadeREST;
    private final FacParametrosFacadeREST facParametrosFacadeREST;
    private final GsonBuilder builder;
    private final Gson gson;

    @ManagedProperty(value = "#{user}")
    private UserManager userManager;
    @ManagedProperty("#{cliente}")
    private CustomerBean cliente;
    @ManagedProperty("#{customers}")
    private CustomersBean clientes;
    @ManagedProperty("#{mapa}")
    private MapaBean mapa;

    //Mensaje
    private String cantidadExcedida, colorCantidadExcedida = "black", observacion;

    public VisitasBean() {
        observacion = new String();
        comVisitaClienteFacadeREST = new ComVisitaClienteFacadeREST();
        facParametrosFacadeREST = new FacParametrosFacadeREST();
        builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    }

    public String enviar() {
        try {
            if (mapa.getDireccion() == null) {
                throw new GPSException("Por favor active el GPS y seleccione Geolocalizar.");
            }

            if (observacion.isEmpty()) {
                throw new EmptyException("Por favor ingrese observación.");
            }

            VCliente vCliente = obtenerVCliente();

            GnrContadorDocFacadeREST gnrContadorDocFacadeREST = new GnrContadorDocFacadeREST();
            BigDecimal numDocumentoResp = gnrContadorDocFacadeREST.numDocumento(BigDecimal.class,
                    "01", "04", "VIS", userManager.getCurrent().getNombreUsuario());

            ComVisitaCliente comVisitaCliente = new ComVisitaCliente();
            comVisitaCliente.setCodCliente(vCliente.getCodCliente().toString());

            ComVisitaClientePK comVisitaClientePK = new ComVisitaClientePK();
            comVisitaClientePK.setCodEmpresa(obtenerEmpresa());
            comVisitaClientePK.setCodDocumento("VIS");
            comVisitaClientePK.setNumVisita(numDocumentoResp.intValue());

            comVisitaCliente.setComVisitaClientePK(comVisitaClientePK);

            comVisitaCliente.setDescCliente(vCliente.getRazonSocial());
            comVisitaCliente.setEstado("G");
            comVisitaCliente.setFechaEstado(Calendar.getInstance().getTime());
            comVisitaCliente.setFechaVisita(Calendar.getInstance().getTime());
            comVisitaCliente.setGnrEmpresa(obtenerGnrEmpresa());
            comVisitaCliente.setObservacion(observacion);
            comVisitaCliente.setNombreUsuario(obtenerUsuario());
            comVisitaCliente.setFacVendedor(new FacVendedor("01", obtenerVendedor()));

            agregarLog(comVisitaCliente);

            LOGGER.info("Enviando Documento ...");
            Response response = comVisitaClienteFacadeREST.save_JSON(comVisitaCliente);

            LOGGER.log(Level.INFO, "Response: {0}", response.toString());
            LOGGER.log(Level.INFO, "Status: {0}", response.getStatus());
            LOGGER.log(Level.INFO, "Status info: {0}", response.getStatusInfo().getReasonPhrase());

            if (response.getStatus() != 200) {
                throw new RestException("No se pudo realizar la visita, por favor contacte al administrador.");
            }

            comVisitaClienteFacadeREST.close();
            LOGGER.info("Documento Enviado.");

            limpiar();

            addMessage("Visita enviada exitosamente.", "Num. Visita: " + numDocumentoResp, FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "index?faces-redirect=true";
        } catch (NullPointerException | GPSException | EmptyException | ClienteException | VendedorException | RestException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            System.err.println(ex);
            addMessage("Advertencia", ex.getMessage(), FacesMessage.SEVERITY_WARN);
            return "visita?faces-redirect=true";
        }
    }

    private void addMessage(String summary, String detail, Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void limpiar() {
        mapa.setDireccion("");

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

    private String obtenerEmpresa() {
        return obtenerGnrEmpresa().getCodEmpresa();
    }

    private GnrEmpresa obtenerGnrEmpresa() {
        return userManager.getGnrEmpresa();
    }

    private void agregarLog(ComVisitaCliente comVisitaCliente) throws VendedorException {
        GnrLogHistorico gnrLogHistorico = new GnrLogHistorico();
        gnrLogHistorico.setDispositivo("Tablet");
        gnrLogHistorico.setEstado("G");
        gnrLogHistorico.setFechaDocumento(Calendar.getInstance().getTime());
        gnrLogHistorico.setFechaEstado(Calendar.getInstance().getTime());
        GnrLogHistoricoPK gnrLogHistoricoPK = new GnrLogHistoricoPK();
        gnrLogHistoricoPK.setCodDocumento(comVisitaCliente.getComVisitaClientePK().getCodDocumento());
        gnrLogHistoricoPK.setCodEmpresa(obtenerEmpresa());
        gnrLogHistoricoPK.setNumDocumento(comVisitaCliente.getComVisitaClientePK().getNumVisita());
        gnrLogHistorico.setGnrLogHistoricoPK(gnrLogHistoricoPK);
        gnrLogHistorico.setNombreUsuario(userManager.getCurrent().getNombreUsuario());
        gnrLogHistorico.setUbicacionGeografica(mapa.getDireccion());
        gnrLogHistorico.setLatitud(Double.valueOf(mapa.getLat()));
        gnrLogHistorico.setLongitud(Double.valueOf(mapa.getLng()));
        comVisitaCliente.setGnrLogHistorico(gnrLogHistorico);
    }

    private VCliente obtenerVCliente() throws ClienteException {
        if (cliente.getCliente() == null) {
            throw new ClienteException("Por favor seleccione el cliente.");
        }

        return cliente.getCliente();

    }

    private GnrUsuarios obtenerUsuario() {
        GnrUsuarios gnrUsuarios = obtenerFacParametros().getGnrUsuarios();
        LOGGER.log(Level.INFO, "gnrUsuarios: {0}", gnrUsuarios);
        return gnrUsuarios;
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

        LOGGER.log(Level.INFO, "codVendedor: {0}", defCodVendedor);
        return defCodVendedor;
    }
}
