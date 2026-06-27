package com.hypherionmc.craterlib.core.networking;

import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public interface PacketRegistrar {

    PacketSide side();

    <T> PacketRegistrar registerPacket(CraterIdentifier id, Class<T> messageType, BiConsumer<T, CraterFriendlyByteBuf> encoder, Function<CraterFriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler);

}