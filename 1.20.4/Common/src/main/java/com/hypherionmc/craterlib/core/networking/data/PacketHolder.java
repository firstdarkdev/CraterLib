package com.hypherionmc.craterlib.core.networking.data;

import com.hypherionmc.craterlib.nojang.network.BridgedFriendlyByteBuf;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public record PacketHolder<T>(ResourceIdentifier type,
                              Class<T> messageType,
                              BiConsumer<T, BridgedFriendlyByteBuf> encoder,
                              Function<BridgedFriendlyByteBuf, T> decoder,
                              Consumer<PacketContext<T>> handler) {
}
