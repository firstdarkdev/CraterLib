package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.core.event.annot.Cancellable;
import lombok.Getter;
import lombok.Setter;

@Cancellable
@Getter
public class CraterServerChatEvent extends CraterEvent {

    public final String message, username;
    public final CraterPlayer player;
    @Setter private Text component;

    public CraterServerChatEvent(CraterPlayer player, String message, Text component) {
        this.message = message;
        this.player = player;
        this.username = player.getGameProfile().getName();
        this.component = component;
    }

}
