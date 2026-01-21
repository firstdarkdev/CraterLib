package com.hypherionmc.craterlib.api.game.client.gui;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

public interface CraterScreen extends CraterWrappedAPI {

    boolean isTitleScreen();
    boolean isRealmsScreen();
    boolean isServerBrowserScreen();
    boolean isLoadingScreen();
    boolean isPauseScreen();
    boolean isDisconnectedScreen();

}
