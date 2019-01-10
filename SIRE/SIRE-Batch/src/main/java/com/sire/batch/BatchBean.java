/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.batch;

import com.sire.batch.constant.Constant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.*;
import javax.annotation.*;
import javax.batch.operations.JobOperator;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.*;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 * @author pestupinan
 */
@Singleton
@Startup
@Path("timer")
public class BatchBean {

    @Resource
    private TimerService timerService;
    @Resource(lookup="java:module/ModuleName")
    private String moduleName;
    @Resource(lookup="java:app/AppName")
    private String applicationName;

    private static final Logger log = LogManager.getLogger(BatchBean.class);
    private String home;
    private static StringBuffer comprobantePropertiesPath;

    @PostConstruct
    public void init() {
        _init();
    }

    private void _init(){
        home = System.getProperty("sire.home");
        if (home == null) {
            log.error("SIRE HOME NOT FOUND.");
            return;
        }

        log.info("SIRE HOME --> " + home);

        comprobantePropertiesPath = new StringBuffer();
        comprobantePropertiesPath.append(home);
        comprobantePropertiesPath.append(File.separator);
        comprobantePropertiesPath.append(Constant.CONFIGURATION_PROPERTIES);

        log.log(Level.INFO, "applicationName -> {}", applicationName);
        log.log(Level.INFO, "moduleName -> {}", moduleName);
        log.info("Configuration Properties --> " + comprobantePropertiesPath);

        loadTimers();
    }

    public void loadTimers() {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));

            String timerRecepcionNames = runtimeParameters.getProperty(Constant.TIMER_RECEPCION_NAMES);
            String timerAutorizacionNames = runtimeParameters.getProperty(Constant.TIMER_AUTORIZACION_NAMES);
            String timerNames = runtimeParameters.getProperty(Constant.TIMER_NAMES);

            createCalendar(timerRecepcionNames);
            createCalendar(timerAutorizacionNames);
            createCalendar(timerNames);

            Collection<Timer> timers = timerService.getTimers();
            log.info("************** TIMERS INFO **************");
            for (Timer timer : timers) {
                log.log(Level.INFO, "Name: {}", timer.getInfo() + " -> h: " + timer.getSchedule().getHour()
                        + " m: " + timer.getSchedule().getMinute());
            }
            log.log(Level.INFO, "Total timers: {}", timers.size());
        } catch (IOException ex) {
            log.log(Level.ERROR, ex);
        }
    }

    private void createCalendar(String timerNames) {
        if(timerNames!=null){
            String[] timerNamesArray = timerNames.split(",");
            for (String timerName : timerNamesArray) {
                if(!timerName.isEmpty())
                    createCalendarTimer(timerName);
            }
        }
    }

    @Timeout
    public void timeout(Timer timer) {
        try {
            Map map = (Map) timer.getInfo();
            log.log(Level.INFO, "timeout: jobName --> {}, tipoComprobante --> {}, reportName --> {}"
                    , map.get(Constant.JOB_NAME), (String) map.get(Constant.TIPO_COMPROBANTE)
                    , map.get(Constant.REPORT_NAME));
            
            executeJob((String)map.get(Constant.JOB_NAME), (String)map.get(Constant.TIPO_COMPROBANTE), (String)map.get(Constant.REPORT_NAME));
        } catch (SecurityException | IllegalArgumentException ex) {
            log.log(Level.ERROR, ex);
        }
    }

    private Timer createCalendarTimer(String timerName) {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));
            boolean persistent = Boolean.parseBoolean(runtimeParameters.getProperty(timerName + ".persistent"));
            String minute = runtimeParameters.getProperty(timerName + ".minute");
            String hour = runtimeParameters.getProperty(timerName + ".hour");
            String tipoComprobante = runtimeParameters.getProperty(timerName + "." + Constant.TIPO_COMPROBANTE);
            String jobName = runtimeParameters.getProperty(timerName + "." + Constant.JOB_NAME);
            String reportName = runtimeParameters.getProperty(timerName + "." + Constant.REPORT_NAME);
            final TimerConfig timerConfig = new TimerConfig(timerName, persistent);
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put(Constant.TIMER_NAME, timerName);
            hashMap.put(Constant.JOB_NAME, jobName);
            hashMap.put(Constant.TIPO_COMPROBANTE, tipoComprobante);
            hashMap.put(Constant.REPORT_NAME, reportName);
            timerConfig.setInfo(hashMap);
            return timerService.createCalendarTimer(new ScheduleExpression().hour(hour).minute(minute).timezone("UTC"), timerConfig);
        } catch (IOException ex) {
            log.log(Level.ERROR, ex);
        }
        return null;
    }

    public void reconfigJob() {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));

            Collection<Timer> timers = timerService.getTimers();

            boolean finish = createNewTimersWhenNoTimers(timers, runtimeParameters);
            if(finish){
                log.log(Level.INFO, "Total timers: {}", timers.size());
                return;
            }

            log.info("***************** TIMERS INFO *****************");

            for (Timer timer : timers) {

                if(!cancelTimer(timer, runtimeParameters))
                    updateTimer(timer, runtimeParameters);

            }
            timers = timerService.getTimers();
            log.log(Level.INFO, "Total timers: {}", timers.size());
        } catch (IOException ex) {
            log.log(Level.ERROR, ex);
        }
    }

    private boolean createNewTimersWhenNoTimers(Collection<Timer> timers, Properties runtimeParameters) {
        String timerRecepcionNames = runtimeParameters.getProperty(Constant.TIMER_RECEPCION_NAMES);
        String timerAutorizacionNames = runtimeParameters.getProperty(Constant.TIMER_AUTORIZACION_NAMES);
        String timerNames = runtimeParameters.getProperty(Constant.TIMER_NAMES);

        StringBuilder totalTimerNames = new StringBuilder();

        if(timerRecepcionNames != null && !timerRecepcionNames.isEmpty())
            totalTimerNames.append(timerRecepcionNames);
        if(timerAutorizacionNames !=null && !timerAutorizacionNames.isEmpty()) {
            if (timerRecepcionNames != null && !timerRecepcionNames.isEmpty())
                totalTimerNames.append(",");
            totalTimerNames.append(timerAutorizacionNames);
        }
        if(!timerNames.isEmpty()) {
            if (timerAutorizacionNames !=null && !timerAutorizacionNames.isEmpty())
                totalTimerNames.append(",");
            totalTimerNames.append(timerNames);
        }

        log.info("totalTimerNames -> " + totalTimerNames);

        // Si no hay timers en memoria y se agregan nuevos timers en el archivo properties,
        // se crean todos los timers del properties.
        if(timers.isEmpty() && !totalTimerNames.toString().trim().isEmpty()
                && totalTimerNames.toString().split(",").length > 0){
            log.info("All timers are new, creating them ...");
            createCalendar(totalTimerNames.toString());
            log.info("Timers created.");
            return true;
        } else if(timers.isEmpty() && totalTimerNames.toString().trim().isEmpty()){
            log.info("No timers found.");
            return true;
        }
        return false;
    }

    private void updateTimer(Timer timer, Properties runtimeParameters) {
        String timerName = ((Map) timer.getInfo()).get(Constant.TIMER_NAME).toString();

        String hour = runtimeParameters.getProperty(timerName + ".hour");
        if (hour != null) {
            hour = hour.trim();
        }
        log.log(Level.INFO, "Timer Name: {}", timerName + " -> Disk Hour = " + hour
                + " -> Mem Hour = " + timer.getSchedule().getHour());

        String minute = runtimeParameters.getProperty(timerName + ".minute");
        if (minute != null) {
            minute = minute.trim();
        }
        log.log(Level.INFO, "Timer Name: {}", timerName + " -> Disk Minute = " + minute
                + " -> Mem Minute = " + timer.getSchedule().getMinute());

        if ((hour != null && !hour.equals(timer.getSchedule().getHour())) || (minute != null && !minute.equals(timer.getSchedule().getMinute()))) {
            log.log(Level.INFO, "Diferentes");
            log.log(Level.INFO, "Cancelling timer: {}", timer.getInfo());
            timer.cancel();
            log.log(Level.INFO, "Creating timer {} -> Every {} hours - Every {} minutes.",
                    timerName, hour, minute);
            Timer t = createCalendarTimer(timerName);
            if (t != null)
                log.log(Level.INFO, "New timer {} created -> Every {} hours - Every {} minutes.",
                        t.getInfo(), t.getSchedule().getHour(), t.getSchedule().getHour());
        }
    }

    private boolean newTimers() {
        boolean newTimers = false;


        return newTimers;
    }

    private boolean cancelTimer(Timer timer, Properties runtimeParameters) {
        String timerRecepcionNames = runtimeParameters.getProperty(Constant.TIMER_RECEPCION_NAMES);
        String timerAutorizacionNames = runtimeParameters.getProperty(Constant.TIMER_AUTORIZACION_NAMES);
        String timerNames = runtimeParameters.getProperty(Constant.TIMER_NAMES);

        StringBuilder totalTimerNames = new StringBuilder();

        if(timerRecepcionNames != null && !timerRecepcionNames.isEmpty())
            totalTimerNames.append(timerRecepcionNames);
        if(timerAutorizacionNames !=null && !timerAutorizacionNames.isEmpty()) {
            if (timerRecepcionNames != null && !timerRecepcionNames.isEmpty())
                totalTimerNames.append(",");
            totalTimerNames.append(timerAutorizacionNames);
        }
        if(!timerNames.isEmpty()) {
            if (timerAutorizacionNames !=null && !timerAutorizacionNames.isEmpty())
                totalTimerNames.append(",");
            totalTimerNames.append(timerNames);
        }

        log.info("totalTimerNames -> " + totalTimerNames);

        String tn = ((Map) timer.getInfo()).get(Constant.TIMER_NAME).toString();
        boolean delete = true;
        String[] timerNamesArray = totalTimerNames.toString().split(",");
        for (String timerName : timerNamesArray) {
            if(!timerName.isEmpty() && tn.equals(timerName)) {
                delete = false;
            }
        }
        if(delete){
            timer.cancel();
        }
        return delete;
    }

    public void executeJob(String jobName, String tipoComprobante, String reportName) {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));
            runtimeParameters.setProperty("urlReporte", runtimeParameters.getProperty("pathReports") + reportName);
            if(tipoComprobante != null)
                runtimeParameters.setProperty(Constant.TIPO_COMPROBANTE, tipoComprobante);

            String batchImplementation = runtimeParameters.getProperty(Constant.BATCH_IMPLEMENTATION) ;

            log.log(Level.INFO, "batchImplementation: {}", batchImplementation);

            if(batchImplementation == null || (batchImplementation != null && batchImplementation.equals(Constant.JEE))){
                JobOperator jobOperator = BatchRuntime.getJobOperator();
                Long executionId = jobOperator.start(jobName, runtimeParameters);
                jobOperator.getJobExecution(executionId);
            } else if(batchImplementation.equals(Constant.SPRING)){
                String[] str = {"context.xml", "META-INF/batch-jobs/*.xml"};
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext(str);
                Job job = (Job) applicationContext.getBean(jobName);
                JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
                try{
                    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

                    Enumeration<String> enums = (Enumeration<String>) runtimeParameters.propertyNames();
                    while (enums.hasMoreElements()) {
                        String key = enums.nextElement();
                        String value = runtimeParameters.getProperty(key);
                        jobParametersBuilder.addString(key, value);
                    }

                    jobParametersBuilder.addLong("time",System.currentTimeMillis());

                    JobParameters jobParameters = jobParametersBuilder.toJobParameters();
                    JobExecution execution = jobLauncher.run(job, jobParameters);
                    log.log(Level.INFO, "Job Execution Status: {}", execution.getStatus());
                }catch(Exception e){
                    log.log(Level.ERROR, e);
                }
            }
        } catch (IOException | JobStartException ex) {
            log.log(Level.ERROR, ex.getCause().getMessage());
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")


    @GET
    @Path("reload")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reloadTimers() {
        reconfigJob();
        return Response.ok().build();
    }
}