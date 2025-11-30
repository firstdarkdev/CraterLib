package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;

@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancementHolder {

    private final Advancement internal;

    public BridgedAdvancement value() {
        return BridgedAdvancement.of(internal);
    }

    public Advancement toMojang() {
        return internal;
    }
}
