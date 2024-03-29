package com.hypherionmc.craterlib.core.networking;

import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterPacketNetwork {

    private final PacketRegistry packetRegistry;
    public static CraterPacketNetwork INSTANCE;
    private static DeferredPacketRegistrar delayedHandler;

    public CraterPacketNetwork(PacketRegistry registry) {
        INSTANCE = this;
        this.packetRegistry = registry;
        getDelayedHandler().registerQueuedPackets(registry);
    }

    private static DeferredPacketRegistrar getDelayedHandler() {
        if (delayedHandler == null) {
            delayedHandler = new DeferredPacketRegistrar();
        }
        return delayedHandler;
    }

    public static <T> PacketRegistrar registerPacket(ResourceLocation id, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler) {
        if (INSTANCE != null) {
            return INSTANCE.packetRegistry.registerPacket(id, messageType, encoder, decoder, handler);
        } else {
            return getDelayedHandler().registerPacket(id, messageType, encoder, decoder, handler);
        }
    }

    public PacketRegistry getPacketRegistry() {
        return packetRegistry;
    }
}
