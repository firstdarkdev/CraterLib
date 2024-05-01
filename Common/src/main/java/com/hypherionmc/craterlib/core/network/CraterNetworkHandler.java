package com.hypherionmc.craterlib.core.network;

import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 */
@Deprecated(forRemoval = true)
public interface CraterNetworkHandler {

    <T extends CraterPacket<T>> void registerPacket(Class<T> clazz, Supplier<T> supplier, PacketDirection packetDirection);

    Packet<?> toServerBound(CraterPacket<?> packet);

    Packet<?> toClientBound(CraterPacket<?> packet);

    default void sendToServer(CraterPacket<?> packet) {
        ClientPlatform.INSTANCE.getClientConnection().send(this.toServerBound(packet));
    }

    default void sendTo(CraterPacket<?> packet, ServerPlayer player) {
        player.connection.send(this.toClientBound(packet));
    }

    default void sendToAll(CraterPacket<?> packet) {
        CommonPlatform.INSTANCE.getMCServer().toMojang().getPlayerList().broadcastAll(this.toClientBound(packet));
    }

}
