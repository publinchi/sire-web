package com.sire.sri.batch.recepcion;

import java.util.Properties;
import java.util.logging.Logger;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;

public class BatchTester {

    private static Logger log = Logger.getLogger(BatchTester.class.getName());

    public static void main(String[] args) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("SriRecepcionJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        log.info("BatchStatus : " + jobExecution.getBatchStatus());
    }
}
