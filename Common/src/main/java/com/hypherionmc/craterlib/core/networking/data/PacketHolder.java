package com.hypherionmc.craterlib.core.networking.data;

import com.hypherionmc.craterlib.api.networking.CommonPacketWrapper;
import com.hypherionmc.craterlib.nojang.network.BridgedFriendlyByteBuf;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public record PacketHolder<T>(CustomPacketPayload.Type<? extends CustomPacketPayload> type,
                              Class<T> messageType,
                              BiConsumer<T, BridgedFriendlyByteBuf> encoder,
                              Function<BridgedFriendlyByteBuf, T> decoder,
                              Consumer<PacketContext<T>> handler) {

    public PacketHolder(ResourceIdentifier packetId, Class<T> messageType, BiConsumer<T, BridgedFriendlyByteBuf> encoder, Function<BridgedFriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler) {
        this(new CustomPacketPayload.Type<>(packetId.toMojang()), messageType, encoder, decoder, handler);
    }

    @SuppressWarnings("unchecked")
    public <K extends CustomPacketPayload> CustomPacketPayload.Type<K> getType()
    {
        return (CustomPacketPayload.Type<K>) type();
    }

    public StreamCodec<FriendlyByteBuf, CommonPacketWrapper> getCodec()
    {
        return CustomPacketPayload.codec(
                (packet, buf) -> this.encoder().accept((T)packet.packet(), BridgedFriendlyByteBuf.of(buf)),
                (buf) -> new CommonPacketWrapper<>(this, this.decoder().apply(BridgedFriendlyByteBuf.of(buf))));
    }

}