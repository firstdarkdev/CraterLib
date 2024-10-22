package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.nojang.network.BridgedFriendlyByteBuf;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterForgeNetworkHandler extends PacketRegistry {
    private final Map<Class<?>, SimpleChannel> CHANNELS = new HashMap<>();

    public CraterForgeNetworkHandler(PacketSide side) {
        super(side);
    }

    protected <T> void registerPacket(PacketHolder<T> holder) {
        if (CHANNELS.get(holder.messageType()) == null) {
            SimpleChannel channel = ChannelBuilder
                    .named(holder.type().id())
                    .clientAcceptedVersions((a, b) -> true)
                    .serverAcceptedVersions((a, b) -> true)
                    .networkProtocolVersion(1)
                    .simpleChannel();

            channel.messageBuilder(holder.messageType())
                    .decoder(mojangDecoder(holder.decoder()))
                    .encoder(mojangEncoder(holder.encoder()))
                    .consumerNetworkThread(buildHandler(holder.handler()))
                    .add();

            CHANNELS.put(holder.messageType(), channel);
        } else {
            CraterConstants.LOG.error("Trying to register duplicate packet for type {}", holder.messageType());
        }
    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        SimpleChannel channel = CHANNELS.get(packet.getClass());
        Connection connection = Minecraft.getInstance().getConnection().getConnection();
        if (channel.isRemotePresent(connection) || ignoreCheck) {
            channel.send(packet, PacketDistributor.SERVER.noArg());
        }
    }

    public <T> void sendToClient(T packet, BridgedPlayer player) {
        SimpleChannel channel = CHANNELS.get(packet.getClass());
        ServerGamePacketListenerImpl connection = player.getConnection();
        if (connection == null)
            return;

        if (channel.isRemotePresent(connection.getConnection())) {
            channel.send(packet, PacketDistributor.PLAYER.with(player.toMojangServerPlayer()));
        }
    }

    private <T> Function<FriendlyByteBuf, T> mojangDecoder(Function<BridgedFriendlyByteBuf, T> handler) {
        return byteBuf -> handler.apply(BridgedFriendlyByteBuf.of(byteBuf));
    }

    private <T> BiConsumer<T, FriendlyByteBuf> mojangEncoder(BiConsumer<T, BridgedFriendlyByteBuf> handler) {
        return ((t, byteBuf) -> handler.accept(t, BridgedFriendlyByteBuf.of(byteBuf)));
    }

    private <T> BiConsumer<T, CustomPayloadEvent.Context> buildHandler(Consumer<PacketContext<T>> handler) {
        return (message, ctx) -> {
            ctx.enqueueWork(() -> {
                PacketSide side = ctx.getDirection().getReceptionSide().isServer() ? PacketSide.SERVER : PacketSide.CLIENT;
                ServerPlayer player = ctx.getSender();
                handler.accept(new PacketContext<>(BridgedPlayer.of(player), message, side));
            });
            ctx.setPacketHandled(true);
        };
    }
}