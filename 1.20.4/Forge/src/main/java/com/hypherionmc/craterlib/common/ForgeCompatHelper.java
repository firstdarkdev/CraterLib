package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CompatUtils;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import redstonedubstep.mods.vanishmod.VanishUtil;

public class ForgeCompatHelper implements CompatUtils {

    @Override
    public boolean isPlayerActive(BridgedPlayer player) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("vmod"))
            return true;

        return !VanishUtil.isVanished(player.toMojangServerPlayer());
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
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("playerrevive"))
            return false;

        IBleeding bleeding = PlayerReviveServer.getBleeding(player.toMojangServerPlayer());
        return bleeding != null && bleeding.revived();
    }
}
