package com.hypherionmc.craterlib.api.loader;

import com.hypherionmc.craterlib.api.game.client.CraterGame;
import com.hypherionmc.craterlib.api.game.server.CraterGameServer;
import com.hypherionmc.craterlib.api.util.CraterLogger;
import com.hypherionmc.craterlib.core.services.CraterServices;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import static com.hypherionmc.craterlib.core.services.CraterServices.ENVIRONMENT;

public final class CraterLoader {

    public static CraterLogger LOGGER = CraterLogger.getLogger("CraterLoader");
    public static CraterGameServer SERVER;

    public static LoaderType getLoaderType() {
        return ENVIRONMENT.getLoaderType();
    }

    public static String getGameVersion() {
        return ENVIRONMENT.getGameVersion();
    }

    public static File getGameFolder() {
        return ENVIRONMENT.getGameFolder();
    }

    public static File getConfigFolder() {
        return ENVIRONMENT.getConfigFolder();
    }

    public static File getModsFolder() {
        return ENVIRONMENT.getModsFolder();
    }

    public static Environment getEnvironment() {
        return ENVIRONMENT.getEnvironment();
    }

    public static boolean isModLoaded(String modid) {
        return ENVIRONMENT.isModLoaded(modid);
    }

    public static boolean isDevEnv() {
        return ENVIRONMENT.isDevEnv();
    }

    public static int getModCount() {
        return ENVIRONMENT.getModCount();
    }

    public static int dataVersion() {
        return ENVIRONMENT.getDataVersion();
    }

    public static CraterGame getClient() {
        return CraterServices.UTILS.getGameInstance();
    }

    @Nullable
    public static CraterGameServer getServer() {
        return SERVER;
    }

}
