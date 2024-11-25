// @excludeplugin
package com.hypherionmc.craterlib.compat.ftbranks;

import com.hypherionmc.craterlib.api.events.compat.FTBRankEvents;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import dev.ftb.mods.ftbranks.api.FTBRanksAPI;
import dev.ftb.mods.ftbranks.api.event.PlayerAddedToRankEvent;
import dev.ftb.mods.ftbranks.api.event.PlayerRemovedFromRankEvent;
import dev.ftb.mods.ftbranks.api.event.RankDeletedEvent;
import dev.ftb.mods.ftbranks.api.event.RankEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FTBRanks {

    public static final FTBRanks INSTANCE = new FTBRanks();

    private FTBRanks() {
        registerEvents();
    }

    public List<BridgedRank> getPlayerRanks(BridgedGameProfile profile) {
        List<BridgedRank> ranks = new ArrayList<>();
        FTBRanksAPI.INSTANCE.getManager().getAddedRanks(profile.toMojang()).forEach(rank -> ranks.add(BridgedRank.of(rank)));
        return ranks;
    }

    public List<BridgedRank> getAllRanks() {
        List<BridgedRank> ranks = new ArrayList<>();
        FTBRanksAPI.INSTANCE.getManager().getAllRanks().forEach(r -> ranks.add(BridgedRank.of(r)));
        return ranks;
    }

    public boolean hasRank(BridgedGameProfile profile, String rank) {
        return getPlayerRanks(profile).stream().anyMatch(r -> r.toFtb().getName().equalsIgnoreCase(rank) || r.toFtb().getId().equalsIgnoreCase(rank));
    }

    public boolean addRank(BridgedGameProfile profile, String rank) {
        rank = rank.toLowerCase();

        AtomicBoolean didAddRank = new AtomicBoolean(false);
        FTBRanksAPI.INSTANCE.getManager().getRank(rank).ifPresent(r -> {
            r.add(profile.toMojang());
            didAddRank.set(true);
        });

        return didAddRank.get();
    }

    public boolean removeRank(BridgedGameProfile profile, String rank) {
        rank = rank.toLowerCase();

        AtomicBoolean didRemoveRank = new AtomicBoolean(false);
        FTBRanksAPI.INSTANCE.getManager().getRank(rank).ifPresent(r -> {
            r.remove(profile.toMojang());
            didRemoveRank.set(true);
        });

        return didRemoveRank.get();
    }

    public void registerEvents() {
        RankEvent.ADD_PLAYER.register(this::playerAddedToRank);
        RankEvent.REMOVE_PLAYER.register(this::playerRemovedFromRank);
        RankEvent.DELETED.register(this::rankDeleted);
    }

    private void rankDeleted(RankDeletedEvent rankDeletedEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankDeletedEvent.of(rankDeletedEvent.getRank()));
    }

    private void playerRemovedFromRank(PlayerRemovedFromRankEvent playerRemovedFromRankEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankRemovedEvent.of(playerRemovedFromRankEvent.getPlayer(), playerRemovedFromRankEvent.getRank()));
    }

    private void playerAddedToRank(PlayerAddedToRankEvent playerAddedToRankEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankAddedEvent.of(playerAddedToRankEvent.getPlayer(), playerAddedToRankEvent.getRank()));
    }

}
