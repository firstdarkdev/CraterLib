package com.hypherionmc.craterlib.core.services;

import com.hypherionmc.craterlib.api.compat.LuckPermsCompat;
import com.hypherionmc.craterlib.api.compat.ftbranks.FTBRanks;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;

public interface CraterCompatUtils {

    boolean isPlayerActive(CraterPlayer player);
    String getSkinUUID(CraterPlayer player);
    boolean isPlayerBleeding(CraterPlayer player);
    boolean playerBledOut(CraterPlayer player);
    boolean playerRevived(CraterPlayer player);
    boolean isPrivateMessage(CraterPlayer player);
    Text getChannelPrefix(CraterPlayer player);
    boolean isPlayerMuted(CraterPlayer player);
    FTBRanks getFTBRanks();
    LuckPermsCompat getLuckperms();
}
