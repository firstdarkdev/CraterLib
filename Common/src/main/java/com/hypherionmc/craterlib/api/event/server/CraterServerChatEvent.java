package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CraterServerChatEvent extends CraterEvent {

    private final String message, username;
    private final ServerPlayer player;
    private Component component;

    public CraterServerChatEvent(ServerPlayer player, String message, Component component) {
        this.message = message;
        this.player = player;
        this.username = player.getGameProfile().getName();
        this.component = component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }

    public String getUsername() {
        return username;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean canCancel() {
        return true;
    }
}
