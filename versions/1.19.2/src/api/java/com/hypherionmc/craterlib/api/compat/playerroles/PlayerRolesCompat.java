package com.hypherionmc.craterlib.api.compat.playerroles;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.util.CraterServiceLoader;

import java.util.List;

public interface PlayerRolesCompat {

    public static final PlayerRolesCompat INSTANCE = CraterServiceLoader.load(PlayerRolesCompat.class);

    void addRole(CraterGameProfile profile, String role);
    void removeRole(CraterGameProfile profile, String role);
    boolean hasRole(CraterGameProfile profile, String role);
    List<String> getRoles(CraterGameProfile profile);
}
