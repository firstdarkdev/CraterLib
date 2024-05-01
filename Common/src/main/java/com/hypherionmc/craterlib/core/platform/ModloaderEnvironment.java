package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.utils.InternalServiceUtil;

import java.io.File;

/**
 * @author HypherionSA
 * Helper class to provide information about the ModLoader
 */
public interface ModloaderEnvironment {

    public final ModloaderEnvironment INSTANCE = InternalServiceUtil.load(ModloaderEnvironment.class);

    boolean isFabric();

    String getGameVersion();

    File getGameFolder();

    File getConfigFolder();

    File getModsFolder();

    Environment getEnvironment();

    boolean isModLoaded(String modid);

    boolean isDevEnv();

    int getModCount();
}