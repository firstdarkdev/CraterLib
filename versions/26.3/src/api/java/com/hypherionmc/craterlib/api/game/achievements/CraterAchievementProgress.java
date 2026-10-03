package com.hypherionmc.craterlib.api.game.achievements;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public interface CraterAchievementProgress extends CraterWrappedAPI {

    boolean isDone();
    boolean hasProgress();
    float getPercent();
    Text getProgressText();
    Iterable<String> getRemainingCriteria();
    Iterable<String> getCompletedCriteria();
    @Nullable Instant getFirstProgressDate();
    int compareTo(CraterAchievementProgress other);

}
