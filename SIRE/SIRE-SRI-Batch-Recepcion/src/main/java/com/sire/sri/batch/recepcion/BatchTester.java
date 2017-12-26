package com.sire.sri.batch.recepcion;

import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;

public class BatchTester  {

    public static void main(String[] args) {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("SriRecepcionJob", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        System.out.println("BatchStatus : " + jobExecution.getBatchStatus());
    }
}
