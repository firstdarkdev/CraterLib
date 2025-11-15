package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.mojang.brigadier.CommandDispatcher;
import lombok.AllArgsConstructor;
import net.minecraft.commands.CommandSourceStack;

@AllArgsConstructor
public class CraterRegisterCommandEvent extends CraterEvent {

    private final CommandDispatcher<CommandSourceStack> stack;

    public void registerCommand(CraterCommand cmd) {
        cmd.register(stack);
    }

}
