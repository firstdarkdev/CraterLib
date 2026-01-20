package com.hypherionmc.craterlib.nojang.server;

import com.hypherionmc.craterlib.hytale.CraterHytalePlugin;
import com.hypherionmc.craterlib.nojang.advancements.BridgedAdvancementHolder;
import com.hypherionmc.craterlib.nojang.advancements.BridgedPlayerAdvancements;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.nojang.commands.BridgedFakePlayer;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.nojang.world.level.BridgedGameRules;
import com.hypherionmc.craterlib.utils.ChatUtils;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.auth.ServerAuthManager;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.universe.Universe;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.*;

@RequiredArgsConstructor(staticName = "of")
public class BridgedMinecraftServer {

    private final HytaleServer internal;

    public boolean isUsingWhitelist() {
        return CraterHytalePlugin.whitelistProvider.isEnabled();
    }

    public int getPlayerCount() {
        return Universe.get().getPlayerCount();
    }

    public int getMaxPlayers() {
        return internal.getConfig().getMaxPlayers();
    }

    public String getServerModName() {
        return internal.getServerName();
    }

    public String getName() {
        return internal.getConfig().getServerName();
    }

    public boolean usesAuthentication() {
        return ServerAuthManager.getInstance().getAuthMode() != ServerAuthManager.AuthMode.NONE;
    }

    public void broadcastSystemMessage(Component text, boolean bl) {
        Universe.get()
                .getWorlds()
                .values()
                .stream()
                .flatMap((world) -> world.getPlayerRefs().stream())
                .forEach((playerRef) -> playerRef.sendMessage(ChatUtils.adventureToMojang(text)));

        ConsoleSender.INSTANCE.sendMessage(ChatUtils.adventureToMojang(text));
    }

    public boolean isPlayerBanned(BridgedGameProfile profile) {
        return CraterHytalePlugin.banProvider.hasBan(profile.getId());
    }

    public void whitelistPlayer(BridgedGameProfile gameProfile) {
        if (!isUsingWhitelist())
            return;

        CraterHytalePlugin.whitelistProvider.modify((list) -> list.add(gameProfile.getId()));
        CraterHytalePlugin.whitelistProvider.syncSave();
    }

    public void unWhitelistPlayer(BridgedGameProfile gameProfile) {
        if (!isUsingWhitelist())
            return;

        CraterHytalePlugin.whitelistProvider.modify((list) -> list.remove(gameProfile.getId()));
        CraterHytalePlugin.whitelistProvider.syncSave();
    }

    public List<BridgedPlayer> getPlayers() {
        return Universe.get().getPlayers()
                .stream()
                .map(p -> Universe.get().getPlayer(p.getUuid()))
                .map(BridgedPlayer::of)
                .toList();
    }

    public BridgedGameRules getGameRules() {
        return BridgedGameRules.bridge();
    }

    public void banPlayer(BridgedGameProfile profile) {
        if (isPlayerBanned(profile)) return;

        // TODO: Implement banning
    }

    public void executeCommand(BridgedMinecraftServer server, BridgedFakePlayer player, String command) {
        CommandManager.get().handleCommand(player.toHytale(), command).exceptionally((throwable -> {
            player.onError(
                    Component.translatable("Failed to execute command: %s".formatted(throwable.getMessage()), command)
            );
            return null;
        }));
    }

    public HytaleServer toHytale() {
        return internal;
    }

    public BridgedPlayerAdvancements getPlayerAdvancements(UUID uuid) {
        return BridgedPlayerAdvancements.of();
    }

    public Collection<BridgedAdvancementHolder> getAdvancements() {
        return new LinkedList<>();
    }

    public boolean isHardcore() {
        return false;
    }
}