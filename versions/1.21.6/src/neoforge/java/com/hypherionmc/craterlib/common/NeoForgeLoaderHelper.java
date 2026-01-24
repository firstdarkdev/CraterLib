package com.hypherionmc.craterlib.common;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.loader.Environment;
import com.hypherionmc.craterlib.api.loader.LoaderType;
import com.hypherionmc.craterlib.core.services.CraterLoaderEnvironment;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;

/**
 * @author HypherionSA
 */
@AutoService(CraterLoaderEnvironment.class)
public class NeoForgeLoaderHelper implements CraterLoaderEnvironment {


    @Override
    public LoaderType getLoaderType() {
        return LoaderType.NEOFORGE;
    }

    @Override
    public String getGameVersion() {
        return SharedConstants.getCurrentVersion().name();
    }

    @Override
    public File getGameFolder() {
        return Minecraft.getInstance().gameDirectory;
    }

    @Override
    public File getConfigFolder() {
        return FMLPaths.CONFIGDIR.get().toFile();
    }

    @Override
    public File getModsFolder() {
        return FMLPaths.MODSDIR.get().toFile();
    }

    @Override
    public Environment getEnvironment() {
        switch (FMLLoader.getDist()) {
            case CLIENT -> {
                return Environment.CLIENT;
            }
            case DEDICATED_SERVER -> {
                return Environment.SERVER;
            }
        }
        return Environment.UNKNOWN;
    }

    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public boolean isDevEnv() {
        return !FMLLoader.isProduction();
    }

    @Override
    public int getModCount() {
        return ModList.get().size();
    }

    @Override
    public int getDataVersion() {
        return SharedConstants.getCurrentVersion().dataVersion().version();
    }

}
