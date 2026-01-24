package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.api.game.client.multiplayer.CraterClientLevel;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterClientTickEvent extends CraterEvent {

    private final CraterClientLevel level;

}
