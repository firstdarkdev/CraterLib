package me.hypherionmc.craterlib.client.gui.config.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import me.hypherionmc.craterlib.client.gui.config.CraterConfigScreen;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class InternalConfigButton extends AbstractButton {

    CraterConfigScreen screen;
    boolean cancel;

    public InternalConfigButton(CraterConfigScreen screen, int i, int j, int k, int l, Component component, boolean cancel) {
        super(i, j, k, l, component);
        this.screen = screen;
        this.cancel = cancel;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        if (cancel) {
            setMessage(Component.translatable(screen.isEdited() ? "t.clc.cancel_discard" : "gui.cancel"));
        } else {
            boolean hasErrors = screen.hasErrors();
            active = screen.isEdited() && !hasErrors;
            setMessage(Component.translatable(hasErrors ? "t.clc.error" : "t.clc.save"));
        }
        super.render(poseStack, i, j, f);
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
