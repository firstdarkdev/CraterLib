package com.hypherionmc.craterlib.core.abstraction.text;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class AbstractComponent {

    public static MutableComponent literal(String component) {
        return Component.literal(component);
    }

    public static MutableComponent translatable(String component) {
        return Component.translatable(component);
    }

    public static Component safeCopy(Component inComponent) {
        String value = inComponent.getString();
        Style style = inComponent.getStyle();
        return Component.literal(value).withStyle(style);
    }

}
