package me.hypherionmc.craterlib.platform.services;

import java.io.File;

/**
 * Helper class to provide information about the ModLoader
 */
public interface ILoaderHelper {

    public boolean isFabric();
    public boolean isForge();
    public String getGameVersion();
    public File getGameFolder();
    public File getConfigFolder();
    public File getModsFolder();
    public Environment getEnvironment();
    public boolean isModLoaded(String modid);
    public boolean isDevEnv();
}
