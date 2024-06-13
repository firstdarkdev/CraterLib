package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.client.multiplayer.BridgedClientLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CraterClientTickEvent extends CraterEvent {

    private final BridgedClientLevel level;

}
