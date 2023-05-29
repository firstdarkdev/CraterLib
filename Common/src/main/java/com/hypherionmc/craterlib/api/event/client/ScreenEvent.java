package com.hypherionmc.craterlib.api.event.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.client.gui.screens.Screen;

import java.util.Objects;

public class ScreenEvent extends CraterEvent {

    private final Screen screen;

    protected ScreenEvent(Screen screen) {
        this.screen = Objects.requireNonNull(screen);
    }

    public Screen getScreen() {
        return screen;
    }

    @Override
    public boolean canCancel() {
        return false;
    }

    public static class Opening extends ScreenEvent {

        private final Screen currentScreen;
        private Screen newScreen;

        public Opening(Screen currentScreen, Screen newScreen) {
            super(newScreen);
            this.currentScreen = currentScreen;
            this.newScreen = newScreen;
        }

        public Screen getCurrentScreen() {
            return currentScreen;
        }

        public Screen getNewScreen() {
            return newScreen;
        }

        public void setNewScreen(Screen newScreen) {
            this.newScreen = newScreen;
        }

    }
}
