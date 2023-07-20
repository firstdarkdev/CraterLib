package com.hypherionmc.craterlib.core.abstraction.server;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;

public class AbstractServer {

    public static void broadcastMessage(MinecraftServer server, MutableComponent message) {
        server.getPlayerList().broadcastSystemMessage(message, false);
    }

}
