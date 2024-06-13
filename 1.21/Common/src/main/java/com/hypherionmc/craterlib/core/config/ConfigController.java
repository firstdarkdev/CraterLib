package com.hypherionmc.craterlib.core.config;

import com.hypherionmc.craterlib.CraterConstants;
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
    private static final HashMap<Object, FileWatcher> monitoredConfigs = new HashMap<>();

    /**
     * INTERNAL METHOD - Register and watch the config
     *
     * @param config - The config class to register and watch
     */
    @ApiStatus.Internal
    public static void register_config(ModuleConfig config) {
        if (monitoredConfigs.containsKey(config)) {
            CraterConstants.LOG.error("Failed to register " + config.getConfigPath().getName() + ". Config already registered");
        } else {
            FileWatcher configWatcher = new FileWatcher();
            try {
                configWatcher.setWatch(config.getConfigPath(), () -> {
                    if (!config.isSaveCalled()) {
                        CraterConstants.LOG.info("Sending Reload Event for: " + config.getConfigPath().getName());
                        config.configReloaded();
                    }
                });
            } catch (Exception e) {
                CraterConstants.LOG.error("Failed to register " + config.getConfigPath().getName() + " for auto reloading. " + e.getMessage());
            }
            monitoredConfigs.put(config, configWatcher);
            CraterConstants.LOG.info("Registered " + config.getConfigPath().getName() + " successfully!");
        }
    }

}
