package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.events.client.LateInitEvent;
import com.hypherionmc.craterlib.client.gui.config.ClothConfigScreenBuilder;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.ClothScreen;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.BridgedOptions;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Objects;

/**
 * @author HypherionSA
 */
public class NeoForgeClientHelper implements ClientPlatform {

    public NeoForgeClientHelper() {
    }

    @Override
    public BridgedMinecraft getClientInstance() {
        return new BridgedMinecraft();
    }

    @Override
    public BridgedPlayer getClientPlayer() {
        return BridgedPlayer.of(Minecraft.getInstance().player);
    }

    @Override
    public BridgedClientLevel getClientLevel() {
        return BridgedClientLevel.of(Minecraft.getInstance().level);
    }

    @Override
    public Connection getClientConnection() {
        Objects.requireNonNull(Minecraft.getInstance().getConnection(), "Cannot send packets when not in game!");
        return Minecraft.getInstance().getConnection().getConnection();
    }

    public static void registerClient() {
        LateInitEvent event = new LateInitEvent(new BridgedMinecraft(), BridgedOptions.of(Minecraft.getInstance().options));
        CraterEventBus.INSTANCE.postEvent(event);

        ConfigController.getWatchedConfigs().forEach((conf, config) -> {
            if (!config.getClass().isAnnotationPresent(ClothScreen.class))
                return;

            if ((ModloaderEnvironment.INSTANCE.isModLoaded("cloth_config") || ModloaderEnvironment.INSTANCE.isModLoaded("cloth-config") || ModloaderEnvironment.INSTANCE.isModLoaded("clothconfig"))) {
                ModList.get()
                        .getModContainerById(config.getModId())
                        .ifPresent(c ->
                                c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> ClothConfigScreenBuilder.buildConfigScreen(config, screen))));
            } else {
                ModList.get()
                        .getModContainerById(config.getModId())
                        .ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> BridgedMinecraft.getInstance().buildWarningScreen(
                            Component.text("Notice").style(Style.style(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD)),
                            Component.text("This mod does have a config screen, but requires Cloth Config to be installed."),
                            screen
                ))));
            }
        });
    }
}
