package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 * @author HypherionSA
 */
public class InternalConfigButton extends AbstractButton {

    CraterConfigScreen screen;
    boolean cancel;

    public InternalConfigButton(CraterConfigScreen screen, int i, int j, int k, int l, Component component, boolean cancel) {
        super(i, j, k, l, component);
        this.screen = screen;
        this.cancel = cancel;
    }

    @Override
    protected void renderWidget(GuiGraphics arg, int i, int j, float f) {
        if (cancel) {
            setMessage(Component.translatable(screen.isEdited() ? "t.clc.cancel_discard" : "gui.cancel"));
        } else {
            boolean hasErrors = screen.hasErrors();
            active = screen.isEdited() && !hasErrors;
            setMessage(Component.translatable(hasErrors ? "t.clc.error" : "t.clc.save"));
        }
        super.renderWidget(arg, i, j, f);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.USAGE, getMessage());
    }

    @Override
    public void onPress() {
        if (cancel) {
            screen.onClose();
        } else {
            screen.save();
        }
    }


}
