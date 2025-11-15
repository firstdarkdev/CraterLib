// @excludeplugin
package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.compat.ftbranks.BridgedRank;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.mojang.authlib.GameProfile;
import dev.ftb.mods.ftbranks.api.Rank;
import lombok.Getter;

public class FTBRankEvents {

    @Getter
    public static class RankAddedEvent extends CraterEvent {
        private final BridgedGameProfile gameProfile;
        private final BridgedRank rank;

        private RankAddedEvent(BridgedGameProfile gameProfile, BridgedRank rank) {
            this.gameProfile = gameProfile;
            this.rank = rank;
        }

        public static RankAddedEvent of(GameProfile profile, Rank rank) {
            return new RankAddedEvent(BridgedGameProfile.of(profile), BridgedRank.of(rank));
        }
    }

    @Getter
    public static class RankRemovedEvent extends CraterEvent {
        private final BridgedGameProfile gameProfile;
        private final BridgedRank rank;

        private RankRemovedEvent(BridgedGameProfile gameProfile, BridgedRank rank) {
            this.gameProfile = gameProfile;
            this.rank = rank;
        }

        public static RankRemovedEvent of(GameProfile profile, Rank rank) {
            return new RankRemovedEvent(BridgedGameProfile.of(profile), BridgedRank.of(rank));
        }
    }

    @Getter
    public static class RankDeletedEvent extends CraterEvent {
        private final BridgedRank rank;

        private RankDeletedEvent(BridgedRank rank) {
            this.rank = rank;
        }

        public static RankDeletedEvent of(Rank rank) {
            return new RankDeletedEvent(BridgedRank.of(rank));
        }
    }

}
