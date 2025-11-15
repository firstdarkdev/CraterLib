package com.hypherionmc.craterlib.core.config;

import com.hypherionmc.craterlib.core.config.formats.AbstractConfigFormat;
import com.hypherionmc.craterlib.core.config.formats.JsonConfigFormat;
import com.hypherionmc.craterlib.core.config.formats.TomlConfigFormat;
import lombok.Getter;
import lombok.Setter;
import me.hypherionmc.moonconfig.core.Config;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@Getter
public abstract class AbstractConfig<S> {

    /* Final Variables */
    private final transient File configPath;
    private final transient String networkID;
    private final transient String configName;
    private final transient String modId;
    private transient boolean wasSaveCalled = false;

    @Setter
    private transient AbstractConfigFormat<S> configFormat;

    public AbstractConfig(String modId, String configName) {
        this(modId, null, configName);
    }

    public AbstractConfig(String modId, @Nullable String subFolder, String configName) {
        Config.setInsertionOrderPreserved(true);

        if (!configName.endsWith(".toml") && !configName.endsWith(".json"))
            configName = configName + ".toml";

        File configDir = new File("config" + (subFolder == null ? "" : File.separator + subFolder));
        configPath = new File(configDir, configName);
        this.modId = modId;
        this.networkID = modId + ":conf_" + configName.replace(".toml", "").replace(".json", "").replace("-", "_").toLowerCase();
        this.configName = configName.replace(".toml", "").replace(".json", "");
        configDir.mkdirs();

        configFormat = configName.endsWith(".json") ? new JsonConfigFormat<>(configPath, this::onSave) : new TomlConfigFormat<>(configPath, this::onSave);
    }

    public void registerAndSetup(S config) {
        configFormat.register(config);
        ConfigController.register_config(this);
        this.configReloaded();
    }

    public void saveConfig(S config) {
        this.wasSaveCalled = true;
        configFormat.saveConfig(config);
    }

    private void onSave() {
        this.configReloaded();
        this.wasSaveCalled = false;
    }

    public S readConfig(S config) {
        return configFormat.readConfig(config);
    }

    public void migrateConfig(S config) {
        configFormat.migrateConfig(config);
    }

    public abstract void configReloaded();
}
