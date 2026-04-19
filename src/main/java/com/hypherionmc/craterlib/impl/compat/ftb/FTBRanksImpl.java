package com.hypherionmc.craterlib.impl.compat.ftb;

import com.hypherionmc.craterlib.api.compat.ftbranks.FTBRanks;
import com.hypherionmc.craterlib.api.events.compat.FTBRankEvents;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import dev.ftb.mods.ftblibrary.platform.event.NativeEventPosting;
import dev.ftb.mods.ftbranks.api.FTBRanksAPI;
import dev.ftb.mods.ftbranks.api.event.PlayerAddedToRankEvent;
import dev.ftb.mods.ftbranks.api.event.PlayerRemovedFromRankEvent;
import dev.ftb.mods.ftbranks.api.event.RankDeletedEvent;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FTBRanksImpl implements FTBRanks {

    public static final FTBRanksImpl INSTANCE = new FTBRanksImpl();

    private FTBRanksImpl() {}

    public List<FTBRankImpl> getPlayerRanks(CraterGameProfile profile) {
        return FTBRanksAPI.manager().getAddedRanks(profile.unwrap()).stream().map(FTBRankImpl::wrap).toList();
    }

    public List<FTBRankImpl> getAllRanks() {
        return FTBRanksAPI.manager().getAllRanks().stream().map(FTBRankImpl::wrap).toList();
    }

    public boolean hasRank(CraterGameProfile profile, String rank) {
        return getPlayerRanks(profile)
                .stream()
                .anyMatch(r -> r.unwrapInternal().getName().equalsIgnoreCase(rank) || r.unwrapInternal().getId().equalsIgnoreCase(rank));
    }

    public boolean addRank(CraterGameProfile profile, String rank) {
        rank = rank.toLowerCase();

        AtomicBoolean didAddRank = new AtomicBoolean(false);
        FTBRanksAPI.manager().getRank(rank).ifPresent(r -> {
            r.add(profile.unwrap());
            didAddRank.set(true);
        });

        return didAddRank.get();
    }

    public boolean removeRank(CraterGameProfile profile, String rank) {
        rank = rank.toLowerCase();

        AtomicBoolean didRemoveRank = new AtomicBoolean(false);
        FTBRanksAPI.manager().getRank(rank).ifPresent(r -> {
            r.remove(profile.unwrap());
            didRemoveRank.set(true);
        });

        return didRemoveRank.get();
    }

    public void rankDeleted(RankDeletedEvent.Data rankDeletedEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankDeletedEvent.wrap(FTBRankImpl.wrap(rankDeletedEvent.rank())));
    }

    public void playerRemovedFromRank(PlayerRemovedFromRankEvent.Data playerRemovedFromRankEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankRemovedEvent.wrap(BridgedGameProfile.of(playerRemovedFromRankEvent.player()), FTBRankImpl.wrap(playerRemovedFromRankEvent.rank())));
    }

    public void playerAddedToRank(PlayerAddedToRankEvent.Data playerAddedToRankEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankAddedEvent.wrap(BridgedGameProfile.of(playerAddedToRankEvent.player()), FTBRankImpl.wrap(playerAddedToRankEvent.rank())));
    }

}
