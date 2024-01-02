package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

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
                ClientPlayNetworking.registerGlobalReceiver(holder.packetId(), ((client, listener, buf, responseSender) -> {
                    buf.readByte();
                    T message = holder.decoder().apply(buf);
                    client.execute(() -> holder.handler().accept(new PacketContext<>(message, PacketSide.CLIENT)));
                }));
            } else {

                ServerPlayNetworking.registerGlobalReceiver(holder.packetId(), ((server, player, listener, buf, responseSender) -> {
                    buf.readByte();
                    T message = holder.decoder().apply(buf);
                    server.execute(() -> holder.handler().accept(new PacketContext<>(player, message, PacketSide.SERVER)));
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

        if (ClientPlayNetworking.canSend(message.id()) || ignoreCheck) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeByte(0);
            message.encoder().accept(packet, buf);
            ClientPlayNetworking.send(message.id(), buf);
        }
    }

    public <T> void sendToClient(T packet, ServerPlayer player) {
        Message<T> message = (Message<T>) CHANNELS.get(packet.getClass());
        if (ServerPlayNetworking.canSend(player, message.id()))
        {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeByte(0);
            message.encoder().accept(packet, buf);
            ServerPlayNetworking.send(player, message.id(), buf);
        }
    }

    public record Message<T>(ResourceLocation id, BiConsumer<T, FriendlyByteBuf> encoder) { }
}
