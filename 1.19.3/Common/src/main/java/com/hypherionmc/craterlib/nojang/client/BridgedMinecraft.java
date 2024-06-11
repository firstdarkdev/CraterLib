package com.hypherionmc.craterlib.nojang.client;

import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedServerData;
import com.hypherionmc.craterlib.nojang.client.server.BridgedIntegratedServer;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

public class BridgedMinecraft {

    @Getter
    private static final BridgedMinecraft instance = new BridgedMinecraft();
    private final Minecraft internal = Minecraft.getInstance();

    public File getGameDirectory() {
        return internal.gameDirectory;
    }

    public BridgedOptions getOptions() {
        return BridgedOptions.of(internal.options);
    }

    @Nullable
    public BridgedClientLevel getLevel() {
        if (internal.level == null)
            return null;

        return BridgedClientLevel.of(internal.level);
    }

    public boolean isRealmServer() {
        return internal.getCurrentServer() != null && internal.isConnectedToRealms();
    }

    public boolean isSinglePlayer() {
        return internal.hasSingleplayerServer();
    }

    @Nullable
    public BridgedPlayer getPlayer() {
        if (internal.player == null)
            return null;

        return BridgedPlayer.of(internal.player);
    }

    public String getGameVersion() {
        return SharedConstants.getCurrentVersion().getName();
    }

    public String getUserName() {
        return internal.getUser().getName();
    }

    public UUID getPlayerId() {
        return internal.getUser().getProfileId();
    }

    @Nullable
    public BridgedServerData getCurrentServer() {
        if (internal.getCurrentServer() == null)
            return null;

        return BridgedServerData.of(internal.getCurrentServer());
    }

    @Nullable
    public BridgedIntegratedServer getSinglePlayerServer() {
        return BridgedIntegratedServer.of(internal.getSingleplayerServer());
    }

    public int getServerPlayerCount () {
        if (internal.getConnection() == null)
            return 0;

        return internal.getConnection().getOnlinePlayers().size();
    }
}
