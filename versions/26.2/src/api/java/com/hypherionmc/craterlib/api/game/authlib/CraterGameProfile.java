package com.hypherionmc.craterlib.api.game.authlib;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.core.services.CraterServices;

import java.util.UUID;

public interface CraterGameProfile extends CraterWrappedAPI {

    String getName();
    UUID getId();

    static CraterGameProfile fromGame(String name, UUID uuid) {
        return CraterServices.UTILS.createGameProfile(name, uuid);
    }
}
