package com.hypherionmc.craterlib.api.networking;

import com.hypherionmc.craterlib.api.game.server.CraterGameServer;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;

import java.util.List;

/**
 * Based on https://github.com/mysticdrew/common-networking/tree/1.20.4
 */
public interface CraterNetworkHandler {

    <T> void sendToServer(T packet);

    <T> void sendToServer(T packet, boolean ignoreCheck);

    <T> void sendToClient(T packet, CraterPlayer player);

    default <T> void sendToClients(T packet, List<? extends CraterPlayer> players) {
        for (CraterPlayer player : players) {
            sendToClient(packet, player);
        }
    }

    default <T> void sendToAllClients(T packet, CraterGameServer server) {
        sendToClients(packet, server.getPlayers());
    }
}
