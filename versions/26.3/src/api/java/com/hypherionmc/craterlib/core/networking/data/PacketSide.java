package com.hypherionmc.craterlib.core.networking.data;

public enum PacketSide {
    CLIENT,
    SERVER;

    public PacketSide flipped() {
        if (CLIENT.equals(this))
            return SERVER;

        return CLIENT;
    }
}