package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.ModuleConfig;
import com.hypherionmc.craterlib.core.config.annotations.NoConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HypherionSA
 */
public class CraterLibModMenuIntegration implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> configScreens = new HashMap<>();

        ConfigController.getMonitoredConfigs().forEach((conf, watcher) -> {
            if (!conf.getClass().isAnnotationPresent(NoConfigScreen.class)) {
                configScreens.put(((ModuleConfig) conf).getModId(), screen -> new CraterConfigScreen((ModuleConfig) conf, screen));
            }
        });

        return configScreens;
    }
}
