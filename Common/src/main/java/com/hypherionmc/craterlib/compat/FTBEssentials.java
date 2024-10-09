package com.hypherionmc.craterlib.compat;

import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import dev.ftb.mods.ftbessentials.util.FTBEPlayerData;

import java.util.Optional;

public class FTBEssentials {

    public static boolean isPlayerMuted(BridgedPlayer player) {
        Optional<FTBEPlayerData> data = FTBEPlayerData.getOrCreate(player.toMojang());
        return data.map(FTBEPlayerData::isMuted).orElse(false);

    }

}
