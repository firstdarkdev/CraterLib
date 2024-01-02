package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.event.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.event.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.common.FabricCommonPlatform;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.network.CraterFabricNetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CraterLibInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        new CraterPacketNetwork(new CraterFabricNetworkHandler(PacketSide.SERVER));
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent(dispatcher)));


        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FabricCommonPlatform.server = server;
            CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(server));
        });

        ServerLifecycleEvents.SERVER_STARTED.register(li -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started()));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopping()));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopped()));
    }
}
