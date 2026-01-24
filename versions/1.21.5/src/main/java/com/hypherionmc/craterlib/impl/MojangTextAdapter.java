package com.hypherionmc.craterlib.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.api.game.text.Text;
import com.hypherionmc.craterlib.api.game.text.TextAdapter;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.mojang.serialization.JsonOps;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

public class MojangTextAdapter implements TextAdapter {

    @Override
    public <T> Text serialize(T message) {
        try {
            final JsonElement serialised = ComponentSerialization.CODEC
                    .encodeStart(JsonOps.INSTANCE, (Component) message)
                    .getOrThrow(JsonParseException::new);

            return Text.fromJson(serialised);
        } catch (Exception e) {
            return Text.literal(((Component)message).getString());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Text text) {
        return (T) ComponentSerialization.CODEC
                .parse(getRegistryLookup().createSerializationContext(JsonOps.INSTANCE), text.toJson())
                .getOrThrow(JsonParseException::new);
    }

    @Override
    public Text getBiomeName(CraterIdentifier name) {
        if (name == null)
            return Text.literal("Unknown");

        return serialize(Component.translatable(Util.makeDescriptionId("biome", (ResourceLocation) name.unwrapInternal())));
    }

    private static HolderLookup.Provider getRegistryLookup() {
        // @noplugin
        if (CraterLoader.getEnvironment().isClient() && Minecraft.getInstance().level != null)
            return Minecraft.getInstance().level.registryAccess();
        // #noplugin

        if (CraterLoader.getEnvironment().isServer() && CraterLoader.getServer() != null)
            return ((MinecraftServer) CraterLoader.getServer().unwrapInternal()).registryAccess();

        return RegistryAccess.EMPTY;
    }
}
