package com.hypherionmc.craterlib.api.util;

import com.hypherionmc.craterlib.core.services.CraterServices;

public interface CraterLogger {

    void trace(String message);
    void trace(String format, Object... args);

    void debug(String message);
    void debug(String format, Object... args);

    void info(String message);
    void info(String format, Object... args);

    void warn(String message);
    void warn(String format, Object... args);

    void error(String error);
    void error(String format, Object... args);

    static CraterLogger getLogger(String name) {
        return CraterServices.UTILS.getLogger(name);
    }

}
