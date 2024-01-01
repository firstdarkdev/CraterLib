package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.event.client.CraterClientTickEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class CraterLibClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register((listener) -> {
            CraterClientTickEvent event = new CraterClientTickEvent(listener.level);
            CraterEventBus.INSTANCE.postEvent(event);
        });

        CraterEventBus.INSTANCE.registerEventListener(CraterLibClientInitializer.class);
    }
}
