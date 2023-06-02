package com.hypherionmc.craterlib.api.event.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

public class LateInitEvent extends CraterEvent {

    private final Minecraft minecraft;
    private final Options options;

    public LateInitEvent(Minecraft minecraft, Options options) {
        this.minecraft = minecraft;
        this.options = options;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public Options getOptions() {
        return options;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
