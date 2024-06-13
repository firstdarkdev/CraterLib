package com.hypherionmc.craterlib.core.platform;

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