package com.hypherionmc.craterlib.api.game.server;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.achievements.CraterAchievementHolder;
import com.hypherionmc.craterlib.api.game.achievements.CraterPlayerAdvancements;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.commands.CraterFakePlayer;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.api.game.world.level.CraterGameRules;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CraterGameServer extends CraterWrappedAPI {

    boolean isUsingWhitelist();
    int getPlayerCount();
    int getMaxPlayers();
    String getServerModName();
    String getName();
    boolean usesAuthentication();
    void broadcastSystemMessage(Text text, boolean bl);
    boolean isPlayerBanned(CraterGameProfile profile);
    void whitelistPlayer(CraterGameProfile profile);
    void unWhitelistPlayer(CraterGameProfile profile);
    List<? extends CraterPlayer> getPlayers();
    CraterGameRules getGameRules();
    void banPlayer(CraterGameProfile profile);
    void executeCommand(CraterGameServer server, CraterFakePlayer player, String command);
    CraterPlayerAdvancements getPlayerAdvancements(UUID playerId);
    Collection<? extends CraterAchievementHolder> getAdvancements();
    boolean isHardcore();
    boolean isPaused();
    boolean isDedicatedServer();
    boolean saveEverything(boolean silent, boolean flush, boolean force);

}
