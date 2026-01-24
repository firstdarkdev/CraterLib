package com.hypherionmc.craterlib.api.game.text;

import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;

public interface TextAdapter {

    <T> Text serialize(T message);
    <T> T deserialize(Text text);
    Text getBiomeName(CraterIdentifier name);

}
