package com.hypherionmc.craterlib.api.game.resources;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

public interface CraterIdentifier extends CraterWrappedAPI {

    String getNamespace();
    String getPath();
    String getString();

}
