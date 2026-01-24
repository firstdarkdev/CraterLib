package com.hypherionmc.craterlib.impl.api.realmsclient.dto;

import com.hypherionmc.craterlib.api.game.realmsclient.dto.CraterRealmsServer;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsServer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedRealmsServer implements CraterRealmsServer {

    private final RealmsServer internal;

    @Override
    public String getName() {
        return internal.getName();
    }

    @Override
    public String getDescription() {
        return internal.getDescription();
    }

    @Override
    public String getWorldType() {
        return internal.worldType.name();
    }

    @Override
    public String getMinigameName() {
        return internal.getMinigameName();
    }

    @Override
    public String getMinigameImage() {
        return internal.minigameImage;
    }

    @Override
    public long getPlayerCount() {
        return internal.players.stream().filter(PlayerInfo::getOnline).count();
    }

    @Override
    public RealmsServer unwrapInternal() {
        return internal;
    }

}
