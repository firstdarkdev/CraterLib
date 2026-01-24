package com.hypherionmc.craterlib.api.game.world.level;

public interface CraterGameRules {

    boolean getBoolean(WrappedKey key);
    int getInt(WrappedKey key);

    interface WrappedKey<T> {
        T unwrap(Object resolver);
    }

}
