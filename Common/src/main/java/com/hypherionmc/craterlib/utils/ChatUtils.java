package com.hypherionmc.craterlib.utils;

import com.hypherionmc.craterlib.core.platform.CommonPlatform;
import com.hypherionmc.craterlib.core.platform.ModloaderEnvironment;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;
import me.hypherionmc.mcdiscordformatter.discord.DiscordSerializer;
import me.hypherionmc.mcdiscordformatter.minecraft.MinecraftSerializer;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONOptions;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.function.Consumer;

public class ChatUtils {

    private static final GsonComponentSerializer adventureSerializer = GsonComponentSerializer.builder().options(
            JSONOptions.byDataVersion().at(SharedConstants.getCurrentVersion().getDataVersion().getVersion())
    ).build();

    public static Component adventureToMojang(net.kyori.adventure.text.Component inComponent) {
        final String serialised = adventureSerializer.serialize(inComponent);
        return Component.Serializer.fromJson(serialised, getRegistryLookup());
    }

    public static net.kyori.adventure.text.Component mojangToAdventure(Component inComponent) {
        final String serialised = Component.Serializer.toJson(inComponent, getRegistryLookup());
        return adventureSerializer.deserialize(serialised);
    }

    private static HolderLookup.Provider getRegistryLookup() {
        if (ModloaderEnvironment.INSTANCE.getEnvironment().isClient() && Minecraft.getInstance().level != null)
            return Minecraft.getInstance().level.registryAccess();

        if (ModloaderEnvironment.INSTANCE.getEnvironment().isServer() && CommonPlatform.INSTANCE.getMCServer() != null)
            return CommonPlatform.INSTANCE.getMCServer().toMojang().registryAccess();

        return RegistryAccess.EMPTY;
    }

    // Some text components contain duplicate text, resulting in duplicate messages
    // sent back to discord. This should help fix those issues
    public static Component safeCopy(Component inComponent) {
        String value = inComponent.getString();
        Style style = inComponent.getStyle();
        return Component.literal(value).withStyle(style);
    }

    public static String strip(String inString, String... toStrip) {
        String finalString = inString;

        for (String strip : toStrip) {
            if (finalString.startsWith(strip))
                finalString = finalString.replaceFirst(strip, "");

            if (finalString.startsWith(" "))
                finalString = finalString.replaceFirst(" ", "");
        }

        return finalString;
    }

    public static String resolve(net.kyori.adventure.text.Component component, boolean formatted) {
        Component c = adventureToMojang(component);
        String returnVal = ChatFormatting.stripFormatting(c.getString());

        if (formatted) {
            returnVal = DiscordSerializer.INSTANCE.serialize(safeCopy(c).copy());
        }

        return returnVal;
    }

    public static net.kyori.adventure.text.Component resolve(String component, boolean formatted) {
        Component returnVal = Component.literal(component);
        if (formatted) {
            returnVal = MinecraftSerializer.INSTANCE.serialize(component);
        }

        return mojangToAdventure(returnVal);
    }

    public static net.kyori.adventure.text.Component getTooltipTitle(String key) {
        return net.kyori.adventure.text.Component.text(NamedTextColor.YELLOW + net.kyori.adventure.text.Component.translatable(key).key());
    }

    public static String resolveTranslation(String key) {
        return net.kyori.adventure.text.Component.translatable(key).key();
    }

    public static net.kyori.adventure.text.Component getTranslation(String key) {
        return net.kyori.adventure.text.Component.translatable(key);
    }

    public static net.kyori.adventure.text.Component makeComponent(String text) {
        return net.kyori.adventure.text.Component.translatable(text);
    }

    public static net.kyori.adventure.text.Component getBiomeName(ResourceIdentifier identifier) {
        if (identifier == null)
            return net.kyori.adventure.text.Component.text("Unknown");

        return mojangToAdventure(Component.translatable(Util.makeDescriptionId("biome", identifier.toMojang())));
    }

}
