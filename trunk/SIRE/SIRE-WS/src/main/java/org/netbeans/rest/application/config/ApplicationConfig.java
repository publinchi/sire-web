/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Administrator
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.sire.ws.service.CxcClaseClienteFacadeREST.class);
        resources.add(com.sire.ws.service.CxcClienteFacadeREST.class);
        resources.add(com.sire.ws.service.CxcSectorFacadeREST.class);
        resources.add(com.sire.ws.service.CxcZonaFacadeREST.class);
        resources.add(com.sire.ws.service.FacTmpFactCFacadeREST.class);
        resources.add(com.sire.ws.service.FacTmpFactDFacadeREST.class);
        resources.add(com.sire.ws.service.GnrDivisaFacadeREST.class);
        resources.add(com.sire.ws.service.GnrEmpresaFacadeREST.class);
        resources.add(com.sire.ws.service.GnrPersonaFacadeREST.class);
        resources.add(com.sire.ws.service.GnrUsuariosFacadeREST.class);
        resources.add(com.sire.ws.service.InvBodegaArtFacadeREST.class);
        resources.add(com.sire.ws.service.InvBodegaFacadeREST.class);
        resources.add(com.sire.ws.service.InvGrupoProveedorFacadeREST.class);
        resources.add(com.sire.ws.service.InvInventarioFacadeREST.class);
        resources.add(com.sire.ws.service.InvMovimientoCabFacadeREST.class);
        resources.add(com.sire.ws.service.InvMovimientoDtllFacadeREST.class);
        resources.add(com.sire.ws.service.InvProveedorFacadeREST.class);
        resources.add(com.sire.ws.service.InvTransaccionesFacadeREST.class);
        resources.add(com.sire.ws.service.InvUnidadAlternativaFacadeREST.class);
        resources.add(com.sire.ws.service.VClienteFacadeREST.class);
    }
    
}
