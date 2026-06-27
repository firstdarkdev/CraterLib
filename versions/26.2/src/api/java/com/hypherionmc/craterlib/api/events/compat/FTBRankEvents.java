package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.api.compat.ftbranks.CraterFTBRank;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FTBRankEvents {

    @Getter
    @RequiredArgsConstructor(staticName = "wrap")
    public static class RankAddedEvent extends CraterEvent {
        private final CraterGameProfile gameProfile;
        private final CraterFTBRank rank;
    }

    @Getter
    @RequiredArgsConstructor(staticName = "wrap")
    public static class RankRemovedEvent extends CraterEvent {
        private final CraterGameProfile gameProfile;
        private final CraterFTBRank rank;
    }

    @Getter
    @RequiredArgsConstructor(staticName = "wrap")
    public static class RankDeletedEvent extends CraterEvent {
        private final CraterFTBRank rank;
    }

}
