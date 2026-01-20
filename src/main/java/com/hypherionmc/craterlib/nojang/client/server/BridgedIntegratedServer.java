package com.hypherionmc.craterlib.nojang.client.server;

import com.hypherionmc.craterlib.nojang.advancements.BridgedAdvancementHolder;
import com.hypherionmc.craterlib.nojang.advancements.BridgedPlayerAdvancements;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

// TODO: Implement if Possible
@RequiredArgsConstructor(staticName = "of")
public class BridgedIntegratedServer {

    //private final IntegratedServer internal;

    public String getLevelName() {
        return "Not Implemented";
    }

//    public IntegratedServer toMojang() {
//        return internal;
//    }

    public BridgedPlayerAdvancements getPlayerAdvancements(UUID uuid) {
        return BridgedPlayerAdvancements.of();
    }

    public Collection<BridgedAdvancementHolder> getAdvancements() {
        return new LinkedList<>();
    }

    public boolean isHardcore() {
        return false;
    }
}