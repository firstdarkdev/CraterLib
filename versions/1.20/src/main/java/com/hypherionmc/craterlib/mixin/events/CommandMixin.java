package com.hypherionmc.craterlib.mixin.events;

import com.google.common.base.Throwables;
import com.hypherionmc.craterlib.api.events.server.CraterCommandEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Commands.class)
public class CommandMixin {

    @Inject(method = "performCommand",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/brigadier/CommandDispatcher;execute(Lcom/mojang/brigadier/ParseResults;)I",
                    shift = At.Shift.BEFORE
            ), cancellable = true
    )
    private void injectCommandEvent(ParseResults<CommandSourceStack> parse, String command, CallbackInfoReturnable<Integer> cir) {
        try {
            CraterCommandEvent commandEvent = CraterCommandEvent.of(command, getCommandString(parse), getPlayer(parse), getTarget(parse), getMessage(parse));
            CraterEventBus.INSTANCE.postEvent(commandEvent);
            if (commandEvent.wasCancelled()) {
                cir.cancel();
                return;
            }

            if (commandEvent.getException() != null) {
                Throwables.throwIfUnchecked(commandEvent.getException());
                cir.cancel();
            }
        } catch (Exception ignored) {}
    }

    public String getCommandString(ParseResults<CommandSourceStack> stackParseResults) {
        return stackParseResults.getReader().getString();
    }

    @Nullable
    public CraterPlayer getPlayer(ParseResults<CommandSourceStack> stackParseResults) {
        try {
            Player p = stackParseResults.getContext().getLastChild().getSource().getPlayerOrException();

            if (p != null)
                return BridgedPlayer.wrap(p);
        } catch (Exception ignored) {}

        return null;
    }

    public String getTarget(ParseResults<CommandSourceStack> stackParseResults) {
        try {
            CommandContext<CommandSourceStack> context = stackParseResults.getContext().build(stackParseResults.getReader().getString());
            StringRange selector_range = stackParseResults.getContext().getArguments().get("targets").getRange();
            return context.getInput().substring(selector_range.getStart(), selector_range.getEnd());
        } catch (Exception ignored) {}
        return "";
    }

    public Text getMessage(ParseResults<CommandSourceStack> stackParseResults) {
        try {
            return Text.fromGame(ComponentArgument.getComponent(stackParseResults.getContext().build(stackParseResults.getReader().getString()), "message"));
        } catch (Exception ignored) {}
        return Text.empty();
    }

}
