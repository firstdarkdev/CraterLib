package com.hypherionmc.craterlib.api.game.network.protocol.status;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

public interface CraterServerStatus {

    interface CraterFavIcon extends CraterWrappedAPI {
        byte[] iconBytes();
    }

}
