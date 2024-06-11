package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;

/**
 * Copied from Cloth Config Lite
 * <a href="https://github.com/shedaniel/cloth-config-lite/blob/1.17/src/main/java/me/shedaniel/clothconfiglite/impl/option/AbstractWidgetOption.java">...</a>
 */
public class AbstractConfigWidget<T, W extends AbstractWidget> extends BaseWidget<T> {

    public static final int buttonWidth = 200;
    public static final int buttonHeight = 20;
    public W widget;

    @Override
    public void render(Minecraft minecraft, Font font, int x, int y, int width, int height, PoseStack matrices, int mouseX, int mouseY, float delta) {
        super.render(minecraft, font, x, y, width, height, matrices, mouseX, mouseY, delta);
        int i = (widget instanceof EditBox ? 1 : 0);
        widget.x = (x + width - 200 - resetButtonOffset + i);
        widget.y = (y + i + 1);
        widget.render(matrices, mouseX, mouseY, delta);
    }
}
