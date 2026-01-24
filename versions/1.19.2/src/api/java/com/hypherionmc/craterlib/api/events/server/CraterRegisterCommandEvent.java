package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class CraterRegisterCommandEvent extends CraterEvent {

    private final Consumer<CraterCommand> stack;

    public void registerCommand(CraterCommand cmd) {
        stack.accept(cmd);
    }

}
