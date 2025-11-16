package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;

import java.util.Optional;

@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancement {

    private final Advancement internal;

    public Optional<BridgedDisplayInfo> displayInfo() {
        if (internal.getDisplay() != null) {
            return Optional.of(BridgedDisplayInfo.of(internal.getDisplay()));
        }

        return Optional.empty();
    }

}
