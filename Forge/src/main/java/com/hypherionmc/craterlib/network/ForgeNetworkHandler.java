package com.hypherionmc.craterlib.network;

import com.google.common.collect.Maps;
import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.network.CraterPacket;
import com.hypherionmc.craterlib.core.network.PacketDirection;
import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 * Partly inspired by and based on <a href="https://github.com/Fuzss/puzzleslib/blob/1.19/Forge/src/main/java/fuzs/puzzleslib/network/ForgeNetworkHandler.java">...</a>
 */
public class ForgeNetworkHandler implements CraterNetworkHandler {

    private static final Map<String, ForgeNetworkHandler> NETWORK_HANDLERS = Maps.newConcurrentMap();
    private static final String PROTOCOL = Integer.toString(1);

    private final SimpleChannel channel;

    private final boolean clientRequired;

    private final boolean serverRequired;

    private final AtomicInteger packetID = new AtomicInteger();

    private ForgeNetworkHandler(SimpleChannel channel, boolean clientRequired, boolean serverRequired) {
        this.channel = channel;
        this.clientRequired = clientRequired;
        this.serverRequired = serverRequired;
    }

    @Override
    public <T extends CraterPacket<T>> void registerPacket(Class<? extends T> clazz, Supplier<T> supplier, PacketDirection packetDirection) {
        BiConsumer<T, FriendlyByteBuf> encoder = CraterPacket::write;
        Function<FriendlyByteBuf, T> decoder = buf -> {
          T packet = supplier.get();
          packet.read(buf);
          return packet;
        };

        BiConsumer<T, Supplier<NetworkEvent.Context>> handler = (packet, sup) -> {
            NetworkEvent.Context context = sup.get();
            LogicalSide expectedSide = getSideFromDirection(packetDirection);
            LogicalSide currentSide = context.getDirection().getReceptionSide();

            if (expectedSide != currentSide) {
                throw new IllegalStateException(String.format("Received message on wrong side, expected %s, was %s", expectedSide, currentSide));
            }

            context.enqueueWork(() -> {
                Player player;
                if (packetDirection == PacketDirection.TO_CLIENT) {
                    player = ClientPlatform.INSTANCE.getClientPlayer();
                } else {
                    player = context.getSender();
                }
                packet.handle(player, LogicalSidedProvider.WORKQUEUE.get(expectedSide));
            });
            context.setPacketHandled(true);
        };
        this.channel.registerMessage(this.packetID.getAndIncrement(), (Class<T>) clazz, encoder, decoder, handler);
    }

    @Override
    public Packet<?> toServerBound(CraterPacket<?> packet) {
        return this.channel.toVanillaPacket(packet, NetworkDirection.PLAY_TO_SERVER);
    }

    @Override
    public Packet<?> toClientBound(CraterPacket<?> packet) {
        return this.channel.toVanillaPacket(packet, NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public void sendToServer(CraterPacket<?> packet) {
        CraterNetworkHandler.super.sendToServer(packet);
    }

    public synchronized static CraterNetworkHandler of(String modId, boolean clientRequired, boolean serverRequired) {
        ForgeNetworkHandler handler = NETWORK_HANDLERS.computeIfAbsent(modId, modId1 -> new ForgeNetworkHandler(buildSimpleChannel(modId1, clientRequired, serverRequired), clientRequired, serverRequired));
        if (handler.clientRequired != clientRequired) throw new IllegalArgumentException("client channel settings mismatch, expected %s, but was %s".formatted(handler.clientRequired, clientRequired));
        if (handler.serverRequired != serverRequired) throw new IllegalArgumentException("server channel settings mismatch, expected %s, but was %s".formatted(handler.serverRequired, serverRequired));
        return handler;
    }

    private static SimpleChannel buildSimpleChannel(String modId, boolean clientAcceptsVanillaOrMissing, boolean serverAcceptsVanillaOrMissing) {
        return NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(modId, "crater_network"))
                .networkProtocolVersion(() -> PROTOCOL)
                .clientAcceptedVersions(clientAcceptsVanillaOrMissing ? NetworkRegistry.acceptMissingOr(PROTOCOL) : PROTOCOL::equals)
                .serverAcceptedVersions(serverAcceptsVanillaOrMissing ? NetworkRegistry.acceptMissingOr(PROTOCOL) : PROTOCOL::equals)
                .simpleChannel();
    }

    private LogicalSide getSideFromDirection(PacketDirection direction) {
        return direction == PacketDirection.TO_CLIENT ? LogicalSide.CLIENT : LogicalSide.SERVER;
    }
}
