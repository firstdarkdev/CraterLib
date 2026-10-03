package com.hypherionmc.craterlib.api.loader;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;

import static com.hypherionmc.craterlib.core.services.CraterServices.COMPAT_UTILS;

public final class CraterCompat {

    public static boolean isPlayerActive(CraterPlayer player) {
        return COMPAT_UTILS.isPlayerActive(player);
    }

    public static String getSkinUUID(CraterPlayer player) {
        return COMPAT_UTILS.getSkinUUID(player);
    }

    public static boolean isPlayerBleeding(CraterPlayer player) {
        return COMPAT_UTILS.isPlayerBleeding(player);
    }

    public static boolean playerBledOut(CraterPlayer player) {
        return COMPAT_UTILS.playerBledOut(player);
    }

    public static boolean playerRevived(CraterPlayer player) {
        return COMPAT_UTILS.playerRevived(player);
    }

    public static boolean isPrivateMessage(CraterPlayer player) {
        return COMPAT_UTILS.isPrivateMessage(player);
    }

    public static Text getChannelPrefix(CraterPlayer player) {
        return COMPAT_UTILS.getChannelPrefix(player);
    }

    public static boolean isPlayerMuted(CraterPlayer player) {
        return COMPAT_UTILS.isPlayerMuted(player);
    }
}
