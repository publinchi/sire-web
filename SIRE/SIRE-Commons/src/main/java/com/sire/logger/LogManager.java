package com.sire.logger;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.*;

import java.util.Objects;

public class LogManager {

    private LogManager() {}

    private static final String LOGGER_CONFIG = System.getProperty("sire.home") + "/conf/log4j2.xml";
    private static final String LOGGER_CONTEXT_NAME = "SIRE";
    private static final String PREFIX = "SIRE.";
    private static LoggerContext logctx;

    public static Logger getLogger() {
        if(java.util.Objects.isNull(logctx))
            buildContext(null);
        return logctx.getLogger(PREFIX);
    }

    public static Logger getLogger(Class<?> clazz) {
        if(java.util.Objects.isNull(logctx))
            buildContext(clazz);
        return logctx.getLogger(PREFIX + clazz);
    }

    public static void destroy(){
        if(Objects.nonNull(logctx))
            Configurator.shutdown(logctx);
    }

    private static synchronized void buildContext(Class<?> clazz) {
        logctx = org.apache.logging.log4j.core.config.Configurator.initialize(LOGGER_CONTEXT_NAME, LOGGER_CONFIG);
        org.apache.logging.log4j.core.Logger logger = logctx.getLogger(PREFIX + clazz);
        logger.info("LOGGER_CONTEXT_NAME: {}, CLASS_NAME: {}", LOGGER_CONTEXT_NAME, clazz);
    }
}