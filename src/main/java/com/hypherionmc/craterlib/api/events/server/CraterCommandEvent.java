package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

@Getter
public class CraterCommandEvent extends CraterEvent {

    //private final ParseResults<CommandSourceStack> parseResults;
    @Setter private Throwable exception;
    private final String command;

    private CraterCommandEvent(String command) {
        this.command = command;
    }

    public static CraterCommandEvent of(String command) {
        return new CraterCommandEvent(command);
    }

    public String getCommandString() {
        return "";
    }

    @Nullable
    public BridgedPlayer getPlayer() {

        return null;
    }

    public String getTarget() {
       return "";
    }

    public Component getMessage() {
        return Component.empty();
    }
}
