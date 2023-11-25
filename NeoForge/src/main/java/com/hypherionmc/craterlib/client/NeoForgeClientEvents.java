package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.event.client.CraterClientTickEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = CraterConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class NeoForgeClientEvents {

    @SubscribeEvent
    public static void clientTick(TickEvent.LevelTickEvent event) {
        CraterClientTickEvent craterClientTickEvent = new CraterClientTickEvent(Minecraft.getInstance().level);
        CraterEventBus.INSTANCE.postEvent(craterClientTickEvent);
    }

}