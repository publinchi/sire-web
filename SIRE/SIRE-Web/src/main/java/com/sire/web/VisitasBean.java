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
import com.sire.entities.GnrEmpresa;
import com.sire.entities.GnrLogHistorico;
import com.sire.entities.GnrLogHistoricoPK;
import com.sire.exception.ClienteException;
import com.sire.exception.EmptyException;
import com.sire.exception.GPSException;
import com.sire.exception.VendedorException;
import com.sire.rs.client.ComVisitaClienteFacadeREST;
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

            CxcCliente cxcCliente = obtenerCliente();

            GnrContadorDocFacadeREST gnrContadorDocFacadeREST = new GnrContadorDocFacadeREST();
            BigDecimal numDocumentoResp = gnrContadorDocFacadeREST.numDocumento(BigDecimal.class,
                    "01", "04", "VIS", userManager.getCurrent().getNombreUsuario());

            ComVisitaCliente comVisitaCliente = new ComVisitaCliente();
            comVisitaCliente.setCodCliente(cxcCliente.getCxcClientePK().getCodCliente().toString());

            ComVisitaClientePK comVisitaClientePK = new ComVisitaClientePK();
            comVisitaClientePK.setCodEmpresa(obtenerEmpresa());
            comVisitaClientePK.setCodDocumento("VIS");
            comVisitaClientePK.setNumVisita(numDocumentoResp.intValue());

            comVisitaCliente.setComVisitaClientePK(comVisitaClientePK);

            comVisitaCliente.setDescCliente(cxcCliente.getClaseCliete().getDescripcion());
            comVisitaCliente.setEstado("G");
            comVisitaCliente.setFechaEstado(Calendar.getInstance().getTime());
            comVisitaCliente.setFechaVisita(Calendar.getInstance().getTime());
            comVisitaCliente.setGnrEmpresa(obtenerGnrEmpresa());
            comVisitaCliente.setLatitud(new BigDecimal(mapa.getLat()));
            comVisitaCliente.setLongitud(new BigDecimal(mapa.getLng()));
            comVisitaCliente.setObservacion(observacion);
            comVisitaCliente.setUbicacionGeografica(mapa.getDireccion());

            agregarLog(comVisitaCliente);

            LOGGER.info("Enviando Documento ...");
            comVisitaClienteFacadeREST.save_JSON(comVisitaCliente);
            comVisitaClienteFacadeREST.close();
            LOGGER.info("Documento Enviado.");

            limpiar();

            addMessage("Visita enviada exitosamente.", "Num. Visita: " + numDocumentoResp, FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "index?faces-redirect=true";
        } catch (NullPointerException | GPSException | EmptyException | ClienteException | VendedorException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
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

}
