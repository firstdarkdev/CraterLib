package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CompatUtils;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.kyori.adventure.text.Component;
import redstonedubstep.mods.vanishmod.VanishUtil;
import team.creative.playerrevive.api.IBleeding;
import team.creative.playerrevive.server.PlayerReviveServer;

public class NeoForgeCompatHelper implements CompatUtils {

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
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("playerrevive"))
            return false;

        return PlayerReviveServer.isBleeding(player.toMojangServerPlayer());
    }

    @Override
    public boolean playerBledOut(BridgedPlayer player) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("playerrevive"))
            return false;

        IBleeding bleeding = PlayerReviveServer.getBleeding(player.toMojangServerPlayer());
        return bleeding != null && bleeding.bledOut();
    }

    @Override
    public boolean playerRevived(BridgedPlayer player) {
        if (!ModloaderEnvironment.INSTANCE.isModLoaded("playerrevive"))
            return false;

        IBleeding bleeding = PlayerReviveServer.getBleeding(player.toMojangServerPlayer());
        return bleeding != null && bleeding.revived();
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
