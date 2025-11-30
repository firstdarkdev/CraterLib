package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.PlayerAdvancements;

@RequiredArgsConstructor(staticName = "of")
public class BridgedPlayerAdvancements {

    private final PlayerAdvancements internal;

    public BridgedAdvancementProgress getOrStartProgress(BridgedAdvancementHolder advancement) {
        return BridgedAdvancementProgress.of(internal.getOrStartProgress(advancement.toMojang()));
    }

    public PlayerAdvancements toMojang() {
        return internal;
    }

}
