package com.hypherionmc.craterlib.nojang.client.multiplayer;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.multiplayer.ClientLevel;

@RequiredArgsConstructor(staticName = "of")
public class BridgedClientLevel {

    private final ClientLevel internal;

}
