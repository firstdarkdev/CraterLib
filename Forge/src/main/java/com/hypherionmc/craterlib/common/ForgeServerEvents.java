package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.api.event.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.event.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeServerEvents {

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
