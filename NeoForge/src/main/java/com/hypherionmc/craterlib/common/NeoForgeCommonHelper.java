package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.network.NeoForgeNetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HypherionSA
 */
public class NeoForgeCommonHelper implements CommonPlatform {

    public static Map<ResourceLocation, CreativeModeTab> TABS = new HashMap<>();

    public NeoForgeCommonHelper() {
    }

    @Override
    public CraterNetworkHandler createPacketHandler(String modid, boolean requiredClient, boolean requiredServer) {
        return NeoForgeNetworkHandler.of(modid, requiredClient, requiredServer);
    }

    @Override
    public MinecraftServer getMCServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
