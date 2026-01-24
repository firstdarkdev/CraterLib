package com.hypherionmc.craterlib.core.config;

import com.hypherionmc.craterlib.api.loader.CraterLoader;
import lombok.Getter;
import me.hypherionmc.moonconfig.core.file.FileWatcher;
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
    private static final HashMap<String, AbstractConfig> watchedConfigs = new HashMap<>();

    private static final FileWatcher watcher = new FileWatcher(e -> CraterLoader.LOGGER.error("Config Watcher Error", e));

    /**
     * INTERNAL METHOD - Register and watch the config
     *
     * @param config - The config class to register and watch
     */
    @ApiStatus.Internal
    public static void register_config(AbstractConfig config) {
        if (watchedConfigs.containsKey(config.getConfigPath().toString())) {
            CraterLoader.LOGGER.error("Failed to register {}. Config already registered", config.getConfigPath().getName());
        } else {
            try {
                watcher.addWatch(config.getConfigPath(), () -> {
                    if (!config.isWasSaveCalled()) {
                        CraterLoader.LOGGER.info("Sending Reload Event for: {}", config.getConfigPath().getName());
                        config.configReloaded();
                    }
                });
            } catch (Exception e) {
                CraterLoader.LOGGER.error("Failed to register {} for auto reloading. {}", config.getConfigPath().getName(), e.getMessage());
            }
            watchedConfigs.put(config.getConfigPath().toString(), config);
            CraterLoader.LOGGER.info("Registered {} successfully!", config.getConfigPath().getName());
        }
    }

}
