package com.hypherionmc.craterlib.nojang.authlib;

import com.mojang.authlib.GameProfile;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.players.NameAndId;

import java.util.UUID;

@RequiredArgsConstructor(staticName = "of")
public class BridgedGameProfile {

    private final GameProfile internal;

    public static BridgedGameProfile mojang(UUID id, String name) {
        return new BridgedGameProfile(new GameProfile(id, name));
    }

    public static BridgedGameProfile of(NameAndId nameAndId) {
        return BridgedGameProfile.of(new GameProfile(nameAndId.id(), nameAndId.name()));
    }

    public String getName() {
        return internal.name();
    }

    public UUID getId() {
        return internal.id();
    }

    public GameProfile toMojang() {
        return internal;
    }

    public NameAndId toNameAndId() {
        return new NameAndId(internal);
    }

}
