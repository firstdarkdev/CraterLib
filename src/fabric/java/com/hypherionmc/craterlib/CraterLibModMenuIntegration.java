package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.client.gui.config.ClothConfigScreenBuilder;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.ClothScreen;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HypherionSA
 */
public class CraterLibModMenuIntegration implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> configScreens = new HashMap<>();

        ConfigController.getWatchedConfigs().forEach((conf, config) -> {
            if (!config.getClass().isAnnotationPresent(ClothScreen.class))
                return;

            if ((ModloaderEnvironment.INSTANCE.isModLoaded("cloth_config") || ModloaderEnvironment.INSTANCE.isModLoaded("cloth-config") || ModloaderEnvironment.INSTANCE.isModLoaded("clothconfig"))) {
                configScreens.put(config.getModId(), screen -> ClothConfigScreenBuilder.buildConfigScreen(config, screen));
            } else {
                configScreens.put(config.getModId(), screen -> BridgedMinecraft.getInstance().buildWarningScreen(
                        Component.text("Notice").style(Style.style(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)),
                        Component.text("This mod does have a config screen, but requires Cloth Config to be installed."),
                        screen
                ));
            }
        });

        return configScreens;
    }
}
