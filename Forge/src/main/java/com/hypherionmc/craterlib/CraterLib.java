package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.event.client.LateInitEvent;
import com.hypherionmc.craterlib.client.CraterClientBus;
import com.hypherionmc.craterlib.common.ForgeServerEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {

    public CraterLib() {
        CraterEventBus.INSTANCE.registerEventListener(CraterClientBus.class);
        MinecraftForge.EVENT_BUS.register(new ForgeServerEvents());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            LateInitEvent event = new LateInitEvent(Minecraft.getInstance(), Minecraft.getInstance().options);
            CraterEventBus.INSTANCE.postEvent(event);
        });
    }
}
