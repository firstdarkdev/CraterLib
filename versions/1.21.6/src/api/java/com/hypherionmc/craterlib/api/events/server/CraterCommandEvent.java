package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class CraterCommandEvent extends CraterEvent {

    @Setter private Throwable exception;
    private final String command;
    private final String commandString;
    private final @Nullable CraterPlayer player;
    private final String target;
    private final Text message;

}
