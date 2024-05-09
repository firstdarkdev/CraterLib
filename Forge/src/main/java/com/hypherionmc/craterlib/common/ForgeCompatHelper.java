package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CompatUtils;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;

public class ForgeCompatHelper implements CompatUtils {

    @Override
    public boolean isPlayerActive(BridgedPlayer player) {
        return true;
    }

    @Override
    public String getSkinUUID(BridgedPlayer player) {
        return player.getStringUUID();
    }
}
