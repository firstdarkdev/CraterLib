package com.hypherionmc.craterlib.network;

import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.networking.PacketRegistry;
import com.hypherionmc.craterlib.core.networking.data.PacketContext;
import com.hypherionmc.craterlib.core.networking.data.PacketHolder;
import com.hypherionmc.craterlib.core.networking.data.PacketSide;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.impl.networking.CommonPacketHolder;
import com.hypherionmc.craterlib.impl.networking.CommonPacketWrapper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public class CraterFabricNetworkHandler extends PacketRegistry {

    public CraterFabricNetworkHandler(PacketSide side) {
        super(side);
    }

    protected <T> void registerPacket(PacketHolder<T> h) {
        CommonPacketHolder<T> holder = CommonPacketHolder.wrap(h);

        try {
            PayloadTypeRegistry.serverboundPlay().register(holder.getType(), holder.getCodec());
            PayloadTypeRegistry.clientboundPlay().register(holder.getType(), holder.getCodec());
        } catch (IllegalArgumentException e) {
            // do nothing
        }

        if (PacketSide.CLIENT.equals(this.side)) {
            ClientPlayNetworking.registerGlobalReceiver(holder.getType(),
                    (ClientPlayNetworking.PlayPayloadHandler<CommonPacketWrapper<T>>) (payload, context) -> context.client().execute(() ->
                            holder.getHandler().accept(
                                    new PacketContext<>(payload.packet(), side))));
        }

        ServerPlayNetworking.registerGlobalReceiver(holder.getType(),
                (ServerPlayNetworking.PlayPayloadHandler<CommonPacketWrapper<T>>) (payload, context) -> context.server().execute(() ->
                        holder.getHandler().accept(
                                new PacketContext<>(BridgedPlayer.wrap(context.player()), payload.packet(), side))));
    }

    public <T> void sendToServer(T packet) {
        this.sendToServer(packet, false);
    }

    public <T> void sendToServer(T packet, boolean ignoreCheck) {
        PacketHolder<T> c = (PacketHolder<T>) PACKET_MAP.get(packet.getClass());
        CommonPacketHolder<T> container = CommonPacketHolder.wrap(c);

        if (container != null) {
            if (ignoreCheck || ClientPlayNetworking.canSend(container.getType().id())) {
                ClientPlayNetworking.send(new CommonPacketWrapper<>(container, packet));
            }
        }
    }

    public <T> void sendToClient(T packet, CraterPlayer player) {
        PacketHolder<T> c = (PacketHolder<T>) PACKET_MAP.get(packet.getClass());
        CommonPacketHolder<T> container = CommonPacketHolder.wrap(c);

        if (container != null) {
            if (ServerPlayNetworking.canSend((ServerPlayer) player.unwrap(), container.getType().id())) {
                ServerPlayNetworking.send(player.unwrap(), new CommonPacketWrapper<>(container, packet));
            }
        }
    }
}
