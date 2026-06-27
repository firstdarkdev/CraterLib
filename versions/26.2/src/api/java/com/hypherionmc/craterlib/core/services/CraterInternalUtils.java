package com.hypherionmc.craterlib.core.services;

import com.hypherionmc.craterlib.api.commands.CraterCommand;
import com.hypherionmc.craterlib.api.game.authlib.CraterGameProfile;
import com.hypherionmc.craterlib.api.game.client.CraterGame;
import com.hypherionmc.craterlib.api.game.commands.CraterFakePlayer;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.api.game.server.CraterGameServer;
import com.hypherionmc.craterlib.api.util.CraterLogger;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface CraterInternalUtils {

    CraterLogger getLogger(String name);

    CraterGame getGameInstance();

    CraterCommand createCommand(String name);

    CraterGameProfile createGameProfile(String name, UUID uuid);

    CraterIdentifier createIdentifier(String name, @Nullable String path);

    CraterFakePlayer createFakePlayer(CraterGameServer server, int permLevel, String name, CraterFakePlayer handler);
}
