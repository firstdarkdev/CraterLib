package com.hypherionmc.craterlib.impl.api.client.gui;

import com.hypherionmc.craterlib.api.game.client.gui.CraterScreen;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.realms.RealmsScreen;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedScreen implements CraterScreen {

    private final Screen internal;

    @Override
    public boolean isTitleScreen() {
        return internal instanceof TitleScreen;
    }

    @Override
    public boolean isRealmsScreen() {
        return internal instanceof RealmsScreen;
    }

    @Override
    public boolean isServerBrowserScreen() {
        return internal instanceof JoinMultiplayerScreen;
    }

    @Override
    public boolean isLoadingScreen() {
        return internal instanceof LevelLoadingScreen || internal instanceof ConnectScreen;
    }

    @Override
    public boolean isPauseScreen() {
        return internal instanceof PauseScreen;
    }

    @Override
    public boolean isDisconnectedScreen() {
        return internal instanceof DisconnectedScreen;
    }

    @Deprecated(forRemoval = true)
    public boolean isDisconnetedScreen() {
        return isDisconnectedScreen();
    }

    @Override
    public Screen unwrapInternal() {
        return internal;
    }

}
