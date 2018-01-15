/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.batch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.NamingException;
import org.javaee7.util.BatchTestHelper;

/**
 *
 * @author pestupinan
 */
@Singleton
@Startup
public class BatchBean {

    private Logger log = Logger.getLogger(BatchBean.class.getName());

    /* RECEPCIONES */
    @Schedule(hour = "*", minute = "*/15", info = "Every 15 minutes timer", timezone = "UTC", persistent = false)
    private void sriRecepcionFacturaJob()
            throws InterruptedException, NamingException, IOException {
        log.info("-> sriRecepcionFacturaJob");

        executeJob("SriRecepcionJob", "01");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriRecepcionNotaCreditoJob()
            throws InterruptedException, NamingException, IOException {
        log.info("-> sriRecepcionNotaCreditoJob");

        executeJob("SriRecepcionJob", "04");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriRecepcionNotaDebitoJob()
            throws InterruptedException, NamingException, IOException {
        log.info("-> sriRecepcionNotaDebitoJob");

        executeJob("SriRecepcionJob", "05");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriRecepcionGuiaRemisionJob()
            throws InterruptedException, NamingException, IOException {
        log.info("-> sriRecepcionGuiaRemisionJob");

        executeJob("SriRecepcionJob", "06");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriRecepcionRetencionJob()
            throws InterruptedException, NamingException, IOException {
        log.info("-> sriRecepcionRetencionJob");

        executeJob("SriRecepcionJob", "07");
    }

    /* AUTORIZACIONES*/
    @Schedule(hour = "*", minute = "*/15", info = "Every 15 minutes timer", timezone = "UTC", persistent = false)
    private void sriAutorizacionFacturaJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("-> sriAutorizacionFacturaJob");

        executeJob("SriAutorizacionJob", "01", "factura.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriAutorizacionNotaCreditoJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("-> sriAutorizacionNotaCreditoJob");

        executeJob("SriAutorizacionJob", "04", "notaCreditoFinal.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriAutorizacionNotaDebitoJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("-> sriAutorizacionNotaDebitoJob");

        executeJob("SriAutorizacionJob", "05", "notaDebitoFinal.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriAutorizacionGuiaRemisionJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("-> sriAutorizacionGuiaRemisionJob");

        executeJob("SriAutorizacionJob", "06", "guiaRemisionFinal.jasper");
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriAutorizacionRetencionJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        log.info("-> sriAutorizacionRetencionJob");

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
        Long executionId = Long.valueOf(jobOperator.start(jobName, runtimeParameters));
        JobExecution jobExecution = jobOperator.getJobExecution(executionId.longValue());

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId.longValue());
        for (StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName().equals("f1_chunk1")) {
                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                log.info("READ_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.READ_COUNT)).longValue());
                log.info("WRITE_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.WRITE_COUNT)).longValue());
                log.info("COMMIT_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.COMMIT_COUNT)).longValue());
            }
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
