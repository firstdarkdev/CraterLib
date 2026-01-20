package com.hypherionmc.craterlib.utils;

import com.google.gson.JsonElement;
import com.hypherionmc.craterlib.nojang.resources.ResourceIdentifier;
import com.hypherionmc.mcdiscordformatter.discord.DiscordSerializer;
import com.hypherionmc.mcdiscordformatter.minecraft.MinecraftSerializer;
import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.server.core.Message;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONOptions;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ChatUtils {

    @Getter
    private static final GsonComponentSerializer adventureSerializer = GsonComponentSerializer.builder().options(
            JSONOptions.compatibility()
    ).build();

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Message adventureToMojang(Component inComponent) {
        return HytaleAdventureAdapter.deserialize(inComponent);
    }

    public static Component mojangToAdventure(Message inComponent) {
        try {
            final JsonElement serialised = HytaleAdventureAdapter.serialize(inComponent);
            return adventureSerializer.deserializeFromTree(serialised);
        } catch (Exception e) {
            e.printStackTrace();
            return Component.text("");
        }
    }

    // Some text components contain duplicate text, resulting in duplicate messages
    // sent back to discord. This should help fix those issues
    public static Component safeCopy(Component inComponent) {
        String value = getString(inComponent);
        Style style = inComponent.style();
        return Component.text(value).style(style);
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

    public static String resolve(Component component, boolean formatted) {
        String returnVal = getString(component);

        if (formatted) {
            returnVal = DiscordSerializer.INSTANCE.serialize(safeCopy(component));
        }

        return returnVal;
    }

    public static Component resolve(String component, boolean formatted) {
        Component returnVal = Component.text(component);

        if (formatted) {
            returnVal = MinecraftSerializer.INSTANCE.serialize(component);
        }

        return returnVal;
    }

    public static Component getTooltipTitle(String key) {
        return Component.text(NamedTextColor.YELLOW + Component.translatable(key).key());
    }

    public static String resolveTranslation(String key) {
        return Component.translatable(key).key();
    }

    public static Component getTranslation(String key) {
        return Component.translatable(key);
    }

    public static Component makeComponent(String text) {
        return Component.translatable(text);
    }

    public static Component getBiomeName(ResourceIdentifier identifier) {
        if (identifier == null)
            return Component.text("Unknown");

        return Component.text(identifier.getPath());
    }

    public static Component format(String value) {
        value = convertFormattingCodes(value);

        try {
            return miniMessage.deserializeOr(value, Component.text(value));
        } catch (Exception ignored) {
            // Mini message fails to format text that contain legacy formatting. Since we support both, that's bad.
            // We just ignore the exception here so that the whole format doesn't fail
        }

        return Component.text(value);
    }

    public static String getString(Component in) {
        return PlainTextComponentSerializer.plainText().serialize(in);
    }

    private static String convertFormattingCodes(String input) {
        return input.replaceAll("§([0-9a-fklmnor])", "\u00A7$1");
    }
}
