package com.hypherionmc.craterlib.nojang.client.server;

import com.hypherionmc.craterlib.nojang.advancements.BridgedAdvancementHolder;
import com.hypherionmc.craterlib.nojang.advancements.BridgedPlayerAdvancements;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.server.IntegratedServer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class BridgedIntegratedServer {

    private final IntegratedServer internal;

    public String getLevelName() {
        return internal.getWorldData().getLevelName();
    }

    public IntegratedServer toMojang() {
        return internal;
    }

    public BridgedPlayerAdvancements getPlayerAdvancements(UUID uuid) {
        return BridgedPlayerAdvancements.of(internal.getPlayerList().getPlayer(uuid).getAdvancements());
    }

    public Collection<BridgedAdvancementHolder> getAdvancements() {
        Collection<Advancement> ah = internal.getAdvancements().getAllAdvancements();
        LinkedList<BridgedAdvancementHolder> ret = new LinkedList<>();
        for(Advancement a: ah) {
            BridgedAdvancementHolder bah = BridgedAdvancementHolder.of(a);
            ret.add(bah);
        }
        return ret;
    }

    public boolean isHardcore() {
        return internal.isHardcore();
    }
}
