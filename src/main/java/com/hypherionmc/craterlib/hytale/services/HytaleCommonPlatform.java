package com.hypherionmc.craterlib.hytale.services;

import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypixel.hytale.server.core.HytaleServer;

public class HytaleCommonPlatform implements CommonPlatform {

    @Override
    public BridgedMinecraftServer getMCServer() {
        return BridgedMinecraftServer.of(HytaleServer.get());
    }
}
