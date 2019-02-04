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
import javax.enterprise.concurrent.ManagedExecutorService;
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
    private Boolean thresholdEnabled;
    private static StringBuffer configurationPropertiesPath;

    private ThreadPoolExecutor threadPoolExecutor;

    private int nThreads, queueCapacity;

    //@Resource(name = "concurrent/__defaultManagedExecutorService")
    //private ManagedExecutorService managedExecutorServiceexecutorService;

    @PostConstruct
    public void init() {
        _init();
    }

    @PreDestroy
    public void finish() {
        if(threadPoolExecutor != null)
            threadPoolExecutor.shutdown();
        cancelTimers();
    }

    private void _init(){
        home = System.getProperty("sire.home");
        if (home == null) {
            log.error("SIRE HOME NOT FOUND.");
            return;
        }

        log.info("SIRE HOME --> {}", home);

        thresholdEnabled = Boolean.parseBoolean(System.getProperty("sire.threshold.enabled"));

        log.info("SIRE THRESHOLD ENABLED --> {}", thresholdEnabled);

        if(thresholdEnabled) {

            String capacity = System.getProperty("sire.capacity");
            if (capacity == null) {
                queueCapacity = Constant.DEFAULT_CAPACITY;
                log.warn("SIRE QUEUE CAPACITY NOT FOUND. SETTING {} CAPACITY BY DEFAULT.", Constant.DEFAULT_CAPACITY);
            } else {
                queueCapacity = Integer.parseInt(capacity);
                log.info("SIRE QUEUE CAPACITY --> {}", queueCapacity);
            }
            String corePoolSize = System.getProperty("sire.corePoolSize");
            if (corePoolSize == null) {
                corePoolSize = Constant.DEFAULT_CORE_POOL_SIZE;
            }
            String keepAliveTime = System.getProperty("sire.keepAliveTime");
            if (keepAliveTime == null) {
                keepAliveTime = Constant.DEFAULT_KEEP_ALIVE_TIME;
            }
            String maximumPoolSize = System.getProperty("sire.maximumPoolSize");
            if (maximumPoolSize == null) {
                nThreads = Constant.DEFAULT_MAXIMUM_POOL_SIZE;
                log.warn("SIRE MAXIMUM POOL SIZE NOT FOUND. SETTING {} THREADS BY DEFAULT.", Constant.DEFAULT_MAXIMUM_POOL_SIZE);
            } else {
                nThreads = Integer.parseInt(maximumPoolSize);
                log.info("SIRE THREADS --> {}", nThreads);
            }

            BlockingQueue q = new ArrayBlockingQueue(queueCapacity);
            threadPoolExecutor = new ThreadPoolExecutor(Integer.valueOf(corePoolSize), nThreads, Integer.valueOf(keepAliveTime), TimeUnit.MILLISECONDS, q);
        }

        configurationPropertiesPath = new StringBuffer();
        configurationPropertiesPath.append(home);
        configurationPropertiesPath.append(File.separator);
        configurationPropertiesPath.append(Constant.CONFIGURATION_PROPERTIES);

        log.log(Level.INFO, "applicationName -> {}", applicationName);
        log.log(Level.INFO, "moduleName -> {}", moduleName);
        log.info("Configuration Properties --> " + configurationPropertiesPath);

        loadTimers();
    }

    private void loadTimers() {
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

    private void cancelTimers() {
        Collection<Timer> timers = timerService.getTimers();
        log.info("Cancelling timers ...");
        log.info("************** TIMERS INFO **************");
        for (Timer timer : timers) {
            log.log(Level.INFO, "Timer Name Cancelled: {} -> h: {} m: {}"
                    , timer.getInfo()
                    , timer.getSchedule().getHour()
                    , timer.getSchedule().getMinute());
            timer.cancel();
        }
        log.info("All timers were cancelled.");
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
        Map map = (Map) timer.getInfo();
        if(thresholdEnabled != null && thresholdEnabled.equals(Boolean.TRUE)){
            executeWork(map); // TODO Hacer funcionar esta mierda
        }
        else {
            executeJob(map);
        }
    }

    private Timer createCalendarTimer(String timerName) {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(configurationPropertiesPath.toString()));
            boolean persistent = Boolean.parseBoolean(runtimeParameters.getProperty(timerName + ".persistent"));

            String second = runtimeParameters.getProperty(timerName + ".second");
            String minute = runtimeParameters.getProperty(timerName + ".minute");
            String hour = runtimeParameters.getProperty(timerName + ".hour");
            String dayOfMonth = runtimeParameters.getProperty(timerName + ".dayOfMonth");
            String month = runtimeParameters.getProperty(timerName + ".month");
            String dayOfWeek = runtimeParameters.getProperty(timerName + ".dayOfWeek");
            String year = runtimeParameters.getProperty(timerName + ".year");
            String timezone = runtimeParameters.getProperty(timerName + ".timezone");

            final TimerConfig timerConfig = new TimerConfig(timerName, persistent);

            HashMap hashMap = new HashMap<String, String>();

            for (String propertyName : runtimeParameters.stringPropertyNames()) {
                if(propertyName.startsWith(timerName+"."))
                    hashMap.put(propertyName.replace(timerName+".",""), runtimeParameters.getProperty(propertyName));
            }

            timerConfig.setInfo(hashMap);

            Collection<Timer> timers = timerService.getTimers();

            for (Timer timer : timers) {
                if(timerConfig.getInfo().equals(timer.getInfo()))
                {
                    return timer;
                }
            }

            ScheduleExpression scheduleExpression = new ScheduleExpression();
            if(second     != null && !second.trim().isEmpty()    ) scheduleExpression.second(second);
            if(minute     != null && !minute.trim().isEmpty()    ) scheduleExpression.minute(minute);
            if(hour       != null && !hour.trim().isEmpty()      ) scheduleExpression.hour(hour);
            if(dayOfMonth != null && !dayOfMonth.trim().isEmpty()) scheduleExpression.dayOfMonth(dayOfMonth);
            if(month      != null && !month.trim().isEmpty()     ) scheduleExpression.month(month);
            if(dayOfWeek  != null && !dayOfWeek.trim().isEmpty() ) scheduleExpression.dayOfWeek(dayOfWeek);
            if(year       != null && !year.trim().isEmpty()      ) scheduleExpression.year(year);
            if(timezone   != null && !timezone.trim().isEmpty()  ) scheduleExpression.timezone(timezone); else scheduleExpression.timezone("UTC");

            Timer timer = timerService.createCalendarTimer(scheduleExpression, timerConfig);

            log.info("************** TIMER CREATING **************");
            log.info("Timer {} Created.", timerName);

            return timer;
        } catch (IOException ex) {
            log.log(Level.ERROR, ex);
        }
        return null;
    }

    private void reconfigTimers() {
        try {
            Properties runtimeParameters = new Properties();
            runtimeParameters.load(new FileInputStream(configurationPropertiesPath.toString()));

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
            runtimeParameters.load(new FileInputStream(configurationPropertiesPath.toString()));

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

    private void executeWork(Map map) {
        if(threadPoolExecutor != null && threadPoolExecutor instanceof ThreadPoolExecutor) {
            int activeCount = threadPoolExecutor.getActiveCount();
            int queueSize = threadPoolExecutor.getQueue().size();
            log.info("Active Threads --> {}, Queue Size --> {}", activeCount, queueSize);

            if(activeCount >= nThreads &&  queueSize >= queueCapacity){
                log.warn("Se ha alcanzado el umbral de {} hilo(s) disponible(s).", activeCount);
                log.warn("Por favor revise si existen hilos colgados.");
                return;
            }
        }

        threadPoolExecutor.execute(new Work(map));
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private void executeJob(Map map){
        try {
            final String timeout = (String) map.get(Constant.TIMEOUT);
            final String jobName = (String) map.get(Constant.JOB_NAME);
            String tipoComprobante = (String) map.get(Constant.TIPO_COMPROBANTE);
            String reportName = (String) map.get(Constant.REPORT_NAME);

            Properties runtimeParametersInitial = new Properties();
            runtimeParametersInitial.load(new FileInputStream(configurationPropertiesPath.toString()));

            Properties runtimeParameters = new Properties();

            for (Object key : map.keySet()) {
                runtimeParameters.setProperty((String) key, (String) map.get(key));
            }

            for (String propertyName :runtimeParametersInitial.stringPropertyNames()) {
                if(propertyName.startsWith(jobName + ".")) {
                    String value = runtimeParameters.getProperty(propertyName);
                    if(value != null)
                        runtimeParameters.setProperty(propertyName, value);
                }
            }

            runtimeParameters.setProperty(Constant.BATCH_IMPLEMENTATION, runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION));
            runtimeParameters.setProperty(Constant.COD_EMPRESA, runtimeParametersInitial.getProperty(Constant.COD_EMPRESA));
            runtimeParameters.setProperty(Constant.DATABASE, runtimeParametersInitial.getProperty(Constant.DATABASE));
            runtimeParameters.setProperty(Constant.PASS_SIGNATURE, runtimeParametersInitial.getProperty(Constant.PASS_SIGNATURE));
            runtimeParameters.setProperty(Constant.PATH_SIGNATURE, runtimeParametersInitial.getProperty(Constant.PATH_SIGNATURE));
            runtimeParameters.setProperty(Constant.URL_AUTORIZACION, runtimeParametersInitial.getProperty(Constant.URL_AUTORIZACION));
            runtimeParameters.setProperty(Constant.URL_RECEPCION, runtimeParametersInitial.getProperty(Constant.URL_RECEPCION));
            runtimeParameters.setProperty(Constant.URL_REPORTE, runtimeParametersInitial.getProperty(Constant.PATH_REPORTS) + reportName);

            if(tipoComprobante != null)
                runtimeParameters.setProperty(Constant.TIPO_COMPROBANTE, tipoComprobante);

            final String batchImplementation = runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION) ;

            if(tipoComprobante != null || reportName != null)
                log.info("Executing job --> {}, batchImplementation: {}, tipoComprobante --> {}, reportName --> {}"
                        , jobName, batchImplementation, map.get(Constant.TIPO_COMPROBANTE), map.get(Constant.REPORT_NAME));
            else
                log.info("Executing job --> {}, batchImplementation: {}", jobName, batchImplementation);

            if(batchImplementation == null || (batchImplementation != null && batchImplementation.equals(Constant.JEE))){

                JobOperator jobOperator = BatchRuntime.getJobOperator();
                final Long executionId = jobOperator.start(jobName, runtimeParameters);
                jobOperator.getJobExecution(executionId).getBatchStatus().name();
                final String parentThread = Thread.currentThread().getName();

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            awaitTermination(parentThread, jobName, batchImplementation, executionId, timeout);
                        } catch (InterruptedException e) {
                            log.log(Level.ERROR, e.getCause().getMessage());
                        }
                    }
                });

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
                    final JobExecution execution = jobLauncher.run(job, jobParameters);
                    final String parentThread = Thread.currentThread().getName();

                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                awaitTermination(parentThread, jobName, batchImplementation, execution, timeout);
                            } catch (InterruptedException e) {
                                log.log(Level.ERROR, e.getCause().getMessage());
                            }
                        }
                    });

                } catch(Exception e){
                    log.log(Level.ERROR, e.getCause().getMessage());
                }

            }
        } catch (IOException | JobStartException ex) {
            log.log(Level.ERROR, ex.getCause().getMessage());
        }
    }

    @GET
    @Path("reload")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reloadTimers() {
        loadTimers();
        return Response.ok().build();
    }

    private void awaitTermination(String threadName, String jobName, String batchImplementation, Object execution, String to)
            throws InterruptedException {
        Long timeout;

        if(to == null)
            timeout = Constant.DEFAULT_TIMEOUT;
        else
            timeout = Long.parseLong(to);

        final long limit = System.currentTimeMillis() + timeout;

        if(batchImplementation.equals(Constant.JEE)){
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            javax.batch.runtime.JobExecution jobExecution = jobOperator.getJobExecution((Long) execution);
            while (true) {
                if (null != jobExecution.getExitStatus()) {
                    log.log(Level.INFO, "Finished {} execution, with exit status {}. Parent Thread {}.", jobName, jobExecution.getExitStatus(), threadName);
                    break;
                }

                if (System.currentTimeMillis() >= limit) {
                    log.log(Level.INFO, "Timeout waiting {}'s answer from timer with thread id {}.", jobName, threadName);
                    break;
                }

                Thread.sleep(timeout/10);
            }
        } else if(batchImplementation.equals(Constant.SPRING)) {
            JobExecution jobExecution = ((JobExecution) execution);
            while (true) {
                if (null != jobExecution.getExitStatus()) {
                    log.log(Level.INFO, "Finished {} execution, with exit status {}. Parent Thread {}.", jobName, jobExecution.getExitStatus(), threadName);
                    break;
                }

                if (System.currentTimeMillis() >= limit) {
                    log.log(Level.INFO, "Timeout waiting {}'s answer from timer with thread id {}.", jobName, threadName);
                    break;
                }

                Thread.sleep(timeout/10);
            }
        }
    }

    class Work implements Runnable {

        Map map;

        public Work(Map map) {
            this.map = map;
        }

        public void run() {
            executeJob(map);
        }
    }
}