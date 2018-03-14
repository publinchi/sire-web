/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.batch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author pestupinan
 */
@Singleton
@Startup
public class BatchBean {

    @Resource
    private TimerService timerService;

    private static final Logger log = Logger.getLogger(BatchBean.class.getName());
    private String home;

    @PostConstruct
    public void init() {
        try {
            home = System.getProperty("sire.home");
            if (home == null) {
                home = System.getProperty("user.home");
            }

            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));

            String[] jobRecepcionNames = runtimeParameters.getProperty("jobRecepcionNames").split(",");

            for (String jobName : jobRecepcionNames) {
                createCalendarTimer(jobName);
            }

            String[] jobAutorizacionNames = runtimeParameters.getProperty("jobAutorizacionNames").split(",");

            for (String jobName : jobAutorizacionNames) {
                createCalendarTimer(jobName);
            }

            Collection<Timer> timers = timerService.getTimers();
            log.info("************** TIMERS INFO **************");
            for (Timer timer : timers) {
                log.log(Level.INFO, "Name: {0}", timer.getInfo() + " -> " + timer.getSchedule().getMinute());
            }
            log.log(Level.INFO, "Total timers: {0}", timers.size());
        } catch (IOException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Timeout
    public void timeout(Timer timer) {
        try {
            InitialContext ic = new InitialContext();
            BatchBean batchBean = (BatchBean) ic.lookup("java:global/SIRE-Batch/BatchBean!com.sire.sri.batch.BatchBean");
            java.lang.reflect.Method method = this.getClass().getDeclaredMethod((String) timer.getInfo());
            method.invoke(batchBean);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NamingException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Timer createCalendarTimer(String jobName) {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));
            String minute = runtimeParameters.getProperty(jobName + ".minute");
            final TimerConfig timerConfig = new TimerConfig(jobName, false);
            return timerService.createCalendarTimer(new ScheduleExpression().hour("*").minute(minute).timezone("UTC"), timerConfig);
        } catch (IOException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    @Schedule(hour = "*", minute = "*/1", info = "reconfigJob", timezone = "UTC", persistent = false)
    public void reconfigJob() {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));

            Collection<Timer> timers = timerService.getTimers();
            log.info("************** TIMERS INFO **************");
            for (Timer timer : timers) {
                String jobName = (String) timer.getInfo();
                String minute = runtimeParameters.getProperty(jobName + ".minute");
                if (minute != null) {
                    minute = minute.trim();
                }
                log.log(Level.INFO, "Timer Name: {0}", jobName + " -> Disk = " + minute
                        + " -> Mem = " + timer.getSchedule().getMinute());
                if (minute != null && !minute.equals(timer.getSchedule().getMinute())) {
                    log.log(Level.INFO, "Diferentes");
                    log.log(Level.INFO, "Cancelling timer: {0}", timer.getInfo());
                    timer.cancel();
                    log.log(Level.INFO, "Timer {0} cancelled.", timer.getInfo());
                    log.log(Level.INFO, "Creating timer {0} -> Every {1} minutes.", new Object[]{jobName, minute});
                    Timer t = createCalendarTimer(jobName);
                    if(t != null)
                        log.log(Level.INFO, "New timer {0} created -> Every {1} minutes.", new Object[]{t.getInfo(), t.getSchedule().getMinute()});
                }
            }
            log.log(Level.INFO, "Total timers: {0}", timers.size());
        } catch (IOException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Schedule(hour = "*", minute = "*/5", info = "Every 5 minutes timer", timezone = "UTC", persistent = false)
    public void sriRecepcionFacturaJob() {
        log.info("* sriRecepcionFacturaJob -> 01");

        executeJob("SriRecepcionJob", "01");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriRecepcionNotaCreditoJob() {
        log.info("* sriRecepcionNotaCreditoJob -> 04");

        executeJob("SriRecepcionJob", "04");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriRecepcionNotaDebitoJob() {
        log.info("* sriRecepcionNotaDebitoJob -> 05");

        executeJob("SriRecepcionJob", "05");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriRecepcionGuiaRemisionJob() {
        log.info("* sriRecepcionGuiaRemisionJob -> 06");

        executeJob("SriRecepcionJob", "06");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriRecepcionRetencionJob() {
        log.info("* sriRecepcionRetencionJob -> 07");

        executeJob("SriRecepcionJob", "07");
    }

//    @Schedule(hour = "*", minute = "*/5", info = "Every 5 minutes timer", timezone = "UTC", persistent = false)
    public void sriAutorizacionFacturaJob() {
        log.info("* sriAutorizacionFacturaJob -> 01");

        executeJob("SriAutorizacionJob", "01", "factura.jasper");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriAutorizacionNotaCreditoJob() {
        log.info("* sriAutorizacionNotaCreditoJob -> 04");

        executeJob("SriAutorizacionJob", "04", "notaCreditoFinal.jasper");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriAutorizacionNotaDebitoJob() {
        log.info("* sriAutorizacionNotaDebitoJob -> 05");

        executeJob("SriAutorizacionJob", "05", "notaDebitoFinal.jasper");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriAutorizacionGuiaRemisionJob() {
        log.info("* sriAutorizacionGuiaRemisionJob -> 06");

        executeJob("SriAutorizacionJob", "06", "guiaRemisionFinal.jasper");
    }

//    @Schedule(hour = "*", minute = "*/3", info = "Every 3 minutes timer", timezone = "UTC", persistent = false)
    public void sriAutorizacionRetencionJob() {
        log.info("* sriAutorizacionRetencionJob -> 07");

        executeJob("SriAutorizacionJob", "07", "comprobanteRetencion.jasper");
    }

    private void executeJob(String jobName, String tipoComprobante) {
        //Se envía null porque la recepcion no genera reporte
        executeJob(jobName, tipoComprobante, null);
    }

    private void executeJob(String jobName, String tipoComprobante, String reportName) {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(home + "/comprobantes.properties"));
            runtimeParameters.setProperty("urlReporte", runtimeParameters.getProperty("pathReports") + reportName);
            runtimeParameters.setProperty("tipoComprobante", tipoComprobante);

            JobOperator jobOperator = BatchRuntime.getJobOperator();
            Long executionId = jobOperator.start(jobName, runtimeParameters);
            jobOperator.getJobExecution(executionId);
        } catch (IOException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
