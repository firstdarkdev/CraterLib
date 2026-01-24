package com.hypherionmc.craterlib.api.game.commands;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.text.Text;

import java.util.function.Supplier;

public interface CraterFakePlayer extends CraterWrappedAPI {

    void onSuccess(Supplier<Text> message, boolean bl);

    default void onError(Text message) {
        onSuccess(() -> message, false);
    }

}
