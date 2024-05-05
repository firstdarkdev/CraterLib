package com.hypherionmc.craterlib.client;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.api.events.client.CraterClientTickEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = CraterConstants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientEvents {

    @SubscribeEvent
    public static void clientTick(LevelTickEvent.Pre event) {
        if (Minecraft.getInstance().level == null)
            return;

        CraterClientTickEvent craterClientTickEvent = new CraterClientTickEvent(BridgedClientLevel.of(Minecraft.getInstance().level));
        CraterEventBus.INSTANCE.postEvent(craterClientTickEvent);
    }

}
