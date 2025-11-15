package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.compat.playerroles.BridgedPlayerRoles;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PlayerRolesEvents {

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class RoleAddedEvent extends CraterEvent {
        private final BridgedPlayerRoles role;
        private final BridgedGameProfile profile;
    }

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class RoleRemovedEvent extends CraterEvent {
        private final BridgedPlayerRoles role;
        private final BridgedGameProfile profile;
    }

}
