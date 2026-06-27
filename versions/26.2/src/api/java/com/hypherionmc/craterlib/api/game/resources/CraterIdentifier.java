package com.hypherionmc.craterlib.api.game.resources;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.core.services.CraterServices;

public interface CraterIdentifier extends CraterWrappedAPI {

    String getNamespace();
    String getPath();
    String getString();

    static CraterIdentifier fromGame(String namespace, String path) {
        return CraterServices.UTILS.createIdentifier(namespace, path);
    }

    static CraterIdentifier fromGame(String path) {
        return CraterServices.UTILS.createIdentifier(path, null);
    }

}
