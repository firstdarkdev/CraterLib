package com.hypherionmc.craterlib.compat;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.compat.playerroles.PlayerRolesCompat;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;

import java.util.List;

@AutoService(PlayerRolesCompat.class)
public class PlayerRolesCompatImpl implements PlayerRolesCompat {

    @Override
    public void addRole(BridgedGameProfile profile, String role) {
        // NO-OP
    }

    @Override
    public void removeRole(BridgedGameProfile profile, String role) {
        // NO-OP
    }

    @Override
    public boolean hasRole(BridgedGameProfile profile, String role) {
        // NO-OP
        return false;
    }

    @Override
    public List<String> getRoles(BridgedGameProfile profile) {
        // NO-OP
        return List.of();
    }
}
