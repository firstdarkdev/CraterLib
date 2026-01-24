package com.hypherionmc.craterlib.impl.api.world.level;

import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.level.CraterGameType;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.level.GameType;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedGameType implements CraterGameType {

    private final GameType internal;

    @Override
    public String toString() {
        return this.internal.getName();
    }

    public String getName() {
        return this.internal.getName();
    }

    public String getSerializedName() {
        return this.internal.getName();
    }

    public Text getLongDisplayName() {
        return Text.fromGame(this.internal.getLongDisplayName());
    }

    public Text getShortDisplayName() {
        return Text.fromGame(this.internal.getShortDisplayName());
    }

    public GameType unwrapInternal() {
        return this.internal;
    }

    @Override
    public Type getMode() {
        return fromMojang(internal);
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

    private static Type fromMojang(GameType type) {
        switch (type) {
            case ADVENTURE -> {
                return Type.ADVENTURE;
            }
            case CREATIVE -> {
                return Type.CREATIVE;
            }
            case SPECTATOR -> {
                return Type.SPECTATOR;
            }
            default -> {
                return Type.SURVIVAL;
            }
        }
    }

}
