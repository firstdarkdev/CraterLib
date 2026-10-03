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
    @Setter private boolean upstreamCancelled = false;
    private final MessageSource source;

    public CraterServerChatEvent(CraterPlayer sender, String message, Text component) {
        this(sender, message, component, MessageSource.MAIN);
    }

    public CraterServerChatEvent(CraterPlayer player, String message, Text component, MessageSource source) {
        this.message = message;
        this.player = player;
        this.username = player.getGameProfile().getName();
        this.component = component;
        this.source = source;
    }

    public enum MessageSource {
        MAIN,
        BACKUP
    }
}
