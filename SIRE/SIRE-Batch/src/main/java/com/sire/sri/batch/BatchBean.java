/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.sri.batch;

import com.sire.sri.batch.constant.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    @Resource(lookup="java:module/ModuleName")
    private String moduleName;
    @Resource(lookup="java:app/AppName")
    private String applicationName;

    private static final Logger log = Logger.getLogger(BatchBean.class.getName());
    private String home;
    private static StringBuffer comprobantePropertiesPath;

    @PostConstruct
    public void init() {
        try {
            home = System.getProperty("sire.home");
            if (home == null) {
                log.severe("SIRE HOME NOT FOUND.");
                return;
                //home = System.getProperty("user.home");
            }

            log.info("SIRE HOME --> " + home);

            comprobantePropertiesPath = new StringBuffer();
            comprobantePropertiesPath.append(home);
            comprobantePropertiesPath.append(File.separator);
            comprobantePropertiesPath.append(Constant.COMPROBANTES_PROPERTIES);

            log.log(Level.INFO, "applicationName -> {0}", applicationName);
            log.log(Level.INFO, "moduleName -> {0}", moduleName);
            log.info("Comprobantes Properties --> " + comprobantePropertiesPath);

            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));

            String[] timerRecepcionNames = runtimeParameters.getProperty(Constant.TIMER_RECEPCION_NAMES).split(",");

            for (String timerName : timerRecepcionNames) {
                createCalendarTimer(timerName);
            }

            String[] timerAutorizacionNames = runtimeParameters.getProperty(Constant.TIMER_AUTORIZACION_NAMES).split(",");

            for (String timerName : timerAutorizacionNames) {
                createCalendarTimer(timerName);
            }

            Collection<Timer> timers = timerService.getTimers();
            log.info("************** TIMERS INFO **************");
            for (Timer timer : timers) {
                log.log(Level.INFO, "Name: {0}", timer.getInfo() + " -> h: " + timer.getSchedule().getHour()
                        + " m: " + timer.getSchedule().getMinute());
            }
            log.log(Level.INFO, "Total timers: {0}", timers.size());
        } catch (IOException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Timeout
    public void timeout(Timer timer) {
        try {
            Map map = (Map) timer.getInfo();
            log.info("timeout: jobName --> " + map.get(Constant.JOB_NAME) + ", tipoComprobante --> "
                    + map.get(Constant.TIPO_COMPROBANTE) + ", reportName --> " + map.get(Constant.REPORT_NAME));
            InitialContext ic = new InitialContext();
            String jndi;
            if(applicationName.equals(moduleName)){
                jndi = "java:global/" + applicationName + "/BatchBean!com.sire.sri.batch.BatchBean";
            } else {
                jndi = "java:global/" + applicationName + "/" + moduleName + "/BatchBean!com.sire.sri.batch.BatchBean";
            }
            BatchBean batchBean = (BatchBean) ic.lookup(jndi);
            java.lang.reflect.Method method = this.getClass().getDeclaredMethod(Constant.EXECUTE_JOB, String.class,
                    String.class, String.class);
            method.invoke(batchBean,map.get(Constant.JOB_NAME), map.get(Constant.TIPO_COMPROBANTE),
                    map.get(Constant.REPORT_NAME));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NamingException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Timer createCalendarTimer(String timerName) {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));
            String minute = runtimeParameters.getProperty(timerName + ".minute");
            String hour = runtimeParameters.getProperty(timerName + ".hour");
            String tipoComprobante = runtimeParameters.getProperty(timerName + "." + Constant.TIPO_COMPROBANTE);
            String jobName = runtimeParameters.getProperty(timerName + "." + Constant.JOB_NAME);
            String reportName = runtimeParameters.getProperty(timerName + "." + Constant.REPORT_NAME);
            final TimerConfig timerConfig = new TimerConfig(timerName, false);
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put(Constant.JOB_NAME, jobName);
            hashMap.put(Constant.TIPO_COMPROBANTE, tipoComprobante);
            hashMap.put(Constant.REPORT_NAME, reportName);
            timerConfig.setInfo(hashMap);
            return timerService.createCalendarTimer(new ScheduleExpression().hour(hour).minute(minute).timezone("UTC"), timerConfig);
        } catch (IOException ex) {
            Logger.getLogger(BatchBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    @Schedule(hour = "*", minute = "*/1", info = "reconfigJob", timezone = "UTC", persistent = false)
    public void reconfigJob() { //TODO Refactorizar toda la implementación de este método
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));

            Collection<Timer> timers = timerService.getTimers();
            log.info("************** TIMERS INFO **************");
            for (Timer timer : timers) {
                String jobName = (String) timer.getInfo();
                String hour = runtimeParameters.getProperty(jobName + ".hour");
                if (hour != null) {
                    hour = hour.trim();
                }
                log.log(Level.INFO, "Timer Name: {0}", jobName + " -> Disk = " + hour
                        + " -> Mem = " + timer.getSchedule().getMinute());
                if (hour != null && !hour.equals(timer.getSchedule().getHour())) {
                    log.log(Level.INFO, "Diferentes");
                    log.log(Level.INFO, "Cancelling timer: {0}", timer.getInfo());
                    timer.cancel();
                    log.log(Level.INFO, "Timer {0} cancelled.", timer.getInfo());
                    log.log(Level.INFO, "Creating timer {0} -> Every {1} hours.", new Object[]{jobName, hour});
                    Timer t = createCalendarTimer(jobName);
                    if(t != null)
                        log.log(Level.INFO, "New timer {0} created -> Every {1} hours.", new Object[]{t.getInfo(), t.getSchedule().getHour()});
                }
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

    private void executeJob(String jobName, String tipoComprobante, String reportName) {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));
            runtimeParameters.setProperty("urlReporte", runtimeParameters.getProperty("pathReports") + reportName);
            runtimeParameters.setProperty(Constant.TIPO_COMPROBANTE, tipoComprobante);

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
