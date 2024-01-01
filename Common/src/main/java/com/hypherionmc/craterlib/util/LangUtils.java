package com.hypherionmc.craterlib.util;

import com.hypherionmc.craterlib.core.abstraction.text.AbstractComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/**
 * @author HypherionSA
 * Utility class to handle Translation Keys and Formatting
 */
public class LangUtils {

    public static Component getTooltipTitle(String key) {
        return AbstractComponent.literal(ChatFormatting.YELLOW + AbstractComponent.translatable(key).getString());
    }

    public static String resolveTranslation(String key) {
        return AbstractComponent.translatable(key).getString();
    }

    public static Component getTranslation(String key) {
        return AbstractComponent.translatable(key);
    }

    public static Component makeComponent(String text) {
        return AbstractComponent.translatable(text);
    }

}
