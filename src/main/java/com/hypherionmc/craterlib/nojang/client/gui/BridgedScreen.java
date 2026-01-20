package com.hypherionmc.craterlib.nojang.client.gui;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class BridgedScreen {

    //private final Screen internal;

    public boolean isTitleScreen() {
        return false;
    }

    public boolean isRealmsScreen() {
        return false;
    }

    public boolean isServerBrowserScreen() {
        return false;
    }

    public boolean isLoadingScreen() {
        return false;
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean isDisconnetedScreen() {
        return false;
    }

//    public Screen toMojang() {
//        return internal;
//    }

}
