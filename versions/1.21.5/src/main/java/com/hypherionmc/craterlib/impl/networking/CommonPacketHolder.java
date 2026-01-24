package com.hypherionmc.craterlib.impl.networking;

import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.impl.api.network.BridgedFriendlyByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class CommonPacketHolder<T> extends PacketHolder<T> {

    private final CustomPacketPayload.Type<? extends CustomPacketPayload> type;

    public CommonPacketHolder(CustomPacketPayload.Type<? extends CustomPacketPayload> type, CraterIdentifier identifier, Class<T> messageType, BiConsumer<T, CraterFriendlyByteBuf> encoder, Function<CraterFriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler) {
        super(identifier, messageType, encoder, decoder, handler);
        this.type = type;
    }

    public static <T> CommonPacketHolder<T> wrap(PacketHolder<T> holder) {
        return new CommonPacketHolder<>(
                new CustomPacketPayload.Type<>(holder.getIdentifier().unwrap()),
                holder.getIdentifier(),
                holder.getMessageType(),
                holder.getEncoder(),
                holder.getDecoder(),
                holder.getHandler()
        );
    }

    @SuppressWarnings("unchecked")
    public <K extends CustomPacketPayload> CustomPacketPayload.Type<K> getType() {
        return (CustomPacketPayload.Type<K>) type;
    }

    public StreamCodec<FriendlyByteBuf, CommonPacketWrapper> getCodec() {
        return CustomPacketPayload.codec(
                (packet, buf) -> this.getEncoder().accept((T)packet.packet(), BridgedFriendlyByteBuf.wrap(buf)),
                (buf) -> new CommonPacketWrapper<>(this, this.getDecoder().apply(BridgedFriendlyByteBuf.wrap(buf))));
    }
}
