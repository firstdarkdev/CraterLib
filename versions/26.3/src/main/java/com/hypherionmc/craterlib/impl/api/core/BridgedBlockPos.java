package com.hypherionmc.craterlib.impl.api.core;

import com.hypherionmc.craterlib.api.game.core.CraterBlockPos;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedBlockPos implements CraterBlockPos {

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

    public BlockPos unwrapInternal() {
        return internal;
    }

}
