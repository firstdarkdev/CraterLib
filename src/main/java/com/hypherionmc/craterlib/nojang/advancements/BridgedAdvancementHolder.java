package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;

// TODO: Implement
@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancementHolder {

    //private final AdvancementHolder internal;

    public BridgedAdvancement value() {
        return BridgedAdvancement.of();
    }

//    public AdvancementHolder toMojang() {
//        return internal;
//    }
}
