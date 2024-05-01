package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.server.BridgedMinecraftServer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CraterServerLifecycleEvent extends CraterEvent {

    @RequiredArgsConstructor
    @Getter
    public static class Starting extends CraterServerLifecycleEvent {
        private final BridgedMinecraftServer server;
    }

    @RequiredArgsConstructor
    @Getter
    public static class Started extends CraterServerLifecycleEvent {
        private final BridgedMinecraftServer server;
    }

    @RequiredArgsConstructor
    @Getter
    public static class Stopping extends CraterServerLifecycleEvent {
        private final BridgedMinecraftServer server;
    }

    @RequiredArgsConstructor
    @Getter
    public static class Stopped extends CraterServerLifecycleEvent {
        private final BridgedMinecraftServer server;
    }

}
