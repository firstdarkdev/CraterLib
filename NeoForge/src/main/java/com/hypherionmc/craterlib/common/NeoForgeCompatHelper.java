package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CompatUtils;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import redstonedubstep.mods.vanishmod.VanishUtil;

public class NeoForgeCompatHelper implements CompatUtils {

    @Override
    public boolean isPlayerActive(BridgedPlayer player) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("vmod"))
            return true;

        return VanishUtil.isVanished(player.toMojangServerPlayer());
    }

    @Override
    public String getSkinUUID(BridgedPlayer player) {
        return player.getStringUUID();
    }
}
