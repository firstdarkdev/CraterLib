package com.hypherionmc.craterlib.core.config.formats;

import me.hypherionmc.moonconfig.core.Config;
import me.hypherionmc.moonconfig.core.conversion.ObjectConverter;
import me.hypherionmc.moonconfig.core.file.FileConfig;

import java.io.File;

public class JsonConfigFormat<S> extends AbstractConfigFormat<S> {

    public JsonConfigFormat(File configPath, Runnable afterSave) {
        super(configPath, afterSave);
    }

    @Override
    public void saveConfig(S conf) {
        ObjectConverter converter = new ObjectConverter();
        FileConfig config = FileConfig.builder(getConfigPath()).sync().build();

        converter.toConfig(conf, config);
        config.save();
        getOnSave().run();
    }

    @Override
    public S readConfig(S conf) {
        /* Set up the Serializer and Config Object */
        ObjectConverter converter = new ObjectConverter();
        FileConfig config = FileConfig.builder(getConfigPath()).build();
        config.load();

        /* Load the config and return the loaded config */
        converter.toObject(config, conf);
        return conf;
    }

    @Override
    public void migrateConfig(S conf) {
        /* Set up the Serializer and Config Objects */
        FileConfig config = FileConfig.builder(getConfigPath()).build();
        FileConfig newConfig = FileConfig.builder(getConfigPath()).sync().build();
        config.load();

        /* Upgrade the config */
        if (wasConfigChanged(config, newConfig)) {
            new ObjectConverter().toConfig(conf, newConfig);
            updateConfigValues(config, newConfig, newConfig, "");
            newConfig.save();
        }

        config.close();
        newConfig.close();
    }

    public void updateConfigValues(Config oldConfig, Config newConfig, Config outputConfig, String subKey) {
        /* Loop over the config keys and check what has changed */
        newConfig.valueMap().forEach((key, value) -> {
            String finalKey = subKey + (subKey.isEmpty() ? "" : ".") + key;
            if (value instanceof Config commentedConfig) {
                updateConfigValues(oldConfig, commentedConfig, outputConfig, finalKey);
            } else {
                outputConfig.set(finalKey,
                        oldConfig.contains(finalKey) ? oldConfig.get(finalKey) : value);
            }
        });
    }
}
