package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public class MessageBroadcastEvent extends CraterEvent {

    private final Text component;
    private final Function<CraterPlayer, Text> function;
    private final boolean bl;
    private final String threadName;

}
