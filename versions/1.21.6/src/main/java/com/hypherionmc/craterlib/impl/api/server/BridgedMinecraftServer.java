package com.hypherionmc.craterlib.impl.api.server;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.commands.CraterFakePlayer;
import com.hypherionmc.craterlib.api.game.server.CraterGameServer;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.impl.api.advancements.BridgedAdvancementHolder;
import com.hypherionmc.craterlib.impl.api.advancements.BridgedPlayerAdvancements;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.impl.api.world.level.BridgedGameRules;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.server.players.UserWhiteListEntry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BridgedMinecraftServer implements CraterGameServer {

    private final MinecraftServer internal;

    protected BridgedMinecraftServer(MinecraftServer server) {
        internal = server;
    }

    public static BridgedMinecraftServer wrap(MinecraftServer server) {
        return new BridgedMinecraftServer(server);
    }

    @Override
    public boolean isUsingWhitelist() {
        return internal.getPlayerList().isUsingWhitelist();
    }

    @Override
    public int getPlayerCount() {
        return internal.getPlayerList().getPlayerCount();
    }

    @Override
    public int getMaxPlayers() {
        return internal.getPlayerList().getMaxPlayers();
    }

    @Override
    public String getServerModName() {
        return internal.getServerModName();
    }

    @Override
    public String getName() {
        return SharedConstants.getCurrentVersion().name();
    }

    @Override
    public boolean usesAuthentication() {
        return internal.usesAuthentication();
    }

    @Override
    public void broadcastSystemMessage(Text text, boolean bl) {
        internal.getPlayerList().broadcastSystemMessage(text.toGame(), bl);
    }

    @Override
    public boolean isPlayerBanned(CraterGameProfile profile) {
        return internal.getPlayerList().getBans().isBanned(((BridgedGameProfile) profile).unwrapInternal());
    }

    @Override
    public void whitelistPlayer(CraterGameProfile gameProfile) {
        if (!internal.getPlayerList().isUsingWhitelist())
            return;

        internal.getPlayerList().getWhiteList().add(new UserWhiteListEntry(((BridgedGameProfile) gameProfile).unwrapInternal()));
    }

    @Override
    public void unWhitelistPlayer(CraterGameProfile gameProfile) {
        if (!internal.getPlayerList().isUsingWhitelist())
            return;

        internal.getPlayerList().getWhiteList().remove(new UserWhiteListEntry(((BridgedGameProfile) gameProfile).unwrapInternal()));
    }

    @Override
    public List<BridgedPlayer> getPlayers() {
        if (internal.getPlayerList() == null)
            return Collections.emptyList();

        return internal.getPlayerList().getPlayers().stream().map(BridgedPlayer::wrap).toList();
    }

    @Override
    public BridgedGameRules getGameRules() {
        return BridgedGameRules.bridge(internal.getWorldData().getGameRules());
    }

    @Override
    public void banPlayer(CraterGameProfile profile) {
        internal.getPlayerList().getBans().add(new UserBanListEntry(((BridgedGameProfile) profile).unwrapInternal()));
    }

    @Override
    public void executeCommand(CraterGameServer server, CraterFakePlayer player, String command) {
        internal.getCommands().performPrefixedCommand(player.unwrap(), command);
    }

    @Override
    public MinecraftServer unwrapInternal() {
        return internal;
    }

    @Override
    public BridgedPlayerAdvancements getPlayerAdvancements(UUID uuid) {
        return BridgedPlayerAdvancements.wrap(internal.getPlayerList().getPlayer(uuid).getAdvancements());
    }

    @Override
    public Collection<BridgedAdvancementHolder> getAdvancements() {
        Collection<AdvancementHolder> ah = internal.getAdvancements().getAllAdvancements();
        return ah.stream().map(BridgedAdvancementHolder::wrap).toList();
    }

    @Override
    public boolean isHardcore() {
        return internal.isHardcore();
    }

    @Override
    public boolean isPaused() {
        return internal.isPaused();
    }

    @Override
    public boolean isDedicatedServer() {
        return internal.isDedicatedServer();
    }

    @Override
    public boolean saveEverything(boolean silent, boolean flush, boolean force) {
        return internal.saveEverything(silent, flush, force);
    }
}
