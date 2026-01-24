package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.events.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.compat.Vanish;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.loader.plugins.CraterPluginLoader;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.impl.api.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.network.CraterFabricNetworkHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CraterLibInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        CraterConstants.setupLibrary();
        new CraterPacketNetwork(new CraterFabricNetworkHandler(PacketSide.SERVER));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent((cmd ) -> dispatcher.register(cmd.unwrap())));
        });


        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            CraterLoader.SERVER = BridgedMinecraftServer.wrap(server);
            CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(BridgedMinecraftServer.wrap(server)));
        });

        ServerLifecycleEvents.SERVER_STARTED.register(li -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started(BridgedMinecraftServer.wrap(li))));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopping(BridgedMinecraftServer.wrap(server))));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopped(BridgedMinecraftServer.wrap(server))));

        if (CraterLoader.isModLoaded("melius-vanish")) {
            Vanish.register();
        }

        CraterPluginLoader.loadIfNotLoaded();
        CraterPluginLoader.initializeServerPlugins();
    }
}
