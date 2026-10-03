package com.hypherionmc.craterlib.api.game.client;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.client.multiplayer.CraterClientLevel;
import com.hypherionmc.craterlib.api.game.client.multiplayer.CraterServerData;
import com.hypherionmc.craterlib.api.game.client.server.CraterIntegratedServer;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

public interface CraterGame extends CraterWrappedAPI {

    File getGameDirectory();
    CraterOptions getOptions();
    @Nullable CraterClientLevel getLevel();
    boolean isRealmServer();
    boolean isSinglePlayer();
    @Nullable CraterPlayer getPlayer();
    String getGameVersion();
    String getUserName();
    UUID getPlayerId();
    @Nullable CraterServerData getCurrentServer();
    @Nullable CraterIntegratedServer getSinglePlayerServer();
    void showWarningScreen(Text title, Text message);
    int getServerPlayerCount();

}
