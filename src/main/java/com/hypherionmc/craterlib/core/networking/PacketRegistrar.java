package com.hypherionmc.craterlib.core.networking;

import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.nojang.network.BridgedFriendlyByteBuf;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public interface PacketRegistrar {

    PacketSide side();

    <T> PacketRegistrar registerPacket(ResourceIdentifier id, Class<T> messageType, BiConsumer<T, BridgedFriendlyByteBuf> encoder, Function<BridgedFriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler);

}