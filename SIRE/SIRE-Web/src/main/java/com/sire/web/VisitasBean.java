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
import com.sire.exception.ClienteException;
import com.sire.exception.EmptyException;
import com.sire.exception.GPSException;
import com.sire.rs.client.ComVisitaClienteFacadeREST;
import com.sire.rs.client.GnrContadorDocFacadeREST;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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

            GnrContadorDocFacadeREST gnrContadorDocFacadeREST = new GnrContadorDocFacadeREST();
            BigDecimal numDocumentoResp = gnrContadorDocFacadeREST.numDocumento(BigDecimal.class,
                    "01", "03", "SAI", userManager.getCurrent().getNombreUsuario());

            ComVisitaCliente comVisitaCliente = new ComVisitaCliente();
            comVisitaCliente.setCodCliente(obtenerCliente().getCxcClientePK().getCodCliente().toString());

            ComVisitaClientePK comVisitaClientePK = new ComVisitaClientePK();
            comVisitaClientePK.setCodEmpresa(obtenerEmpresa());
//            comVisitaClientePK.setCodDocumento();  //validar
//            comVisitaClientePK.setNumVisita(); //validar

            comVisitaCliente.setComVisitaClientePK(comVisitaClientePK);

//            comVisitaCliente.setDescCliente(""); //validar
//            comVisitaCliente.setEstado(""); //validar
//            comVisitaCliente.setFechaEstado(); //validar
//            comVisitaCliente.setFechaVisita(); //validar
            comVisitaCliente.setGnrEmpresa(obtenerGnrEmpresa()); //validar
            comVisitaCliente.setLatitud(new BigDecimal(mapa.getLat()));
            comVisitaCliente.setLongitud(new BigDecimal(mapa.getLng()));
            comVisitaCliente.setObservacion(observacion);
            comVisitaCliente.setUbicacionGeografica(mapa.getDireccion());

            LOGGER.info("Enviando Documento ...");
            comVisitaClienteFacadeREST.create_JSON(comVisitaCliente);
            comVisitaClienteFacadeREST.close();
            LOGGER.info("Documento Enviado.");

            limpiar();

            addMessage("Visita enviada exitosamente.", "Num. Pedido: " + "???", FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "index?faces-redirect=true";
        } catch (NullPointerException | GPSException | EmptyException | ClienteException ex) {
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

}
