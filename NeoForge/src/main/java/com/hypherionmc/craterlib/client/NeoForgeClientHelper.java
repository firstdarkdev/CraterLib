package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.events.client.LateInitEvent;
import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import com.hypherionmc.craterlib.core.config.AbstractConfig;
import com.hypherionmc.craterlib.core.config.ConfigController;
import com.hypherionmc.craterlib.core.config.annotations.NoConfigScreen;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.BridgedOptions;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
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

        ConfigController.getWatchedConfigs().forEach((conf, watcher) -> {
            if (!conf.getClass().isAnnotationPresent(NoConfigScreen.class)) {
                AbstractConfig config = watcher.getLeft();
                ModList.get().getModContainerById(config.getModId()).ifPresent(c -> c.registerExtensionPoint(IConfigScreenFactory.class, ((minecraft, screen) -> new CraterConfigScreen(config, screen))));
            }
        });
    }
}
