package com.hypherionmc.craterlib.core.abstraction.server;

import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;

public class AbstractServer {

    public static void broadcastMessage(MinecraftServer server, MutableComponent message) {
        server.getPlayerList().broadcastMessage(message, ChatType.CHAT, Util.NIL_UUID);
    }

}
