package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.network.NeoForgeNetworkHandler;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
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

    public NeoForgeCommonHelper() {
    }

    @Override
    public BridgedMinecraftServer getMCServer() {
        return BridgedMinecraftServer.of(ServerLifecycleHooks.getCurrentServer());
    }
}
