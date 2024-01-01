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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class CommandMixin {

    @Inject(method = "performCommand",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/commands/Commands;finishParsing(Lcom/mojang/brigadier/ParseResults;Ljava/lang/String;Lnet/minecraft/commands/CommandSourceStack;)Lcom/mojang/brigadier/context/ContextChain;",
                    shift = At.Shift.BEFORE
            ), cancellable = true
    )
    private void injectCommandEvent(ParseResults<CommandSourceStack> stackParseResults, String command, CallbackInfo ci) {
        CraterCommandEvent commandEvent = new CraterCommandEvent(stackParseResults, command);
        CraterEventBus.INSTANCE.postEvent(commandEvent);
        if (commandEvent.wasCancelled()) {
            ci.cancel();
            return;
        }

        if (commandEvent.getException() != null) {
            Throwables.throwIfUnchecked(commandEvent.getException());
            ci.cancel();
        }
    }

}
