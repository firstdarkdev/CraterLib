package com.hypherionmc.craterlib.impl;

import com.google.auto.service.AutoService;
import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.client.CraterGame;
import com.hypherionmc.craterlib.api.game.commands.CraterFakePlayer;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.api.game.server.CraterGameServer;
import com.hypherionmc.craterlib.api.util.CraterLogger;
import com.hypherionmc.craterlib.core.services.CraterInternalUtils;
import com.hypherionmc.craterlib.impl.api.authlib.BridgedGameProfile;
import com.hypherionmc.craterlib.impl.api.client.BridgedMinecraft;
import com.hypherionmc.craterlib.impl.api.commands.BridgedFakePlayer;
import com.hypherionmc.craterlib.impl.api.commands.CraterCommandImpl;
import com.hypherionmc.craterlib.impl.api.resources.ResourceIdentifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

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
        return CraterCommandImpl.create(name);
    }

    @Override
    public CraterGameProfile createGameProfile(String name, UUID uuid) {
        return BridgedGameProfile.mojang(uuid, name);
    }

    @Override
    public CraterIdentifier createIdentifier(String name, @Nullable String path) {
        if (path == null)
            return new ResourceIdentifier(name);

        return new ResourceIdentifier(name, path);
    }

    @Override
    public CraterFakePlayer createFakePlayer(CraterGameServer server, int permLevel, String name, CraterFakePlayer handler) {
        return new BridgedFakePlayer(server, permLevel, name, handler);
    }
}
