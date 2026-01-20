package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

// TODO: Implement
@RequiredArgsConstructor(staticName = "of")
public class BridgedDisplayInfo {

    //private final DisplayInfo internal;

    public boolean shouldDisplay() {
        return false;
    }

    public boolean isHidden() {
        return false;
    }

    public Component displayName() {
        return Component.empty();
    }

    public Component description() {
        return Component.empty();
    }

}
