package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class WhitelistChangedEvent {

    @RequiredArgsConstructor
    @Getter
    public static final class EntryAdded extends CraterEvent {

        private final CraterGameProfile profile;

    }

    @RequiredArgsConstructor
    @Getter
    public static final class EntryRemoved extends CraterEvent {

        private final CraterGameProfile profile;

    }

}
