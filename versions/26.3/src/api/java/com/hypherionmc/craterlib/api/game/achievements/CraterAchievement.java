package com.hypherionmc.craterlib.api.game.achievements;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;

import java.util.Optional;

public interface CraterAchievement extends CraterWrappedAPI {

    Optional<CraterDisplayInfo> displayInfo();

}
