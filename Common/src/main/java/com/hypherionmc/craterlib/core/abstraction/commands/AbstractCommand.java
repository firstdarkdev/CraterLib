package com.hypherionmc.craterlib.core.abstraction.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

public class AbstractCommand {

    public static void replySuccess(CommandContext<CommandSourceStack> stack, MutableComponent message) {
        stack.getSource().sendSuccess(message, false);
    }

    public static void replyFailure(CommandContext<CommandSourceStack> stack, MutableComponent message) {
        stack.getSource().sendFailure(message);
    }
}
