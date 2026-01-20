package com.hypherionmc.craterlib.nojang.client;

import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedServerData;
import com.hypherionmc.craterlib.nojang.client.server.BridgedIntegratedServer;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;

// TODO: Implement if Possible
public class BridgedMinecraft {

    @Getter
    private static final BridgedMinecraft instance = new BridgedMinecraft();
    //private final Minecraft internal = Minecraft.getInstance();

    public File getGameDirectory() {
        return new File(".");
    }

    public BridgedOptions getOptions() {
        return BridgedOptions.of();
    }

    @Nullable
    public BridgedClientLevel getLevel() {
        return null;
    }

    public boolean isRealmServer() {
        return false;
    }

    public boolean isSinglePlayer() {
        return false;
    }

    @Nullable
    public BridgedPlayer getPlayer() {
        return null;
    }

    public String getGameVersion() {
        return "Not Implemented";
    }

    public String getUserName() {
        return "Not Implemented";
    }

    public UUID getPlayerId() {
        return UUID.fromString("Not Implemented");
    }

    @Nullable
    public BridgedServerData getCurrentServer() {
        return null;
    }

    @Nullable
    public BridgedIntegratedServer getSinglePlayerServer() {
        return null;
    }

    public void showWarningScreen(Component title, Component message) {
//        Screen currentScreen = internal.screen;
//        internal.setScreen(
//                new AlertScreen(
//                        () -> internal.setScreen(currentScreen),
//                        ChatUtils.adventureToMojang(title),
//                        ChatUtils.adventureToMojang(message)
//                )
//        );
    }

//    public Screen buildWarningScreen(Component title, Component message, Screen parent) {
//        return new AlertScreen(
//                () -> internal.setScreen(parent),
//                ChatUtils.adventureToMojang(title),
//                ChatUtils.adventureToMojang(message)
//        );
//    }

    public int getServerPlayerCount () {
        return 0;
    }
}
