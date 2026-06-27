package com.hypherionmc.craterlib.compat;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.compat.playerroles.PlayerRolesCompat;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;

import java.util.List;

@AutoService(PlayerRolesCompat.class)
public class PlayerRolesCompatImpl implements PlayerRolesCompat {

    @Override
    public void addRole(CraterGameProfile profile, String role) {
        // NO-OP
    }

    @Override
    public void removeRole(CraterGameProfile profile, String role) {
        // NO-OP
    }

    @Override
    public boolean hasRole(CraterGameProfile profile, String role) {
        // NO-OP
        return false;
    }

    @Override
    public List<String> getRoles(CraterGameProfile profile) {
        // NO-OP
        return List.of();
    }
}
