package com.hypherionmc.craterlib.api.game.achievements;


import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

public interface CraterPlayerAdvancements extends CraterWrappedAPI {

    CraterAchievementProgress getOrStartProgress(CraterAchievementHolder achievement);
}
