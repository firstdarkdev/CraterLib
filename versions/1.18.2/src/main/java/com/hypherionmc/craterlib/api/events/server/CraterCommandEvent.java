package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import com.hypherionmc.craterlib.utils.ChatUtils;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.StringRange;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

@Getter
public class CraterCommandEvent extends CraterEvent {

    private final ParseResults<CommandSourceStack> parseResults;
    @Setter private Throwable exception;
    private final String command;

    private CraterCommandEvent(ParseResults<CommandSourceStack> parseResults, String command) {
        this.parseResults = parseResults;
        this.command = command;
    }

    public static CraterCommandEvent of(ParseResults<CommandSourceStack> stack, String command) {
        return new CraterCommandEvent(stack, command);
    }

    public String getCommandString() {
        return parseResults.getReader().getString();
    }

    @Nullable
    public BridgedPlayer getPlayer() {
        try {
            Player p = parseResults.getContext().getLastChild().getSource().getPlayerOrException();

            if (p != null)
                return BridgedPlayer.of(p);
        } catch (Exception ignored) {}

        return null;
    }

    public String getTarget() {
        CommandContext<CommandSourceStack> context = parseResults.getContext().build(parseResults.getReader().getString());
        StringRange selector_range = parseResults.getContext().getArguments().get("targets").getRange();
        return context.getInput().substring(selector_range.getStart(), selector_range.getEnd());
    }

    public Component getMessage() {
        return ChatUtils.mojangToAdventure(ComponentArgument.getComponent(parseResults.getContext().build(parseResults.getReader().getString()), "message"));
    }
}
