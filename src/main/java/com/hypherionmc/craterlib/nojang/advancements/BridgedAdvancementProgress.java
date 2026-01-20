package com.hypherionmc.craterlib.nojang.advancements;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

// TODO: Implement
@RequiredArgsConstructor(staticName = "of")
public class BridgedAdvancementProgress {

    //private final AdvancementProgress internal;

    public boolean isDone() {
        return false;
    }

    public boolean hasProgress() {
        return false;
    }

    public float getPercent() {
        return 0f;
    }

    public Component getProgressText() {
        return Component.empty();
    }

    public Iterable<String> getRemainingCriteria() {
        //return internal.getRemainingCriteria();
        return null;
    }

    public Iterable<String> getCompletedCriteria() {
        return null;
    }

    @Nullable
    public Instant getFirstProgressDate() {
        return Instant.now();
        //return internal.getFirstProgressDate();
    }

    public int compareTo(BridgedAdvancementProgress other) {
        return 0;
        //return internal.compareTo(other.internal);
    }

//    public AdvancementProgress toMojang() {
//        return internal;
//    }
}
