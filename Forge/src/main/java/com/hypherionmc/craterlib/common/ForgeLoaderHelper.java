package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.services.Environment;
import com.hypherionmc.craterlib.core.platform.services.ILoaderHelper;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;

/**
 * @author HypherionSA
 */
public class ForgeLoaderHelper implements ILoaderHelper {

    public ForgeLoaderHelper() {}

    @Override
    public boolean isFabric() {
        return false;
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
}
