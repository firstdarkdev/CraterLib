package com.hypherionmc.craterlib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class CraterLibClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(FabricClientHelper::registerCreativeItems);
    }
}
