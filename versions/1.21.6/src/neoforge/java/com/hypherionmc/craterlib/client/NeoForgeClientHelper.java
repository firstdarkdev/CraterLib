package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.events.client.LateInitEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.client.gui.config.ClothConfigScreenBuilder;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.ClothScreen;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.loader.plugins.CraterPluginLoader;
import com.hypherionmc.craterlib.impl.api.client.BridgedMinecraft;
import com.hypherionmc.craterlib.impl.api.client.BridgedOptions;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * @author HypherionSA
 */
public class NeoForgeClientHelper {
    
    public static void registerClient() {
        CraterPluginLoader.initializeEarly();

        LateInitEvent event = new LateInitEvent(new BridgedMinecraft(), BridgedOptions.wrap(Minecraft.getInstance().options));
        CraterEventBus.INSTANCE.postEvent(event);

        ConfigController.getWatchedConfigs().forEach((conf, config) -> {
            if (!config.getClass().isAnnotationPresent(ClothScreen.class))
                return;

            if ((CraterLoader.isModLoaded("cloth_config") || CraterLoader.isModLoaded("cloth-config") || CraterLoader.isModLoaded("clothconfig"))) {
                ModList.get()
                        .getModContainerById(config.getModId())
                        .ifPresent(c ->
                                c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> ClothConfigScreenBuilder.buildConfigScreen(config, screen))));
            } else {
                ModList.get()
                        .getModContainerById(config.getModId())
                        .ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> ((BridgedMinecraft) CraterLoader.getClient()).buildWarningScreen(
                            Text.literal("Notice").style(Style.style(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)),
                            Text.literal("This mod does have a config screen, but requires Cloth Config to be installed."),
                            screen
                ))));
            }
        });
    }
}
