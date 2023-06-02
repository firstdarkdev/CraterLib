package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.Environment;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * @author HypherionSA
 * @date 07/08/2022
 */
public class FabricLoaderHelper implements ModloaderEnvironment {

    @Override
    public boolean isFabric() {
        return true;
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
}
