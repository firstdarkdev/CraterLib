package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.client.gui.config.ClothConfigScreenBuilder;
import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import com.hypherionmc.craterlib.core.config.AbstractConfig;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.ClothScreen;
import com.hypherionmc.craterlib.core.config.annotations.NoConfigScreen;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
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

        ConfigController.getWatchedConfigs().forEach((conf, watcher) -> {
            AbstractConfig config = watcher.getLeft();
            if (config.getClass().isAnnotationPresent(NoConfigScreen.class))
                return;

            if (watcher.getLeft().getClass().isAnnotationPresent(ClothScreen.class) && (ModloaderEnvironment.INSTANCE.isModLoaded("cloth_config") || ModloaderEnvironment.INSTANCE.isModLoaded("cloth-config") || ModloaderEnvironment.INSTANCE.isModLoaded("clothconfig"))) {
                configScreens.put(config.getModId(), screen -> ClothConfigScreenBuilder.buildConfigScreen(config, screen));
            } else {
                //configScreens.put(config.getModId(), screen -> new CraterConfigScreen(config, screen));
            }
        });

        return configScreens;
    }
}
