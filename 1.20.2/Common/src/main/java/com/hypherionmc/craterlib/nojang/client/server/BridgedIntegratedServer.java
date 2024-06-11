package com.hypherionmc.craterlib.nojang.client.server;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.server.IntegratedServer;

@RequiredArgsConstructor(staticName = "of")
public class BridgedIntegratedServer {

    private final IntegratedServer internal;

    public String getLevelName() {
        return internal.getWorldData().getLevelName();
    }

    public IntegratedServer toMojang() {
        return internal;
    }

}
