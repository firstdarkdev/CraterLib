package com.hypherionmc.craterlib.impl.api.client.server;

import com.hypherionmc.craterlib.api.game.client.server.CraterIntegratedServer;
import com.hypherionmc.craterlib.impl.api.server.BridgedMinecraftServer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;

public class BridgedIntegratedServer extends BridgedMinecraftServer implements CraterIntegratedServer {

    private final IntegratedServer internal;

    BridgedIntegratedServer(IntegratedServer server) {
        super(server);
        this.internal = server;
    }

    public static BridgedIntegratedServer wrap(IntegratedServer server) {
        return new BridgedIntegratedServer(server);
    }

    @Override
    public String getLevelName() {
        return internal.getWorldData().getLevelName();
    }

    @Override
    public boolean isHardcore() {
        return internal.isHardcore();
    }

    @Override
    public MinecraftServer unwrapInternal() {
        return internal;
    }
}