package com.hypherionmc.craterlib.nojang.world.level;

import com.hypherionmc.craterlib.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import net.minecraft.world.level.GameType;

public enum BridgedGameType {
    SURVIVAL("survival", GameType.SURVIVAL),
    CREATIVE("creative", GameType.CREATIVE),
    ADVENTURE("adventure", GameType.ADVENTURE),
    SPECTATOR("spectator", GameType.SPECTATOR);

    private final String name;
    private final GameType internal;

    BridgedGameType(String name, GameType internal) {
        this.name = name;
        this.internal = internal;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.internal.getName();
    }

    public String getSerializedName() {
        return this.internal.getName();
    }

    public Component getLongDisplayName() {
        return ChatUtils.mojangToAdventure(this.internal.getLongDisplayName());
    }

    public Component getShortDisplayName() {
        return ChatUtils.mojangToAdventure(this.internal.getShortDisplayName());
    }

    public GameType toMojang() {
        return this.internal;
    }

    public boolean isBlockPlacingRestricted() {
        return this.internal.isBlockPlacingRestricted();
    }

    public boolean isCreative() {
        return this.internal.isCreative();
    }

    public boolean isSurvival() {
        return this.internal.isSurvival();
    }

    public static BridgedGameType fromMojang(GameType type) {
        for (BridgedGameType gameType : values()) {
            if (gameType.toMojang() == type)
                return gameType;
        }
        return BridgedGameType.SURVIVAL;
    }

}
