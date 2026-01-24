package com.hypherionmc.craterlib.impl;

import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.text.TextAdapter;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MojangTextAdapter implements TextAdapter {

    @Override
    public <T> Text serialize(T message) {
        try {
            return Text.fromJson(Component.Serializer.toJsonTree((Component) message));
        } catch (Exception e) {
            return Text.literal(((Component)message).getString());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Text text) {
        return (T) Component.Serializer.fromJson(text.toJson());
    }

    @Override
    public Text getBiomeName(CraterIdentifier name) {
        if (name == null)
            return Text.literal("Unknown");

        return serialize(Component.translatable(Util.makeDescriptionId("biome", (ResourceLocation) name.unwrapInternal())));
    }

}
