package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.systems.internal.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod.EventBusSubscriber(modid = CraterConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NeoForgeCommonEvents {

    @SubscribeEvent
    public static void registerTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();

        CreativeTabRegistry.getTabItems().stream()
                .filter(p -> p.getLeft().get() == tab && p.getRight() != null)
                .forEach(itemPair -> event.accept(itemPair.getRight().get()));
    }
}
