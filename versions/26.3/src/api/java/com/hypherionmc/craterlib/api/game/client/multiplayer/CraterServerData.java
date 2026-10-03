package com.hypherionmc.craterlib.api.game.client.multiplayer;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.text.Text;

public interface CraterServerData extends CraterWrappedAPI {

    String name();
    String ip();
    Text motd();
    int getMaxPlayers();

}
