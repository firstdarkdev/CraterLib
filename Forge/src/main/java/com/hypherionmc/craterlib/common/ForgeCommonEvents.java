package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.event.server.CraterRegisterCommandEvent;
import com.hypherionmc.craterlib.api.event.server.CraterServerLifecycleEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.systems.internal.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.*;
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

    @SubscribeEvent
    public static void serverStarting(ServerStartingEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Starting(event.getServer()));
    }

    @SubscribeEvent
    public static void serverStarted(ServerStartedEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Started());
    }

    @SubscribeEvent
    public static void serverStopping(ServerStoppingEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopping());
    }

    @SubscribeEvent
    public static void serverStopped(ServerStoppedEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterServerLifecycleEvent.Stopped());
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        CraterEventBus.INSTANCE.postEvent(new CraterRegisterCommandEvent(event.getDispatcher()));
    }
}
