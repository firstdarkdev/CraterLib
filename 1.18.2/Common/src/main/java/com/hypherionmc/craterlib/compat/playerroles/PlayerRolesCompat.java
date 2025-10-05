package com.hypherionmc.craterlib.compat.playerroles;

import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.utils.InternalServiceUtil;

import java.util.List;

public interface PlayerRolesCompat {

    public static final PlayerRolesCompat INSTANCE = InternalServiceUtil.load(PlayerRolesCompat.class);

    void addRole(BridgedGameProfile profile, String role);
    void removeRole(BridgedGameProfile profile, String role);
    boolean hasRole(BridgedGameProfile profile, String role);
    List<String> getRoles(BridgedGameProfile profile);
}
