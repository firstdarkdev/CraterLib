package com.hypherionmc.craterlib.api.events.server;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.event.CraterEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.SocketAddress;

@RequiredArgsConstructor
@Getter
public class PlayerPreLoginEvent extends CraterEvent {

    private final SocketAddress address;
    private final CraterGameProfile gameProfile;
    @Setter private Text message;

}
