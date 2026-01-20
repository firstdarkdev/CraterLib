package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CraterRegisterCommandEvent extends CraterEvent {

    private final CommandRegistry stack;

    public void registerCommand(CraterCommand cmd) {
        cmd.register(stack);
    }

}
