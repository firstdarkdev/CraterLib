package com.hypherionmc.craterlib.nojang.authlib;

import com.hypixel.hytale.server.core.auth.PlayerAuthentication;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class BridgedGameProfile {

    private final PlayerAuthentication internal;

    public static BridgedGameProfile mojang(UUID id, String name) {
        return new BridgedGameProfile(new PlayerAuthentication(id, name));
    }

    public String getName() {
        return internal.getUsername();
    }

    public UUID getId() {
        return internal.getUuid();
    }

    public PlayerAuthentication toHytale() {
        return internal;
    }

}
