package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import com.hypherionmc.craterlib.core.config.ModuleConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * @author HypherionSA
 */
public class SubConfigWidget<T> extends AbstractConfigWidget<T, Button> {

    private final Object subConfig;
    private final ModuleConfig config;
    private final Screen screen;

    public SubConfigWidget(ModuleConfig config, Screen screen, Object subConfig) {
        this.config = config;
        this.subConfig = subConfig;
        this.screen = screen;

        this.widget = addChild(new Button(0, 0, 200, buttonHeight, Component.translatable("t.clc.opensubconfig"), this::openSubConfig));
    }

    @Override
    public void render(Minecraft minecraft, Font font, int x, int y, int width, int height, PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.text = Component.literal(subConfig.getClass().getSimpleName().toLowerCase());
        this.hideReset();
        super.render(minecraft, font, x, y, width, height, matrices, mouseX, mouseY, delta);
    }

    private void openSubConfig(Button button) {
        Minecraft.getInstance().setScreen(new CraterConfigScreen(config, screen, subConfig));
    }

}
