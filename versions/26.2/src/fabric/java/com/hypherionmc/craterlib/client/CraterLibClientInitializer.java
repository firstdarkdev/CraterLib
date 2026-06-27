package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.events.client.CraterClientTickEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.loader.plugins.CraterPluginLoader;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.impl.api.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.network.CraterFabricNetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CraterLibClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CraterConstants.setupLibrary();
        new CraterPacketNetwork(new CraterFabricNetworkHandler(PacketSide.CLIENT));
        ClientTickEvents.START_CLIENT_TICK.register((listener) -> {
            if (listener.level == null)
                return;

            CraterClientTickEvent event = new CraterClientTickEvent(BridgedClientLevel.wrap(listener.level));
            CraterEventBus.INSTANCE.postEvent(event);
        });

        CraterEventBus.INSTANCE.registerEventListener(CraterLibClientInitializer.class);

        CraterPluginLoader.loadIfNotLoaded();
        CraterPluginLoader.initializeClientPlugins();
    }
}
