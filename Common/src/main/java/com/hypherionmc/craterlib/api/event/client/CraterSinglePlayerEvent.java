package com.hypherionmc.craterlib.api.event.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.mojang.realmsclient.dto.RealmsServer;
import net.minecraft.world.entity.player.Player;

public class CraterSinglePlayerEvent extends CraterEvent {

    private final Player player;

    public CraterSinglePlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean canCancel() {
        return false;
    }

    public static class PlayerLogin extends CraterSinglePlayerEvent {

        public PlayerLogin(Player player) {
            super(player);
        }

    }
}
