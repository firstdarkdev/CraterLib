package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.api.game.network.CraterFriendlyByteBuf;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.impl.api.network.BridgedFriendlyByteBuf;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
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
        if (CHANNELS.get(holder.getMessageType()) == null) {
            CHANNELS.put(holder.getMessageType(), new Message<>(holder.getIdentifier(), holder.getEncoder()));

            if (PacketSide.CLIENT.equals(this.side)) {
                ClientPlayNetworking.registerGlobalReceiver(holder.getIdentifier().unwrap(), ((client, listener, buf, responseSender) -> {
                    buf.readByte();
                    T message = holder.getDecoder().apply(BridgedFriendlyByteBuf.wrap(buf));
                    client.execute(() -> holder.getHandler().accept(new PacketContext<>(message, PacketSide.CLIENT)));
                }));
            } else {

                ServerPlayNetworking.registerGlobalReceiver(holder.getIdentifier().unwrap(), ((server, player, listener, buf, responseSender) -> {
                    buf.readByte();
                    T message = holder.getDecoder().apply(BridgedFriendlyByteBuf.wrap(buf));
                    server.execute(() -> holder.getHandler().accept(new PacketContext<>(BridgedPlayer.wrap(player), message, PacketSide.SERVER)));
                }));
            }

        } else {
            CraterLoader.LOGGER.error("Trying to register duplicate packet for type {}", holder.getMessageType());
        }
    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        Message<T> message = (Message<T>) CHANNELS.get(packet.getClass());

        if (ClientPlayNetworking.canSend(message.id().unwrap()) || ignoreCheck) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeByte(0);
            message.encoder().accept(packet, BridgedFriendlyByteBuf.wrap(buf));
            ClientPlayNetworking.send(message.id().unwrap(), buf);
        }
    }

    public <T> void sendToClient(T packet, CraterPlayer player) {
        Message<T> message = (Message<T>) CHANNELS.get(packet.getClass());
        if (ServerPlayNetworking.canSend((ServerPlayer) player.unwrap(), message.id().unwrap())) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            buf.writeByte(0);
            message.encoder().accept(packet, BridgedFriendlyByteBuf.wrap(buf));
            ServerPlayNetworking.send(player.unwrap(), message.id().unwrap(), buf);
        }
    }

    public record Message<T>(CraterIdentifier id, BiConsumer<T, CraterFriendlyByteBuf> encoder) { }
}
