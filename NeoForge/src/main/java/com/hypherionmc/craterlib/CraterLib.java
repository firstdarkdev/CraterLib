package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.event.client.LateInitEvent;
import com.hypherionmc.craterlib.common.NeoForgeServerEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.network.CraterNeoForgeNetworkHandler;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {

    private final PacketRegistry handler;

    public CraterLib(IEventBus eventBus) {
        NeoForge.EVENT_BUS.register(new NeoForgeServerEvents());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        handler = new CraterNeoForgeNetworkHandler(FMLLoader.getDist().isClient() ? PacketSide.CLIENT : PacketSide.SERVER);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        new CraterPacketNetwork(handler);
        if (FMLEnvironment.dist.isClient()) {
            LateInitEvent event = new LateInitEvent(Minecraft.getInstance(), Minecraft.getInstance().options);
            CraterEventBus.INSTANCE.postEvent(event);
        }
    }
}