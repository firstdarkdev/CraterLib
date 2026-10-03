package com.hypherionmc.craterlib.api.loader;

/**
 * @author HypherionSA
 */
public enum Environment {
    CLIENT,
    SERVER,
    UNKNOWN;

    public boolean isClient() {
        return this == CLIENT;
    }

    public boolean isServer() {
        return this == SERVER;
    }
}