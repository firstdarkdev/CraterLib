package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterPlayerEvent extends CraterEvent {

    private final BridgedPlayer player;

    public static class PlayerLoggedIn extends CraterPlayerEvent {

        public PlayerLoggedIn(BridgedPlayer player) {
            super(player);
        }

    }

    public static class PlayerLoggedOut extends CraterPlayerEvent {

        public PlayerLoggedOut(BridgedPlayer player) {
            super(player);
        }

    }

}
