package com.hypherionmc.craterlib.api.event.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.client.multiplayer.ClientLevel;

public class CraterClientTickEvent extends CraterEvent {

    private final ClientLevel level;

    public CraterClientTickEvent(ClientLevel level) {
        this.level = level;
    }

    public ClientLevel getLevel() {
        return level;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
