package me.hypherionmc.craterlib.common.config;

import me.hypherionmc.nightconfig.core.CommentedConfig;
import me.hypherionmc.nightconfig.core.Config;
import me.hypherionmc.nightconfig.core.conversion.ObjectConverter;
import me.hypherionmc.nightconfig.core.file.CommentedFileConfig;

import java.io.File;

/**
 * Base Config class containing the save, upgrading and loading logic. All config classes must extend this class
 */
public class ModuleConfig {

    /* Final Variables */
    private final transient File configPath;
    private final transient String networkID;

    /**
     * Set up the config
     *
     * @param modId      - The ID of the Mod/Module the config belongs to
     * @param configName - The name of the config file, excluding extension
     */
    public ModuleConfig(String modId, String configName) {
        this(modId, "", configName);
    }

    public ModuleConfig(String modId, String subFolder, String configName) {
        /* Preserve the order of the config values */
        Config.setInsertionOrderPreserved(true);

        /* Configure Paths and Network SYNC ID */
        File configDir = new File("config" + (subFolder.isEmpty() ? "" : File.separator + subFolder));
        configPath = new File(configDir + File.separator + configName + ".toml");
        networkID = modId + ":conf_" + configName.replace("-", "_");

        /* Check if the required directories exists, otherwise we create them */
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        /* Register the Config for Watching and events */
        ConfigController.register_config(this);
    }

    /**
     * This method has to be called in the config constructor. This creates or upgrades the config file as needed
     *
     * @param config - The config class to use
     */
    public void registerAndSetup(ModuleConfig config) {
        if (!configPath.exists() || configPath.length() < 10) {
            saveConfig(config);
        } else {
            migrateConfig(config);
        }
    }

    /**
     * Save the config to the disk
     *
     * @param conf - The config class to serialize and save
     */
    public void saveConfig(ModuleConfig conf) {
        /* Set up the Serializer and Config Object */
        ObjectConverter converter = new ObjectConverter();
        CommentedFileConfig config = CommentedFileConfig.builder(configPath).build();

        /* Save the config and fire the reload event */
        converter.toConfig(conf, config);
        config.save();
    }

    /**
     * Load the config from the file into a Class
     *
     * @param conf - The config Class to load
     * @return - Returns the loaded version of the class
     */
    public <T> T loadConfig(Object conf) {
        /* Set up the Serializer and Config Object */
        ObjectConverter converter = new ObjectConverter();
        CommentedFileConfig config = CommentedFileConfig.builder(configPath).build();
        config.load();

        /* Load the config and return the loaded config */
        converter.toObject(config, conf);
        return (T) conf;
    }

    /**
     * INTERNAL METHOD - Upgrades the config files in the event the config structure changes
     *
     * @param conf - The config class to load
     */
    private void migrateConfig(ModuleConfig conf) {
        /* Set up the Serializer and Config Objects */
        CommentedFileConfig config = CommentedFileConfig.builder(configPath).build();
        CommentedFileConfig newConfig = CommentedFileConfig.builder(configPath).build();
        config.load();

        /* Upgrade the config */
        new ObjectConverter().toConfig(conf, newConfig);
        updateConfigValues(config, newConfig, CommentedConfig.copy(newConfig), "");
        newConfig.save();
    }

    private void updateConfigValues(CommentedConfig oldConfig, CommentedConfig newConfig, CommentedConfig outputConfig, String subKey) {
        /* Loop over the config keys and check what has changed */
        newConfig.valueMap().forEach((key, value) -> {
            String finalKey = subKey + (subKey.isEmpty() ? "" : ".") + key;
            if (value instanceof CommentedConfig commentedConfig) {
                updateConfigValues(oldConfig, commentedConfig, outputConfig, finalKey);
            } else {
                outputConfig.set(finalKey,
                        oldConfig.contains(finalKey) ? oldConfig.get(finalKey) : value);
            }
        });
    }

    /**
     * Get the location of the config file
     *
     * @return - The FILE object containing the config file
     */
    public File getConfigPath() {
        return configPath;
    }

    /**
     * Get the NETWORK SYNC ID
     *
     * @return - Returns the Sync ID in format modid:config_name
     */
    public String getNetworkID() {
        return networkID;
    }

    /**
     * Fired whenever changes to the config are detected
     */
    public void configReloaded() {

    }

}
