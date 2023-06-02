package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.common.FabricCommonPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CraterLibInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricCommonPlatform.server = server);
    }
}
