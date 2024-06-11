package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.commands.CommandsRegistry;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CraterRegisterCommandEvent extends CraterEvent {

    public void registerCommand(CraterCommand cmd) {
        CommandsRegistry.INSTANCE.registerCommand(cmd);
    }

}
