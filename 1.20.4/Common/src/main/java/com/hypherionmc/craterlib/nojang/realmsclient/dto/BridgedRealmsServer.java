package com.hypherionmc.craterlib.nojang.realmsclient.dto;

import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsServer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class BridgedRealmsServer {

    private final RealmsServer internal;

    public String getName() {
        return internal.getName();
    }

    public String getDescription() {
        return internal.getDescription();
    }

    public String getWorldType() {
        return internal.worldType.name();
    }

    public String getMinigameName() {
        return internal.getMinigameName();
    }

    public String getMinigameImage() {
        return internal.minigameImage;
    }

    public long getPlayerCount() {
        return internal.players.stream().filter(PlayerInfo::getOnline).count();
    }

    public RealmsServer toMojang() {
        return internal;
    }

}
