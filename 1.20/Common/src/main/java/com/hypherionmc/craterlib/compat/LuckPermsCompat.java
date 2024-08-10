package com.hypherionmc.craterlib.compat;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.server.level.ServerPlayer;

public class LuckPermsCompat {

    public static final LuckPermsCompat INSTANCE = new LuckPermsCompat();
    private final LuckPerms luckPerms = LuckPermsProvider.get();

    LuckPermsCompat() {}

    public boolean hasPermission(ServerPlayer player, String perm) {
        User luckPermsUser = luckPerms.getPlayerAdapter(ServerPlayer.class).getUser(player);
        return luckPermsUser.getCachedData().getPermissionData().checkPermission(perm).asBoolean();
    }

}
