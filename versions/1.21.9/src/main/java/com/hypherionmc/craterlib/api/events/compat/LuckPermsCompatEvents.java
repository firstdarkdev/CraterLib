package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public class LuckPermsCompatEvents {

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class GroupAddedEvent extends CraterEvent {
        private final String identifier;
        private final UUID uuid;
        private final String username;

        public BridgedGameProfile toProfile() {
            return BridgedGameProfile.mojang(uuid, username);
        }
    }

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class GroupRemovedEvent extends CraterEvent {
        private final String identifier;
        private final UUID uuid;
        private final String username;

        public BridgedGameProfile toProfile() {
            return BridgedGameProfile.mojang(uuid, username);
        }
    }
}
