package com.hypherionmc.craterlib.core.networking.data;

import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
@Getter
@RequiredArgsConstructor
public class PacketHolder<T> {

    private final CraterIdentifier identifier;
    private final Class<T> messageType;
    private final BiConsumer<T, CraterFriendlyByteBuf> encoder;
    private final Function<CraterFriendlyByteBuf, T> decoder;
    private final Consumer<PacketContext<T>> handler;

}