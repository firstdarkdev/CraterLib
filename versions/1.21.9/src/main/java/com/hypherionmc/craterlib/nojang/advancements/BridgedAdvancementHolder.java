package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.AdvancementHolder;

@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancementHolder {

    private final AdvancementHolder internal;

    public BridgedAdvancement value() {
        return BridgedAdvancement.of(internal.value());
    }

    public AdvancementHolder toMojang() {
        return internal;
    }
}
