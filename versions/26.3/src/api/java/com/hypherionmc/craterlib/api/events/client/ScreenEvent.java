package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.api.game.client.gui.CraterScreen;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ScreenEvent extends CraterEvent {

    private final CraterScreen screen;

    @Getter
    public static class Opening extends ScreenEvent {

        private final CraterScreen currentScreen;
        @Setter private CraterScreen newScreen;

        public Opening(CraterScreen currentScreen, CraterScreen newScreen) {
            super(newScreen);
            this.currentScreen = currentScreen;
            this.newScreen = newScreen;
        }
    }
}