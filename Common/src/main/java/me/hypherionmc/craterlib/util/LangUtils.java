package me.hypherionmc.craterlib.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class LangUtils {

    public static Component getTooltipTitle(String key) {
        return new TextComponent(ChatFormatting.YELLOW + new TranslatableComponent(key).getString());
    }

    public static String resolveTranslation(String key) {
        return new TranslatableComponent(key).getString();
    }

    public static Component getTranslation(String key) {
        return new TranslatableComponent(key);
    }

    public static Component makeComponent(String text) {
        return new TranslatableComponent(text);
    }

}
