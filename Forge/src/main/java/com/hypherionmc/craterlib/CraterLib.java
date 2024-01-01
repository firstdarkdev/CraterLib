package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.event.client.LateInitEvent;
import com.hypherionmc.craterlib.common.ForgeServerEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {

    public CraterLib() {
        MinecraftForge.EVENT_BUS.register(new ForgeServerEvents());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            LateInitEvent event = new LateInitEvent(Minecraft.getInstance(), Minecraft.getInstance().options);
            CraterEventBus.INSTANCE.postEvent(event);
        });
    }
}
