package com.hypherionmc.craterlib;

import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.client.NeoForgeClientHelper;
import com.hypherionmc.craterlib.common.NeoForgeServerEvents;
import com.hypherionmc.craterlib.compat.PlayerReviveEvents;
import com.hypherionmc.craterlib.compat.Vanish;
import com.hypherionmc.craterlib.core.loader.plugins.CraterPluginLoader;
import com.hypherionmc.craterlib.core.networking.CraterPacketNetwork;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.network.CraterNeoForgeNetworkHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CraterConstants.MOD_ID)
public class CraterLib {

    private final PacketRegistry handler;

    public CraterLib(IEventBus eventBus) {
        CraterConstants.setupLibrary();
        NeoForge.EVENT_BUS.register(new NeoForgeServerEvents());
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);
        handler = new CraterNeoForgeNetworkHandler(FMLLoader.getDist().isClient() ? PacketSide.CLIENT : PacketSide.SERVER);

        if (CraterLoader.isModLoaded("vmod")) {
            NeoForge.EVENT_BUS.register(new Vanish());
        }

        if (CraterLoader.isModLoaded("playerrevive")) {
            NeoForge.EVENT_BUS.register(new PlayerReviveEvents());
        }

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
        NeoForgeClientHelper.registerClient();
    }
}
