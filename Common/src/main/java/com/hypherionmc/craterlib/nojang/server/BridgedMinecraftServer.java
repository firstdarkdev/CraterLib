package com.hypherionmc.craterlib.nojang.server;

import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.commands.BridgedFakePlayer;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.SharedConstants;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.server.players.UserWhiteListEntry;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class BridgedMinecraftServer {

    private final MinecraftServer internal;

    public boolean isUsingWhitelist() {
        return internal.getPlayerList().isUsingWhitelist();
    }

    public int getPlayerCount() {
        return internal.getPlayerList().getPlayerCount();
    }

    public int getMaxPlayers() {
        return internal.getPlayerList().getMaxPlayers();
    }

    public String getServerModName() {
        return internal.getServerModName();
    }

    public String getName() {
        return SharedConstants.getCurrentVersion().getName();
    }

    public boolean usesAuthentication() {
        return internal.usesAuthentication();
    }

    public void broadcastSystemMessage(Component text, boolean bl) {
        internal.getPlayerList().broadcastSystemMessage(ChatUtils.adventureToMojang(text), bl);
    }

    public boolean isPlayerBanned(BridgedGameProfile profile) {
        return internal.getPlayerList().getBans().isBanned(profile.toMojang());
    }

    public void whitelistPlayer(BridgedGameProfile gameProfile) {
        if (!internal.getPlayerList().isUsingWhitelist())
            return;

        internal.getPlayerList().getWhiteList().add(new UserWhiteListEntry(gameProfile.toMojang()));
    }

    public void unWhitelistPlayer(BridgedGameProfile gameProfile) {
        if (!internal.getPlayerList().isUsingWhitelist())
            return;

        internal.getPlayerList().getWhiteList().remove(new UserWhiteListEntry(gameProfile.toMojang()));
    }

    public List<BridgedPlayer> getPlayers() {
        List<BridgedPlayer> profiles = new ArrayList<>();

        if (internal.getPlayerList() == null)
            return profiles;

        internal.getPlayerList().getPlayers().forEach(p -> profiles.add(BridgedPlayer.of(p)));

        return profiles;
    }

    public void banPlayer(BridgedGameProfile profile) {
        internal.getPlayerList().getBans().add(new UserBanListEntry(profile.toMojang()));
    }

    public void executeCommand(BridgedMinecraftServer server, BridgedFakePlayer player, String command) {
        internal.getCommands().performPrefixedCommand(player.toMojang(), command);
    }

    public MinecraftServer toMojang() {
        return internal;
    }

}
