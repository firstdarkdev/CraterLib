package com.hypherionmc.craterlib.core.networking.data;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public record PacketHolder<T>(ResourceLocation packetId,
                              Class<T> messageType,
                              BiConsumer<T, FriendlyByteBuf> encoder,
                              Function<FriendlyByteBuf, T> decoder,
                              Consumer<PacketContext<T>> handler) {
}
