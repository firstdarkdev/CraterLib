package com.hypherionmc.craterlib.impl.api.advancements;

import com.hypherionmc.craterlib.api.game.achievements.CraterAchievementProgress;
import com.hypherionmc.craterlib.api.game.text.Text;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.AdvancementProgress;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedAdvancementProgress implements CraterAchievementProgress {

    private final AdvancementProgress internal;

    @Override
    public boolean isDone() {
        return internal.isDone();
    }

    @Override
    public boolean hasProgress() {
        return internal.hasProgress();
    }

    @Override
    public float getPercent() {
        return internal.getPercent();
    }

    @Override
    public Text getProgressText() {
        return Text.fromGame(internal.getProgressText());
    }

    @Override
    public Iterable<String> getRemainingCriteria() {
        return internal.getRemainingCriteria();
    }

    @Override
    public Iterable<String> getCompletedCriteria() {
        return internal.getCompletedCriteria();
    }

    @Nullable
    public Instant getFirstProgressDate() {
        return internal.getFirstProgressDate().toInstant();
    }

    @Override
    public int compareTo(CraterAchievementProgress other) {
        return internal.compareTo(other.unwrap());
    }

    @Override
    public AdvancementProgress unwrapInternal() {
        return internal;
    }
}
