package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.events.client.LateInitEvent;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.common.ForgeServerEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.core.loader.plugins.CraterPluginLoader;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.impl.api.client.BridgedMinecraft;
import com.hypherionmc.craterlib.impl.api.client.BridgedOptions;
import com.hypherionmc.craterlib.network.CraterForgeNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {
    private final PacketRegistry handler;

    public CraterLib() {
        CraterConstants.setupLibrary();
        IEventBus modBusGroup = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(new ForgeServerEvents());

        modBusGroup.addListener(this::commonSetup);
        modBusGroup.addListener(this::clientSetup);

        handler = new CraterForgeNetworkHandler(FMLLoader.getDist().isClient() ? PacketSide.CLIENT : PacketSide.SERVER);

        CraterPluginLoader.loadIfNotLoaded();

        if (CraterLoader.getEnvironment().isClient()) {
            CraterPluginLoader.initializeClientPlugins();
        } else {
            CraterPluginLoader.initializeServerPlugins();
        }
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        new CraterPacketNetwork(handler);
    }

    public void clientSetup(FMLClientSetupEvent evt) {
        LateInitEvent event = new LateInitEvent(new BridgedMinecraft(), BridgedOptions.wrap(Minecraft.getInstance().options));
        CraterEventBus.INSTANCE.postEvent(event);
        CraterPluginLoader.initializeEarly();
    }
}
