package com.hypherionmc.craterlib.core.loader.plugins;

import com.hypherionmc.craterlib.api.loader.CraterLoader;

import java.util.HashSet;
import java.util.Set;

public final class CraterPluginLoader {

    private static boolean hasLoaded = false;

    private static final EntryPointHolder plugins = new EntryPointHolder();
    private static final Set<String> pluginIds = new HashSet<>();

    public static boolean hasLoaded() {
        return hasLoaded;
    }

    public static void loadIfNotLoaded() {
        if (hasLoaded()) return;

        discoverPlugins();
    }

    public static void discoverPlugins() {
        if (hasLoaded)
            throw new RuntimeException("Cannot call discoverPlugins after plugins have already loaded");

        CraterLoader.LOADER_LOGGER.info("Discovering Plugins");
        pluginIds.clear();
        plugins.loadAll(pluginIds);

        CraterLoader.LOADER_LOGGER.info("Discovered {} plugins", pluginIds.size());
        hasLoaded = true;
    }

    public static void initializeEarly() {
        if (pluginIds.isEmpty())
            return;

        CraterLoader.LOADER_LOGGER.info("Loading Early Init Plugins");
        plugins.invokeEarly();
    }

    public static void initializeClientPlugins() {
        if (pluginIds.isEmpty())
            return;

        CraterLoader.LOADER_LOGGER.info("Loading Client Plugins");
        plugins.invokeClient();

        CraterLoader.LOADER_LOGGER.info("Loaded Client Plugins");
    }

    public static void initializeServerPlugins() {
        if (pluginIds.isEmpty())
            return;

        CraterLoader.LOADER_LOGGER.info("Loading Server Plugins");
        plugins.invokeServer();

        CraterLoader.LOADER_LOGGER.info("Loaded Server Plugins");
    }

}
