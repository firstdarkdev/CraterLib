package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.api.game.client.CraterGame;
import com.hypherionmc.craterlib.api.game.client.CraterOptions;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LateInitEvent extends CraterEvent {

    private final CraterGame minecraft;
    private final CraterOptions options;

}
