package com.hypherionmc.craterlib.common;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.loader.Environment;
import com.hypherionmc.craterlib.api.loader.LoaderType;
import com.hypherionmc.craterlib.core.services.CraterLoaderEnvironment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * @author HypherionSA
 */
@AutoService(CraterLoaderEnvironment.class)
public class FabricLoaderHelper implements CraterLoaderEnvironment {

    @Override
    public LoaderType getLoaderType() {
        return LoaderType.FABRIC;
    }

    @Override
    public String getGameVersion() {
        return SharedConstants.VERSION_STRING;
    }

    @Override
    public File getGameFolder() {
        return Minecraft.getInstance().gameDirectory;
    }

    @Override
    public File getConfigFolder() {
        return FabricLoader.getInstance().getConfigDir().toFile();
    }

    @Override
    public File getModsFolder() {
        return new File(FabricLoader.getInstance().getGameDir().toString() + File.separator + "mods");
    }

    @Override
    public Environment getEnvironment() {
        switch (FabricLoader.getInstance().getEnvironmentType()) {
            case SERVER -> {
                return Environment.SERVER;
            }
            case CLIENT -> {
                return Environment.CLIENT;
            }
        }
        return Environment.UNKNOWN;
    }

    @Override
    public boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public boolean isDevEnv() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public int getModCount() {
        return FabricLoader.getInstance().getAllMods().size();
    }

    @Override
    public int getDataVersion() {
        return SharedConstants.getCurrentVersion().getDataVersion().getVersion();
    }
}
