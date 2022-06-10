package me.hypherionmc.craterlib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class CraterLibInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        var resources = ResourceManagerHelper.get(PackType.CLIENT_RESOURCES);

    }
}
