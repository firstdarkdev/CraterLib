package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;

import java.util.Optional;

@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancement {

    private final Advancement internal;

    public Optional<BridgedDisplayInfo> displayInfo() {
        if (internal.display().isPresent()) {
            return Optional.of(BridgedDisplayInfo.of(internal.display().get()));
        }

        return Optional.empty();
    }

}
