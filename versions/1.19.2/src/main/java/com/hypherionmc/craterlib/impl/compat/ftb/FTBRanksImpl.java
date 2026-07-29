package com.hypherionmc.craterlib.impl.compat.ftb;

import com.hypherionmc.craterlib.api.compat.ftbranks.FTBRanks;
import com.hypherionmc.craterlib.api.events.compat.FTBRankEvents;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import dev.ftb.mods.ftbranks.api.FTBRanksAPI;
import dev.ftb.mods.ftbranks.api.event.PlayerAddedToRankEvent;
import dev.ftb.mods.ftbranks.api.event.PlayerRemovedFromRankEvent;
import dev.ftb.mods.ftbranks.api.event.RankDeletedEvent;
import dev.ftb.mods.ftbranks.api.event.RankEvent;
import net.minecraft.server.level.ServerPlayer;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FTBRanksImpl implements FTBRanks {

    public static final FTBRanksImpl INSTANCE = new FTBRanksImpl();

    private FTBRanksImpl() {
        registerEvents();
    }

    @Override
    public List<FTBRankImpl> getPlayerRanks(CraterGameProfile profile) {
        // Explicitly added ranks via "/ftbranks add player rank"
        return FTBRanksAPI.INSTANCE.getManager().getAddedRanks(profile.unwrap()).stream().map(FTBRankImpl::wrap).toList();
    }

    @Override
    public List<FTBRankImpl> getPlayerRanks(CraterPlayer player) {
        // All ranks the player has. Includes explicitly added ranks and condition-added ranks.
        ServerPlayer serverPlayer = player.unwrap();
        return FTBRanksAPI.INSTANCE.getManager().getRanks(serverPlayer).stream().map(FTBRankImpl::wrap).toList();
    }

    public List<FTBRankImpl> getAllRanks() {
        return FTBRanksAPI.INSTANCE.getManager().getAllRanks().stream().map(FTBRankImpl::wrap).toList();
    }

    @Override
    public boolean hasRank(CraterGameProfile profile, String rank) {
        //Original method for compatibility and for SDLink Discord2Minecraft
        return getPlayerRanks(profile)
                .stream()
                .anyMatch(r -> r.unwrapInternal().getName().equalsIgnoreCase(rank) || r.unwrapInternal().getId().equalsIgnoreCase(rank));
    }
    
    @Override
    public boolean hasRank(CraterPlayer player, String rank) {
        //Updated Method using ServerPlayer
        return getPlayerRanks(player)
                .stream()
                .anyMatch(r -> r.unwrapInternal().getName().equalsIgnoreCase(rank) || r.unwrapInternal().getId().equalsIgnoreCase(rank));
    }

    public boolean addRank(CraterGameProfile profile, String rank) {
        rank = rank.toLowerCase();

        AtomicBoolean didAddRank = new AtomicBoolean(false);
        FTBRanksAPI.INSTANCE.getManager().getRank(rank).ifPresent(r -> {
            r.add(profile.unwrap());
            didAddRank.set(true);
        });

        return didAddRank.get();
    }

    public boolean removeRank(CraterGameProfile profile, String rank) {
        rank = rank.toLowerCase();

        AtomicBoolean didRemoveRank = new AtomicBoolean(false);
        FTBRanksAPI.INSTANCE.getManager().getRank(rank).ifPresent(r -> {
            r.remove(profile.unwrap());
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
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankDeletedEvent.wrap(FTBRankImpl.wrap(rankDeletedEvent.getRank())));
    }

    private void playerRemovedFromRank(PlayerRemovedFromRankEvent playerRemovedFromRankEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankRemovedEvent.wrap(BridgedGameProfile.wrap(playerRemovedFromRankEvent.getPlayer()), FTBRankImpl.wrap(playerRemovedFromRankEvent.getRank())));
    }

    private void playerAddedToRank(PlayerAddedToRankEvent playerAddedToRankEvent) {
        CraterEventBus.INSTANCE.postEvent(FTBRankEvents.RankAddedEvent.wrap(BridgedGameProfile.wrap(playerAddedToRankEvent.getPlayer()), FTBRankImpl.wrap(playerAddedToRankEvent.getRank())));
    }

}
