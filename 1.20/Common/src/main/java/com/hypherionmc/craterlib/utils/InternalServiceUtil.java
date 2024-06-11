package com.hypherionmc.craterlib.utils;

import com.hypherionmc.craterlib.CraterConstants;

import java.util.ServiceLoader;

/**
 * @author HypherionSA
 * Utility class to handle SPI loading
 */
public class InternalServiceUtil {

    /**
     * Try to load a service
     *
     * @param clazz The service class type to load
     * @return The loaded class
     */
    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        CraterConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

}