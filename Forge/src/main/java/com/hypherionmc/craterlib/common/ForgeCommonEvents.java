package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.systems.internal.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CraterConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeCommonEvents {

    @SubscribeEvent
    public static void registerTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();

        CreativeTabRegistry.getTabItems().stream()
                .filter(p -> p.getLeft().get() == tab && p.getRight() != null)
                .forEach(itemPair -> event.accept(itemPair.getRight()));
    }
}
