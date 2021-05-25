/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.batch;

import com.sire.batch.constant.Constant;
import com.sire.logger.LogManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.operations.NoSuchJobExecutionException;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author pestupinan
 */
@Singleton
@Startup
@Path(Constant.TASKS)
public class BatchBean {

    private static TimerService timerService;
    @Resource(lookup="java:module/ModuleName")
    private String moduleName;
    @Resource(lookup="java:app/AppName")
    private String applicationName;

    private static Logger logger;
    private static StringBuilder configurationPropertiesPath;
    private static ThreadPoolExecutor threadPoolExecutor;
    private static int nThreads;
    private static int queueCapacity;
    private static List<String> jobNames;
    private static Properties runtimeParameters;
    private static Scheduler scheduler;

    @PostConstruct
    @TransactionAttribute(value=TransactionAttributeType.NOT_SUPPORTED)
    public void init() {
        init(applicationName, moduleName);
    }

    private static org.springframework.context.support.ClassPathXmlApplicationContext startUpSpringFramework() {
        Properties runtimeParametersInitial = getProperties();
        if(Objects.isNull(runtimeParametersInitial))
            return null;
        org.springframework.context.support.ClassPathXmlApplicationContext applicationContext = null;

        try( FileInputStream fileInputStream = new FileInputStream(configurationPropertiesPath.toString()) ) {
            runtimeParametersInitial.load(fileInputStream);
            if(runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION) != null) {
                final String batchImplementation = runtimeParametersInitial.getProperty(Constant.BATCH_IMPLEMENTATION);
                if(batchImplementation != null && batchImplementation.equals(Constant.SPRING)) {

                    int jobsNum = jobNames.size();
                    int total = jobsNum + 1;
                    String[] str = new String[total];
                    if(Objects.nonNull(runtimeParametersInitial.getProperty(Constant.BATCH_PERSISTENT))
                            && runtimeParametersInitial.getProperty(Constant.BATCH_PERSISTENT).equals("true"))
                        str[0] = "context.xml";
                    else
                        str[0] = "context-in-memory.xml";

                    int i = 1;

                    for (Object jobName:jobNames) {
                        StringBuilder jobXml = new StringBuilder().append("META-INF/batch-jobs/").append(jobName)
                                .append(Constant.XML_SUFFIX);
                        str[i] = jobXml.toString();
                        i++;
                    }

                    applicationContext = new org.springframework.context.support.ClassPathXmlApplicationContext(str);

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
        try {
            if(Objects.nonNull(scheduler)) {
                logger.info("Shutting down quartz with status started: {}, standbyMode: {} ...",
                        scheduler.isStarted(), scheduler.isInStandbyMode());
                scheduler.shutdown();
                logger.info("Is Quartz Shutdown?: {}.", scheduler.isShutdown());
            } else if(Objects.nonNull(timerService))
                cancelTimers();
        } catch (SchedulerException e) {
            logger.error(e);
        }
        LogManager.destroy();
    }

    private static void init(String applicationName, String moduleName){
        logger = LogManager.getLogger(BatchBean.class);

        String home = System.getProperty(Constant.SIRE_HOME);
        if (Objects.isNull(home)) {
            logger.error(Constant.SIRE_HOME_NOT_FOUND);
            return;
        }

        logger.info("SIRE HOME --> {}", home);

        boolean thresholdEnabled = Boolean.parseBoolean(System.getProperty("sire.threshold.enabled"));

        logger.info("SIRE THRESHOLD ENABLED --> {}", thresholdEnabled);

        if(thresholdEnabled) {
            applyThreshold();
        }

        configurationPropertiesPath = new StringBuilder();
        configurationPropertiesPath.append(home);
        configurationPropertiesPath.append(File.separator);
        configurationPropertiesPath.append(Constant.CONFIGURATION_PROPERTIES);

        logger.log(Level.INFO, "applicationName -> {}", applicationName);
        logger.log(Level.INFO, "moduleName -> {}", moduleName);
        logger.info("Configuration Properties --> {}", configurationPropertiesPath);

        Properties properties = getProperties();
        if(Objects.nonNull(properties) && Objects.isNull(properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)) ||
                Objects.nonNull(properties) && properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)
                        .equals(Constant.JEE)) {
            logger.info("EJB TIMER NOT MORE SUPPORTED.");
        }
        else if(Objects.nonNull(properties) && properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)
                .equals(Constant.QUARTZ)) {
            logger.info("QUARTZ ENABLED.");
            initQuartz();
        }
    }

    private static void initQuartz() {
        try {
            if(Objects.isNull(scheduler) || scheduler.isShutdown()) {
                scheduler = new StdSchedulerFactory().getScheduler();
                scheduler.start();

                loadTimers();
            }
        } catch (SchedulerException e) {
            logger.error("QUARTZ FAILED -> ", e);
        }
    }

    private static void applyThreshold() {
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
            logger.warn("SIRE MAXIMUM POOL SIZE NOT FOUND. SETTING {} THREADS BY DEFAULT."
                    , Constant.DEFAULT_MAXIMUM_POOL_SIZE);
        } else {
            nThreads = Integer.parseInt(maximumPoolSize);
            logger.info("SIRE THREADS --> {}", nThreads);
        }

        BlockingQueue<Runnable> q = new ArrayBlockingQueue<>(queueCapacity);
        threadPoolExecutor = new ThreadPoolExecutor(Integer.valueOf(corePoolSize), nThreads
                , Integer.valueOf(keepAliveTime), TimeUnit.MILLISECONDS, q);
    }

    private static void loadTimers() {
        Properties properties = getProperties();

        if(Objects.nonNull(properties) && Objects.isNull(properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)) ||
                Objects.nonNull(properties) && properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)
                        .equals(Constant.JEE)) {
            reconfigTimers();
        }

        configTimers();

        logger.info("************** TIMERS INFO **************");

        int size = 0;
        if(Objects.nonNull(properties) && Objects.isNull(properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)) ||
                Objects.nonNull(properties) && properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)
                        .equals(Constant.JEE)) {
            Collection<javax.ejb.Timer> timers = timerService.getTimers();

            for (javax.ejb.Timer timer : timers) {
                logger.log(Level.INFO, "Name: {}", timer.getInfo() + " -> h: " + timer.getSchedule().getHour()
                        + " m: " + timer.getSchedule().getMinute());
            }
            size = timers.size();
        }
        else if(Objects.nonNull(properties) && properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)
                .equals(Constant.QUARTZ)) {
            try {
                size = scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(Constant.SIRE)).size();
            } catch (SchedulerException e) {
                logger.error(e);
            }
        }
        logger.log(Level.INFO, Constant.TOTAL_TIMERS, size);
    }

    private void cancelTimers() {
        Collection<javax.ejb.Timer> timers = timerService.getTimers();
        logger.info("Cancelling timers ...");
        logger.info("************** TIMERS INFO **************");
        for (javax.ejb.Timer timer : timers) {
            logger.log(Level.INFO, "Timer Name Cancelled: {} -> h: {} m: {}"
                    , timer.getInfo()
                    , timer.getSchedule().getHour()
                    , timer.getSchedule().getMinute());
            timer.cancel();
        }
        logger.info("All timers were cancelled.");
    }

    private static void createCalendar(String timerNames) {
        if(timerNames!=null){
            String[] timerNamesArray = timerNames.split(",");
            for (String timerName : timerNamesArray) {
                if(!timerName.isEmpty())
                    createCalendarTimer(timerName);
            }
        }
    }

    private static void createCalendarTimer(String timerName) {
        Properties properties = getProperties();

        boolean persistent = Boolean.parseBoolean(runtimeParameters.getProperty(timerName + Constant.PERSISTENT_SUFFIX));

        HashMap<String, String> hashMap = new HashMap<>();

        if (jobNames == null)
            jobNames = new ArrayList<>();

        for (String propertyName : runtimeParameters.stringPropertyNames()) {
            if (propertyName.startsWith(timerName + ".")) {
                String name = propertyName.replace(timerName + ".", "");
                String value = runtimeParameters.getProperty(propertyName);
                hashMap.put(name, value);
                if (name.equals(Constant.JOB_NAME) && !jobNames.contains(value)) {
                    jobNames.add(value);
                }
            }
        }

        if(Objects.nonNull(properties) && Objects.isNull(properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)) ||
                Objects.nonNull(properties) && properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)
                        .equals(Constant.JEE)) {

            scheduleJEE(timerName, persistent, hashMap, runtimeParameters);

        } else if(Objects.nonNull(properties) && properties.getProperty(Constant.SCHEDULE_IMPLEMENTATION)
                .equals(Constant.QUARTZ)) {

            scheduleQuartz(timerName, hashMap, runtimeParameters);
        }

        logger.info("************** TIMER CREATING **************");
        logger.info("Timer {} Created.", timerName);
    }

    private static void scheduleJEE(String timerName, boolean persistent, HashMap<String, String> hashMap
            , Properties runtimeParameters) {
        final TimerConfig timerConfig = new TimerConfig(timerName, persistent);

        timerConfig.setInfo(hashMap);

        Collection<javax.ejb.Timer> timers = timerService.getTimers();

        for (javax.ejb.Timer timer : timers) {
            if (timerConfig.getInfo().equals(timer.getInfo())) {
                return;
            }
        }

        javax.ejb.Timer timer = timerService.createCalendarTimer(generateScheduleExpression(timerName
                , runtimeParameters), timerConfig);

        if (Objects.nonNull(timer))
            logger.log(Level.INFO, "New timer {} created -> Every {} hours - Every {} minutes.",
                    timer.getInfo(), timer.getSchedule().getHour(), timer.getSchedule().getMinute());
    }

    private static ScheduleExpression generateScheduleExpression(String timerName, Properties runtimeParameters) {
        Optional<String> second = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.SECOND_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> minute = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.MINUTE_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> hour = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.HOUR_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> dayOfMonth = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.DAY_MONTH_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> month = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.MONTH_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> dayOfWeek = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.DAY_WEEK_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> year = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.YEAR_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> timezone = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.TIME_ZONE_SUFFIX)).filter(s -> !s.isEmpty());

        ScheduleExpression scheduleExpression = new ScheduleExpression();
        if (second.isPresent()) scheduleExpression.second(second.get());
        if (minute.isPresent()) scheduleExpression.minute(minute.get());
        if (hour.isPresent()) scheduleExpression.hour(hour.get());
        if (dayOfMonth.isPresent()) scheduleExpression.dayOfMonth(dayOfMonth.get());
        if (month.isPresent()) scheduleExpression.month(month.get());
        if (dayOfWeek.isPresent()) scheduleExpression.dayOfWeek(dayOfWeek.get());
        if (year.isPresent()) scheduleExpression.year(year.get());
        if (timezone.isPresent()) scheduleExpression.timezone(timezone.get());
        else scheduleExpression.timezone(Constant.UTC);

        return scheduleExpression;
    }

    private static void scheduleQuartz(String timerName, HashMap<String, String> hashMap, Properties runtimeParameters) {
        Optional<String> jobName = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.JOB_NAME_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> minute = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.MINUTE_SUFFIX)).filter(s -> !s.isEmpty());
        Optional<String> hour = Optional.ofNullable(runtimeParameters
                .getProperty(timerName + Constant.HOUR_SUFFIX)).filter(s -> !s.isEmpty());

        if(!jobName.isPresent()) throw new NullPointerException();
        if(!minute.isPresent()) throw new NullPointerException();
        if(!hour.isPresent()) throw new NullPointerException();

        String key = jobName.get().concat(".").concat(timerName);

        String group = Constant.SIRE;

        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.putAll(hashMap);

        JobDetail job = JobBuilder
                .newJob(JobImpl.class)
                .withIdentity(key, group)
                .usingJobData(jobDataMap)
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(timerName, group)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 " + minute.get() + " " + hour.get() + " * * ? *"))
                .build();
        try {
            JobKey jobKey = new JobKey(key, group);
            if(scheduler.checkExists(jobKey))
                scheduler.deleteJob(jobKey);
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error(e);
        }
    }

    private static Properties getProperties() {
        if(Objects.nonNull(runtimeParameters))
            return runtimeParameters;

        try( FileInputStream fileInputStream = new FileInputStream(configurationPropertiesPath.toString()) ) {
            runtimeParameters = new Properties();
            runtimeParameters.load(fileInputStream);
            return runtimeParameters;
        } catch (IOException ex) {
            logger.log(Level.ERROR, ex);
            return null;
        }
    }

    private static void reconfigTimers() {
        Properties runtimeParameters = getProperties();

        Collection<javax.ejb.Timer> timers = timerService.getTimers();

        boolean finish = createNewTimersWhenNoTimers(timers, runtimeParameters);

        if(finish){
            timers = timerService.getTimers();
            logger.log(Level.INFO, "Total timers: {}", timers.size());
            return;
        }

        logger.info("***************** TIMERS INFO *****************");

        for (javax.ejb.Timer timer : timers) {

            if(!cancelTimer(timer, runtimeParameters))
                updateTimer(timer, runtimeParameters);

        }
        timers = timerService.getTimers();
        logger.log(Level.INFO, "Total timers: {}", timers.size());
    }

    private static boolean createNewTimersWhenNoTimers(Collection<javax.ejb.Timer> timers, Properties runtimeParameters) {
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

    private static String groupTimerNames(Properties runtimeParameters) {
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

    private static void updateTimer(javax.ejb.Timer timer, Properties runtimeParameters) {
        String timerName = ((Map) timer.getInfo()).get(Constant.TIMER_NAME).toString();

        String hour = runtimeParameters.getProperty(timerName + Constant.HOUR_SUFFIX);
        if (hour != null) {
            hour = hour.trim();
        }

        String minute = runtimeParameters.getProperty(timerName + Constant.MINUTE_SUFFIX);
        if (minute != null) {
            minute = minute.trim();
        }

        if ((hour != null && !hour.equals(timer.getSchedule().getHour()))
                || (minute != null && !minute.equals(timer.getSchedule().getMinute()))) {
            logger.log(Level.INFO, "Timer Name: {} -> Disk Hour = {} -> Mem Hour = {}", timerName, hour,
                    timer.getSchedule().getHour());

            logger.log(Level.INFO, "Timer Name: {} -> Disk Minute = {} -> Mem Minute = {}", timerName, minute,
                    timer.getSchedule().getMinute());

            logger.log(Level.INFO, "Diferentes");
            logger.log(Level.INFO, "Cancelling timer: {}", timer.getInfo());

            timer.cancel();

            logger.log(Level.INFO, "Creating timer {} -> Every {} hours - Every {} minutes.",
                    timerName, hour, minute);

            createCalendarTimer(timerName);
        }
    }

    private static void configTimers() {
        Properties runtimeParameters = getProperties();

        if(Objects.isNull(runtimeParameters))
            return;
        String timerRecepcionNames = runtimeParameters.getProperty(Constant.TIMER_RECEPCION_NAMES);
        String timerAutorizacionNames = runtimeParameters.getProperty(Constant.TIMER_AUTORIZACION_NAMES);
        String timerNames = runtimeParameters.getProperty(Constant.TIMER_NAMES);

        createCalendar(timerRecepcionNames);
        createCalendar(timerAutorizacionNames);
        createCalendar(timerNames);
    }

    private static boolean cancelTimer(javax.ejb.Timer timer, Properties runtimeParameters) {
        String totalTimerNames = groupTimerNames(runtimeParameters);

        Map<String, String> map = ((Map) timer.getInfo());
        String tn = null;
        if(map.get(Constant.TIMER_NAME) != null)
            tn = map.get(Constant.TIMER_NAME);

        boolean delete = true;
        String[] timerNamesArray = totalTimerNames.split(",");
        for (String timerName : timerNamesArray) {
            if(!timerName.isEmpty() && timerName.equals(tn)) {
                delete = false;
            }
        }
        if(delete){
            logger.info("Timer Canceled -> {}", timer.getInfo());
            timer.cancel();
        }
        return delete;
    }

    private static void executeWork(Map<String, String> map) {
        if(threadPoolExecutor instanceof ThreadPoolExecutor) {
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

        Map<String, String> map = new HashMap<>();

        for (String param: params) {
            if(param.startsWith(Constant.NAME)){
                String[] p = param.split(Constant.EQUAL);
                map.put(Constant.JOB_NAME, p[1]);
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
    public static void executeJob(Map<String, String> map) {
        final Optional<String> timeout = Optional.ofNullable(map.get(Constant.TIMEOUT)).filter(s -> !s.isEmpty());
        final Optional<String> jobName = Optional.ofNullable(map.get(Constant.JOB_NAME)).filter(s -> !s.isEmpty());
        Optional<String> tipoComprobante = Optional.ofNullable(map.get(Constant.TIPO_COMPROBANTE))
                .filter(s -> !s.isEmpty());
        Optional<String> reportName = Optional.ofNullable(map.get(Constant.REPORT_NAME)).filter(s -> !s.isEmpty());
        Optional<String> quartzJobName = Optional.ofNullable(map.get(Constant.QUARTZ_JOB_NAME))
                .filter(s -> !s.isEmpty());
        Optional<String> timerName = Optional.ofNullable(map.get(Constant.TIMER_NAME)).filter(s -> !s.isEmpty());

        Optional<Properties> runtimeParametersInitial = Optional.ofNullable(getProperties());

        Properties runtimeParameters = new Properties();

        map.forEach((key, value) -> runtimeParameters.setProperty(key, value));

        if(!runtimeParametersInitial.isPresent() || !jobName.isPresent()) return;

        for (String propertyName : runtimeParametersInitial.get().stringPropertyNames()) {
            if(propertyName.startsWith(jobName + ".")) {
                Optional<String> value = Optional.ofNullable(runtimeParameters.getProperty(propertyName));
                value.ifPresent(s -> runtimeParameters.setProperty(propertyName, s));
            }
        }

        Optional<String> batchImplementation = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.BATCH_IMPLEMENTATION));
        Optional<String> codEmpresa = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.COD_EMPRESA));
        Optional<String> database = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.DATABASE));
        Optional<String> passSignature = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.PASS_SIGNATURE));
        Optional<String> pathSignature = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.PATH_SIGNATURE));
        Optional<String> urlAutorizacion = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.URL_AUTORIZACION));
        Optional<String> urlRecepcion = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.URL_RECEPCION));
        Optional<String> pathReports = Optional.ofNullable(runtimeParametersInitial.get()
                .getProperty(Constant.PATH_REPORTS));

        batchImplementation.ifPresent(s -> runtimeParameters.setProperty(Constant.BATCH_IMPLEMENTATION, s));
        codEmpresa.ifPresent(s -> runtimeParameters.setProperty(Constant.COD_EMPRESA, s));
        database.ifPresent(s -> runtimeParameters.setProperty(Constant.DATABASE, s));
        passSignature.ifPresent(s -> runtimeParameters.setProperty(Constant.PASS_SIGNATURE, s));
        pathSignature.ifPresent(s -> runtimeParameters.setProperty(Constant.PATH_SIGNATURE, s));
        urlAutorizacion.ifPresent(s -> runtimeParameters.setProperty(Constant.URL_AUTORIZACION, s));
        urlRecepcion.ifPresent(s -> runtimeParameters.setProperty(Constant.URL_RECEPCION, s));
        pathReports.ifPresent(s -> runtimeParameters.setProperty(Constant.PATH_REPORTS, s));

        tipoComprobante.ifPresent(s -> runtimeParameters.setProperty(Constant.TIPO_COMPROBANTE, s));

        printExecutingLog(batchImplementation, quartzJobName, tipoComprobante, jobName, reportName);

        final String parentThread = Thread.currentThread().getName();
        Object eId = null;

        org.springframework.context.support.ClassPathXmlApplicationContext applicationContext = null;

        if(!batchImplementation.isPresent() || batchImplementation.get().equals(Constant.JEE)) {

            JobOperator jobOperator = BatchRuntime.getJobOperator();
            eId = jobOperator.start(jobName.get(), runtimeParameters);
            logger.info("Initializing {} job from trigger/timer {} with execution id {}.", jobName, timerName
                    , eId);

        } else if(batchImplementation.get().equals(Constant.SPRING)){

            applicationContext = startUpSpringFramework();

            if(Objects.isNull(applicationContext)) {
                logger.error("No se ejecuta el job, el contexto spring no pudo iniciarse.");
                return;
            }

            org.springframework.batch.core.launch.support.SimpleJobLauncher jobLauncher
                    = (org.springframework.batch.core.launch.support.SimpleJobLauncher) applicationContext
                    .getBean(Constant.JOB_LAUNCHER);

            try {
                org.springframework.batch.core.JobParametersBuilder jobParametersBuilder
                        = new org.springframework.batch.core.JobParametersBuilder();

                Enumeration<String> enums = (Enumeration<String>) runtimeParameters.propertyNames();
                while (enums.hasMoreElements()) {
                    String key = enums.nextElement();
                    String value = runtimeParameters.getProperty(key);
                    jobParametersBuilder.addString(key, value);
                }

                jobParametersBuilder.addLong(Constant.TIME, System.currentTimeMillis());

                org.springframework.batch.core.Job job = (org.springframework.batch.core.Job) applicationContext
                        .getBean(jobName.get());
                org.springframework.batch.core.JobParameters jobParameters = jobParametersBuilder.toJobParameters();

                jobLauncher.run(job, jobParameters);
                logger.info("Initializing {} job from trigger/timer {}.", jobName, timerName);
                eId = jobParameters;

            } catch(Exception e){
                logger.log(Level.ERROR, e);
                return;
            }

        }

        final Object object = eId;

        executeAwaitTermination(parentThread, jobName, object, timeout, batchImplementation, applicationContext);
    }

    private static void executeAwaitTermination(String parentThread, Optional<String> jobName, Object object
            , Optional<String> timeout, Optional<String> batchImplementation
            , org.springframework.context.support.ClassPathXmlApplicationContext applicationContext) {

        if(parentThread == null || !jobName.isPresent() || object == null)
            return;

        if(batchImplementation.isPresent() && !batchImplementation.get().equals(Constant.SPRING))
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    awaitTermination(parentThread, jobName.get(), object, timeout.get(), batchImplementation.get());
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR, e.getCause().getMessage());
                    Thread.currentThread().interrupt();
                }
            });
        else {
            ClassPathXmlApplicationContext finalApplicationContext = applicationContext;
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    awaitTermination(parentThread, jobName.get(), object, timeout.get(), finalApplicationContext);
                } catch (InterruptedException e) {
                    logger.log(Level.ERROR, e.getCause().getMessage());
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    private static void printExecutingLog(Optional<String> batchImplementation, Optional<String> quartzJobName
            , Optional<String> tipoComprobante, Optional<String> jobName, Optional<String> reportName) {
        if(batchImplementation.isPresent() && quartzJobName.isPresent() && tipoComprobante.isPresent())
            logger.info("Executing quartz job --> {}, batch job --> {}, batchImplementation: {}" +
                            ", tipoComprobante --> {}, reportName --> {}"
                    , quartzJobName, jobName, batchImplementation.get(), tipoComprobante.get(), reportName);
        else if(batchImplementation.isPresent() && tipoComprobante.isPresent() && reportName.isPresent())
            logger.info("Executing job --> {}, batchImplementation: {}, tipoComprobante --> {}, reportName --> {}"
                    , jobName, batchImplementation.get(), tipoComprobante.get(), reportName);
        else if(batchImplementation.isPresent())
            logger.info("Executing job --> {}, batchImplementation: {}", jobName, batchImplementation.get());
    }

    @GET
    @Path("reload")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response reloadTimers() {
        loadTimers();
        return Response.ok().build();
    }

    private static void awaitTermination(String threadName, String jobName, Object object, String to
            , String batchImplementation) throws InterruptedException {
        if(batchImplementation == null || batchImplementation.equals(Constant.JEE))
            awaitTermination(threadName, jobName, (Long) object, to);
    }

    private static void awaitTermination(String threadName, String jobName, Long execution, String to)
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

        boolean exit = false;

        while (true) {
            if (null != jobExecution.getExitStatus()) {
                logger.log(Level.INFO, "Finished {} execution, with exit status {}. Parent Thread {}.", jobName
                        , jobExecution.getExitStatus(), threadName);
                exit = true;
            }

            if (!exit && System.currentTimeMillis() >= limit) {
                logger.log(Level.INFO, "Timeout of {} ms waiting {}s answer from timer with thread id {}.", timeout,
                        jobName, threadName);
                exit = true;
            }

            if(exit)
                break;

            Thread.sleep(timeout/10);
        }
    }

    private static void awaitTermination(String threadName, String jobName, Object jobParameters, String to
            , org.springframework.context.support.ClassPathXmlApplicationContext applicationContext) throws InterruptedException {
        Long timeout;

        if(to == null)
            timeout = Constant.DEFAULT_TIMEOUT;
        else
            timeout = Long.parseLong(to);

        final long limit = System.currentTimeMillis() + timeout;

        org.springframework.batch.core.repository.JobRepository jobRepository =
                (org.springframework.batch.core.repository.JobRepository) applicationContext
                        .getBean(Constant.JOB_REPOSITORY);
        org.springframework.batch.core.JobExecution jobExecution = jobRepository.getLastJobExecution(jobName,
                (org.springframework.batch.core.JobParameters) jobParameters);

        boolean exit = false;

        while (true) {
            if (Objects.nonNull(jobExecution) && !jobExecution.getExitStatus().getExitCode()
                    .equals(org.springframework.batch.core.ExitStatus.UNKNOWN.getExitCode())) {
                logger.log(Level.INFO, "Finished {} with execution id {} and exit status {}. Parent Thread {}.",
                        jobName, jobExecution.getId(), jobExecution.getExitStatus().getExitCode(), threadName);
                exit = true;
            }

            if (!exit && System.currentTimeMillis() >= limit) {
                logger.log(Level.INFO, "Timeout of {} ms waiting {}s answer from timer with thread id {}.", timeout,
                        jobName, threadName);
                exit = true;
            }

            if(exit)
                break;

            Thread.sleep(timeout/10);
        }
    }

    static class Work implements Runnable {

        Map<String, String> map;

        public Work(Map<String, String> map) {
            this.map = map;
        }

        public void run() {
            executeJob(map);
        }
    }

    public static class JobImpl implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            jobExecutionContext.getJobDetail().getJobDataMap().put(Constant.QUARTZ_JOB_NAME
                    , jobExecutionContext.getJobDetail().getKey().getName());

            jobExecutionContext.getJobDetail().getJobDataMap().put(Constant.TIMER_NAME
                    , jobExecutionContext.getTrigger().getKey().getName());

            Map<String, String> map = new HashMap<>();
            jobExecutionContext.getJobDetail().getJobDataMap().forEach((key, value) -> map.put(key, value.toString()));

            executeJob(map);
        }
    }
}