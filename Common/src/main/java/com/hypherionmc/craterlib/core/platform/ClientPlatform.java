package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.platform.services.LibClientHelper;

import java.util.ServiceLoader;

/**
 * @author HypherionSA
 */
public class ClientPlatform {

    public static final LibClientHelper CLIENT_HELPER = load(LibClientHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        CraterConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
