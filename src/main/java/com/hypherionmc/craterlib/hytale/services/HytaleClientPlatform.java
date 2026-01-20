package com.hypherionmc.craterlib.hytale.services;

import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;

public class HytaleClientPlatform implements ClientPlatform {

    @Override
    public BridgedMinecraft getClientInstance() {
        return BridgedMinecraft.getInstance();
    }

    @Override
    public BridgedPlayer getClientPlayer() {
        return BridgedMinecraft.getInstance().getPlayer();
    }

    @Override
    public BridgedClientLevel getClientLevel() {
        return BridgedMinecraft.getInstance().getLevel();
    }
}
