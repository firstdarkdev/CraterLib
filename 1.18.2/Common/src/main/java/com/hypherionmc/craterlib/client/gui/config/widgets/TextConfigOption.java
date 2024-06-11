package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.util.function.Function;

/**
 * Copied from Cloth Config Lite
 * <a href="https://github.com/shedaniel/cloth-config-lite/blob/1.17/src/main/java/me/shedaniel/clothconfiglite/impl/option/TextFieldOption.java">...</a>
 */
public class TextConfigOption<T> extends AbstractConfigWidget<T, WrappedEditBox> {

    private final Function<T, String> toString;
    private final Function<String, T> fromString;

    public TextConfigOption(Function<T, String> toString, Function<String, T> fromString) {
        this.toString = toString;
        this.fromString = fromString;
        this.widget = addChild(new WrappedEditBox(Minecraft.getInstance().font, 0, 0, 198, 18, null));
    }

    @Override
    public void onAdd() {
        widget.setMaxLength(1000000);
        widget.setValue(toString.apply(value));
        widget.setResponder(this::update);
    }

    @Override
    public void render(Minecraft minecraft, Font font, int x, int y, int width, int height, PoseStack matrices, int mouseX, int mouseY, float delta) {
        widget.setTextColor(hasErrors ? 16733525 : 14737632);
        super.render(minecraft, font, x, y, width, height, matrices, mouseX, mouseY, delta);
    }

    private void update(String s) {
        try {
            this.value = fromString.apply(s);
            this.hasErrors = false;
        } catch (Exception e) {
            this.hasErrors = true;
        }
    }
}
