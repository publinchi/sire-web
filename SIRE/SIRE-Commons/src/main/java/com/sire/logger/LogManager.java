package com.sire.logger;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.*;

import java.util.Objects;

public class LogManager {

    private static final String LOGGER_CONFIG = System.getProperty("sire.home") + "/conf/log4j2.xml";
    private static final String LOGGER_CONTEXT_NAME = "SIRE";
    private static final String prefix = "SIRE.";
    private static LoggerContext logctx;

    public static Logger getLogger() {
        if(Objects.isNull(logctx))
            logctx = Configurator.initialize(LOGGER_CONTEXT_NAME, LOGGER_CONFIG);
        Logger logger = logctx.getLogger(prefix);
        logger.info("LOGGER_CONTEXT_NAME: {}",  LOGGER_CONTEXT_NAME);
        return logger;
    }

    public static Logger getLogger(Class<?> clazz) {
        if(Objects.isNull(logctx))
            logctx = Configurator.initialize(LOGGER_CONTEXT_NAME, LOGGER_CONFIG);
        Logger logger = logctx.getLogger(prefix + clazz);
        logger.info("LOGGER_CONTEXT_NAME: {}",  LOGGER_CONTEXT_NAME);
        return logger;
    }

    public static void destroy(){
        if(Objects.nonNull(logctx))
            Configurator.shutdown(logctx);
    }
}