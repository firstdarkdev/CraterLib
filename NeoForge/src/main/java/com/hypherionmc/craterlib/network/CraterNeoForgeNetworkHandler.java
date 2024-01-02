package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterNeoForgeNetworkHandler extends PacketRegistry {

    private final Map<Class<?>, NeoForgePacketContainer> PACKETS = new HashMap<>();

    public CraterNeoForgeNetworkHandler(PacketSide side) {
        super(side);
    }

    @SubscribeEvent
    public void register(final RegisterPayloadHandlerEvent event) {
        if (!PACKETS.isEmpty()) {
            PACKETS.forEach((type, container) -> {
                final IPayloadRegistrar registrar = event.registrar(container.packetId().getNamespace());
                registrar.common(
                        container.packetId(),
                        container.decoder(),
                        container.handler());
            });
        }
    }

    protected <T> void registerPacket(PacketHolder<T> container) {
        if (PACKETS.get(container.messageType()) == null) {
            var packetContainer = new NeoForgePacketContainer<>(
                    container.messageType(),
                    container.packetId(),
                    container.encoder(),
                    decoder(container.decoder()),
                    buildHandler(container.handler())
            );

            PACKETS.put(container.messageType(), packetContainer);
        }
    }

    private <T> FriendlyByteBuf.Reader<NeoForgePacket<T>> decoder(Function<FriendlyByteBuf, T> decoder) {
        return (buf -> {
            T packet = decoder.apply(buf);
            return new NeoForgePacket<T>(PACKETS.get(packet.getClass()), packet);
        });
    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        NeoForgePacketContainer<T> container = PACKETS.get(packet.getClass());
        try {
            PacketDistributor.SERVER.noArg().send(new NeoForgePacket<>(container, packet));
        } catch (Throwable t) {
            CraterConstants.LOG.error("{} packet not registered on the client, this is needed.", packet.getClass(), t);
        }
    }

    public <T> void sendToClient(T packet, ServerPlayer player) {
        NeoForgePacketContainer<T> container = PACKETS.get(packet.getClass());
        try {
            if (player.connection.isConnected(container.packetId())) {
                PacketDistributor.PLAYER.with(player).send(new NeoForgePacket<>(container, packet));
            }
        } catch (Throwable t) {
            CraterConstants.LOG.error("{} packet not registered on the server, this is needed.", packet.getClass(), t);
        }
    }

    private <T, K extends NeoForgePacket<T>> IPayloadHandler<K> buildHandler(Consumer<PacketContext<T>> handler) {
        return (payload, ctx) -> {
            try {
                PacketSide side = ctx.flow().getReceptionSide().equals(LogicalSide.SERVER) ? PacketSide.SERVER : PacketSide.CLIENT;
                Player player = ctx.player().orElse(null);
                handler.accept(new PacketContext<>(player, payload.packet(), side));
            } catch (Throwable t) {
                CraterConstants.LOG.error("Error handling packet: {} -> ", payload.packet().getClass(), t);
            }
        };
    }
}