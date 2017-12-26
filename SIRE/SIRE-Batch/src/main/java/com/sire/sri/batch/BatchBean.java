/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.batch;

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
    private void sriRecepcionJob()
            throws InterruptedException, NamingException {
        System.out.println("-> sriRecepcionJob");
        Properties runtimeParameters = new Properties();
        runtimeParameters.setProperty("pathSignature", "/opt/payara41/SIRE/keystore.p12");
        runtimeParameters.setProperty("passSignature", "Charlie2011");

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
    private void sriAutorizacionJob()
            throws InterruptedException, NamingException {
        System.out.println("-> sriAutorizacionJob");

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = Long.valueOf(jobOperator.start("SriAutorizacionJob", null));
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
