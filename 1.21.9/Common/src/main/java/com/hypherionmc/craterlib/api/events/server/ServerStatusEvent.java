package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.network.protocol.status.WrappedServerStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ServerStatusEvent {

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class StatusRequestEvent extends CraterEvent {

        private final Component status;
        @Nullable
        private Component newStatus = null;

    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class FaviconRequestEvent extends CraterEvent {

        private final Optional<WrappedServerStatus.WrappedFavicon> favicon;
        private Optional<WrappedServerStatus.WrappedFavicon> newIcon = Optional.empty();

    }
}
