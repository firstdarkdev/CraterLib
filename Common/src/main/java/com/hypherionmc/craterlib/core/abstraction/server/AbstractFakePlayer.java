package com.hypherionmc.craterlib.core.abstraction.server;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class AbstractFakePlayer extends CommandSourceStack {

    private final UUID uuid;

    public AbstractFakePlayer(MinecraftServer server, String name, MutableComponent displayName, UUID uuid) {
        super(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, server.overworld(), 4, name, displayName, server, null);
        this.uuid = uuid;
    }

    public void onSuccess(Component component, boolean bl) {

    }

    @Override
    public void sendSuccess(Component component, boolean bl) {
        this.onSuccess(component, bl);
    }

    @Override
    public void sendFailure(Component component) {
        sendSuccess(component, false);
    }

    public UUID getUuid() {
        return uuid;
    }
}
