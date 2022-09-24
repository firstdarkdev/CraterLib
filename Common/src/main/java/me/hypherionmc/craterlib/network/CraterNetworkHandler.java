package me.hypherionmc.craterlib.network;

import me.hypherionmc.craterlib.platform.Platform;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

/**
 * @author HypherionSA
 * @date 24/09/2022
 */
public interface CraterNetworkHandler {

    <T extends CraterPacket<T>> void registerPacket(Class<? extends T> clazz, Supplier<T> supplier, PacketDirection packetDirection);

    Packet<?> toServerBound(CraterPacket<?> packet);

    Packet<?> toClientBound(CraterPacket<?> packet);

    default void sendToServer(CraterPacket<?> packet) {
        Platform.COMMON_HELPER.getClientConnection().send(this.toServerBound(packet));
    }

    default void sendTo(CraterPacket<?> packet, ServerPlayer player) {
        player.connection.send(this.toClientBound(packet));
    }

    default void sendToAll(CraterPacket<?> packet) {
        Platform.COMMON_HELPER.getMCServer().getPlayerList().broadcastAll(this.toClientBound(packet));
    }

}
