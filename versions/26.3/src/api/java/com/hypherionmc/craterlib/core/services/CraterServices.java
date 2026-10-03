package com.hypherionmc.craterlib.core.services;

import com.hypherionmc.craterlib.api.util.CraterServiceLoader;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class CraterServices {

    public static CraterInternalUtils UTILS = CraterServiceLoader.load(CraterInternalUtils.class);
    public static final CraterLoaderEnvironment ENVIRONMENT = CraterServiceLoader.load(CraterLoaderEnvironment.class);
    public static final CraterCompatUtils COMPAT_UTILS = CraterServiceLoader.load(CraterCompatUtils.class);

}
