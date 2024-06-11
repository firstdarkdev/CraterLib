package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.client.gui.BridgedScreen;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ScreenEvent extends CraterEvent {

    private final BridgedScreen screen;

    @Getter
    public static class Opening extends ScreenEvent {

        private final BridgedScreen currentScreen;
        @Setter private BridgedScreen newScreen;

        public Opening(BridgedScreen currentScreen, BridgedScreen newScreen) {
            super(newScreen);
            this.currentScreen = currentScreen;
            this.newScreen = newScreen;
        }
    }
}