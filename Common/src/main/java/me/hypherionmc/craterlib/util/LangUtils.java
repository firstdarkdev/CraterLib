package me.hypherionmc.craterlib.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class LangUtils {

    public static Component getTooltipTitle(String key) {
        return Component.literal(ChatFormatting.YELLOW + Component.translatable(key).getString());
    }

    public static String resolveTranslation(String key) {
        return Component.translatable(key).getString();
    }

    public static Component getTranslation(String key) {
        return Component.translatable(key);
    }

    public static Component makeComponent(String text) {
        return Component.translatable(text);
    }

}
