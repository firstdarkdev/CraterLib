package com.hypherionmc.craterlib.impl.api.advancements;

import com.hypherionmc.craterlib.api.game.achievements.CraterAchievementHolder;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedAdvancementHolder implements CraterAchievementHolder {

    private final Advancement internal;

    @Override
    public BridgedAdvancement value() {
        return BridgedAdvancement.wrap(internal);
    }

    @Override
    public Advancement unwrapInternal() {
        return internal;
    }
}
