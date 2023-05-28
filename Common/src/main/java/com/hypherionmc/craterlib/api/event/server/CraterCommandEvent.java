package com.hypherionmc.craterlib.api.event.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.mojang.brigadier.ParseResults;
import net.minecraft.commands.CommandSourceStack;

public class CraterCommandEvent extends CraterEvent {

    private ParseResults<CommandSourceStack> parseResults;
    private Throwable exception;
    private String command;

    public CraterCommandEvent(ParseResults<CommandSourceStack> parseResults, String command) {
        this.parseResults = parseResults;
        this.command = command;
    }

    public ParseResults<CommandSourceStack> getParseResults() {
        return parseResults;
    }

    public void setParseResults(ParseResults<CommandSourceStack> parseResults) {
        this.parseResults = parseResults;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public boolean canCancel() {
        return true;
    }
}
