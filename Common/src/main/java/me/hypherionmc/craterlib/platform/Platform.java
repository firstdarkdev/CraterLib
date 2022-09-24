package me.hypherionmc.craterlib.platform;

import me.hypherionmc.craterlib.CraterConstants;
import me.hypherionmc.craterlib.platform.services.ILoaderHelper;
import me.hypherionmc.craterlib.platform.services.LibClientHelper;
import me.hypherionmc.craterlib.platform.services.LibCommonHelper;

import java.util.ServiceLoader;

public class Platform {

    public static final ILoaderHelper LOADER = load(ILoaderHelper.class);

    public static final LibClientHelper CLIENT_HELPER = load(LibClientHelper.class);

    public static final LibCommonHelper COMMON_HELPER = load(LibCommonHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        CraterConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}