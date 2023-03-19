package me.hypherionmc.craterlib;

import me.hypherionmc.craterlib.common.FabricCommonHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CraterLibInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricCommonHelper.server = server);
    }
}
