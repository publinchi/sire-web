/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.batch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.NamingException;

/**
 *
 * @author pestupinan
 */
@Singleton
@Startup
public class BatchBean {

    private Logger log = Logger.getLogger(BatchBean.class.getName());

    @Schedule(hour = "*", minute = "*/15", info = "Every 15 minutes timer", timezone = "UTC", persistent = false)
    void sriRecepcionFacturaJob()
            throws InterruptedException, NamingException, IOException {
        log.info("* sriRecepcionFacturaJob -> 01");

        executeJob("SriRecepcionJob", "01");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriRecepcionNotaCreditoJob()
            throws InterruptedException, NamingException, IOException {
        log.info("* sriRecepcionNotaCreditoJob -> 04");

        executeJob("SriRecepcionJob", "04");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriRecepcionNotaDebitoJob()
            throws InterruptedException, NamingException, IOException {
        log.info("* sriRecepcionNotaDebitoJob -> 05");

        executeJob("SriRecepcionJob", "05");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriRecepcionGuiaRemisionJob()
            throws InterruptedException, NamingException, IOException {
        log.info("* sriRecepcionGuiaRemisionJob -> 06");

        executeJob("SriRecepcionJob", "06");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriRecepcionRetencionJob()
            throws InterruptedException, NamingException, IOException {
        log.info("* sriRecepcionRetencionJob -> 07");

        executeJob("SriRecepcionJob", "07");
    }

    @Schedule(hour = "*", minute = "*/15", info = "Every 15 minutes timer", timezone = "UTC", persistent = false)
    void sriAutorizacionFacturaJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("* sriAutorizacionFacturaJob -> 01");

        executeJob("SriAutorizacionJob", "01", "factura.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriAutorizacionNotaCreditoJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("* sriAutorizacionNotaCreditoJob -> 04");

        executeJob("SriAutorizacionJob", "04", "notaCreditoFinal.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriAutorizacionNotaDebitoJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("* sriAutorizacionNotaDebitoJob -> 05");

        executeJob("SriAutorizacionJob", "05", "notaDebitoFinal.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriAutorizacionGuiaRemisionJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("* sriAutorizacionGuiaRemisionJob -> 06");

        executeJob("SriAutorizacionJob", "06", "guiaRemisionFinal.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    void sriAutorizacionRetencionJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("* sriAutorizacionRetencionJob -> 07");

        executeJob("SriAutorizacionJob", "07", "comprobanteRetencion.jasper");

    }

    private void executeJob(String jobName, String tipoComprobante) throws IOException, FileNotFoundException, InterruptedException {
        //Se envía null porque la recepcion no genera reporte
        executeJob(jobName, tipoComprobante, null);
    }

    private void executeJob(String jobName, String tipoComprobante, String reportName) throws FileNotFoundException, IOException, InterruptedException {
        String home = System.getProperty("sire.home");
        if (home == null) {
            home = System.getProperty("user.home");
        }
        Properties runtimeParameters = new Properties();
        runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));
        runtimeParameters.setProperty("urlReporte", runtimeParameters.getProperty("pathReports") + reportName);
        runtimeParameters.setProperty("tipoComprobante", tipoComprobante);

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start(jobName, runtimeParameters);
        jobOperator.getJobExecution(executionId);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
