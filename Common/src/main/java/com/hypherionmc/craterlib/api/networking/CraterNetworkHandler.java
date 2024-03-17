package com.hypherionmc.craterlib.api.networking;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public interface CraterNetworkHandler {

    <T> void sendToServer(T packet);

    <T> void sendToServer(T packet, boolean ignoreCheck);

    <T> void sendToClient(T packet, ServerPlayer player);

    default <T> void sendToClients(T packet, List<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            sendToClient(packet, player);
        }
    }

    default <T> void sendToAllClients(T packet, MinecraftServer server) {
        sendToClients(packet, server.getPlayerList().getPlayers());
    }
}
