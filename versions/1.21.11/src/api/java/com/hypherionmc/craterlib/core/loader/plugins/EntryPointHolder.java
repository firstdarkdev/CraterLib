package com.hypherionmc.craterlib.core.loader.plugins;

import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.api.loader.plugins.entrypoints.CPlugin;
import com.hypherionmc.craterlib.api.loader.plugins.entrypoints.CraterClientPlugin;
import com.hypherionmc.craterlib.api.loader.plugins.entrypoints.CraterEarlyPlugin;
import com.hypherionmc.craterlib.api.loader.plugins.entrypoints.CraterServerPlugin;
import com.hypherionmc.craterlib.api.util.CraterServiceLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class EntryPointHolder {

    private final List<CPlugin> entryPoints = new ArrayList<>();

    void loadAll(Set<String> pluginIdList) {
        entryPoints.clear();
        entryPoints.addAll(CraterServiceLoader.loadAll(CPlugin.class));
        entryPoints.forEach(p -> pluginIdList.add(p.getPluginId()));
    }

    void invokeEarly() {
        entryPoints.stream()
                .filter(CraterEarlyPlugin.class::isInstance)
                .map(CraterEarlyPlugin.class::cast)
                .forEach(p -> safeInvoke(p::onLoad, p));
    }

    void invokeClient() {
        entryPoints.stream()
                .filter(CraterClientPlugin.class::isInstance)
                .map(CraterClientPlugin.class::cast)
                .forEach(p -> safeInvoke(p::onLoadClient, p));
    }

    void invokeServer() {
        entryPoints.stream()
                .filter(CraterServerPlugin.class::isInstance)
                .map(CraterServerPlugin.class::cast)
                .forEach(p -> safeInvoke(p::onLoadServer, p));
    }

    private void safeInvoke(Runnable call, CPlugin plugin) {
        try {
            call.run();
        } catch (Exception e) {
            CraterLoader.LOGGER.error("Failed to initialize plugin {}", plugin.getPluginId(), e);
        }
    }

}
