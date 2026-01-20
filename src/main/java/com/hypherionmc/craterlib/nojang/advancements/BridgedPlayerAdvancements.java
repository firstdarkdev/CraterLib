package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;

// TODO: Implement
@RequiredArgsConstructor(staticName = "of")
public class BridgedPlayerAdvancements {

    //private final PlayerAdvancements internal;

    public BridgedAdvancementProgress getOrStartProgress(BridgedAdvancementHolder advancement) {
        return BridgedAdvancementProgress.of();
    }

//    public PlayerAdvancements toMojang() {
//        return internal;
//    }

}
