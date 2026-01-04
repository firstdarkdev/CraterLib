package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterPlayerEvent extends CraterEvent {

    private final BridgedPlayer player;

    @Getter
    public static class PlayerLoggedIn extends CraterPlayerEvent {
        private final boolean isFromVanish;

        public PlayerLoggedIn(BridgedPlayer player) {
            this(player, false);
        }

        public PlayerLoggedIn(BridgedPlayer player, boolean isFromVanish) {
            super(player);
            this.isFromVanish = isFromVanish;
        }

    }

    @Getter
    public static class PlayerLoggedOut extends CraterPlayerEvent {
        private final boolean isFromVanish;

        public PlayerLoggedOut(BridgedPlayer player) {
            this(player, false);
        }

        public PlayerLoggedOut(BridgedPlayer player, boolean isFromVanish) {
            super(player);
            this.isFromVanish = isFromVanish;
        }

    }

}
