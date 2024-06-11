package com.hypherionmc.craterlib.mixin.events;

import com.google.common.base.Throwables;
import com.hypherionmc.craterlib.api.events.server.CraterCommandEvent;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Commands.class)
public class CommandMixin {

    @Shadow
    @Final
    private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(
            method = "performCommand",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void injectCommandEvent(CommandSourceStack stack, String command, CallbackInfoReturnable<Integer> cir) {
        StringReader stringreader = new StringReader(command);
        if (stringreader.canRead() && stringreader.peek() == '/') {
            stringreader.skip();
        }

        try {
            ParseResults<CommandSourceStack> parse = dispatcher.parse(stringreader, stack);

            CraterCommandEvent commandEvent = CraterCommandEvent.of(parse, command);
            CraterEventBus.INSTANCE.postEvent(commandEvent);
            if (commandEvent.wasCancelled()) {
                cir.setReturnValue(1);
                return;
            }

            if (commandEvent.getException() != null) {
                Throwables.throwIfUnchecked(commandEvent.getException());
                cir.setReturnValue(1);
            }
        } catch (Exception ignored) {}
    }

}
