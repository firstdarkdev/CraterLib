package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.api.events.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.events.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.commands.CommandsRegistry;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeServerEvents {

    @SubscribeEvent
    public void serverStarting(ServerStartingEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(BridgedMinecraftServer.of(event.getServer())));
    }

    @SubscribeEvent
    public void serverStarted(ServerStartedEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started(BridgedMinecraftServer.of(event.getServer())));
    }

    @SubscribeEvent
    public void serverStopping(ServerStoppingEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopping(BridgedMinecraftServer.of(event.getServer())));
    }

    @SubscribeEvent
    public void serverStopped(ServerStoppedEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopped(BridgedMinecraftServer.of(event.getServer())));
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent());
        CommandsRegistry.INSTANCE.registerCommands(event.getDispatcher());
    }

}
