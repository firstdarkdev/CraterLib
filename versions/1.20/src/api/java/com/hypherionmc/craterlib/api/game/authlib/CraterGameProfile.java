package com.hypherionmc.craterlib.api.game.authlib;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

import java.util.UUID;

public interface CraterGameProfile extends CraterWrappedAPI {

    String getName();
    UUID getId();

}
