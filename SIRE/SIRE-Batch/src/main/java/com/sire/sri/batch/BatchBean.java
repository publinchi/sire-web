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

    @Schedule(hour = "*", minute = "*/15", info = "Every 15 minutes timer", timezone = "UTC", persistent = false)
    private void sriRecepcionFacturaJob()
            throws InterruptedException, NamingException, IOException {
        System.out.println("-> sriRecepcionFacturaJob");
        String comprobanteSQL = "SELECT COD_EMPRESA, RUC_EMPRESA, RAZON_SOCIAL_EMPRESA, "
                + "NOMBRE_COMERCIAL, COD_DOCUMENTO, NUM_FACTURA_INTERNO, ESTABLECIMIENTO, "
                + "PUNTO_EMISION, SECUENCIAL, DIRECCION_MATRIZ, DIRECCION_ESTABLECIMIENTO, "
                + "CONTRIBUYENTE_ESPECIAL, LLEVA_CONTABILIDAD, RAZON_SOCIAL_COMPRADOR, "
                + "FECHA_FACTURA, TIPO_IDENTIFICACION_COMPRADOR, IDENTIFICACION_COMPRADOR, "
                + "DIRECCION_COMPRADOR, TELEFONO_COMPRADOR, EMAIL_COMPRADOR, "
                + "TOTAL_SIN_IMPUESTOS, TOTAL_DESCUENTOS, PROPINA, IMPORTE_TOTAL, "
                + "CLAVE_ACCESO, CODIGO_IMPUESTO, CODIGO_PORCENTAJE, BASE_IMPONIBLE, "
                + "VALOR, MONEDA, OBSERVACION FROM V_FACTURA_ELECTRONICA_C WHERE "
                + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_FACTURA";
        String home = System.getProperty("sire.home");
        if (home == null) {
            home = System.getProperty("user.home");
        }
        Properties runtimeParameters = new Properties();
        runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));
        runtimeParameters.setProperty("comprobanteSQL", comprobanteSQL);
        runtimeParameters.setProperty("tipoComprobante", "01");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = Long.valueOf(jobOperator.start("SriRecepcionJob", runtimeParameters));
        JobExecution jobExecution = jobOperator.getJobExecution(executionId.longValue());

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId.longValue());
        for (StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName().equals("f1_chunk1")) {
                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                System.out.println("READ_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.READ_COUNT)).longValue());
                System.out.println("WRITE_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.WRITE_COUNT)).longValue());
                System.out.println("COMMIT_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.COMMIT_COUNT)).longValue());
            }
        }
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriRecepcionRetencionJob()
            throws InterruptedException, NamingException, IOException {
        System.out.println("-> sriRecepcionRetencionJob");
        String comprobanteSQL = "SELECT COD_EMPRESA, RUC_EMPRESA, RAZON_SOCIAL_EMPRESA, "
                + "NOMBRE_COMERCIAL, COD_DOCUMENTO, NUM_RETENCION_INTERNO, CLAVE_ACCESO, "
                + "ESTABLECIMIENTO, PUNTO_EMISION, SECUENCIAL, DIRECCION_MATRIZ, "
                + "DIRECCION_ESTABLECIMIENTO, CONTRIBUYENTE_ESPECIAL, LLEVA_CONTABILIDAD, "
                + "RAZON_SOCIAL_SUJETO_RETENIDO, DIRECCION_RETENIDO, TELEFONO_RETENIDO, "
                + "EMAIL_RETENIDO, FECHA_RETENCION, TIPO_IDENT_SUJETO_RETENIDO, "
                + "IDENTIFICACION_SUJETO_RETENIDO, PERIODO_FISCAL, ESTADO_SRI, "
                + "CLAVE_ACCESO_LOTE FROM V_RETENCION_ELECTRONICA_C WHERE "
                + "ESTADO_SRI='GRABADA' AND ROWNUM <= 20 ORDER BY FECHA_RETENCION";
        String home = System.getProperty("sire.home");
        if (home == null) {
            home = System.getProperty("user.home");
        }
        Properties runtimeParameters = new Properties();
        runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));
        runtimeParameters.setProperty("comprobanteSQL", comprobanteSQL);
        runtimeParameters.setProperty("tipoComprobante", "07");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = Long.valueOf(jobOperator.start("SriRecepcionJob", runtimeParameters));
        JobExecution jobExecution = jobOperator.getJobExecution(executionId.longValue());

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId.longValue());
        for (StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName().equals("f1_chunk1")) {
                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                System.out.println("READ_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.READ_COUNT)).longValue());
                System.out.println("WRITE_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.WRITE_COUNT)).longValue());
                System.out.println("COMMIT_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.COMMIT_COUNT)).longValue());
            }
        }
    }

    @Schedule(hour = "*", minute = "*/15", info = "Every 15 minutes timer", timezone = "UTC", persistent = false)
    private void sriAutorizacionFacturaJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        System.out.println("-> sriAutorizacionFacturaJob");
        String home = System.getProperty("sire.home");
        if (home == null) {
            home = System.getProperty("user.home");
        }
        Properties runtimeParameters = new Properties();
        runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));
        runtimeParameters.setProperty("tipoComprobante", "01");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = Long.valueOf(jobOperator.start("SriAutorizacionJob", runtimeParameters));
        JobExecution jobExecution = jobOperator.getJobExecution(executionId.longValue());

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId.longValue());
        for (StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName().equals("f1_chunk1")) {
                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                System.out.println("READ_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.READ_COUNT)).longValue());
                System.out.println("WRITE_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.WRITE_COUNT)).longValue());
                System.out.println("COMMIT_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.COMMIT_COUNT)).longValue());
            }
        }
    }

    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    private void sriAutorizacionRetencionJob()
            throws InterruptedException, NamingException, FileNotFoundException, IOException {
        System.out.println("-> sriAutorizacionRetencionJob");
        String home = System.getProperty("sire.home");
        if (home == null) {
            home = System.getProperty("user.home");
        }
        Properties runtimeParameters = new Properties();
        runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));
        runtimeParameters.setProperty("urlReporte", runtimeParameters.getProperty("pathReports") + "comprobanteRetencion.jasper");
        runtimeParameters.setProperty("tipoComprobante", "07");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = Long.valueOf(jobOperator.start("SriAutorizacionJob", runtimeParameters));
        JobExecution jobExecution = jobOperator.getJobExecution(executionId.longValue());

        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);

        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId.longValue());
        for (StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName().equals("f1_chunk1")) {
                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                System.out.println("READ_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.READ_COUNT)).longValue());
                System.out.println("WRITE_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.WRITE_COUNT)).longValue());
                System.out.println("COMMIT_COUNT: " + ((Long) metricsMap.get(Metric.MetricType.COMMIT_COUNT)).longValue());
            }
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
