package com.hypherionmc.craterlib.mixin.events;

import com.google.common.base.Throwables;
import com.hypherionmc.craterlib.api.events.server.CraterCommandEvent;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEventBus;
import com.hypherionmc.craterlib.impl.api.world.entity.player.BridgedPlayer;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
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
        try {
            CraterCommandEvent commandEvent = CraterCommandEvent.of(command, getCommandString(stackParseResults), getPlayer(stackParseResults), getTarget(stackParseResults), getMessage(stackParseResults));
            CraterEventBus.INSTANCE.postEvent(commandEvent);
            if (commandEvent.wasCancelled()) {
                ci.cancel();
                return;
            }

            if (commandEvent.getException() != null) {
                Throwables.throwIfUnchecked(commandEvent.getException());
                ci.cancel();
            }
        } catch (Exception ignored) {}
    }

    public String getCommandString(ParseResults<CommandSourceStack> stackParseResults) {
        return stackParseResults.getReader().getString();
    }

    @Nullable
    public CraterPlayer getPlayer(ParseResults<CommandSourceStack> stackParseResults) {
        try {
            Player p = stackParseResults.getContext().getLastChild().getSource().getPlayer();

            if (p != null)
                return BridgedPlayer.wrap(p);
        } catch (Exception ignored) {}

        return null;
    }

    public String getTarget(ParseResults<CommandSourceStack> stackParseResults) {
        CommandContext<CommandSourceStack> context = stackParseResults.getContext().build(stackParseResults.getReader().getString());
        StringRange selector_range = stackParseResults.getContext().getArguments().get("targets").getRange();
        return context.getInput().substring(selector_range.getStart(), selector_range.getEnd());
    }

    public Text getMessage(ParseResults<CommandSourceStack> stackParseResults) {
        return Text.fromGame(ComponentArgument.getComponent(stackParseResults.getContext().build(stackParseResults.getReader().getString()), "message"));
    }

}
