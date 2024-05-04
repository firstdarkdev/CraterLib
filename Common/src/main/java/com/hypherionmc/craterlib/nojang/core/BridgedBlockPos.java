package com.hypherionmc.craterlib.nojang.core;

import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;

@RequiredArgsConstructor(staticName = "of")
public class BridgedBlockPos {

    private final BlockPos internal;

    public int getX() {
        return internal.getX();
    }

    public int getY() {
        return internal.getY();
    }

    public int getZ() {
        return internal.getZ();
    }

    public BlockPos toMojang() {
        return internal;
    }

}
