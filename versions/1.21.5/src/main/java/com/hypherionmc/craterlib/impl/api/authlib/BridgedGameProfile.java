package com.hypherionmc.craterlib.impl.api.authlib;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.mojang.authlib.GameProfile;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedGameProfile implements CraterGameProfile {

    private final GameProfile internal;

    @ApiStatus.Internal
    public static BridgedGameProfile mojang(UUID id, String name) {
        return new BridgedGameProfile(new GameProfile(id, name));
    }

    @Override
    public String getName() {
        return internal.getName();
    }

    @Override
    public UUID getId() {
        return internal.getId();
    }

    @Override
    public GameProfile unwrapInternal() {
        return internal;
    }

}
