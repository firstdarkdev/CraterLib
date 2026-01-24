package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.api.compat.playerroles.BridgedPlayerRoles;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PlayerRolesEvents {

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class RoleAddedEvent extends CraterEvent {
        private final BridgedPlayerRoles role;
        private final CraterGameProfile profile;
    }

    @RequiredArgsConstructor(staticName = "of")
    @Getter
    public static class RoleRemovedEvent extends CraterEvent {
        private final BridgedPlayerRoles role;
        private final CraterGameProfile profile;
    }

}
