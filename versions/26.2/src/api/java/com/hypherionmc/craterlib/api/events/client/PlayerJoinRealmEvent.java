package com.hypherionmc.craterlib.api.events.client;

import com.hypherionmc.craterlib.api.game.realmsclient.dto.CraterRealmsServer;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PlayerJoinRealmEvent extends CraterEvent {

    private final CraterRealmsServer server;

}
