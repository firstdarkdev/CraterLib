package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.nojang.network.BridgedFriendlyByteBuf;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterFabricNetworkHandler extends PacketRegistry {

    private final Map<Class<?>, Message<?>> CHANNELS = new HashMap();

    public CraterFabricNetworkHandler(PacketSide side) {
        super(side);
    }

    protected <T> void registerPacket(PacketHolder<T> holder) {
        if (CHANNELS.get(holder.messageType()) == null) {
            CHANNELS.put(holder.messageType(), new Message<>(holder.packetId(), holder.encoder()));

            if (PacketSide.CLIENT.equals(this.side)) {
                ClientPlayNetworking.registerGlobalReceiver(holder.packetId().toMojang(), ((client, listener, buf, responseSender) -> {
                    buf.readByte();
                    T message = holder.decoder().apply(BridgedFriendlyByteBuf.of(buf));
                    client.execute(() -> holder.handler().accept(new PacketContext<>(message, PacketSide.CLIENT)));
                }));
            } else {

                ServerPlayNetworking.registerGlobalReceiver(holder.packetId().toMojang(), ((server, player, listener, buf, responseSender) -> {
                    buf.readByte();
                    T message = holder.decoder().apply(BridgedFriendlyByteBuf.of(buf));
                    server.execute(() -> holder.handler().accept(new PacketContext<>(BridgedPlayer.of(player), message, PacketSide.SERVER)));
                }));
            }

        } else {
            CraterConstants.LOG.error("Trying to register duplicate packet for type {}", holder.messageType());
        }
    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        Message<T> message = (Message<T>) CHANNELS.get(packet.getClass());

        if (ClientPlayNetworking.canSend(message.id().toMojang()) || ignoreCheck) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeByte(0);
            message.encoder().accept(packet, BridgedFriendlyByteBuf.of(buf));
            ClientPlayNetworking.send(message.id().toMojang(), buf);
        }
    }

    public <T> void sendToClient(T packet, BridgedPlayer player) {
        Message<T> message = (Message<T>) CHANNELS.get(packet.getClass());
        if (ServerPlayNetworking.canSend(player.toMojangServerPlayer(), message.id().toMojang()))
        {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeByte(0);
            message.encoder().accept(packet, BridgedFriendlyByteBuf.of(buf));
            ServerPlayNetworking.send(player.toMojangServerPlayer(), message.id().toMojang(), buf);
        }
    }

    public record Message<T>(ResourceIdentifier id, BiConsumer<T, BridgedFriendlyByteBuf> encoder) { }
}
