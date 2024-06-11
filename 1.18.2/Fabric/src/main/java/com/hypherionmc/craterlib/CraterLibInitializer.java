package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.events.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.common.FabricCommonPlatform;
import com.hypherionmc.craterlib.compat.Vanish;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.network.CraterFabricNetworkHandler;
import com.hypherionmc.craterlib.nojang.commands.CommandsRegistry;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CraterLibInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        new CraterPacketNetwork(new CraterFabricNetworkHandler(PacketSide.SERVER));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent());
            CommandsRegistry.INSTANCE.registerCommands(dispatcher);
        });


        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            FabricCommonPlatform.server = server;
            CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(BridgedMinecraftServer.of(server)));
        });

        ServerLifecycleEvents.SERVER_STARTED.register(li -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started(BridgedMinecraftServer.of(li))));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopping(BridgedMinecraftServer.of(server))));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopped(BridgedMinecraftServer.of(server))));

        if (ModloaderEnvironment.INSTANCE.isModLoaded("melius-vanish")) {
            Vanish.register();
        }
    }
}
