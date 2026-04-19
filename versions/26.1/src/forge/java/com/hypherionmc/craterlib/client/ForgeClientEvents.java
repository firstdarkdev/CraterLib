package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.events.client.CraterClientTickEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.client.multiplayer.BridgedClientLevel;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CraterConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {

    @SubscribeEvent
    public static void clientTick(TickEvent.LevelTickEvent.Pre event) {
        if (Minecraft.getInstance().level == null)
            return;

        CraterClientTickEvent craterClientTickEvent = new CraterClientTickEvent(BridgedClientLevel.wrap(Minecraft.getInstance().level));
        CraterEventBus.INSTANCE.postEvent(craterClientTickEvent);
    }

}
