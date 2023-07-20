package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Function;

public class MessageBroadcastEvent extends CraterEvent {

    private final Component component;
    private final Function<ServerPlayer, Component> function;
    private final boolean bl;

    public MessageBroadcastEvent(Component component, Function<ServerPlayer, Component> function, boolean bl) {
        this.component = component;
        this.function = function;
        this.bl = bl;
    }

    public Component getComponent() {
        return component;
    }

    public boolean isBl() {
        return bl;
    }

    public Function<ServerPlayer, Component> getFunction() {
        return function;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
