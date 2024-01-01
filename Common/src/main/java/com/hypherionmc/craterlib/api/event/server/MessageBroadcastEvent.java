package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class MessageBroadcastEvent extends CraterEvent {

    private final Component component;
    private final UUID uuid;
    private final ChatType bl;

    public MessageBroadcastEvent(Component component, UUID uuid, ChatType bl) {
        this.component = component;
        this.uuid = uuid;
        this.bl = bl;
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

    @Override
    public boolean canCancel() {
        return false;
    }
}
