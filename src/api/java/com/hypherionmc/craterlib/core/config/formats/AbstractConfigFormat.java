package com.hypherionmc.craterlib.core.config.formats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.hypherionmc.moonconfig.core.Config;

import java.io.File;

@RequiredArgsConstructor
@Getter
public abstract class AbstractConfigFormat<S> {

    private final File configPath;
    private final Runnable onSave;

    public void register(S conf) {
        if (!configPath.exists() || configPath.length() < 2) {
            saveConfig(conf);
        } else {
            migrateConfig(conf);
        }
    }

    public boolean wasConfigChanged(Config old, Config newConfig) {
        return true;
    }

    public abstract void saveConfig(S config);
    public abstract S readConfig(S config);
    public abstract void migrateConfig(S config);

}
