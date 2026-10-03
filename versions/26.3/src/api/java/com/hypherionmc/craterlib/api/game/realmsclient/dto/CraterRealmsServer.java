package com.hypherionmc.craterlib.api.game.realmsclient.dto;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

public interface CraterRealmsServer extends CraterWrappedAPI {

    String getName();
    String getDescription();
    String getWorldType();
    String getMinigameName();
    String getMinigameImage();
    long getPlayerCount();

}
