package com.hypherionmc.craterlib.impl.api.client;

import com.hypherionmc.craterlib.api.game.client.CraterGame;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.impl.api.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.impl.api.client.multiplayer.BridgedServerData;
import com.hypherionmc.craterlib.impl.api.client.server.BridgedIntegratedServer;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

public class BridgedMinecraft implements CraterGame {

    private final Minecraft internal = Minecraft.getInstance();

    @Override
    public File getGameDirectory() {
        return internal.gameDirectory;
    }

    @Override
    public BridgedOptions getOptions() {
        return BridgedOptions.wrap(internal.options);
    }

    @Nullable
    @Override
    public BridgedClientLevel getLevel() {
        if (internal.level == null)
            return null;

        return BridgedClientLevel.wrap(internal.level);
    }

    @Override
    public boolean isRealmServer() {
        return internal.getCurrentServer() != null && internal.isConnectedToRealms();
    }

    @Override
    public boolean isSinglePlayer() {
        return internal.hasSingleplayerServer();
    }

    @Override
    @Nullable
    public BridgedPlayer getPlayer() {
        if (internal.player == null)
            return null;

        return BridgedPlayer.wrap(internal.player);
    }

    @Override
    public String getGameVersion() {
        return SharedConstants.getCurrentVersion().getName();
    }

    @Override
    public String getUserName() {
        return internal.getUser().getName();
    }

    @Override
    public UUID getPlayerId() {
        return internal.getUser().getGameProfile().getId();
    }

    @Override
    @Nullable
    public BridgedServerData getCurrentServer() {
        if (internal.getCurrentServer() == null)
            return null;

        return BridgedServerData.wrap(internal.getCurrentServer());
    }

    @Override
    @Nullable
    public BridgedIntegratedServer getSinglePlayerServer() {
        if (internal.getSingleplayerServer() == null)
            return null;

        return BridgedIntegratedServer.wrap(internal.getSingleplayerServer());
    }

    @Override
    public void showWarningScreen(Text title, Text message) {
        Screen currentScreen = internal.screen;
        internal.setScreen(
                new AlertScreen(
                        () -> internal.setScreen(currentScreen),
                        title.toGame(),
                        message.toGame()
                )
        );
    }

    public Screen buildWarningScreen(Text title, Text message, Screen parent) {
        return new AlertScreen(
                () -> internal.setScreen(parent),
                title.toGame(),
                message.toGame()
        );
    }

    @Override
    public int getServerPlayerCount () {
        if (internal.getConnection() == null)
            return 0;

        return internal.getConnection().getOnlinePlayers().size();
    }

    @Override
    public Minecraft unwrapInternal() {
        return internal;
    }
}
