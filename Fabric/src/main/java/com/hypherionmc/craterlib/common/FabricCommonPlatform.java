package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.network.FabricNetworkHandler;
import net.minecraft.server.MinecraftServer;

/**
 * @author HypherionSA
 */
public class FabricCommonPlatform implements CommonPlatform {

    public static MinecraftServer server;

    @Override
    public CraterNetworkHandler createPacketHandler(String modid, boolean requireClient, boolean requireServer) {
        return FabricNetworkHandler.of(modid);
    }

    @Override
    public MinecraftServer getMCServer() {
        return server;
    }
}
