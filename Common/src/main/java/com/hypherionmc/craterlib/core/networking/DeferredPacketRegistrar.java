package com.hypherionmc.craterlib.core.networking;

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
public class DeferredPacketRegistrar implements PacketRegistrar {

    private static final Map<Class<?>, PacketHolder<?>> QUEUED_PACKET_MAP = new HashMap<>();

    @Override
    public PacketSide side() {
        return PacketSide.CLIENT;
    }

    @Override
    public <T> PacketRegistrar registerPacket(ResourceLocation packetIdentifier, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler) {
        PacketHolder<T> container = new PacketHolder<>(packetIdentifier, messageType, encoder, decoder, handler);
        QUEUED_PACKET_MAP.put(messageType, container);
        return this;
    }


    public void registerQueuedPackets(PacketRegistry packetRegistration) {
        if (!QUEUED_PACKET_MAP.isEmpty()) {
            packetRegistration.PACKET_MAP.putAll(QUEUED_PACKET_MAP);
            QUEUED_PACKET_MAP.forEach((aClass, container) -> packetRegistration.registerPacket(container));
        }
    }
}
