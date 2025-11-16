package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author HypherionSA
 * A Custom Cloth Config GUI entry to allow buttons to be added to the GUI
 */
public class ClothConfigButtonEntry extends AbstractConfigListEntry<Void> {

    private final Button button;
    private final Button deleteButton;
    private final Component displayName;
    private final boolean hasDeleteButton;
    private final boolean wasEdited;

    /**
     * Create a new Cloth Button Entry, that will have no delete button
     *
     * @param displayName The Display Name that will be used for the field
     * @param fieldName The Display Name that will be used on the button
     * @param onPress The Action to perform when the button was pressed
     */
    public ClothConfigButtonEntry(Component displayName, Component fieldName, @Nullable Button.OnPress onPress) {
        this(displayName, fieldName, onPress, null, false);
    }

    /***
     * Create a new Cloth Button Entry, with optional delete button
     *
     * @param displayName The Display Name that will be used for the field
     * @param fieldName The Display Name that will be used on the button
     * @param onPress The Action to perform when the button was pressed
     * @param deletePress The Action to perform when the delete button is pressed. If this is null, the button is disabled
     * @param wasEdited Was a change made to the field this button belongs to. This is to tell cloth to enable the save button
     */
    public ClothConfigButtonEntry(Component displayName, Component fieldName, Button.OnPress onPress, @Nullable Button.OnPress deletePress, boolean wasEdited) {
        super(fieldName, false);
        this.hasDeleteButton = deletePress != null;
        this.wasEdited = wasEdited;

        int mainButtonWidth = hasDeleteButton ? 75 : 100;
        this.button = new Button(0, 0, mainButtonWidth, 20, fieldName, onPress);
        this.deleteButton = deletePress != null ? new Button(0, 0, 20, 20, Component.literal("X"), deletePress) : null;
        this.displayName = displayName;
    }

    @Override
    public void render(PoseStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float delta) {
        Window window = Minecraft.getInstance().getWindow();
        Component displayedFieldName = displayName;
        if (Minecraft.getInstance().font.isBidirectional()) {
            drawString(matrices, Minecraft.getInstance().font, displayedFieldName.getVisualOrderText(), window.getGuiScaledWidth() - x - Minecraft.getInstance().font.width(displayedFieldName), y + 6, 16777215);
            this.button.x = x;
            if (hasDeleteButton) {
                this.deleteButton.x = x + this.button.getWidth() + 4;
            }
        } else {
            drawString(matrices, Minecraft.getInstance().font, displayedFieldName.getVisualOrderText(), x, y + 6, this.getPreferredTextColor());
            if (hasDeleteButton) {
                this.button.x = x + entryWidth - this.button.getWidth() - 24;
                this.deleteButton.x = x + entryWidth - 20;
            } else {
                this.button.x = x + entryWidth - this.button.getWidth();
            }
        }

        button.y = y + (entryHeight - 20) / 2;
        button.render(matrices, mouseX, mouseY, delta);

        if (hasDeleteButton) {
            deleteButton.y = y + (entryHeight - 20) / 2;
            deleteButton.render(matrices, mouseX, mouseY, delta);
        }
    }

    @Override
    public Void getValue() { return null; }

    @Override
    public Optional<Void> getDefaultValue() { return Optional.empty(); }

    @Override
    public void save() {}

    @NotNull
    @Override
    public List<? extends GuiEventListener> children() {
        ArrayList<GuiEventListener> children = new ArrayList<>();
        children.add(button);

        if (hasDeleteButton) {
            children.add(deleteButton);
        }

        return children;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        ArrayList<NarratableEntry> children = new ArrayList<>();
        children.add(button);

        if (hasDeleteButton) {
            children.add(deleteButton);
        }

        return children;
    }

    @Override
    public boolean isEdited() {
        return wasEdited;
    }
}
