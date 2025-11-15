package com.hypherionmc.craterlib.api.events.compat;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.world.entity.player.BridgedPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class PlayerRevivedEvent extends CraterEvent {
    private final BridgedPlayer player;
}
