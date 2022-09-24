package me.hypherionmc.craterlib;

import me.hypherionmc.craterlib.common.FabricCommonHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class CraterLibInitializer implements ModInitializer, PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {

    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> FabricCommonHelper.server = server);
    }
}
