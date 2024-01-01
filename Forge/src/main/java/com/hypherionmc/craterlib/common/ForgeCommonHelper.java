package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.network.ForgeNetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HypherionSA
 */
public class ForgeCommonHelper implements CommonPlatform {

    public static Map<ResourceLocation, CreativeModeTab> TABS = new HashMap<>();

    public ForgeCommonHelper() {
    }

    @Override
    public CraterNetworkHandler createPacketHandler(String modid, boolean requiredClient, boolean requiredServer) {
        return ForgeNetworkHandler.of(modid, requiredClient, requiredServer);
    }

    @Override
    public MinecraftServer getMCServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
