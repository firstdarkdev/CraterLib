package com.hypherionmc.craterlib.impl;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.game.client.CraterGame;
import com.hypherionmc.craterlib.api.util.CraterLogger;
import com.hypherionmc.craterlib.core.services.CraterInternalUtils;
import com.hypherionmc.craterlib.impl.api.client.BridgedMinecraft;

@AutoService(CraterInternalUtils.class)
public class CraterLoaderUtils implements CraterInternalUtils {

    @Override
    public CraterLogger getLogger(String name) {
        return new CraterLoggerImpl(name);
    }

    @Override
    public CraterGame getGameInstance() {
        return new BridgedMinecraft();
    }

    @Override
    public CraterCommand createCommand(String name) {
        return CraterCommand.literal(name);
    }
}
