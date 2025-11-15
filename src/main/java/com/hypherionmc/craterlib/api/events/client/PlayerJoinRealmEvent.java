package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.realmsclient.dto.BridgedRealmsServer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PlayerJoinRealmEvent extends CraterEvent {

    private final BridgedRealmsServer server;

}
