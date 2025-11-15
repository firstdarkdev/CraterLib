package com.hypherionmc.craterlib.core.networking.data;

import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import org.jetbrains.annotations.Nullable;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public record PacketContext<T>(@Nullable BridgedPlayer sender, T message, PacketSide side) {

    public PacketContext(T message, PacketSide side) {
        this(null, message, side);
    }

}