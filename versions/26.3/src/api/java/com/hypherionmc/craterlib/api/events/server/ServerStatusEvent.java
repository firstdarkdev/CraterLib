package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.network.protocol.status.CraterServerStatus;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ServerStatusEvent {

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class StatusRequestEvent extends CraterEvent {

        private final Text status;
        @Nullable
        private Text newStatus = null;

    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class FaviconRequestEvent extends CraterEvent {

        private final Optional<CraterServerStatus.CraterFavIcon> favicon;
        private Optional<CraterServerStatus.CraterFavIcon> newIcon = Optional.empty();

    }
}
