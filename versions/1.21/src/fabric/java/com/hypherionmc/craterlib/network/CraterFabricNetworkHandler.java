package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.api.networking.CommonPacketWrapper;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterFabricNetworkHandler extends PacketRegistry {

    public CraterFabricNetworkHandler(PacketSide side) {
        super(side);
    }

    protected <T> void registerPacket(PacketHolder<T> holder) {
        try
        {
            PayloadTypeRegistry.playC2S().register(holder.getType(), holder.getCodec());
            PayloadTypeRegistry.playS2C().register(holder.getType(), holder.getCodec());
        }
        catch (IllegalArgumentException e)
        {
            // do nothing
        }

        if (PacketSide.CLIENT.equals(this.side)) {
            ClientPlayNetworking.registerGlobalReceiver(holder.getType(),
                    (ClientPlayNetworking.PlayPayloadHandler<CommonPacketWrapper<T>>) (payload, context) -> context.client().execute(() ->
                            holder.handler().accept(
                                    new PacketContext<>(payload.packet(), side))));
        }

        ServerPlayNetworking.registerGlobalReceiver(holder.getType(),
                (ServerPlayNetworking.PlayPayloadHandler<CommonPacketWrapper<T>>) (payload, context) -> context.player().server.execute(() ->
                        holder.handler().accept(
                                new PacketContext<>(BridgedPlayer.of(context.player()), payload.packet(), side))));
    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        PacketHolder<T> container = (PacketHolder<T>) PACKET_MAP.get(packet.getClass());

        if (container != null) {
            if (ignoreCheck || ClientPlayNetworking.canSend(container.type().id())) {
                ClientPlayNetworking.send(new CommonPacketWrapper<>(container, packet));
            }
        }
    }

    public <T> void sendToClient(T packet, BridgedPlayer player) {
        PacketHolder<T> container = (PacketHolder<T>) PACKET_MAP.get(packet.getClass());
        if (container != null) {
            if (ServerPlayNetworking.canSend(player.toMojangServerPlayer(), container.type().id())) {
                ServerPlayNetworking.send(player.toMojangServerPlayer(), new CommonPacketWrapper<>(container, packet));
            }
        }
    }
}
