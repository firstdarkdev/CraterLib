package com.hypherionmc.craterlib.impl.api.advancements;

import com.hypherionmc.craterlib.api.game.achievements.CraterAchievement;
import com.hypherionmc.craterlib.api.game.achievements.CraterDisplayInfo;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;

import java.util.Optional;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedAdvancement implements CraterAchievement {

    private final Advancement internal;

    @Override
    public Optional<CraterDisplayInfo> displayInfo() {
        if (internal.getDisplay() != null) {
            return Optional.of(BridgedDisplayInfo.wrap(internal.getDisplay()));
        }

        return Optional.empty();
    }

    @Override
    public Advancement unwrapInternal() {
        return internal;
    }
}
