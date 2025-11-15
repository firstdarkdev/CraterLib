package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterSinglePlayerEvent extends CraterEvent {

    private final BridgedPlayer player;

    public static class PlayerLogin extends CraterSinglePlayerEvent {

        public PlayerLogin(BridgedPlayer player) {
            super(player);
        }

    }
}
