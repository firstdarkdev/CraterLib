package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.client.BridgedMinecraft;
import com.hypherionmc.craterlib.nojang.client.BridgedOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LateInitEvent extends CraterEvent {

    private final BridgedMinecraft minecraft;
    private final BridgedOptions options;

}
