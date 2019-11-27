/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.batch;

import com.sire.batch.constant.Constant;
import org.apache.logging.log4j.Level;
import com.sire.logger.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import javax.annotation.*;
import javax.batch.operations.JobOperator;
import javax.batch.operations.JobStartException;
import javax.batch.operations.NoSuchJobExecutionException;
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
@Path(Constant.TASKS)
public class BatchBean {

    @Resource
    private TimerService timerService;
    @Resource(lookup="java:module/ModuleName")
    private String moduleName;
    @Resource(lookup="java:app/AppName")
    private String applicationName;

    private Logger logger;
    private String home;
    private Boolean thresholdEnabled;
    private static StringBuffer configurationPropertiesPath;
    private ThreadPoolExecutor threadPoolExecutor;
    private int nThreads, queueCapacity;
    private List jobNames;

    @PostConstruct
    @TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
    public void init() {
        _init();
        //startUpSpringFramework();
    }

    private org.springframework.context.support.ClassPathXmlApplicationContext startUpSpringFramework() {
        Properties runtimeParametersInitial = new Properties();
        org.springframework.context.support.ClassPathXmlApplicationContext applicationContext = null;
        try {
            runtimeParametersInitial.load(new FileInputStream(configurationPropertiesPath.toString()));
            if(runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION) != null) {
                final String batchImplementation = runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION);
                if(batchImplementation != null && batchImplementation.equals(Constant.SPRING)) {

                    int jobsNum = jobNames.size();
                    int total = jobsNum + 1;
                    String[] str = new String[total];
                    //str[0] = "context.xml";
                    str[0] = "context-in-memory.xml";

                    int i = 1;

                    for (Object jobName:jobNames) {
                        StringBuilder jobXml = new StringBuilder().append("META-INF/batch-jobs/").append(jobName).append(".xml");
                        str[i] = jobXml.toString();
                        i++;
                    }

                    applicationContext = new org.springframework.context.support.ClassPathXmlApplicationContext(str);

                    //jobNames.clear();
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
        return applicationContext;
    }

    @PreDestroy
    public void finish() {
        if(threadPoolExecutor != null)
            threadPoolExecutor.shutdown();
        cancelTimers();
        LogManager.destroy();
    }

    private void _init(){
        logger = LogManager.getLogger(this.getClass());

        home = System.getProperty("sire.home");
        if (home == null) {
            logger.error("SIRE HOME NOT FOUND.");
            return;
        }

        logger.info("SIRE HOME --> {}", home);

        thresholdEnabled = Boolean.parseBoolean(System.getProperty("sire.threshold.enabled"));

        logger.info("SIRE THRESHOLD ENABLED --> {}", thresholdEnabled);

        if(thresholdEnabled) {

            String capacity = System.getProperty("sire.capacity");
            if (capacity == null) {
                queueCapacity = Constant.DEFAULT_CAPACITY;
                logger.warn("SIRE QUEUE CAPACITY NOT FOUND. SETTING {} CAPACITY BY DEFAULT.", Constant.DEFAULT_CAPACITY);
            } else {
                queueCapacity = Integer.parseInt(capacity);
                logger.info("SIRE QUEUE CAPACITY --> {}", queueCapacity);
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
                logger.warn("SIRE MAXIMUM POOL SIZE NOT FOUND. SETTING {} THREADS BY DEFAULT.", Constant.DEFAULT_MAXIMUM_POOL_SIZE);
            } else {
                nThreads = Integer.parseInt(maximumPoolSize);
                logger.info("SIRE THREADS --> {}", nThreads);
            }

            BlockingQueue q = new ArrayBlockingQueue(queueCapacity);
            threadPoolExecutor = new ThreadPoolExecutor(Integer.valueOf(corePoolSize), nThreads, Integer.valueOf(keepAliveTime), TimeUnit.MILLISECONDS, q);
        }

        configurationPropertiesPath = new StringBuffer();
        configurationPropertiesPath.append(home);
        configurationPropertiesPath.append(File.separator);
        configurationPropertiesPath.append(Constant.CONFIGURATION_PROPERTIES);

        logger.log(Level.INFO, "applicationName -> {}", applicationName);
        logger.log(Level.INFO, "moduleName -> {}", moduleName);
        logger.info("Configuration Properties --> " + configurationPropertiesPath);

        loadTimers();
    }

    private void loadTimers() {
        reconfigTimers();

        configTimers();

        Collection<Timer> timers = timerService.getTimers();
        logger.info("************** TIMERS INFO **************");
        for (Timer timer : timers) {
            logger.log(Level.INFO, "Name: {}", timer.getInfo() + " -> h: " + timer.getSchedule().getHour()
                    + " m: " + timer.getSchedule().getMinute());
        }
        logger.log(Level.INFO, "Total timers: {}", timers.size());
    }

    private void cancelTimers() {
        Collection<Timer> timers = timerService.getTimers();
        logger.info("Cancelling timers ...");
        logger.info("************** TIMERS INFO **************");
        for (Timer timer : timers) {
            logger.log(Level.INFO, "Timer Name Cancelled: {} -> h: {} m: {}"
                    , timer.getInfo()
                    , timer.getSchedule().getHour()
                    , timer.getSchedule().getMinute());
            timer.cancel();
        }
        logger.info("All timers were cancelled.");
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

            if(jobNames == null)
                jobNames = new ArrayList();

            for (String propertyName : runtimeParameters.stringPropertyNames()) {
                if(propertyName.startsWith(timerName+".")) {
                    String name = propertyName.replace(timerName+".","");
                    String value = runtimeParameters.getProperty(propertyName);
                    hashMap.put(name, value);
                    if(name.equals("jobName") && !jobNames.contains(value)) {
                        jobNames.add(value);
                    }
                }
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
            if(timezone   != null && !timezone.trim().isEmpty()  ) scheduleExpression.timezone(timezone);
            else scheduleExpression.timezone("UTC");

            Timer timer = timerService.createCalendarTimer(scheduleExpression, timerConfig);

            logger.info("************** TIMER CREATING **************");
            logger.info("Timer {} Created.", timerName);

            return timer;
        } catch (IOException ex) {
            logger.log(Level.ERROR, ex);
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
                logger.log(Level.INFO, "Total timers: {}", timers.size());
                return;
            }

            logger.info("***************** TIMERS INFO *****************");

            for (Timer timer : timers) {

                if(!cancelTimer(timer, runtimeParameters))
                    updateTimer(timer, runtimeParameters);

            }
            timers = timerService.getTimers();
            logger.log(Level.INFO, "Total timers: {}", timers.size());
        } catch (IOException ex) {
            logger.log(Level.ERROR, ex);
        }
    }

    private boolean createNewTimersWhenNoTimers(Collection<Timer> timers, Properties runtimeParameters) {
        String totalTimerNames = groupTimerNames(runtimeParameters);

        // Si no hay timers en memoria y se agregan nuevos timers en el archivo properties,
        // se crean todos los timers del properties.
        if(timers.isEmpty() && !totalTimerNames.trim().isEmpty()
                && totalTimerNames.split(",").length > 0){
            logger.info("All timers are new, creating them ...");
            createCalendar(totalTimerNames);
            logger.info("Timers created.");
            return true;
        } else if(timers.isEmpty() && totalTimerNames.trim().isEmpty()){
            logger.info("No timers found.");
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

        if ((hour != null && !hour.equals(timer.getSchedule().getHour()))
                || (minute != null && !minute.equals(timer.getSchedule().getMinute()))) {
            logger.log(Level.INFO, "Timer Name: {}", timerName + " -> Disk Hour = " + hour
                    + " -> Mem Hour = " + timer.getSchedule().getHour());

            logger.log(Level.INFO, "Timer Name: {}", timerName + " -> Disk Minute = " + minute
                    + " -> Mem Minute = " + timer.getSchedule().getMinute());

            logger.log(Level.INFO, "Diferentes");
            logger.log(Level.INFO, "Cancelling timer: {}", timer.getInfo());

            timer.cancel();

            logger.log(Level.INFO, "Creating timer {} -> Every {} hours - Every {} minutes.",
                    timerName, hour, minute);

            Timer t = createCalendarTimer(timerName);

            if (t != null)
                logger.log(Level.INFO, "New timer {} created -> Every {} hours - Every {} minutes.",
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
            logger.log(Level.ERROR, ex);
        }
    }

    private boolean cancelTimer(Timer timer, Properties runtimeParameters) {
        String totalTimerNames = groupTimerNames(runtimeParameters);

        Map map = ((Map) timer.getInfo());
        String tn = null;
        if(map.get(Constant.TIMER_NAME) != null)
            tn = map.get(Constant.TIMER_NAME).toString();

        boolean delete = true;
        String[] timerNamesArray = totalTimerNames.split(",");
        for (String timerName : timerNamesArray) {
            if(!timerName.isEmpty() && timerName.equals(tn)) {
                delete = false;
            }
        }
        if(delete){
            logger.info("Timer Canceled -> " + timer.getInfo());
            timer.cancel();
        }
        return delete;
    }

    private void executeWork(Map map) {
        if(threadPoolExecutor != null && threadPoolExecutor instanceof ThreadPoolExecutor) {
            int activeCount = threadPoolExecutor.getActiveCount();
            int queueSize = threadPoolExecutor.getQueue().size();
            logger.info("Active Threads --> {}, Queue Size --> {}", activeCount, queueSize);

            if(activeCount >= nThreads &&  queueSize >= queueCapacity){
                logger.warn("Se ha alcanzado el umbral de {} hilo(s) disponible(s).", activeCount);
                logger.warn("Por favor revise si existen hilos colgados.");
                return;
            }
        }

        threadPoolExecutor.execute(new Work(map));
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @POST
    @Path(Constant.EXECUTIONS)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void executeTask(String message){
        String[] params = message.split(Constant.AMPERSAND);

        Map map = new HashMap();

        for (String param: params) {
            if(param.startsWith(Constant.NAME)){
                String[] p = param.split(Constant.EQUAL);
                map.put(Constant.JOB_NAME,p[1]);
            } else if(param.startsWith(Constant.PROPERTIES)) {
                // TODO Implementar
            } else if(param.startsWith(Constant.ARGUMENTS)) {
                String[] p = param.split(Constant.EQUAL);
                String[] q = p[1].split(Constant.PLUS);
                for (String r : q) {
                    String[] s = r.split(Constant.TRES_D);
                    map.put(s[0].replaceAll(Constant.GUION_GUION, Constant.EMPTY), s[1]);
                }
            }
        }
        executeJob(map);
    }

    @POST
    @Consumes("application/json")
    public void executeJob(Map map){
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

            if(runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION) != null)
                runtimeParameters.setProperty(Constant.BATCH_IMPLEMENTATION, runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION));
            if(runtimeParametersInitial.getProperty(Constant.COD_EMPRESA) != null)
                runtimeParameters.setProperty(Constant.COD_EMPRESA, runtimeParametersInitial.getProperty(Constant.COD_EMPRESA));
            if(runtimeParametersInitial.getProperty(Constant.DATABASE) != null)
                runtimeParameters.setProperty(Constant.DATABASE, runtimeParametersInitial.getProperty(Constant.DATABASE));
            if(runtimeParametersInitial.getProperty(Constant.PASS_SIGNATURE) != null)
                runtimeParameters.setProperty(Constant.PASS_SIGNATURE, runtimeParametersInitial.getProperty(Constant.PASS_SIGNATURE));
            if(runtimeParametersInitial.getProperty(Constant.PATH_SIGNATURE) != null)
                runtimeParameters.setProperty(Constant.PATH_SIGNATURE, runtimeParametersInitial.getProperty(Constant.PATH_SIGNATURE));
            if(runtimeParametersInitial.getProperty(Constant.URL_AUTORIZACION) != null)
                runtimeParameters.setProperty(Constant.URL_AUTORIZACION, runtimeParametersInitial.getProperty(Constant.URL_AUTORIZACION));
            if(runtimeParametersInitial.getProperty(Constant.URL_RECEPCION) != null)
                runtimeParameters.setProperty(Constant.URL_RECEPCION, runtimeParametersInitial.getProperty(Constant.URL_RECEPCION));
            if(runtimeParametersInitial.getProperty(Constant.PATH_REPORTS) != null)
                runtimeParameters.setProperty(Constant.URL_REPORTE, runtimeParametersInitial.getProperty(Constant.PATH_REPORTS) + reportName);

            if(tipoComprobante != null)
                runtimeParameters.setProperty(Constant.TIPO_COMPROBANTE, tipoComprobante);

            final String batchImplementation = runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION) ;

            if(tipoComprobante != null || reportName != null)
                logger.info("Executing job --> {}, batchImplementation: {}, tipoComprobante --> {}, reportName --> {}"
                        , jobName, batchImplementation, map.get(Constant.TIPO_COMPROBANTE), map.get(Constant.REPORT_NAME));
            else
                logger.info("Executing job --> {}, batchImplementation: {}", jobName, batchImplementation);

            final String parentThread = Thread.currentThread().getName();
            Object eId = null;

            if(batchImplementation == null || (batchImplementation != null && batchImplementation.equals(Constant.JEE))){

                JobOperator jobOperator = BatchRuntime.getJobOperator();
                eId = jobOperator.start(jobName, runtimeParameters);
                logger.info("Initializing {} job with execution id {}.", jobName, eId.toString());

            } else if(batchImplementation.equals(Constant.SPRING)){

                org.springframework.context.support.ClassPathXmlApplicationContext applicationContext = startUpSpringFramework();

                if(applicationContext == null) {
                    logger.error("No se ejecuta el job, el contexto spring no pudo iniciarse.");
                    return;
                }

                org.springframework.batch.core.launch.support.SimpleJobLauncher jobLauncher
                        = (org.springframework.batch.core.launch.support.SimpleJobLauncher) applicationContext.getBean("jobLauncher");

                try {
                    org.springframework.batch.core.JobParametersBuilder jobParametersBuilder
                            = new org.springframework.batch.core.JobParametersBuilder();

                    Enumeration<String> enums = (Enumeration<String>) runtimeParameters.propertyNames();
                    while (enums.hasMoreElements()) {
                        String key = enums.nextElement();
                        String value = runtimeParameters.getProperty(key);
                        jobParametersBuilder.addString(key, value);
                    }

                    jobParametersBuilder.addLong("time", System.currentTimeMillis());

                    org.springframework.batch.core.Job job = (org.springframework.batch.core.Job) applicationContext.getBean(jobName);
                    org.springframework.batch.core.JobParameters jobParameters = jobParametersBuilder.toJobParameters();

                    jobLauncher.run(job, jobParameters);
                    logger.info("Initializing {} job.", jobName);
                    eId = jobParameters;

                } catch(Exception e){
                    logger.log(Level.ERROR, e);
                    return;
                }

            }

            final Object object = eId;

            if(parentThread == null || jobName == null || object == null)
                return;

            if(!batchImplementation.equals(Constant.SPRING))
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            awaitTermination(parentThread, jobName, object, timeout, batchImplementation);
                        } catch (InterruptedException e) {
                            logger.log(Level.ERROR, e.getCause().getMessage());
                        }
                    }
                });

        } catch (IOException | JobStartException ex) {
            logger.log(Level.ERROR, ex);
        }
    }

    @GET
    @Path("reload")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reloadTimers() {
        loadTimers();
        return Response.ok().build();
    }

    private void awaitTermination(String threadName, String jobName, Object object, String to, String batchImplementation)
            throws InterruptedException {
        if(batchImplementation == null || batchImplementation.equals(Constant.JEE))
            _awaitTermination(threadName, jobName, (Long) object, to);
        else
            _awaitTermination(threadName, jobName, object, to);
    }

    private void _awaitTermination(String threadName, String jobName, Long execution, String to)
            throws InterruptedException {
        Long timeout;

        if(to == null)
            timeout = Constant.DEFAULT_TIMEOUT;
        else
            timeout = Long.parseLong(to);

        final long limit = System.currentTimeMillis() + timeout;

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        javax.batch.runtime.JobExecution jobExecution;

        try {
            jobExecution = jobOperator.getJobExecution(execution);
        } catch (NoSuchJobExecutionException nsjee) {
            logger.catching(nsjee);
            return;
        }

        while (true) {
            if (null != jobExecution.getExitStatus()) {
                logger.log(Level.INFO, "Finished {} execution, with exit status {}. Parent Thread {}.", jobName
                        , jobExecution.getExitStatus(), threadName);
                break;
            }

            if (System.currentTimeMillis() >= limit) {
                logger.log(Level.INFO, "Timeout of {} ms waiting {}'s answer from timer with thread id {}.", timeout,
                        jobName, threadName);
                break;
            }

            Thread.sleep(timeout/10);
        }
    }

    private void _awaitTermination(String threadName, String jobName, Object jobParameters, String to)
            throws InterruptedException {
        Long timeout;

        if(to == null)
            timeout = Constant.DEFAULT_TIMEOUT;
        else
            timeout = Long.parseLong(to);

        final long limit = System.currentTimeMillis() + timeout;

        org.springframework.context.support.ClassPathXmlApplicationContext applicationContext = null;

        org.springframework.batch.core.repository.JobRepository jobRepository =
                (org.springframework.batch.core.repository.JobRepository) applicationContext.getBean("jobRepository");
        org.springframework.batch.core.JobExecution jobExecution = jobRepository.getLastJobExecution(jobName,
                (org.springframework.batch.core.JobParameters) jobParameters);
        while (true) {
            if (jobExecution != null && null != jobExecution.getExitStatus()
                    && !jobExecution.getExitStatus().getExitCode()
                    .equals(org.springframework.batch.core.ExitStatus.UNKNOWN.getExitCode())) {
                logger.log(Level.INFO, "Finished {} with execution id {} and exit status {}. Parent Thread {}.",
                        jobName, jobExecution.getId(), jobExecution.getExitStatus().getExitCode(), threadName);
                break;
            }

            if (System.currentTimeMillis() >= limit) {
                logger.log(Level.INFO, "Timeout of {} ms waiting {}'s answer from timer with thread id {}.", timeout,
                        jobName, threadName);
                break;
            }

            Thread.sleep(timeout/10);
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