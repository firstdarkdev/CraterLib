package com.hypherionmc.craterlib.nojang.client;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.Options;

@RequiredArgsConstructor(staticName = "of")
public class BridgedOptions {

    private final Options internal;

    public String getLanguage() {
        return internal.languageCode;
    }

}
