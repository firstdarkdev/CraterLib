package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

// TODO: Implement
@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancement {

    //private final Advancement internal;

    public Optional<BridgedDisplayInfo> displayInfo() {
//        if (internal.display().isPresent()) {
//            return Optional.of(BridgedDisplayInfo.of(internal.display().get()));
//        }

        return Optional.empty();
    }

//    public Advancement toMojang() {
//        return internal;
//    }

}
