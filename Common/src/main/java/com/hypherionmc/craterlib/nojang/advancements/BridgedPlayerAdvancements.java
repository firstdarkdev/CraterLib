package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.PlayerAdvancements;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(staticName = "of")
public class BridgedPlayerAdvancements {

    private final PlayerAdvancements internal;

    public BridgedAdvancementProgress getOrStartProgress(BridgedAdvancementHolder ah) {
        return BridgedAdvancementProgress.of(internal.getOrStartProgress(ah.toMojang()));
    }

    public PlayerAdvancements toMojang(){
        return internal;
    }
}
