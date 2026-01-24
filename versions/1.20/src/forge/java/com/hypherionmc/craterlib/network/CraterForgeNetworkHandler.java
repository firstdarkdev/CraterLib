package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.impl.api.network.BridgedFriendlyByteBuf;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterForgeNetworkHandler extends PacketRegistry {
    private final Map<Class<?>, SimpleChannel> CHANNELS = new HashMap<>();

    public CraterForgeNetworkHandler(PacketSide side) {
        super(side);
    }

    protected <T> void registerPacket(PacketHolder<T> holder) {
        if (CHANNELS.get(holder.getMessageType()) == null) {
            SimpleChannel channel = NetworkRegistry.ChannelBuilder
                    .named(holder.getIdentifier().unwrap())
                    .clientAcceptedVersions((a) -> true)
                    .serverAcceptedVersions((a) -> true)
                    .networkProtocolVersion(() -> "1")
                    .simpleChannel();

            channel.registerMessage(
                    0,
                    holder.getMessageType(),
                    mojangEncoder(holder.getEncoder()),
                    mojangDecoder(holder.getDecoder()),
                    buildHandler(holder.getHandler()));

            CHANNELS.put(holder.getMessageType(), channel);
        } else {
            CraterLoader.LOGGER.error("Trying to register duplicate packet for type {}", holder.getMessageType());
        }
    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        SimpleChannel channel = CHANNELS.get(packet.getClass());
        Connection connection = Minecraft.getInstance().getConnection().getConnection();
        if (channel.isRemotePresent(connection) || ignoreCheck) {
            channel.sendToServer(packet);
        }
    }

    public <T> void sendToClient(T packet, CraterPlayer player) {
        SimpleChannel channel = CHANNELS.get(packet.getClass());
        ServerGamePacketListenerImpl connection = ((ServerPlayer) player.unwrap()).connection;
        if (connection == null)
            return;

        if (channel.isRemotePresent(connection.connection)) {
            channel.sendTo(packet, ((ServerPlayer) player.unwrap()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    private <T> Function<FriendlyByteBuf, T> mojangDecoder(Function<CraterFriendlyByteBuf, T> handler) {
        return byteBuf -> handler.apply(BridgedFriendlyByteBuf.wrap(byteBuf));
    }

    private <T> BiConsumer<T, FriendlyByteBuf> mojangEncoder(BiConsumer<T, CraterFriendlyByteBuf> handler) {
        return ((t, byteBuf) -> handler.accept(t, BridgedFriendlyByteBuf.wrap(byteBuf)));
    }

    private <T> BiConsumer<T, Supplier<NetworkEvent.Context>> buildHandler(Consumer<PacketContext<T>> handler) {
        return (message, ctx) -> {
            ctx.get().enqueueWork(() -> {
                PacketSide side = ctx.get().getDirection().getReceptionSide().isServer() ? PacketSide.SERVER : PacketSide.CLIENT;
                ServerPlayer player = ctx.get().getSender();
                handler.accept(new PacketContext<>(BridgedPlayer.wrap(player), message, side));
            });
            ctx.get().setPacketHandled(true);
        };
    }
}
