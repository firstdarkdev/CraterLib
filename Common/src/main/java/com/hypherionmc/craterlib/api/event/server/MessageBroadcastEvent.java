package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Function;

public class MessageBroadcastEvent extends CraterEvent {

    private final Component component;
    private final Function<ServerPlayer, Component> function;
    private final boolean bl;
    private final String threadName;

    public MessageBroadcastEvent(Component component, Function<ServerPlayer, Component> function, boolean bl, String threadName) {
        this.component = component;
        this.function = function;
        this.bl = bl;
        this.threadName = threadName;
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

    public String getThreadName() {
        return threadName;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
