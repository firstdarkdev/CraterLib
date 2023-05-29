package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.server.level.ServerPlayer;

public class CraterPlayerEvent extends CraterEvent {

    private final ServerPlayer player;

    public CraterPlayerEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    @Override
    public boolean canCancel() {
        return false;
    }

    public static class PlayerLoggedIn extends CraterPlayerEvent {

        public PlayerLoggedIn(ServerPlayer player) {
            super(player);
        }

    }

    public static class PlayerLoggedOut extends CraterPlayerEvent {

        public PlayerLoggedOut(ServerPlayer player) {
            super(player);
        }

    }
}
