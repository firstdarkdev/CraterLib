package com.hypherionmc.craterlib.utils;

import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;
import me.hypherionmc.mcdiscordformatter.discord.DiscordSerializer;
import me.hypherionmc.mcdiscordformatter.minecraft.MinecraftSerializer;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class ChatUtils {

    public static Component adventureToMojang(net.kyori.adventure.text.Component inComponent) {
        final String serialised = GsonComponentSerializer.gson().serialize(inComponent);
        return Component.Serializer.fromJson(serialised);
    }

    public static net.kyori.adventure.text.Component mojangToAdventure(Component inComponent) {
        final String serialised = Component.Serializer.toJson(inComponent);
        return GsonComponentSerializer.gson().deserialize(serialised);
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
