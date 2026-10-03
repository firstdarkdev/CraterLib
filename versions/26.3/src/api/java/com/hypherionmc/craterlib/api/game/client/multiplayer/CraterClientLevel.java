package com.hypherionmc.craterlib.api.game.client.multiplayer;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.core.CraterBlockPos;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.api.game.text.Text;

public interface CraterClientLevel extends CraterWrappedAPI {

    boolean isClientSide();
    long getGameTime();
    long getDayTime();
    long dayTime();
    boolean isRaining();
    boolean isThundering();
    CraterIdentifier getDimensionKey();
    CraterIdentifier getBiomeIdentifier(CraterBlockPos onPos);
    Text getDifficulty();

}
