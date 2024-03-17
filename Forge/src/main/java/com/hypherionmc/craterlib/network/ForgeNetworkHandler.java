package com.hypherionmc.craterlib.network;

import com.google.common.collect.Maps;
import com.hypherionmc.craterlib.core.network.CraterNetworkHandler;
import com.hypherionmc.craterlib.core.network.CraterPacket;
import com.hypherionmc.craterlib.core.network.PacketDirection;
import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 * Partly inspired by and based on <a href="https://github.com/Fuzss/puzzleslib/blob/1.19/Forge/src/main/java/fuzs/puzzleslib/network/ForgeNetworkHandler.java">...</a>
 */
@Deprecated(forRemoval = true)
public class ForgeNetworkHandler implements CraterNetworkHandler {

    private static final Map<String, ForgeNetworkHandler> NETWORK_HANDLERS = Maps.newConcurrentMap();

    private final SimpleChannel channel;

    private final boolean clientRequired;

    private final boolean serverRequired;

    private final AtomicInteger packetID = new AtomicInteger();

    private HashMap<Class<?>, Integer> packetMap = new LinkedHashMap<>();

    private ForgeNetworkHandler(SimpleChannel channel, boolean clientRequired, boolean serverRequired) {
        this.channel = channel;
        this.clientRequired = clientRequired;
        this.serverRequired = serverRequired;
    }

    public synchronized static CraterNetworkHandler of(String modId, boolean clientRequired, boolean serverRequired) {
        ForgeNetworkHandler handler = NETWORK_HANDLERS.computeIfAbsent(modId, modId1 -> new ForgeNetworkHandler(buildSimpleChannel(modId1, clientRequired, serverRequired), clientRequired, serverRequired));
        if (handler.clientRequired != clientRequired)
            throw new IllegalArgumentException("client channel settings mismatch, expected %s, but was %s".formatted(handler.clientRequired, clientRequired));
        if (handler.serverRequired != serverRequired)
            throw new IllegalArgumentException("server channel settings mismatch, expected %s, but was %s".formatted(handler.serverRequired, serverRequired));
        return handler;
    }

    private static SimpleChannel buildSimpleChannel(String modId, boolean clientAcceptsVanillaOrMissing, boolean serverAcceptsVanillaOrMissing) {
        ChannelBuilder builder = ChannelBuilder.named(new ResourceLocation(modId, "crater_network"));

        if (clientAcceptsVanillaOrMissing) {
            builder = builder.optionalClient();
        }

        if (serverAcceptsVanillaOrMissing) {
            builder = builder.optionalServer();
        }

        return builder.simpleChannel();
    }

    @Override
    public <T extends CraterPacket<T>> void registerPacket(Class<T> clazz, Supplier<T> supplier, PacketDirection packetDirection) {
        BiConsumer<T, FriendlyByteBuf> encoder = CraterPacket::write;
        Function<FriendlyByteBuf, T> decoder = buf -> {
            T packet = supplier.get();
            packet.read(buf);
            return packet;
        };

        BiConsumer<T, CustomPayloadEvent.Context> handler = (packet, sup) -> {
            LogicalSide expectedSide = getSideFromDirection(packetDirection);
            LogicalSide currentSide = sup.getDirection().getReceptionSide();

            if (expectedSide != currentSide) {
                throw new IllegalStateException(String.format("Received message on wrong side, expected %s, was %s", expectedSide, currentSide));
            }

            sup.enqueueWork(() -> {
                Player player;
                if (packetDirection == PacketDirection.TO_CLIENT) {
                    player = ClientPlatform.INSTANCE.getClientPlayer();
                } else {
                    player = sup.getSender();
                }
                packet.handle(player, LogicalSidedProvider.WORKQUEUE.get(expectedSide));
            });
            sup.setPacketHandled(true);
        };

        int id = packetID.getAndIncrement();
        packetMap.put(clazz, id);

        this.channel
                .messageBuilder((Class<T>) clazz, id)
                .encoder(encoder)
                .decoder(decoder)
                .consumerNetworkThread(handler)
                .add();
    }

    @Override
    public Packet<?> toServerBound(CraterPacket<?> packet) {
        return NetworkDirection.PLAY_TO_SERVER.buildPacket(toBuffer(packet), channel.getName()).getThis();
        //return this.channel.toVanillaPacket(packet, NetworkDirection.PLAY_TO_SERVER);
    }

    @Override
    public Packet<?> toClientBound(CraterPacket<?> packet) {
        return NetworkDirection.PLAY_TO_CLIENT.buildPacket(toBuffer(packet), channel.getName()).getThis();
        //return this.channel.toVanillaPacket(packet, NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public void sendToServer(CraterPacket<?> packet) {
        CraterNetworkHandler.super.sendToServer(packet);
    }

    private LogicalSide getSideFromDirection(PacketDirection direction) {
        return direction == PacketDirection.TO_CLIENT ? LogicalSide.CLIENT : LogicalSide.SERVER;
    }

    protected FriendlyByteBuf toBuffer(CraterPacket<?> message) {
        var msg = packetMap.get(message.getClass());

        var ret = new FriendlyByteBuf(Unpooled.buffer());
        ret.writeVarInt(msg);
        message.write(ret);
        return ret;
    }
}
