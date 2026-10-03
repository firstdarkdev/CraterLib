package com.hypherionmc.craterlib.api.game.network;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.nbt.CraterDataTag;

public interface CraterFriendlyByteBuf extends CraterWrappedAPI {

    CraterDataTag readNbt();
    CraterFriendlyByteBuf writeNbt(CraterDataTag tag);
    CraterFriendlyByteBuf writeUtf(String value);
    String readUtf();

}
