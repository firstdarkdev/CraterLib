package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.events.client.LateInitEvent;
import com.hypherionmc.craterlib.common.ForgeServerEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.network.CraterForgeNetworkHandler;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import com.hypherionmc.craterlib.nojang.client.BridgedOptions;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {
    private final PacketRegistry handler;

    public CraterLib(FMLJavaModLoadingContext context) {
        BusGroup modBusGroup = context.getModBusGroup();

        MinecraftForge.EVENT_BUS.register(new ForgeServerEvents());

        //https://www.reddit.com/r/MinecraftForge/comments/1mqinfr/help_with_mod_coding/
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
        FMLClientSetupEvent.getBus(modBusGroup).addListener(this::clientSetup);

        handler = new CraterForgeNetworkHandler(FMLLoader.getDist().isClient() ? PacketSide.CLIENT : PacketSide.SERVER);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        new CraterPacketNetwork(handler);
    }

    public void clientSetup(FMLClientSetupEvent evt) {
        LateInitEvent event = new LateInitEvent(new BridgedMinecraft(), BridgedOptions.of(Minecraft.getInstance().options));
        CraterEventBus.INSTANCE.postEvent(event);
    }
}
