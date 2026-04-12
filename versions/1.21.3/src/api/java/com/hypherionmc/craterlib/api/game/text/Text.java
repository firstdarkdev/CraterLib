package com.hypherionmc.craterlib.api.game.text;

import com.google.gson.JsonElement;
import com.hypherionmc.craterlib.api.game.resources.CraterIdentifier;
import com.hypherionmc.craterlib.api.loader.CraterLoader;
import com.hypherionmc.craterlib.api.util.DiscordMarkdownStripper;
import com.hypherionmc.mcdiscordformatter.discord.DiscordSerializer;
import com.hypherionmc.mcdiscordformatter.discord.DiscordSerializerOptions;
import com.hypherionmc.mcdiscordformatter.minecraft.MinecraftSerializer;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONOptions;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.Arrays;
import java.util.List;

public final class Text {

    @Getter
    private Component component;
    private static TextAdapter textAdapter;
    private static final DiscordSerializer DISCORD_SERIALIZER = new DiscordSerializer(DiscordSerializerOptions.defaults().withEscapeMarkdown(false));

    Text(Component c) {
        this.component = c;
    }

    public static void setTextAdapter(TextAdapter textAdapter) {
        Text.textAdapter = textAdapter;
    }

    public static Text empty() {
        return new Text(Component.empty());
    }

    public static Text literal(String text) {
        return new Text(Component.text(text));
    }

    public static Text translatable(String key) {
        return new Text(Component.translatable(key));
    }

    public static Text translatable(String key, Text... value) {
        return new Text(Component.translatable(key, Arrays.stream(value).map(Text::getComponent).toList()));
    }

    @Getter
    private static final GsonComponentSerializer adventureSerializer = GsonComponentSerializer.builder()
            .options(JSONOptions.byDataVersion().at(CraterLoader.dataVersion()))
            .build();

    @Getter
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static Text from(Component message) {
        return new Text(message);
    }

    public List<Text> children() {
        return component.children().stream().map(Text::new).toList();
    }

    public Text append(Text text) {
        component = component.append(text.component);
        return this;
    }

    public Style style() {
        return component.style();
    }

    public Text style(Style style) {
        component = component.style(style);
        return this;
    }

    public Text style(Style.Builder builder) {
        component = component.style(builder);
        return this;
    }

    public Text mergeStyle(Text that) {
        component = component.mergeStyle(that.component);
        return this;
    }

    public TextColor color() {
        return component.color();
    }

    public Text color(TextColor color) {
        component = component.color(color);
        return this;
    }

    public boolean hasDecoration(TextDecoration decoration) {
        return component.hasDecoration(decoration);
    }

    public Text decorate(TextDecoration decoration) {
        component = component.decorate(decoration);
        return this;
    }

    public ClickEvent clickEvent() {
        return component.clickEvent();
    }

    public Text clickEvent(ClickEvent event) {
        component = component.clickEvent(event);
        return this;
    }

    public HoverEvent<?> hoverEvent() {
        return component.style().hoverEvent();
    }

    public Text hoverEvent(HoverEventSource<?> source) {
        component = component.hoverEvent(source);
        return this;
    }

    public Text applyFallbackStyle(Style style) {
        component = component.applyFallbackStyle(style);
        return this;
    }

    public Text appendNewline() {
        component = component.appendNewline();
        return this;
    }

    // Utilities
    public static Text fromJson(JsonElement element) {
        return new Text(adventureSerializer.deserializeFromTree(element));
    }

    public static Text fromJson(String json) {
        return new Text(adventureSerializer.deserialize(json));
    }

    public JsonElement toJson() {
        return adventureSerializer.serializeToTree(component);
    }

    public String toJsonString() {
        return adventureSerializer.serialize(component);
    }

    public static <T> Text fromGame(T gameText) {
        return textAdapter.serialize(gameText);
    }

    public <T> T toGame() {
        return textAdapter.deserialize(this);
    }

    public Text safeCopy(Text text) {
        String value = text.asString();
        Style style = text.component.style();
        return new Text(Component.text(value).style(style));
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

    public String asString() {
        return asString(false);
    }

    public String asString(boolean formatted) {
        String value = DiscordMarkdownStripper.stripMarkdown(getString(component));

        if (formatted) {
            value = DISCORD_SERIALIZER.serialize(component);
        }

        return value;
    }

    public static Text fromString(String text) {
        return fromString(text, false);
    }

    public static Text fromString(String text, boolean formatted) {
        Component c = Component.text(text);

        if (formatted) {
            c = MinecraftSerializer.INSTANCE.serialize(text);
        }

        return new Text(c);
    }

    private static String getString(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static Text getBiomeName(CraterIdentifier identifier) {
        return textAdapter.getBiomeName(identifier);
    }

    public static String resolveTranslation(String key) {
        return Component.translatable(key).key();
    }

    public static Text getTranslation(String key) {
        return new Text(Component.translatable(key));
    }

    public static Text formatted(String value) {
        value = convertFormattingCodes(value);

        try {
            return new Text(miniMessage.deserializeOr(value, Component.text(value)));
        } catch (Exception ignored) {
            // Mini message fails to format text that contain legacy formatting. Since we support both, that's bad.
            // We just ignore the exception here so that the whole format doesn't fail
        }

        return new Text(Component.text(value));
    }

    private static String convertFormattingCodes(String input) {
        return input.replaceAll("§([0-9a-fklmnor])", "\u00A7$1");
    }
}
