package com.hypherionmc.craterlib.utils;

import com.google.gson.*;
import com.hypixel.hytale.protocol.FormattedMessage;
import com.hypixel.hytale.protocol.MaybeBool;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.modules.i18n.I18nModule;
import com.hypixel.hytale.server.core.util.MessageUtil;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Arrays;

class HytaleAdventureAdapter {

    static JsonElement serialize(Message m) {
        return serialize(m.getFormattedMessage());
    }

    static JsonElement serialize(FormattedMessage m) {
        JsonObject element = new JsonObject();

        String translatedMessages = "";

        if (m.messageId != null) {
            String message = I18nModule.get().getMessage("en-US", m.messageId);
            if (message != null)
                translatedMessages = MessageUtil.formatText(message, m.params, m.messageParams);
        }

        element.addProperty("text", getOrElse(m.rawText, translatedMessages));

        if (m.color != null) {
            element.addProperty("color", m.color);
        }

        element.addProperty("bold", m.bold == MaybeBool.True);
        element.addProperty("italic", m.italic == MaybeBool.True);

        if (m.link != null) {
            JsonObject click = new JsonObject();
            click.addProperty("url", m.link);
            click.addProperty("action", "open_url");
            element.add("click_event", click);
        }

        if (m.children != null && m.children.length > 0) {
            JsonArray extra = new JsonArray();
            Arrays.stream(m.children).map(HytaleAdventureAdapter::serialize).forEach(extra::add);
            element.add("extra", extra);
        }

        return element;
    }

    static Message deserialize(Component e) {
        Message m = Message.empty();

        boolean hasChildren = !e.children().isEmpty();
        if (!hasChildren) {
            String content = ChatUtils.getString(e);
            if (!content.isBlank()) {
                m = Message.raw(content);
            }
        }

        if (e.color() != null)
            m = m.color(e.color().asHexString());

        Style style = e.style();

        if (style.hasDecoration(TextDecoration.BOLD))
            m = m.bold(true);

        if (style.hasDecoration(TextDecoration.ITALIC))
            m = m.italic(true);

        if (style.clickEvent() != null && style.clickEvent().action() == ClickEvent.Action.OPEN_URL)
            m = m.link(style.clickEvent().value());

        if (!e.children().isEmpty()) {
            for (Component child : e.children()) {
                m = m.insert(deserialize(child));
            }
        }

        return m;
    }

    private static String getOrElse(String first, String second) {
        if (first == null) {
            return second == null ? "" : second;
        }

        return first;
    }

}
