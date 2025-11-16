package com.hypherionmc.craterlib.nojang.authlib;

import com.mojang.authlib.GameProfile;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class BridgedGameProfile {

    private final GameProfile internal;

    public static BridgedGameProfile mojang(UUID id, String name) {
        return new BridgedGameProfile(new GameProfile(id, name));
    }

    public String getName() {
        return internal.getName();
    }

    public UUID getId() {
        return internal.getId();
    }

    public GameProfile toMojang() {
        return internal;
    }

}
