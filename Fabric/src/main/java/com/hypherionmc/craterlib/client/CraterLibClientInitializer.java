package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.event.client.CraterClientTickEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.network.CraterFabricNetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CraterLibClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new CraterPacketNetwork(new CraterFabricNetworkHandler(PacketSide.CLIENT));
        ClientTickEvents.START_CLIENT_TICK.register((listener) -> {
            CraterClientTickEvent event = new CraterClientTickEvent(listener.level);
            CraterEventBus.INSTANCE.postEvent(event);
        });

        CraterEventBus.INSTANCE.registerEventListener(CraterLibClientInitializer.class);
    }
}
