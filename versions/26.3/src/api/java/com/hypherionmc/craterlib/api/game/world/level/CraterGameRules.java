package com.hypherionmc.craterlib.api.game.world.level;

public interface CraterGameRules {

    boolean getBoolean(CraterCommonGameRules.GameRuleKey<Boolean> key);
    int getInt(CraterCommonGameRules.GameRuleKey<Integer> key);

}
