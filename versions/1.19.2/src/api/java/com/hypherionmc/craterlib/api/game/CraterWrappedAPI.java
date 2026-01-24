package com.hypherionmc.craterlib.api.game;

public interface CraterWrappedAPI {

    Object unwrapInternal();

    @SuppressWarnings("unchecked")
    default <T> T unwrap() {
        return (T) unwrapInternal();
    }
}
