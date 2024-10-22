package com.hypherionmc.craterlib.nojang.client.gui;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.realms.RealmsScreen;

@RequiredArgsConstructor(staticName = "of")
public class BridgedScreen {

    private final Screen internal;

    public boolean isTitleScreen() {
        return internal instanceof TitleScreen;
    }

    public boolean isRealmsScreen() {
        return internal instanceof RealmsScreen;
    }

    public boolean isServerBrowserScreen() {
        return internal instanceof JoinMultiplayerScreen;
    }

    public boolean isLoadingScreen() {
        return internal instanceof LevelLoadingScreen || internal instanceof ReceivingLevelScreen;
    }

    public Screen toMojang() {
        return internal;
    }

}
