package com.hypherionmc.craterlib.core.services;

import com.hypherionmc.craterlib.api.loader.Environment;
import com.hypherionmc.craterlib.api.loader.LoaderType;

import java.io.File;

public interface CraterLoaderEnvironment {

    LoaderType getLoaderType();

    String getGameVersion();

    File getGameFolder();

    File getConfigFolder();

    File getModsFolder();

    Environment getEnvironment();

    boolean isModLoaded(String modid);

    boolean isDevEnv();

    int getModCount();

    int getDataVersion();

}
