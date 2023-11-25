package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.api.event.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.event.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

public class NeoForgeServerEvents {

    @SubscribeEvent
    public void serverStarting(ServerStartingEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(event.getServer()));
    }

    @SubscribeEvent
    public void serverStarted(ServerStartedEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started());
    }

    @SubscribeEvent
    public void serverStopping(ServerStoppingEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopping());
    }

    @SubscribeEvent
    public void serverStopped(ServerStoppedEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopped());
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent(event.getDispatcher()));
    }

}
