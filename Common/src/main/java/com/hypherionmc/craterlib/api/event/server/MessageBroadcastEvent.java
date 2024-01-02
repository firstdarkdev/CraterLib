package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class MessageBroadcastEvent extends CraterEvent {

    private final Component component;
    private final UUID uuid;
    private final ChatType bl;
    private final String threadName;

    public MessageBroadcastEvent(Component component, UUID uuid, ChatType bl, String threadName) {
        this.component = component;
        this.uuid = uuid;
        this.bl = bl;
        this.threadName = threadName;
    }

    public Component getComponent() {
        return component;
    }

    public ChatType getChatType() {
        return this.bl;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getThreadName() {
        return threadName;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
