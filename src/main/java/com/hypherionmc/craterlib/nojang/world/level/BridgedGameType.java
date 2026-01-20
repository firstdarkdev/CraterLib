package com.hypherionmc.craterlib.nojang.world.level;

import com.hypixel.hytale.protocol.GameMode;
import net.kyori.adventure.text.Component;

public enum BridgedGameType {
    SURVIVAL("survival", GameMode.Adventure),  // TODO: Fix
    CREATIVE("creative", GameMode.Creative),
    ADVENTURE("adventure", GameMode.Adventure),
    SPECTATOR("spectator", GameMode.Adventure); // TODO: Fix

    private final String name;
    private final GameMode internal;

    BridgedGameType(String name, GameMode internal) {
        this.name = name;
        this.internal = internal;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        if (internal == GameMode.Creative) {
            return "Creative";
        }
        return "Adventure";
    }

    public String getSerializedName() {
        return internal.name();
    }

    public Component getLongDisplayName() {
        return Component.text(internal.name());
    }

    public Component getShortDisplayName() {
        return Component.text(getName());
    }

    public GameMode toHytale() {
        return this.internal;
    }

    public boolean isBlockPlacingRestricted() {
        return false;
    }

    public boolean isCreative() {
        return internal == GameMode.Creative;
    }

    public boolean isSurvival() {
        return internal == GameMode.Adventure;
    }

    public static BridgedGameType fromHytale(GameMode type) {
        for (BridgedGameType gameType : values()) {
            if (gameType.toHytale() == type)
                return gameType;
        }
        return BridgedGameType.SURVIVAL;
    }

}
