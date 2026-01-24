package com.hypherionmc.craterlib.api.compat;

import com.hypherionmc.craterlib.core.services.CraterServices;

import java.util.Set;
import java.util.UUID;

public interface LuckPermsCompat {

    static LuckPermsCompat getInstance() {
        return CraterServices.COMPAT_UTILS.getLuckperms();
    }

    boolean hasGroup(UUID uuid, String group);
    Set<String> getUserGroups(UUID uuid);
    boolean addGroupToUser(UUID uuid, String group);
    boolean removeGroupFromUser(UUID uuid, String group);
}
