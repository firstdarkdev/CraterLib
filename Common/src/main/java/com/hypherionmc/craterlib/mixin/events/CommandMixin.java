package com.hypherionmc.craterlib.mixin.events;

import com.google.common.base.Throwables;
import com.hypherionmc.craterlib.api.event.server.CraterCommandEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Commands.class)
public class CommandMixin {

    @Inject(method = "performCommand",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/brigadier/CommandDispatcher;execute(Lcom/mojang/brigadier/ParseResults;)I",
                    shift = At.Shift.BEFORE
            ), cancellable = true, remap = false
    )
    private void injectCommandEvent(ParseResults<CommandSourceStack> stackParseResults, String command, CallbackInfoReturnable<Integer> cir) {
        CraterCommandEvent commandEvent = new CraterCommandEvent(stackParseResults, command);
        CraterEventBus.INSTANCE.postEvent(commandEvent);
        if (commandEvent.wasCancelled()) {
            cir.setReturnValue(1);
            return;
        }

        if (commandEvent.getException() != null) {
            Throwables.throwIfUnchecked(commandEvent.getException());
            cir.setReturnValue(1);
        }
    }

}
