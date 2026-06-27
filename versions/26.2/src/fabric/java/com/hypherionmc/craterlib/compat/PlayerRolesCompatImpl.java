package com.hypherionmc.craterlib.compat;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.compat.playerroles.PlayerRolesCompat;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import dev.gegy.roles.SimpleRole;
import dev.gegy.roles.api.Role;
import dev.gegy.roles.config.PlayerRolesConfig;
import dev.gegy.roles.store.PlayerRoleManager;
import dev.gegy.roles.store.PlayerRoleSet;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

@AutoService(PlayerRolesCompat.class)
public class PlayerRolesCompatImpl implements PlayerRolesCompat {

    @Override
    public void addRole(CraterGameProfile profile, String roleName) {
        if (isNotLoaded()) return;
        updateRoles(PlayerRoleSet::add, profile, roleName);
    }

    @Override
    public void removeRole(CraterGameProfile profile, String roleName) {
        if (isNotLoaded()) return;
        updateRoles(PlayerRoleSet::remove, profile, roleName);
    }

    @Override
    public boolean hasRole(CraterGameProfile profile, String roleName) {
        if (isNotLoaded()) return false;
        return getRoles(profile).contains(roleName);
    }

    @Override
    public List<String> getRoles(CraterGameProfile profile) {
        if (isNotLoaded()) return List.of();
        var manager = PlayerRoleManager.get();
        var roles = manager.peekRoles(CraterLoader.getServer().unwrap(), profile.getId()).stream().toList();
        return roles.stream().map(Role::getId).toList();
    }

    private boolean isNotLoaded() {
        return !CraterLoader.isModLoaded("player_roles");
    }

    private void updateRoles(BiPredicate<PlayerRoleSet, SimpleRole> apply, CraterGameProfile profile, String roleName) {
        var manager = PlayerRoleManager.get();
        var role = getRoleInternal(roleName);
        if (role == null) return;
        manager.updateRoles(CraterLoader.getServer().unwrap(), profile.getId(), roles -> apply.test(roles, role));
    }

    @Nullable
    private SimpleRole getRoleInternal(String roleName) {
        return PlayerRolesConfig.get().get(roleName);
    }
}
