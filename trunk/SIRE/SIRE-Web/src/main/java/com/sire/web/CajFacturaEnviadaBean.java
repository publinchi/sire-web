/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sire.entities.CajFacturaEnviada;
import com.sire.entities.CajFacturaEnviadaPK;
import com.sire.entities.PryProyecto;
import com.sire.entities.PrySubproyecto;
import com.sire.errorhandling.ErrorMessage;
import com.sire.rs.client.CajFacturaEnviadaFacadeREST;
import com.sire.rs.client.PryProyectoFacadeREST;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author publio
 */
@ManagedBean(name = "cajFacturaEnviadaBean")
@SessionScoped
@Getter
@Setter
public class CajFacturaEnviadaBean {

    private static final Logger logger = Logger.getLogger(CajFacturaEnviadaBean.class.getName());

    private UploadedFile file;
    private String cliente;
    private CajFacturaEnviada cajFacturaEnviada;
    @ManagedProperty(value = "#{user}")
    private UserManager userManager;
    private List<PryProyecto> proyectos;
    private List<PrySubproyecto> subProyectos;
    private final Gson gson;
    private CajFacturaEnviadaFacadeREST cajFacturaEnviadaFacadeREST;
    private PryProyectoFacadeREST pryProyectoFacadeREST;

    public CajFacturaEnviadaBean() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        pryProyectoFacadeREST = new PryProyectoFacadeREST();
        cajFacturaEnviadaFacadeREST = new CajFacturaEnviadaFacadeREST();
        CajFacturaEnviadaPK cajFacturaEnviadaPK = new CajFacturaEnviadaPK();
        this.cajFacturaEnviada = new CajFacturaEnviada();
        this.cajFacturaEnviada.setCajFacturaEnviadaPK(cajFacturaEnviadaPK);
    }

    public void enviar() {
        logger.log(Level.INFO, "file: {0}", file.getFileName());
        logger.log(Level.INFO, "cajFacturaEnviada: {0}", cajFacturaEnviada);

        cajFacturaEnviada.getCajFacturaEnviadaPK().setCodEmpresa(obtenerEmpresa());
        cajFacturaEnviada.getCajFacturaEnviadaPK().setSecuencial(null);
        cajFacturaEnviada.setFechaEstado(Calendar.getInstance().getTime());
        cajFacturaEnviada.setIdFoto(file.getFileName());
        cajFacturaEnviada.setNombreUsuario(userManager.getCurrent());
        cajFacturaEnviada.getCajFacturaEnviadaPK().setCodSupervisor(Integer.valueOf(userManager.getCurrent().getNombreUsuario()));
        cajFacturaEnviada.setEstado("G");
        Response response = cajFacturaEnviadaFacadeREST.save_JSON(cajFacturaEnviada);
        if (response.getStatus() == 200) {
            savePicture();
            addMessage("Factura enviada exitosamente.", "Num. Factura: " + cajFacturaEnviada.getCajFacturaEnviadaPK().getNumDocumento(), FacesMessage.SEVERITY_INFO);
        } else {
            String developerMessage = response.readEntity(ErrorMessage.class).getDeveloperMessage();
            logger.log(Level.SEVERE, developerMessage);
            addMessage("Advertencia", developerMessage, FacesMessage.SEVERITY_WARN);
        }
        limpiar();
    }

    public void findProyectos() {
        logger.info("findProyectos()");
        String codEmpresa = obtenerEmpresa();
        logger.log(Level.INFO, "codEmpresa: {0}", codEmpresa);
        if ((codEmpresa != null && proyectos == null) || (codEmpresa != null && proyectos.isEmpty())) {
            String proyectosString = pryProyectoFacadeREST.findByCodEmpresa_JSON(String.class, obtenerEmpresa());
            proyectos = gson.fromJson(proyectosString, new TypeToken<java.util.List<PryProyecto>>() {
            }.getType());
        }
    }

    public List<PryProyecto> getProyectos() {
        findProyectos();
        return proyectos;
    }

    public void findSubProyecto() {
        logger.log(Level.INFO, "findSubProyecto()");
        logger.log(Level.INFO, "CodProyecto: {0}", cajFacturaEnviada.getCajFacturaEnviadaPK().getCodProyecto());
        String subProyectosString = pryProyectoFacadeREST.findSubByCodProyectoCodEmpresa_JSON(String.class, String.valueOf(cajFacturaEnviada.getCajFacturaEnviadaPK().getCodProyecto()), obtenerEmpresa());
        subProyectos = gson.fromJson(subProyectosString, new TypeToken<java.util.List<PrySubproyecto>>() {
        }.getType());
    }

    public void calcularTotalDocumento() {
        cajFacturaEnviada.setTotalDocumento(cajFacturaEnviada.getIvaDocumento() + cajFacturaEnviada.getTotalConIva() + cajFacturaEnviada.getTotalSinIva());
    }

    private void savePicture() {
        if (file != null) {
            String currentUsersHomeDir = System.getProperty("user.home");
            String photosFolder = currentUsersHomeDir + File.separator + "photos";

            File targetFile = new File(photosFolder + File.separator + file.getFileName());

            try {
                FileUtils.copyInputStreamToFile(file.getInputstream(), targetFile);
            } catch (IOException ex) {
                logger.severe(ex.getMessage());
            }
        }
    }

    private void limpiar() {
        this.file = null;
        this.cajFacturaEnviada = null;
        CajFacturaEnviadaPK cajFacturaEnviadaPK = new CajFacturaEnviadaPK();
        this.cajFacturaEnviada = new CajFacturaEnviada();
        this.cajFacturaEnviada.setCajFacturaEnviadaPK(cajFacturaEnviadaPK);
        proyectos.clear();
    }

    private void addMessage(String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private String obtenerEmpresa() {
        return userManager.getGnrEmpresa().getCodEmpresa();
    }
}
