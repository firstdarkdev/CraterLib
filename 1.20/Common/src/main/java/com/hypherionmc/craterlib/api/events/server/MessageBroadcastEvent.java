package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public class MessageBroadcastEvent extends CraterEvent {

    private final Component component;
    private final Function<BridgedPlayer, Component> function;
    private final boolean bl;
    private final String threadName;

}
