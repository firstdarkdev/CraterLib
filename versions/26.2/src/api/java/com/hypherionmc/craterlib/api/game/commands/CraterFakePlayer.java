package com.hypherionmc.craterlib.api.game.commands;

import com.hypherionmc.craterlib.api.game.CraterWrappedAPI;
import com.hypherionmc.craterlib.api.game.server.CraterGameServer;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.core.services.CraterServices;

import java.util.function.Supplier;

public interface CraterFakePlayer extends CraterWrappedAPI {

    void onSuccess(Supplier<Text> message, boolean bl);

    default void onError(Text message) {
        onSuccess(() -> message, false);
    }

    static CraterFakePlayer create(CraterGameServer server, int permLevel, String name, CraterFakePlayer handler) {
        return CraterServices.UTILS.createFakePlayer(server, permLevel, name, handler);
    }
}
