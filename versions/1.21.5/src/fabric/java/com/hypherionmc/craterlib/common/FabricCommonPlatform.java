package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import net.minecraft.server.MinecraftServer;

/**
 * @author HypherionSA
 */
public class FabricCommonPlatform implements CommonPlatform {

    public static MinecraftServer server;

    @Override
    public BridgedMinecraftServer getMCServer() {
        return BridgedMinecraftServer.of(server);
    }
}
