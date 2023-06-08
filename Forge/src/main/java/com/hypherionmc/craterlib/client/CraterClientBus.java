package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.event.client.LateInitEvent;
import com.hypherionmc.craterlib.core.event.annot.CraterEventListener;
import com.hypherionmc.craterlib.core.systems.internal.CreativeTabRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class CraterClientBus {

    @CraterEventListener
    public static void lateInit(LateInitEvent event) {
        CraterConstants.LOG.info("Registering Creative Tabs");

        CreativeTabRegistry.getTabs().forEach(tab -> {
            CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable("itemGroup." + tab.getResourceLocation().toString().replace(":", ".")));
            builder.icon(tab.getIcon());

            if (!tab.getBackgroundSuffix().isEmpty()) {
                builder.backgroundSuffix(tab.getBackgroundSuffix());
            }

            CreativeModeTab tabb = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, tab.getResourceKey(), builder.build());
            tab.setTab(tabb);
        });
    }

}
