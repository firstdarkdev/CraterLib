package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;

/**
 * @author HypherionSA
 */
@Deprecated(forRemoval = true, since = "2.1.3")
public class InternalConfigButton extends AbstractButton {

    CraterConfigScreen screen;
    boolean cancel;

    public InternalConfigButton(CraterConfigScreen screen, int i, int j, int k, int l, Component component, boolean cancel) {
        super(i, j, k, l, component);
        this.screen = screen;
        this.cancel = cancel;
    }

    @Override
    protected void renderContents(GuiGraphics arg, int i, int j, float f) {

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.USAGE, getMessage());
    }

    @Override
    public void onPress(InputWithModifiers arg) {
        if (cancel) {
            screen.onClose();
        } else {
            screen.save();
        }
    }

}
