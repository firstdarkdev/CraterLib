package com.hypherionmc.craterlib.core.network;

import com.hypherionmc.craterlib.core.platform.ClientPlatform;
import com.hypherionmc.craterlib.core.platform.Platform;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 */
public interface CraterNetworkHandler {

    <T extends CraterPacket<T>> void registerPacket(Class<? extends T> clazz, Supplier<T> supplier, PacketDirection packetDirection);

    Packet<?> toServerBound(CraterPacket<?> packet);

    Packet<?> toClientBound(CraterPacket<?> packet);

    default void sendToServer(CraterPacket<?> packet) {
        ClientPlatform.CLIENT_HELPER.getClientConnection().send(this.toServerBound(packet));
    }

    default void sendTo(CraterPacket<?> packet, ServerPlayer player) {
        player.connection.send(this.toClientBound(packet));
    }

    default void sendToAll(CraterPacket<?> packet) {
        Platform.COMMON_HELPER.getMCServer().getPlayerList().broadcastAll(this.toClientBound(packet));
    }

}
