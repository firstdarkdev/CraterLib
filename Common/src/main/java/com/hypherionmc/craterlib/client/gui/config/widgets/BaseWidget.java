package com.hypherionmc.craterlib.client.gui.config.widgets;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;

/**
 * Copied from Cloth Config Lite
 * <a href="https://github.com/shedaniel/cloth-config-lite/blob/1.17/src/main/java/me/shedaniel/clothconfiglite/impl/option/BaseOption.java">...</a>
 */
public class BaseWidget<T> extends Option<T> {

    public static final int resetButtonOffset = 48;
    private final Button resetButton = addChild(Button.builder(Component.literal("Reset"), this::onResetPressed).size(46, 20).build());
    private boolean hideReset = false;

    private boolean isSubConfig = false;

    private void onResetPressed(Button button) {
        value = defaultValue.get();
        reset();
    }

    public void hideReset() {
        this.hideReset = true;
    }

    public boolean isSubConfig() {
        return isSubConfig;
    }

    public void setSubConfig(boolean subConfig) {
        isSubConfig = subConfig;
    }

    @Override
    public void render(Minecraft minecraft, Font font, int x, int y, int width, int height, GuiGraphics matrices, int mouseX, int mouseY, float delta) {
        MutableComponent text = Component.literal(this.text.getString());
        boolean edited = isEdited() || hasErrors;
        if (edited) {
            text.withStyle(ChatFormatting.ITALIC);
            if (hasErrors) {
                text.withStyle(style -> style.withColor(TextColor.fromRgb(16733525)));
            }
        } else {
            text.withStyle(ChatFormatting.GRAY);
        }
        matrices.drawString(font, text, x, y + font.lineHeight - 2, 0xFFFFFF);
        resetButton.setX(x + width - 46);
        resetButton.setY(y + 1);
        resetButton.active = isNotDefault();
        if (!hideReset) {
            resetButton.render(matrices, mouseX, mouseY, delta);
        }
    }
}
