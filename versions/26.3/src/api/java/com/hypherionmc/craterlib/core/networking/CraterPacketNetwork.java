package com.hypherionmc.craterlib.core.networking;

import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
@Getter
public class CraterPacketNetwork {

    private final PacketRegistry packetRegistry;
    public static CraterPacketNetwork INSTANCE;
    private static com.hypherionmc.craterlib.core.networking.DeferredPacketRegistrar delayedHandler;

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

    public static <T> PacketRegistrar registerPacket(CraterIdentifier id, Class<T> messageType, BiConsumer<T, CraterFriendlyByteBuf> encoder, Function<CraterFriendlyByteBuf, T> decoder, Consumer<PacketContext<T>> handler) {
        if (INSTANCE != null) {
            return INSTANCE.packetRegistry.registerPacket(id, messageType, encoder, decoder, handler);
        } else {
            return getDelayedHandler().registerPacket(id, messageType, encoder, decoder, handler);
        }
    }

}