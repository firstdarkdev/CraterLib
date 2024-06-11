package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.compat.FabricTailor;
import com.hypherionmc.craterlib.compat.Vanish;
import com.hypherionmc.craterlib.core.platform.CompatUtils;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;

public class FabricCompatHelper implements CompatUtils {

    @Override
    public boolean isPlayerActive(BridgedPlayer player) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("melius-vanish"))
            return true;

        return Vanish.isPlayerVanished(player.toMojangServerPlayer());
    }

    @Override
    public String getSkinUUID(BridgedPlayer player) {
        return FabricTailor.getTailorSkin(player.toMojangServerPlayer());
    }
}
