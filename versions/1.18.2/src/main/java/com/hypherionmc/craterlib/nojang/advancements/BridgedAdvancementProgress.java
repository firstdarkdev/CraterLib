package com.hypherionmc.craterlib.nojang.advancements;

import com.hypherionmc.craterlib.utils.ChatUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.advancements.AdvancementProgress;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancementProgress {

    private final AdvancementProgress internal;

    public boolean isDone() {
        return internal.isDone();
    }

    public boolean hasProgress() {
        return internal.hasProgress();
    }

    public float getPercent() {
        return internal.getPercent();
    }

    public Component getProgressText() {
        if (internal.getProgressText() == null) return Component.empty();
        return Component.translatable(internal.getProgressText());
    }

    public Iterable<String> getRemainingCriteria() {
        return internal.getRemainingCriteria();
    }

    public Iterable<String> getCompletedCriteria() {
        return internal.getCompletedCriteria();
    }

    @Nullable
    public Instant getFirstProgressDate() {
        return internal.getFirstProgressDate().toInstant();
    }

    public int compareTo(BridgedAdvancementProgress other) {
        return internal.compareTo(other.internal);
    }

    public AdvancementProgress toMojang() {
        return internal;
    }
}
