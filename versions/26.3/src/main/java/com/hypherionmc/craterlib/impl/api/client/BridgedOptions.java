package com.hypherionmc.craterlib.impl.api.client;

import com.hypherionmc.craterlib.api.game.client.CraterOptions;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Options;

@RequiredArgsConstructor(staticName = "wrap")
public class BridgedOptions implements CraterOptions {

    private final Options internal;

    @Override
    public String getLanguage() {
        return internal.languageCode;
    }

    public Options unwrapInternal() {
        return internal;
    }

}
