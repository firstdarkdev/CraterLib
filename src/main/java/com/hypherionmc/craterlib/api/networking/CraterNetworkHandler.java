package com.hypherionmc.craterlib.api.networking;

import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;

import java.util.List;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public interface CraterNetworkHandler {

    <T> void sendToServer(T packet);

    <T> void sendToServer(T packet, boolean ignoreCheck);

    <T> void sendToClient(T packet, BridgedPlayer player);

    default <T> void sendToClients(T packet, List<BridgedPlayer> players) {
        for (BridgedPlayer player : players) {
            sendToClient(packet, player);
        }
    }

    default <T> void sendToAllClients(T packet, BridgedMinecraftServer server) {
        sendToClients(packet, server.getPlayers());
    }
}
