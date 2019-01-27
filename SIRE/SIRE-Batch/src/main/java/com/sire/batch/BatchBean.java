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
import java.util.concurrent.*;
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

    private ExecutorService executorService;
    private int nThreads;

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

        log.info("SIRE HOME --> {}", home);

        String sireThreads = System.getProperty("sire.threads");
        if (sireThreads == null) {
            nThreads = 10;
            log.error("SIRE THREADS NOT FOUND. SETTING 10 THREADS BY DEFAULT.");
        } else {
            nThreads = Integer.parseInt(sireThreads);
            log.info("SIRE THREADS --> {}", nThreads);
        }

        executorService = Executors.newFixedThreadPool(nThreads);

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
        reconfigTimers();

        configTimers();

        Collection<Timer> timers = timerService.getTimers();
        log.info("************** TIMERS INFO **************");
        for (Timer timer : timers) {
            log.log(Level.INFO, "Name: {}", timer.getInfo() + " -> h: " + timer.getSchedule().getHour()
                    + " m: " + timer.getSchedule().getMinute());
        }
        log.log(Level.INFO, "Total timers: {}", timers.size());
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
        String jobName;
        try {
            Map map = (Map) timer.getInfo();
            jobName = (String) map.get(Constant.JOB_NAME);
            if(map.get(Constant.TIPO_COMPROBANTE) != null || map.get(Constant.REPORT_NAME) != null)
                log.log(Level.INFO, "Executing job --> {}, tipoComprobante --> {}, reportName --> {}"
                        , jobName, map.get(Constant.TIPO_COMPROBANTE)
                        , map.get(Constant.REPORT_NAME));
            else
                log.log(Level.INFO, "Executing job --> {}", jobName);

            if(executorService != null && executorService instanceof ThreadPoolExecutor) {
                int activeCount = ((ThreadPoolExecutor) executorService).getActiveCount();
                log.info("Active Threads --> {}", activeCount);

                if(activeCount >= nThreads){
                    log.warn("Se ha alcanzado el umbral de {} hilo(s) disponible(s).", activeCount);
                    log.warn("Por favor revise si existen hilos colgados.");
                    return;
                }
            }

            executorService.execute(new Work(map));

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
            String timeout = runtimeParameters.getProperty(timerName + "." + Constant.TIMEOUT);
            final TimerConfig timerConfig = new TimerConfig(timerName, persistent);
            HashMap hashMap = new HashMap<String, String>();
            hashMap.put(Constant.TIMER_NAME, timerName);
            hashMap.put(Constant.JOB_NAME, jobName);
            hashMap.put(Constant.TIPO_COMPROBANTE, tipoComprobante);
            hashMap.put(Constant.REPORT_NAME, reportName);
            hashMap.put(Constant.TIMEOUT, timeout);
            timerConfig.setInfo(hashMap);

            Collection<Timer> timers = timerService.getTimers();

            for (Timer timer : timers) {
                if(timerConfig.getInfo().equals(timer.getInfo()))
                {
                    return timer;
                }
            }

            log.info("************** TIMER CREATING **************");
            log.info("Timer {} Created.", timerName);
            return timerService.createCalendarTimer(new ScheduleExpression().hour(hour).minute(minute).timezone("UTC"), timerConfig);
        } catch (IOException ex) {
            log.log(Level.ERROR, ex);
        }
        return null;
    }

    public void reconfigTimers() {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));

            Collection<Timer> timers = timerService.getTimers();

            boolean finish = createNewTimersWhenNoTimers(timers, runtimeParameters);

            if(finish){
                timers = timerService.getTimers();
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
        String totalTimerNames = groupTimerNames(runtimeParameters);

        // Si no hay timers en memoria y se agregan nuevos timers en el archivo properties,
        // se crean todos los timers del properties.
        if(timers.isEmpty() && !totalTimerNames.trim().isEmpty()
                && totalTimerNames.split(",").length > 0){
            log.info("All timers are new, creating them ...");
            createCalendar(totalTimerNames);
            log.info("Timers created.");
            return true;
        } else if(timers.isEmpty() && totalTimerNames.trim().isEmpty()){
            log.info("No timers found.");
            return true;
        }
        return false;
    }

    private String groupTimerNames(Properties runtimeParameters) {
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
        if(timerNames != null && !timerNames.isEmpty()) {
            if (timerAutorizacionNames !=null && !timerAutorizacionNames.isEmpty())
                totalTimerNames.append(",");
            totalTimerNames.append(timerNames);
        }

        return totalTimerNames.toString();
    }

    private void updateTimer(Timer timer, Properties runtimeParameters) {
        String timerName = ((Map) timer.getInfo()).get(Constant.TIMER_NAME).toString();

        String hour = runtimeParameters.getProperty(timerName + ".hour");
        if (hour != null) {
            hour = hour.trim();
        }

        String minute = runtimeParameters.getProperty(timerName + ".minute");
        if (minute != null) {
            minute = minute.trim();
        }

        if ((hour != null && !hour.equals(timer.getSchedule().getHour())) || (minute != null && !minute.equals(timer.getSchedule().getMinute()))) {
            log.log(Level.INFO, "Timer Name: {}", timerName + " -> Disk Hour = " + hour
                    + " -> Mem Hour = " + timer.getSchedule().getHour());

            log.log(Level.INFO, "Timer Name: {}", timerName + " -> Disk Minute = " + minute
                    + " -> Mem Minute = " + timer.getSchedule().getMinute());

            log.log(Level.INFO, "Diferentes");
            log.log(Level.INFO, "Cancelling timer: {}", timer.getInfo());

            timer.cancel();

            log.log(Level.INFO, "Creating timer {} -> Every {} hours - Every {} minutes.",
                    timerName, hour, minute);

            Timer t = createCalendarTimer(timerName);

            if (t != null)
                log.log(Level.INFO, "New timer {} created -> Every {} hours - Every {} minutes.",
                        t.getInfo(), t.getSchedule().getHour(), t.getSchedule().getMinute());
        }
    }

    private void configTimers() {
        try{
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(comprobantePropertiesPath.toString()));

            String timerRecepcionNames = runtimeParameters.getProperty(Constant.TIMER_RECEPCION_NAMES);
            String timerAutorizacionNames = runtimeParameters.getProperty(Constant.TIMER_AUTORIZACION_NAMES);
            String timerNames = runtimeParameters.getProperty(Constant.TIMER_NAMES);

            createCalendar(timerRecepcionNames);
            createCalendar(timerAutorizacionNames);
            createCalendar(timerNames);
        } catch (IOException ex) {
            log.log(Level.ERROR, ex);
        }
    }

    private boolean cancelTimer(Timer timer, Properties runtimeParameters) {
        String totalTimerNames = groupTimerNames(runtimeParameters);

        String tn = ((Map) timer.getInfo()).get(Constant.TIMER_NAME).toString();
        boolean delete = true;
        String[] timerNamesArray = totalTimerNames.split(",");
        for (String timerName : timerNamesArray) {
            if(!timerName.isEmpty() && tn.equals(timerName)) {
                delete = false;
            }
        }
        if(delete){
            log.info("Timer Canceled -> " + timer.getInfo());
            timer.cancel();
        }
        return delete;
    }

    public String executeJob(String jobName, String tipoComprobante, String reportName) {
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
                String status = jobOperator.getJobExecution(executionId).getBatchStatus().name();
                log.log(Level.INFO, "Finished {} execution, with status {}.", jobName, status);
                return status;

            } else if(batchImplementation.equals(Constant.SPRING)){

                StringBuilder jobXml = new StringBuilder().append("META-INF/batch-jobs/").append(jobName).append(".xml");
                String[] str = {"context.xml",  jobXml.toString()};
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext(str);
                Job job = (Job) applicationContext.getBean(jobName);
                JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");

                try {
                    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

                    Enumeration<String> enums = (Enumeration<String>) runtimeParameters.propertyNames();
                    while (enums.hasMoreElements()) {
                        String key = enums.nextElement();
                        String value = runtimeParameters.getProperty(key);
                        jobParametersBuilder.addString(key, value);
                    }

                    jobParametersBuilder.addLong("time", System.currentTimeMillis());

                    JobParameters jobParameters = jobParametersBuilder.toJobParameters();
                    JobExecution execution = jobLauncher.run(job, jobParameters);
                    String status = execution.getStatus().getBatchStatus().name();
                    log.log(Level.INFO, "Finished {} execution, with status {}.", jobName, status);
                    return status;
                } catch(Exception e){
                    log.log(Level.ERROR, e.getCause().getMessage());
                    return null;
                }

            } else { return null; }
        } catch (IOException | JobStartException ex) {
            log.log(Level.ERROR, ex.getCause().getMessage());
            return null;
        }
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")


    @GET
    @Path("reload")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reloadTimers() {
        loadTimers();
        return Response.ok().build();
    }

    class Work implements Runnable {

        Map map;

        public Work(Map map) {
            this.map = map;
        }

        public void run() {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future future = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    executeJob((String) map.get(Constant.JOB_NAME), (String) map.get(Constant.TIPO_COMPROBANTE),
                            (String) map.get(Constant.REPORT_NAME));
                }
            });
            String timeout = (String) map.get(Constant.TIMEOUT);
            if(timeout != null) {
                try {
                    future.get(Long.parseLong(timeout),TimeUnit.MILLISECONDS);
                } catch (InterruptedException | ExecutionException e) {
                    log.error(e.getMessage());
                } catch (TimeoutException e) {
                    log.error("Se ha excedido el timeout de {} ms para el timer {}.", timeout,
                            map.get(Constant.TIMER_NAME));
                }
            }
            else
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error(e.getMessage());
                }
        }
    }
}