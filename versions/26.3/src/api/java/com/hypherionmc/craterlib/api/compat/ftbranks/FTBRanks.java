package com.hypherionmc.craterlib.api.compat.ftbranks;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.services.CraterServices;

import java.util.List;

public interface FTBRanks {

    static FTBRanks getInstance() {
        return CraterServices.COMPAT_UTILS.getFTBRanks();
    }

    List<? extends CraterFTBRank> getPlayerRanks(CraterGameProfile profile);
    List<? extends CraterFTBRank> getPlayerRanks(CraterPlayer player);
    List<? extends CraterFTBRank> getAllRanks();
    boolean hasRank(CraterGameProfile profile, String rank);
    boolean hasRank(CraterPlayer player, String rank);
    boolean addRank(CraterGameProfile profile, String rank);
    boolean removeRank(CraterGameProfile profile, String rank);

}
