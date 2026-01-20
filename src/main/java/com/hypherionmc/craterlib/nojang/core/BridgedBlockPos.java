package com.hypherionmc.craterlib.nojang.core;

import com.hypixel.hytale.protocol.Position;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class BridgedBlockPos {

    private final Position internal;

    public int getX() {
        return (int) internal.x;
    }

    public int getY() {
        return (int) internal.y;
    }

    public int getZ() {
        return (int) internal.z;
    }

    public Position toHytale() {
        return internal;
    }

}
