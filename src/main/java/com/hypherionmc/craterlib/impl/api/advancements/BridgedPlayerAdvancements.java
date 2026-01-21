package com.hypherionmc.craterlib.impl.api.advancements;

import com.hypherionmc.craterlib.api.game.achievements.CraterAchievementHolder;
import com.hypherionmc.craterlib.api.game.achievements.CraterPlayerAdvancements;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.PlayerAdvancements;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedPlayerAdvancements implements CraterPlayerAdvancements {

    private final PlayerAdvancements internal;

    @Override
    public BridgedAdvancementProgress getOrStartProgress(CraterAchievementHolder achievement) {
        return BridgedAdvancementProgress.wrap(internal.getOrStartProgress(achievement.value().unwrap()));
    }

    public PlayerAdvancements unwrapInternal() {
        return internal;
    }

}
