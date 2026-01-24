package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.util.function.BiFunction;

public class LuckPermsCompatEvents {

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class GroupAddedEvent extends CraterEvent {
        private final String identifier;
        private final UUID uuid;
        private final String username;
        private final BiFunction<String, UUID, CraterGameProfile> converter;

        public CraterGameProfile toProfile() {
            return converter.apply(username, uuid);
        }
    }

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class GroupRemovedEvent extends CraterEvent {
        private final String identifier;
        private final UUID uuid;
        private final String username;
        private final BiFunction<String, UUID, CraterGameProfile> converter;

        public CraterGameProfile toProfile() {
            return converter.apply(username, uuid);
        }
    }
}
