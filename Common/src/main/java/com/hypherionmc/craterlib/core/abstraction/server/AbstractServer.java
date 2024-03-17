package com.hypherionmc.craterlib.core.abstraction.server;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;

public class AbstractServer {

    public static void broadcastMessage(MinecraftServer server, MutableComponent message) {
        server.getPlayerList().broadcastSystemMessage(message, false);
    }

    public static void executeCommand(MinecraftServer server, CommandSourceStack stack, String command) {
        server.getCommands().performPrefixedCommand(stack, command);
    }
}
