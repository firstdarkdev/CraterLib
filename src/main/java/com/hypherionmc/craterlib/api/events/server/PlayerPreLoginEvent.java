package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.core.event.CraterEvent;
import com.hypherionmc.craterlib.nojang.authlib.BridgedGameProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;

import java.net.SocketAddress;

@RequiredArgsConstructor
@Getter
public class PlayerPreLoginEvent extends CraterEvent {

    private final SocketAddress address;
    private final BridgedGameProfile gameProfile;
    @Setter private Component message;

}
