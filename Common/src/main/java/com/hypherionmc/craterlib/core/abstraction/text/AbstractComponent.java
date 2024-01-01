package com.hypherionmc.craterlib.core.abstraction.text;

import net.minecraft.network.chat.*;

public class AbstractComponent {

    public static MutableComponent literal(String component) {
        return new TextComponent(component);
    }

    public static MutableComponent translatable(String component) {
        return new TranslatableComponent(component);
    }

    public static MutableComponent translatable(String component, Object... objects) {
        return new TranslatableComponent(component, objects);
    }

    public static Component safeCopy(Component inComponent) {
        String value = inComponent.getString();
        Style style = inComponent.getStyle();
        return new TextComponent(value).withStyle(style);
    }

    public static Component empty() {
        return new TextComponent("");
    }

}
