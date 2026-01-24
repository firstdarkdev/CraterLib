package com.hypherionmc.craterlib.api.game.world.entity.player;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.core.CraterBlockPos;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.world.level.CraterGameType;

import java.util.UUID;

public interface CraterPlayer extends CraterWrappedAPI {

    Text getDisplayName();
    Text getName();
    UUID getUUID();
    String getStringUUID();
    CraterGameProfile getGameProfile();
    boolean isServerPlayer();
    CraterBlockPos getOnPos();
    float getHealth();
    float getMaxHealth();
    String getHeldItemMainHand();
    String getHeldItemOffHand();
    CraterGameType getGameMode();
    void disconnect(Text message);

}
