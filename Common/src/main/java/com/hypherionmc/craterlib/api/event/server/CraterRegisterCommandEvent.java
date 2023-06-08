package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

public class CraterRegisterCommandEvent extends CraterEvent {

    private final CommandDispatcher<CommandSourceStack> dispatcher;

    public CraterRegisterCommandEvent(CommandDispatcher<CommandSourceStack> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public CommandDispatcher<CommandSourceStack> getDispatcher() {
        return dispatcher;
    }

    @Override
    public boolean canCancel() {
        return false;
    }
}
