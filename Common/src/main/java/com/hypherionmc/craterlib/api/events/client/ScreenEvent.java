package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.client.gui.screens.Screen;

// TODO NOJANG
@Getter
@RequiredArgsConstructor
public class ScreenEvent extends CraterEvent {

    private final Screen screen;

    @Getter
    public static class Opening extends ScreenEvent {

        private final Screen currentScreen;
        @Setter private Screen newScreen;

        public Opening(Screen currentScreen, Screen newScreen) {
            super(newScreen);
            this.currentScreen = currentScreen;
            this.newScreen = newScreen;
        }
    }
}