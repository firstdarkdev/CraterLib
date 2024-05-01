package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import net.minecraft.resources.ResourceLocation;
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
    public BridgedMinecraftServer getMCServer() {
        return BridgedMinecraftServer.of(ServerLifecycleHooks.getCurrentServer());
    }
}
