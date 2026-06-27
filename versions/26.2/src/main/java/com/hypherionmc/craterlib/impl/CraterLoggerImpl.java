package com.hypherionmc.craterlib.impl;

import com.hypherionmc.craterlib.api.util.CraterLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CraterLoggerImpl implements CraterLogger {

    private final Logger LOGGER;

    public CraterLoggerImpl(String name) {
        LOGGER = LoggerFactory.getLogger(name);
    }

    @Override
    public void trace(String message) {
        LOGGER.trace(message);
    }

    @Override
    public void trace(String format, Object... args) {
        LOGGER.trace(format, args);
    }

    @Override
    public void debug(String message) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String format, Object... args) {
        LOGGER.debug(format, args);
    }

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void info(String format, Object... args) {
        LOGGER.info(format, args);
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String format, Object... args) {
        LOGGER.warn(format, args);
    }

    @Override
    public void error(String error) {
        LOGGER.error(error);
    }

    @Override
    public void error(String format, Object... args) {
        LOGGER.error(format, args);
    }
}
