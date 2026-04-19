package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.api.game.world.entity.player.CraterPlayer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class PlayerRevivedEvent extends CraterEvent {
    private final CraterPlayer player;
}
