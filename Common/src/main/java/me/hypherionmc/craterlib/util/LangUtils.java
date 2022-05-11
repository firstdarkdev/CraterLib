package me.hypherionmc.hyperlighting.utils;

import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class LangUtils {

    public static BaseText getTooltipTitle(String key) {
        return new LiteralText(Formatting.YELLOW + new TranslatableText(key).getString());
    }

    public static String resolveTranslation(String key) {
        return new TranslatableText(key).getString();
    }

    public static BaseText getTranslation(String key) {
        return new TranslatableText(key);
    }

    public static BaseText makeComponent(String text) {
        return new LiteralText(text);
    }

}
