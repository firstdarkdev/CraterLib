package com.hypherionmc.craterlib.core.platform.services;

import java.io.File;

/**
 * @author HypherionSA
 * Helper class to provide information about the ModLoader
 */
public interface ILoaderHelper {

    boolean isFabric();
    String getGameVersion();
    File getGameFolder();
    File getConfigFolder();
    File getModsFolder();
    Environment getEnvironment();
    boolean isModLoaded(String modid);
    boolean isDevEnv();
}
