/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.BanCtaCte;
import com.sire.entities.CxcAbonoC;
import com.sire.entities.CxcAbonoCPK;
import com.sire.entities.CxcAbonoD;
import com.sire.entities.CxcAbonoDPK;
import com.sire.entities.CxcCheque;
import com.sire.entities.CxcChequePK;
import com.sire.entities.CxcCliente;
import com.sire.entities.CxcDocCobrar;
import com.sire.entities.CxcPagoContado;
import com.sire.entities.CxcPagoContadoPK;
import com.sire.entities.FacParametros;
import com.sire.entities.Pago;
import com.sire.entities.VCliente;
import com.sire.exception.EmptyException;
import com.sire.rs.client.BanCtaCteFacadeREST;
import com.sire.rs.client.CxcDocCobrarFacadeREST;
import com.sire.rs.client.FacParametrosFacadeREST;
import com.sire.rs.client.GnrContadorDocFacadeREST;
import com.sire.utils.Round;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author publio
 */
@ManagedBean(name = "cxcDocCobrarBean")
@SessionScoped

public class CxcDocCobrarBean {
    
    private static final Logger logger = Logger.getLogger(CxcDocCobrarBean.class.getName());
    
    private final FacParametrosFacadeREST facParametrosFacadeREST;
    private final Gson gson;
    
    @Setter
    private List<CxcDocCobrar> cxcDocCobrarList;
    
    @Getter
    @Setter
    @ManagedProperty("#{cliente}")
    private CustomerBean cliente;
    
    @Getter
    @Setter
    @ManagedProperty(value = "#{user}")
    private UserManager userManager;
    
    @Getter
    @Setter
    private Double totalSaldo, totalCapital, capital, diferencia,
            retencion = 0.0, retencionIVA = 0.0, efectivo = 0.0, deposito = 0.0,
            otros = 0.0, valorCheque = 0.0, totalCheques = 0.0;
    
    @Getter
    @Setter
    private VCliente client;
    
    @Getter
    @Setter
    private CxcDocCobrar cxcDocCobrarSeleccionado;
    
    @Getter
    @Setter
    private boolean botonEnviarBloqueado = true, botonAgegarChequeBloqueado = true;
    
    @Getter
    @Setter
    private String numeroCuenta, codBanco, numCuenta;
    
    @Getter
    @Setter
    private Date fechaCheque;
    
    @Getter
    @Setter
    private BigInteger numCheque;
    
    @Getter
    @Setter
    private List<BanCtaCte> banCtaCtes;
    
    @Getter
    @Setter
    private List<CxcCheque> cxcCheques;
    
    public CxcDocCobrarBean() {
        facParametrosFacadeREST = new FacParametrosFacadeREST();
        GsonBuilder builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        
        cxcCheques = new ArrayList<>();
        loadBanCtaCtes();
    }
    
    public void findDocCobrar() {
//        VCliente c = cliente.getCliente();
//
//        if (c != null) {
//            c.getCodCliente();
//        }

    }
    
    public List<CxcDocCobrar> getCxcDocCobrarList() {
        if (cliente.getCliente() != null && cxcDocCobrarList == null) {
            loadCxcDocCobrarList();
        }
        return cxcDocCobrarList;
    }
    
    public void seleccionarCxcDocCobrar(SelectEvent event) {
        CxcDocCobrar cxcDocCobrar = (CxcDocCobrar) event.getObject();
        logger.log(Level.INFO, "CxcDocCobrar 1 {0}", cxcDocCobrar.getCxcDocCobrarPK().getNumDocumento());
        cxcDocCobrarSeleccionado = cxcDocCobrarList.get(cxcDocCobrarList.indexOf(cxcDocCobrar));
        logger.log(Level.INFO, "CxcDocCobrar 2 {0}", cxcDocCobrar.getCxcDocCobrarPK().getNumDocumento());
    }
    
    public void calcularSaldo() {
        Double nuevoSaldo;
        
        logger.info("capital: " + cxcDocCobrarSeleccionado.getCapital());
        
        if (cxcDocCobrarSeleccionado.getSaldoOri() == null) {
            cxcDocCobrarSeleccionado.setSaldoOri(cxcDocCobrarSeleccionado.getSaldoDocumento());
        } else {
            cxcDocCobrarSeleccionado.setSaldoDocumento(cxcDocCobrarSeleccionado.getSaldoOri());
        }
        
        logger.info("antiguoSaldo: " + cxcDocCobrarSeleccionado.getSaldoDocumento());
        
        if (capital != null && cxcDocCobrarSeleccionado.getSaldoDocumento() >= capital) {
            
            cxcDocCobrarSeleccionado.setCapital(capital);
            nuevoSaldo = cxcDocCobrarSeleccionado.getSaldoDocumento() - cxcDocCobrarSeleccionado.getCapital();
            cxcDocCobrarSeleccionado.setSaldoDocumento(nuevoSaldo);
            
            logger.info("nuevoSaldo: " + cxcDocCobrarSeleccionado.getSaldoDocumento());
        }
        
        calcularTotales();
        calcularFormaPago();
        capital = null;
        RequestContext.getCurrentInstance().update("cobro:accordionPanel:formaPagoForm:pagoTotal");
    }
    
    public void calcularFormaPago() {
        logger.info("calcularFormaPago()");
        diferencia = totalCapital - retencion - retencionIVA - efectivo - deposito - otros - totalCheques - valorCheque;
        diferencia = Round.round(diferencia, 2);
        if (diferencia == 0) {
            botonEnviarBloqueado = false;
        } else if (diferencia >= 0) {
            botonAgegarChequeBloqueado = false;
        } else {
            botonEnviarBloqueado = true;
            botonAgegarChequeBloqueado = true;
        }
    }
    
    public String enviar() {
        try {
            logger.info("enviar()");
            GnrContadorDocFacadeREST gnrContadorDocFacadeREST = new GnrContadorDocFacadeREST();
            BigDecimal numDocumentoResp = gnrContadorDocFacadeREST.numDocumento(BigDecimal.class, "01", "06", "CIN");
            
            CxcAbonoC cxcAbonoC = new CxcAbonoC();
            CxcAbonoCPK cxcAbonoCPK = new CxcAbonoCPK();
            cxcAbonoCPK.setCodDocumento("CIN");
            cxcAbonoCPK.setCodEmpresa(obtenerEmpresa());
            cxcAbonoCPK.setNumAbono(numDocumentoResp.toBigInteger());
            cxcAbonoC.setCxcAbonoCPK(cxcAbonoCPK);
            cxcAbonoC.setCodDivisa("01");
            cxcAbonoC.setCodMotivo("");
            cxcAbonoC.setCodVendedor(obtenerVendedor());
            cxcAbonoC.setCxcCliente(obtenerCliente());
            cxcAbonoC.setCxcInforme(null);
            cxcAbonoC.setDetalle("Pago Web");
            cxcAbonoC.setEstado("G");
            cxcAbonoC.setFechaAbono(Calendar.getInstance().getTime());
            cxcAbonoC.setFechaEstado(Calendar.getInstance().getTime());
            cxcAbonoC.setNombreUsuario(userManager.getCurrent());
            cxcAbonoC.setTotalCapital(totalCapital);
            cxcAbonoC.setTotalMora(BigInteger.ZERO);
            List<CxcAbonoD> cxcAbonoDList = new ArrayList<>();
            int i = 1;
            for (CxcDocCobrar cxcDocCobrar : cxcDocCobrarList) {
                if (cxcDocCobrar.getSaldoOri() != null && !Objects.equals(cxcDocCobrar.getSaldoOri(), cxcDocCobrar.getSaldoDocumento())) {
                    CxcAbonoD cxcAbonoD = new CxcAbonoD();
                    CxcAbonoDPK cxcAbonoDPK = new CxcAbonoDPK();
                    cxcAbonoDPK.setAuxiliar(i);
                    cxcAbonoDPK.setCodDocumento("CIN");
                    cxcAbonoDPK.setCodEmpresa(cxcDocCobrar.getCxcDocCobrarPK().getCodEmpresa());
                    cxcAbonoDPK.setNumAbono(numDocumentoResp.intValue());
                    cxcAbonoD.setCxcAbonoDPK(cxcAbonoDPK);
                    cxcAbonoD.setCapital(cxcDocCobrar.getCapital());
                    cxcAbonoD.setCodAbono(cxcDocCobrar.getCxcDocCobrarPK().getCodDocumento());
                    cxcAbonoD.setDias(0);
                    cxcAbonoD.setNumDocumento(cxcDocCobrar.getCxcDocCobrarPK().getNumDocumento().intValue());
                    cxcAbonoD.setNumeroCuota(cxcDocCobrar.getCxcDocCobrarPK().getNumeroCuota().intValue());
                    cxcAbonoD.setPorcComision(BigDecimal.ZERO);
                    cxcAbonoD.setValorMora(BigInteger.ZERO);
                    
                    cxcAbonoDList.add(cxcAbonoD);
                    i++;
                }
            }
            cxcAbonoC.setCxcAbonoDList(cxcAbonoDList);
            
            CxcPagoContado cxcPagoContado = new CxcPagoContado();
            CxcPagoContadoPK cxcPagoContadoPK = new CxcPagoContadoPK();
            cxcPagoContadoPK.setCodDocumento("CIN");
            cxcPagoContadoPK.setCodEmpresa(obtenerEmpresa());
            cxcPagoContadoPK.setNumPago(new BigInteger(numDocumentoResp.toString()));
            cxcPagoContado.setCxcPagoContadoPK(cxcPagoContadoPK);
            cxcPagoContado.setCheques(totalCheques);
            cxcPagoContado.setCodTarjeta(null);
            cxcPagoContado.setCodVendedor(obtenerVendedor());
            cxcPagoContado.setCredito(BigInteger.ZERO);
            cxcPagoContado.setCtaCorriente(numeroCuenta);
            cxcPagoContado.setCxcCliente(obtenerCliente());
            cxcPagoContado.setDeposito(deposito);
            cxcPagoContado.setDetalle("Pago Web");
            cxcPagoContado.setEfectivo(efectivo);
            cxcPagoContado.setFechaDocumento(Calendar.getInstance().getTime());
            cxcPagoContado.setNombreUsuario(userManager.getCurrent().getNombreUsuario());
            cxcPagoContado.setOtros(otros);
            cxcPagoContado.setPagoTotal(totalCapital);
            cxcPagoContado.setRetencion(retencion);
            cxcPagoContado.setRetencionIva(retencionIVA);
            cxcPagoContado.setTarjeta(BigInteger.ZERO);
            
            CxcDocCobrarFacadeREST cxcDocCobrarFacadeREST = new CxcDocCobrarFacadeREST();
            Pago pago = new Pago();
            pago.setCxcAbonoC(cxcAbonoC);
            for (CxcCheque cheque : cxcCheques) {
                cheque.getCxcChequePK().setNumDocumento(numDocumentoResp.intValue());
            }
            pago.setCxcChequeList(cxcCheques);
            pago.setCxcDocCobrarList(cxcDocCobrarList);
            pago.setCxcPagoContado(cxcPagoContado);
            cxcDocCobrarFacadeREST.save_JSON(pago);
            
            addMessage("Cobro enviado exitosamente.", "Num. Cobro: " + numDocumentoResp, FacesMessage.SEVERITY_INFO);
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getFlash().setKeepMessages(true);
            return "index?faces-redirect=true";
        } catch (EmptyException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            addMessage("Advertencia", ex.getMessage(), FacesMessage.SEVERITY_INFO);
            return "cobro?faces-redirect=true";
        }
    }
    
    public void calcularCheques() {
        logger.info("calcularCheques()");
        calcularFormaPago();
        RequestContext.getCurrentInstance().update("cobro:accordionPanel:chequesForm:agregarChequeButton");
    }
    
    public void agregarCheque() {
        logger.info("codBanco: " + codBanco);
        logger.info("fechaCheque: " + fechaCheque);
        logger.info("numCheque: " + numCheque);
        logger.info("numCuenta: " + numCuenta);
        logger.info("valorCheque: " + valorCheque);
        
        CxcCheque cheque = new CxcCheque();
        cheque.setCodBanco(codBanco);
        cheque.setCodDeposito(null);
        cheque.setCodDivisa("01");
        CxcChequePK cxcChequePK = new CxcChequePK();
        cxcChequePK.setAuxiliar(cxcCheques.size() + 1);
        cxcChequePK.setCodDocumento("CIN");
        cxcChequePK.setCodEmpresa(obtenerEmpresa());
//        cxcChequePK.setNumDocumento(cxcCheques.size() + 1);
        cheque.setCxcChequePK(cxcChequePK);
        try {
            cheque.setCxcCliente(obtenerCliente());
        } catch (EmptyException ex) {
            addMessage("Advertencia", ex.getMessage(), FacesMessage.SEVERITY_INFO);
        }
        cheque.setDetalle(null);
        cheque.setEstado("G");
        
        cheque.setFechaCheque(fechaCheque);
        cheque.setFechaEstado(Calendar.getInstance().getTime());
        cheque.setFechaRecepcion(Calendar.getInstance().getTime());
        cheque.setNumCheque(numCheque);
        cheque.setNumCuenta(numCuenta);
        cheque.setNumDeposito(0L);
        cheque.setReferencia(null);
        cheque.setValorCheque(valorCheque);
        
        cxcCheques.add(cheque);
        totalCheques += valorCheque;
        
        limpiar();
        
        RequestContext.getCurrentInstance().update("cobro:accordionPanel:formaPagoForm:diferencia");
        RequestContext.getCurrentInstance().update("cobro:accordionPanel:formaPagoForm:cxcChequeDataTable");
        RequestContext.getCurrentInstance().update("cobro:accordionPanel:formaPagoForm:enviarButton");
    }
    
    private void loadCxcDocCobrarList() {
        client = cliente.getCliente();
        BigInteger codCliente = cliente.getCliente().getCodCliente();
        CxcDocCobrarFacadeREST cxcDocCobrarFacadeREST = new CxcDocCobrarFacadeREST();
        String cxcDocCobrarListString = cxcDocCobrarFacadeREST.findByCodCliente(String.class, codCliente.toString());
        cxcDocCobrarList = gson.fromJson(cxcDocCobrarListString, new TypeToken<List<CxcDocCobrar>>() {
        }.getType());
        calcularTotales();
    }
    
    private void calcularTotales() {
        totalSaldo = 0.0;
        totalCapital = 0.0;
        for (CxcDocCobrar cxcDocCobrar : cxcDocCobrarList) {
            totalSaldo += cxcDocCobrar.getSaldoDocumento();
            
            if (cxcDocCobrar.getCapital() != null) {
                totalCapital += cxcDocCobrar.getCapital();
            }
        }
        
        totalSaldo = Round.round(totalSaldo, 2);
        totalCapital = Round.round(totalCapital, 2);
    }
    
    private void loadBanCtaCtes() {
        BanCtaCteFacadeREST banCtaCteFacadeREST = new BanCtaCteFacadeREST();
        String banCtaCtesString = banCtaCteFacadeREST.findAll_JSON(String.class);
        banCtaCtes = gson.fromJson(banCtaCtesString, new TypeToken<List<BanCtaCte>>() {
        }.getType());
    }
    
    private String obtenerEmpresa() {
        return userManager.getGnrEmpresa().getCodEmpresa();
    }
    
    private CxcCliente obtenerCliente() throws EmptyException {
        if (cliente.getCliente() == null) {
            throw new EmptyException("Por favor seleccione el cliente.");
        }
        
        return new CxcCliente(obtenerEmpresa(), cliente.getCliente().getCodCliente());
    }
    
    private BigInteger obtenerVendedor() throws EmptyException {
        FacParametros facParametros = obtenerFacParametros();
        
        if (facParametros == null) {
            throw new EmptyException("Vendedor no asociado a facturación.");
        }
        
        BigInteger defCodVendedor = new BigInteger(facParametros.getDefCodVendedor().toString());
        logger.log(Level.INFO, "defCodVendedor: {0}", defCodVendedor);
        return defCodVendedor;
    }
    
    private FacParametros obtenerFacParametros() {
        String facParametrosString = facParametrosFacadeREST.findAll_JSON(String.class);
        List<FacParametros> listaFacParametros = gson.fromJson(facParametrosString, new TypeToken<java.util.List<FacParametros>>() {
        }.getType());
        
        String nombreUsuario = userManager.getCurrent().getNombreUsuario();
        String codEmpresa = obtenerEmpresa();
        logger.log(Level.INFO, "nombreUsuario: {0}", nombreUsuario);
        logger.log(Level.INFO, "codEmpresa: {0}", codEmpresa);
        
        for (FacParametros facParametros : listaFacParametros) {
            String facNombreUsuario = facParametros.getFacParametrosPK().getNombreUsuario();
            logger.log(Level.INFO, "facNombreUsuario: {0}", facNombreUsuario);
            String facCodEmpresa = facParametros.getFacParametrosPK().getCodEmpresa();
            logger.log(Level.INFO, "facCodEmpresa: {0}", facCodEmpresa);
            
            if (facNombreUsuario.toLowerCase().
                    equals(nombreUsuario.toLowerCase())
                    && facCodEmpresa.
                    equals(codEmpresa)) {
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
    
    private void limpiar() {
        codBanco = null;
        fechaCheque = null;
        numCheque = null;
        numCuenta = null;
        valorCheque = 0.0;
    }
}
