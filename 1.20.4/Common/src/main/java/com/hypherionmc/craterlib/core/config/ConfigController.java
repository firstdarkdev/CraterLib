package com.hypherionmc.craterlib.core.config;

import com.hypherionmc.craterlib.CraterConstants;
import lombok.Getter;
import me.hypherionmc.moonconfig.core.file.FileWatcher;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.ApiStatus;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author HypherionSA
 * Controls Config File Reloads and Events
 */
public final class ConfigController implements Serializable {

    /**
     * Cache of registered configs
     */
    @Getter
    private static final HashMap<String, Pair<AbstractConfig, FileWatcher>> watchedConfigs = new HashMap<>();

    /**
     * INTERNAL METHOD - Register and watch the config
     *
     * @param config - The config class to register and watch
     */
    @ApiStatus.Internal
    @Deprecated
    public static void register_config(ModuleConfig config) {
        register_config((AbstractConfig) config);
    }

    /**
     * INTERNAL METHOD - Register and watch the config
     *
     * @param config - The config class to register and watch
     */
    @ApiStatus.Internal
    public static void register_config(AbstractConfig config) {
        if (watchedConfigs.containsKey(config.getConfigPath().toString())) {
            CraterConstants.LOG.error("Failed to register {}. Config already registered", config.getConfigPath().getName());
        } else {
            FileWatcher configWatcher = new FileWatcher();
            try {
                configWatcher.setWatch(config.getConfigPath(), () -> {
                    if (!config.isWasSaveCalled()) {
                        CraterConstants.LOG.info("Sending Reload Event for: {}", config.getConfigPath().getName());
                        config.configReloaded();
                    }
                });
            } catch (Exception e) {
                CraterConstants.LOG.error("Failed to register {} for auto reloading. {}", config.getConfigPath().getName(), e.getMessage());
            }
            watchedConfigs.put(config.getConfigPath().toString(), Pair.of(config, configWatcher));
            CraterConstants.LOG.info("Registered {} successfully!", config.getConfigPath().getName());
        }
    }

}
