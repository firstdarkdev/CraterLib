package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.platform.services.ILoaderHelper;
import com.hypherionmc.craterlib.core.platform.services.LibCommonHelper;
import com.hypherionmc.craterlib.core.platform.services.LibFluidHelper;

import java.util.ServiceLoader;

/**
 * @author HypherionSA
 */
public class Platform {

    public static final ILoaderHelper LOADER = load(ILoaderHelper.class);

    public static final LibCommonHelper COMMON_HELPER = load(LibCommonHelper.class);

    public static final LibFluidHelper FLUID_HELPER = load(LibFluidHelper.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        CraterConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
