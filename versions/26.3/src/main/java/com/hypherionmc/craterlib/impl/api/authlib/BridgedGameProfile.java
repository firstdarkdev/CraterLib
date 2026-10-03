package com.hypherionmc.craterlib.impl.api.authlib;

import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.mojang.authlib.GameProfile;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.players.NameAndId;
import org.jetbrains.annotations.ApiStatus;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedGameProfile implements CraterGameProfile {

    private final GameProfile internal;

    @ApiStatus.Internal
    public static BridgedGameProfile mojang(UUID id, String name) {
        return new BridgedGameProfile(new GameProfile(id, name));
    }

    @ApiStatus.Internal
    public static BridgedGameProfile of(NameAndId nameAndId) {
        return BridgedGameProfile.wrap(new GameProfile(nameAndId.id(), nameAndId.name()));
    }

    @Override
    public String getName() {
        return internal.name();
    }

    @Override
    public UUID getId() {
        return internal.id();
    }

    @Override
    public GameProfile unwrapInternal() {
        return internal;
    }

    @ApiStatus.Internal
    public NameAndId toNameAndId() {
        return new NameAndId(internal);
    }

}
