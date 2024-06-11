package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.core.event.annot.Cancellable;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;

@Cancellable
@Getter
public class CraterServerChatEvent extends CraterEvent {

    public final String message, username;
    public final BridgedPlayer player;
    @Setter private Component component;

    public CraterServerChatEvent(BridgedPlayer player, String message, Component component) {
        this.message = message;
        this.player = player;
        this.username = player.getGameProfile().getName();
        this.component = component;
    }

}
