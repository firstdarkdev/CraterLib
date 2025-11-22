package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.AdvancementProgress;

@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancementProgress {
    private final AdvancementProgress internal;

    public boolean isDone() {
        return internal.isDone();
    }

    public AdvancementProgress toMojang() {
        return internal;
    }
}
