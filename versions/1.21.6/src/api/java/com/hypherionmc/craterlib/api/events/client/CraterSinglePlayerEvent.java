package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterSinglePlayerEvent extends CraterEvent {

    private final CraterPlayer player;

    public static class PlayerLogin extends CraterSinglePlayerEvent {

        public PlayerLogin(CraterPlayer player) {
            super(player);
        }

    }
}
