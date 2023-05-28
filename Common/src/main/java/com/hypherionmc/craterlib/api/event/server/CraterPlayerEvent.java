package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.net.SocketAddress;

public class CraterPlayerEvent extends CraterEvent {

    private final Player player;

    public CraterPlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean canCancel() {
        return false;
    }

    public static class PlayerLoggedIn extends CraterPlayerEvent {

        public PlayerLoggedIn(Player player) {
            super(player);
        }

    }

    public static class PlayerLoggedOut extends CraterPlayerEvent {

        public PlayerLoggedOut(Player player) {
            super(player);
        }

    }
}
