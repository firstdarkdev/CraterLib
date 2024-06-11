package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;

/**
 * Copied from Cloth Config Lite
 * <a href="https://github.com/shedaniel/cloth-config-lite/blob/1.17/src/main/java/me/shedaniel/clothconfiglite/impl/option/BaseOption.java">...</a>
 */
public class BaseWidget<T> extends Option<T> {

    public static final int resetButtonOffset = 48;
    private final Button resetButton = addChild(new Button(0, 0, 46, 20, new TextComponent("Reset"), this::onResetPressed));
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
    public void render(Minecraft minecraft, Font font, int x, int y, int width, int height, PoseStack matrices, int mouseX, int mouseY, float delta) {
        MutableComponent text = new TextComponent(this.text.getString());
        boolean edited = isEdited() || hasErrors;
        if (edited) {
            text.withStyle(ChatFormatting.ITALIC);
            if (hasErrors) {
                text.withStyle(style -> style.withColor(TextColor.fromRgb(16733525)));
            }
        } else {
            text.withStyle(ChatFormatting.GRAY);
        }
        font.draw(matrices, text, x, y, 0xFFFFFF);
        resetButton.x = (x + width - 46);
        resetButton.y = (y + 1);
        resetButton.active = isNotDefault();
        if (!hideReset) {
            resetButton.render(matrices, mouseX, mouseY, delta);
        }
    }
}
