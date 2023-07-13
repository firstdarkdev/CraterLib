package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.api.event.client.CraterClientTickEvent;
import com.hypherionmc.craterlib.api.event.client.LateInitEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.event.annot.CraterEventListener;
import com.hypherionmc.craterlib.core.systems.internal.CreativeTabRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import org.apache.commons.lang3.tuple.Pair;

public class CraterLibClientInitializer implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register((listener) -> {
            CraterClientTickEvent event = new CraterClientTickEvent(listener.level);
            CraterEventBus.INSTANCE.postEvent(event);
        });

        CraterEventBus.INSTANCE.registerEventListener(CraterLibClientInitializer.class);
    }

    @CraterEventListener
    public static void lateInitEvent(LateInitEvent event) {
        CreativeTabRegistry.getTabs().forEach(tab -> {
            CreativeModeTab finalTab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, tab.getResourceKey(), FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup." +
                            tab.getResourceLocation().toString().replace(":", ".")
                    ))
                    .icon(tab.getIcon())
                    .build());

            tab.setTab(finalTab);

            ItemGroupEvents.modifyEntriesEvent(tab.getResourceKey()).register(entries -> CreativeTabRegistry
                    .getTabItems()
                    .stream().filter(t -> t.getLeft().get() == finalTab && t.getRight() != null)
                    .map(Pair::getRight).forEach(itm -> entries.accept(itm.get())));
        });
    }
}
