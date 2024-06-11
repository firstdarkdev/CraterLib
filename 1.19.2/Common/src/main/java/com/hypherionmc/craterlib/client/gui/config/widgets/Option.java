package com.hypherionmc.craterlib.client.gui.config.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Copied from Cloth Config Lite
 * <a href="https://github.com/shedaniel/cloth-config-lite/blob/1.17/src/main/java/me/shedaniel/clothconfiglite/impl/option/Option.java">...</a>
 */
public abstract class Option<T> extends AbstractContainerEventHandler {

    public Component text;
    @Nullable
    public Supplier<T> defaultValue;
    public Consumer<T> savingConsumer;
    public T originalValue;
    public T value;
    public boolean hasErrors;
    public List<? extends GuiEventListener> children = new ArrayList<>();
    @Setter
    @Getter
    private List<String> langKeys = new ArrayList<>();

    public abstract void render(Minecraft minecraft, Font font, int x, int y, int width, int height, PoseStack matrices, int mouseX, int mouseY, float delta);

    public int height() {
        return 22;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return children;
    }

    protected <R extends GuiEventListener> R addChild(R listener) {
        ((List) children).add(listener);
        return listener;
    }

    public void onAdd() {
    }

    protected void reset() {
        onAdd();
    }

    public boolean isEdited() {
        return !Objects.equals(originalValue, value);
    }

    protected boolean isNotDefault() {
        return defaultValue != null && !Objects.equals(defaultValue.get(), value);
    }

    public void save() {
        savingConsumer.accept(value);
    }
}
