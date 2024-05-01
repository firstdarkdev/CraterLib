package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/**
 * @author HypherionSA
 */
public class NeoForgeCommonHelper implements CommonPlatform {

    public NeoForgeCommonHelper() {
    }

    @Override
    public BridgedMinecraftServer getMCServer() {
        return BridgedMinecraftServer.of(ServerLifecycleHooks.getCurrentServer());
    }
}
