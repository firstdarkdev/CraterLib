package com.hypherionmc.craterlib.core.networking;

import com.hypherionmc.craterlib.api.networking.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public abstract class PacketRegistry implements CraterNetworkHandler, PacketRegistrar {

    final Map<Class<?>, PacketHolder<?>> PACKET_MAP = new HashMap<>();

    protected final PacketSide side;

    public PacketRegistry(PacketSide side) {
        this.side = side;
    }

    public <T> PacketRegistrar registerPacket(ResourceLocation id, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler) {
        PacketHolder<T> holder = new PacketHolder<>(id, messageType, encoder, decoder, handler);
        PACKET_MAP.put(messageType, holder);
        registerPacket(holder);
        return this;
    }

    public PacketSide side() {
        return side;
    }

    protected abstract <T> void registerPacket(PacketHolder<T> packetHolder);
}
