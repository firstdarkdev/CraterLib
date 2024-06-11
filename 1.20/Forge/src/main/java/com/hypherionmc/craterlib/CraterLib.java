package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.events.client.LateInitEvent;
import com.hypherionmc.craterlib.common.ForgeServerEvents;
import com.hypherionmc.craterlib.compat.Vanish;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.network.CraterForgeNetworkHandler;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.BridgedOptions;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {

    public CraterLib() {
        MinecraftForge.EVENT_BUS.register(new ForgeServerEvents());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        new CraterPacketNetwork(new CraterForgeNetworkHandler(FMLLoader.getDist().isClient() ? PacketSide.CLIENT : PacketSide.SERVER));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            LateInitEvent event = new LateInitEvent(new BridgedMinecraft(), BridgedOptions.of(Minecraft.getInstance().options));
            CraterEventBus.INSTANCE.postEvent(event);
        });

        if (ModloaderEnvironment.INSTANCE.isModLoaded("vmod")) {
            MinecraftForge.EVENT_BUS.register(new Vanish());
        }
    }
}
