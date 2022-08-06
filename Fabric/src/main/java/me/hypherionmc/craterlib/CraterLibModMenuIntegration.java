package me.hypherionmc.craterlib;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import me.hypherionmc.craterlib.common.config.ConfigController;
import me.hypherionmc.craterlib.common.config.ModuleConfig;
import me.hypherionmc.craterlib.common.config.annotations.NoConfigScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HypherionSA
 * @date 06/08/2022
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
