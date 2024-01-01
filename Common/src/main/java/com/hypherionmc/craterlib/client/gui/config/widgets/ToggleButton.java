package com.hypherionmc.craterlib.client.gui.config.widgets;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Function;

/**
 * Copied from Cloth Config Lite
 * <a href="https://github.com/shedaniel/cloth-config-lite/blob/1.17/src/main/java/me/shedaniel/clothconfiglite/impl/option/ToggleOption.java">...</a>
 */
public class ToggleButton<T> extends AbstractConfigWidget<T, Button> {

    private final List<T> options;
    private final Function<T, Component> toComponent;

    public ToggleButton(List<T> options, Function<T, Component> toComponent) {
        this.options = options;
        this.toComponent = toComponent;
        this.widget = addChild(Button.builder(Component.empty(), this::switchNext).size(buttonWidth, buttonHeight).build());
    }

    @Override
    public void onAdd() {
        widget.setMessage(toComponent.apply(value));
    }

    private void switchNext(Button button) {
        value = options.get((options.indexOf(value) + 1) % options.size());
        onAdd();
    }
}
