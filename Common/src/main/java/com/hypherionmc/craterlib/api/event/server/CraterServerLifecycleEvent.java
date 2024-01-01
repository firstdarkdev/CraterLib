package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.server.MinecraftServer;

public class CraterServerLifecycleEvent extends CraterEvent {

    public CraterServerLifecycleEvent() {
    }

    @Override
    public boolean canCancel() {
        return false;
    }

    public static class Starting extends CraterServerLifecycleEvent {

        private final MinecraftServer server;

        public Starting(MinecraftServer server) {
            this.server = server;
        }

        public MinecraftServer getServer() {
            return server;
        }
    }

    public static class Started extends CraterServerLifecycleEvent {

        public Started() {
        }

    }

    public static class Stopping extends CraterServerLifecycleEvent {

        public Stopping() {
        }

    }

    public static class Stopped extends CraterServerLifecycleEvent {

        public Stopped() {
        }

    }
}
