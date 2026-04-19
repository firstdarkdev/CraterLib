package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterPlayerEvent extends CraterEvent {

    private final CraterPlayer player;

    @Getter
    public static class PlayerLoggedIn extends CraterPlayerEvent {
        private final boolean isFromVanish;

        public PlayerLoggedIn(CraterPlayer player) {
            this(player, false);
        }

        public PlayerLoggedIn(CraterPlayer player, boolean isFromVanish) {
            super(player);
            this.isFromVanish = isFromVanish;
        }

    }

    @Getter
    public static class PlayerLoggedOut extends CraterPlayerEvent {
        private final boolean isFromVanish;

        public PlayerLoggedOut(CraterPlayer player) {
            this(player, false);
        }

        public PlayerLoggedOut(CraterPlayer player, boolean isFromVanish) {
            super(player);
            this.isFromVanish = isFromVanish;
        }

    }

}
