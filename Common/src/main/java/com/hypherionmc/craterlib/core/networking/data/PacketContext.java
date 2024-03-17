package com.hypherionmc.craterlib.core.networking.data;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public record PacketContext<T>(@Nullable Player sender, T message, PacketSide side) {

    public PacketContext(T message, PacketSide side) {
        this(null, message, side);
    }

}
