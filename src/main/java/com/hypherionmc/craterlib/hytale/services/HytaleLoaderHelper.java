package com.hypherionmc.craterlib.hytale.services;

import com.hypherionmc.craterlib.core.platform.Environment;
import com.hypherionmc.craterlib.core.platform.LoaderType;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypixel.hytale.server.core.HytaleServer;

import java.io.File;

public class HytaleLoaderHelper implements ModloaderEnvironment {

    @Override
    public LoaderType getLoaderType() {
        return LoaderType.PAPER;
    }

    @Override
    public String getGameVersion() {
        return HytaleServer.class.getPackage().getImplementationVersion();
    }

    @Override
    public File getGameFolder() {
        return new File(".");
    }

    @Override
    public File getConfigFolder() {
        return new File("./config");
    }

    @Override
    public File getModsFolder() {
        return new File("./mods");
    }

    @Override
    public Environment getEnvironment() {
        return Environment.SERVER;
    }

    @Override
    public boolean isModLoaded(String modid) {
        return false;
    }

    @Override
    public boolean isDevEnv() {
        return false;
    }

    @Override
    public int getModCount() {
        return 0;
    }
}
