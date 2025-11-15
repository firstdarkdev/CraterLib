package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CompatUtils;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.kyori.adventure.text.Component;

public class ForgeCompatHelper implements CompatUtils {

    @Override
    public boolean isPlayerActive(BridgedPlayer player) {
        return true;
    }

    @Override
    public String getSkinUUID(BridgedPlayer player) {
        return player.getStringUUID();
    }

    @Override
    public boolean isPlayerBleeding(BridgedPlayer player) {
        return false;
    }

    @Override
    public boolean playerBledOut(BridgedPlayer player) {
        return false;
    }

    @Override
    public boolean playerRevived(BridgedPlayer player) {
        return false;
    }

    @Override
    public boolean isPrivateMessage(BridgedPlayer player) {
        return false;
    }

    @Override
    public Component getChannelPrefix(BridgedPlayer player) {
        return Component.empty();
    }
}
