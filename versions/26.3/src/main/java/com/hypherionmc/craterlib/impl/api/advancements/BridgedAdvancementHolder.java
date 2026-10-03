package com.hypherionmc.craterlib.impl.api.advancements;

import com.hypherionmc.craterlib.api.game.achievements.CraterAchievementHolder;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.AdvancementHolder;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedAdvancementHolder implements CraterAchievementHolder {

    private final AdvancementHolder internal;

    @Override
    public BridgedAdvancement value() {
        return BridgedAdvancement.wrap(internal.value());
    }

    @Override
    public AdvancementHolder unwrapInternal() {
        return internal;
    }
}
