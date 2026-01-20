package com.hypherionmc.craterlib.utils;

import com.hypixel.hytale.logger.HytaleLogger;

public class CraterLibLogger {

    private final HytaleLogger logger;

    CraterLibLogger(String name) {
        logger = HytaleLogger.get(name);
    }

    public static CraterLibLogger getLogger(String name) {
        return new CraterLibLogger(name);
    }

    public void error(String message) {
        logger.atSevere().log(message.replace("{}", "%s"));
    }

    public void error(String message, Object... o) {
        logger.atSevere().log(message.replace("{}", "%s"), o);
    }

    public void info(String message) {
        logger.atInfo().log(message.replace("{}", "%s"));
    }

    public void info(String message, Object... o) {
        logger.atInfo().log(message.replace("{}", "%s"), o);
    }

}
