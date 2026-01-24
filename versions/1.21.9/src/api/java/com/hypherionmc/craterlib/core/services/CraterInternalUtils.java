package com.hypherionmc.craterlib.core.services;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.game.client.CraterGame;
import com.hypherionmc.craterlib.api.util.CraterLogger;

public interface CraterInternalUtils {

    CraterLogger getLogger(String name);

    CraterGame getGameInstance();

    CraterCommand createCommand(String name);
}
