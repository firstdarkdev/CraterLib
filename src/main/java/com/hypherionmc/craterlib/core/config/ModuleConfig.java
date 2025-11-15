package com.hypherionmc.craterlib.core.config;

import com.hypherionmc.craterlib.core.config.formats.TomlConfigFormat;
import me.hypherionmc.moonconfig.core.CommentedConfig;

import java.io.File;

/**
 * @author HypherionSA
 * Base Config class containing the save, upgrading and loading logic.
 * All config classes must extend this class
 */
@Deprecated(forRemoval = true, since = "2.1.0")
public class ModuleConfig extends AbstractConfig {

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
       super(modId, subFolder.isEmpty() ? null : subFolder, configName);
    }

    /**
     * This method has to be called in the config constructor. This creates or upgrades the config file as needed
     *
     * @param config - The config class to use
     */
    public void registerAndSetup(ModuleConfig config) {
        super.registerAndSetup(config);
    }

    /**
     * Save the config to the disk
     *
     * @param conf - The config class to serialize and save
     */
    public void saveConfig(ModuleConfig conf) {
       super.registerAndSetup(conf);
    }

    /**
     * Load the config from the file into a Class
     *
     * @param conf - The config Class to load
     * @return - Returns the loaded version of the class
     */
    public <T> T loadConfig(Object conf) {
        return (T) super.readConfig(conf);
    }

    /**
     * INTERNAL METHOD - Upgrades the config files in the events the config structure changes
     *
     * @param conf - The config class to load
     */
    public void migrateConfig(ModuleConfig conf) {
        super.migrateConfig(conf);
    }

    public void updateConfigValues(CommentedConfig oldConfig, CommentedConfig newConfig, CommentedConfig outputConfig, String subKey) {
        if (getConfigFormat() instanceof TomlConfigFormat<?> t) {
            t.updateConfigValues(oldConfig, newConfig, outputConfig, subKey);
        }
    }

    /**
     * Get the location of the config file
     *
     * @return - The FILE object containing the config file
     */
    public File getConfigPath() {
        return super.getConfigPath();
    }

    /**
     * Get the NETWORK SYNC ID
     *
     * @return - Returns the Sync ID in format modid:config_name
     */
    public String getNetworkID() {
        return super.getNetworkID();
    }

    /**
     * Fired whenever changes to the config are detected
     */
    @Override
    public void configReloaded() {

    }

    /**
     * Get the name of the Config File
     *
     * @return
     */
    public String getConfigName() {
        return super.getConfigName();
    }

    /**
     * Get the MODID of the Module the config is registered to
     *
     * @return
     */
    public String getModId() {
        return super.getModId();
    }

    public boolean isSaveCalled() {
        return super.isWasSaveCalled();
    }
}
