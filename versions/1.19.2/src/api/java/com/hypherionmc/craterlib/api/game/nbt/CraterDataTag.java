package com.hypherionmc.craterlib.api.game.nbt;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

import java.util.Set;

public interface CraterDataTag extends CraterWrappedAPI {

    static CraterDataTag empty() {
        return null;
    }

    CraterDataTag getCompound(String key);
    Set<String> getAllKeys();
    String getString(String key);
    boolean getBoolean(String key);
    void putString(String key, String value);
    void put(String key, CraterDataTag value);
    void putBoolean(String key, boolean value);

}
