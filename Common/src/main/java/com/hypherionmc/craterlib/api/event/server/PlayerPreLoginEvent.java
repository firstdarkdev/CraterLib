package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;

import java.net.SocketAddress;

public class PlayerPreLoginEvent extends CraterEvent {

    private final SocketAddress address;
    private final GameProfile gameProfile;
    private Component message;

    public PlayerPreLoginEvent(SocketAddress address, GameProfile profile) {
        this.address = address;
        this.gameProfile = profile;
    }

    public Component getMessage() {
        return message;
    }

    public void setMessage(Component message) {
        this.message = message;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public SocketAddress getAddress() {
        return address;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
