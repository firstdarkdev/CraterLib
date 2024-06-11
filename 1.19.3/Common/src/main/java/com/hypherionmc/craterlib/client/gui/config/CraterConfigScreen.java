package com.hypherionmc.craterlib.client.gui.config;

import com.hypherionmc.craterlib.CraterConstants;
import com.hypherionmc.craterlib.client.gui.config.widgets.*;
import com.hypherionmc.craterlib.core.config.ModuleConfig;
import com.hypherionmc.craterlib.core.config.annotations.HideFromScreen;
import com.hypherionmc.craterlib.core.config.annotations.SubConfig;
import com.hypherionmc.craterlib.core.config.annotations.Tooltip;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.hypherionmc.moonconfig.core.conversion.SpecComment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author HypherionSA
 */
public class CraterConfigScreen extends Screen {
    public static final float SCROLLBAR_BOTTOM_COLOR = .5f;
    public static final float SCROLLBAR_TOP_COLOR = .67f;
    private static final int TOP = 26;
    private static final int BOTTOM = 24;
    private final Screen parent;
    private final List<Option<?>> options = new ArrayList<>();
    private final ModuleConfig config;
    public double scrollerAmount;
    private boolean dragging;

    public CraterConfigScreen(ModuleConfig config, Screen parent, Object subConfig) {
        super(Component.translatable("cl." + config.getClass().getSimpleName().toLowerCase() + ".title"));
        this.parent = parent;
        this.config = config;
        if (subConfig != null) {
            setupScreenFromConfig(subConfig, subConfig.getClass());
        } else {
            setupScreenFromConfig(config, config.getClass());
        }
    }

    public CraterConfigScreen(ModuleConfig config, Screen parent) {
        this(config, parent, null);
    }

    private static Component toText(Enum<?> val) {
        return Component.translatable(val.toString());
    }

    private static Component toText(Boolean bool) {
        return Component.translatable(bool.toString());
    }

    private void setupScreenFromConfig(Object object, Class<?> clazz) {
        while (clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                final int fieldModifiers = field.getModifiers();
                if (object == null || Modifier.isStatic(fieldModifiers) || Modifier.isTransient(fieldModifiers)) {
                    continue;
                }

                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    if (field.isAnnotationPresent(HideFromScreen.class))
                        return;
                    Object val = field.get(object);

                    /* Lang Stuff */
                    String baseLangKey = "cl." + clazz.getSimpleName().toLowerCase() + "." + field.getName().toLowerCase();
                    String[] tooltipLang = field.isAnnotationPresent(SpecComment.class) ? new String[]{field.getAnnotation(SpecComment.class).value()} : new String[0];
                    if (field.isAnnotationPresent(Tooltip.class)) {
                        tooltipLang = field.getAnnotation(Tooltip.class).value();
                    }

                    add(Component.translatable(baseLangKey),
                            val,
                            () -> val,
                            (ret) -> {
                                try {
                                    field.set(object, ret);
                                    config.saveConfig(config);
                                } catch (IllegalAccessException e) {
                                    CraterConstants.LOG.error("Failed to update value for field {} in config {}", field.getName(), config.getConfigName(), e);
                                }
                            },
                            field.isAnnotationPresent(SubConfig.class),
                            tooltipLang
                    );
                } catch (IllegalAccessException e) {
                    CraterConstants.LOG.error("Failed to access value for field {} in config {}", field.getName(), config.getConfigName(), e);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    public <T> void add(Component text, T value, @Nullable Supplier<T> defaultValue, Consumer<T> savingConsumer, boolean isSubConfig, String... langKeys) {
        Option<T> option = (Option<T>) createOption(value, isSubConfig);
        option.text = text;
        option.defaultValue = defaultValue;
        option.savingConsumer = savingConsumer;
        option.originalValue = value;
        option.value = value;
        option.setLangKeys(List.of(langKeys));
        options.add(option);
        option.onAdd();
    }

    private <T> Option<?> createOption(T value, boolean isSubConfig) {
        if (value instanceof Enum) {
            Object[] objects = value.getClass().getEnumConstants();
            return new ToggleButton<Enum<?>>((List) Arrays.asList(objects), CraterConfigScreen::toText);
        }
        if (value instanceof Boolean) {
            return new ToggleButton<>(Arrays.asList(Boolean.TRUE, Boolean.FALSE), CraterConfigScreen::toText);
        }
        if (value instanceof String) {
            return new TextConfigOption<>(Function.identity(), Function.identity());
        }
        if (value instanceof Integer) {
            return new TextConfigOption<>(Objects::toString, Integer::valueOf);
        }
        if (value instanceof Long) {
            return new TextConfigOption<>(Objects::toString, Long::valueOf);
        }
        if (value instanceof Double) {
            return new TextConfigOption<>(Objects::toString, Double::valueOf);
        }
        if (value instanceof Float) {
            return new TextConfigOption<>(Objects::toString, Float::valueOf);
        }
        if (value instanceof BigInteger) {
            return new TextConfigOption<>(Objects::toString, BigInteger::new);
        }
        if (value instanceof BigDecimal) {
            return new TextConfigOption<>(Objects::toString, BigDecimal::new);
        }
        if (value instanceof ResourceLocation) {
            return new TextConfigOption<>(Objects::toString, ResourceLocation::new);
        }
        if (isSubConfig) {
            return new SubConfigWidget<>(config, this, value);
        }
        throw new IllegalArgumentException(String.valueOf(value));
    }

    @Override
    protected void init() {
        super.init();
        ((List) children()).addAll(options);

        int buttonWidths = Math.min(200, (width - 50 - 12) / 3);
        addRenderableWidget(new InternalConfigButton(this, width / 2 - buttonWidths - 3, height - 22, buttonWidths, 20, Component.empty(), true));
        addRenderableWidget(new InternalConfigButton(this, width / 2 + 3, height - 22, buttonWidths, 20, Component.empty(), false));
    }

    @Override
    public void render(@NotNull PoseStack matrices, int mouseX, int mouseY, float delta) {
        overlayBackground(matrices, TOP, height - BOTTOM, 32);

        renderScrollBar();

        matrices.pushPose();
        matrices.translate(0, 0, 500.0);
        overlayBackground(matrices, 0, TOP, 64);
        overlayBackground(matrices, height - BOTTOM, height, 64);
        renderShadow(matrices);
        drawCenteredString(matrices, font, getTitle(), width / 2, 9, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
        matrices.popPose();

        int y = (int) (TOP + 4 - Math.round(scrollerAmount));
        for (Option<?> option : options) {
            int height1 = option.height();
            option.render(minecraft, font, 40, y, width - 80, height1, matrices, mouseX, mouseY, delta);
            renderConfigTooltip(matrices, font, mouseX, mouseY, 40, y, font.width(option.text), height1, option.text.getString(), option.getLangKeys().toArray(new String[0]));
            y += height1;
        }
    }

    private void renderScrollBar() {
        int listHeight = height - BOTTOM - TOP;
        int totalHeight = totalHeight();
        if (totalHeight > listHeight) {
            int maxScroll = Math.max(0, totalHeight - listHeight);
            int height = listHeight * listHeight / totalHeight;
            height = Mth.clamp(height, 32, listHeight);
            height = Math.max(10, height);
            int minY = Math.min(Math.max((int) scrollerAmount * (listHeight - height) / maxScroll + TOP, TOP), this.height - BOTTOM - height);

            int scrollbarPositionMaxX = width;
            int scrollbarPositionMinX = scrollbarPositionMaxX - 6;

            int maxY = this.height - BOTTOM;
            //RenderSystem.disableTexture();
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            buffer.vertex(scrollbarPositionMinX, maxY, 0.0D).color(0, 0, 0, 255).endVertex();
            buffer.vertex(scrollbarPositionMaxX, maxY, 0.0D).color(0, 0, 0, 255).endVertex();
            buffer.vertex(scrollbarPositionMaxX, TOP, 0.0D).color(0, 0, 0, 255).endVertex();
            buffer.vertex(scrollbarPositionMinX, TOP, 0.0D).color(0, 0, 0, 255).endVertex();

            buffer.vertex(scrollbarPositionMinX, minY + height, 0.0D).color(SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, 1).endVertex();
            buffer.vertex(scrollbarPositionMaxX, minY + height, 0.0D).color(SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, 1).endVertex();
            buffer.vertex(scrollbarPositionMaxX, minY, 0.0D).color(SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, 1).endVertex();
            buffer.vertex(scrollbarPositionMinX, minY, 0.0D).color(SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, SCROLLBAR_BOTTOM_COLOR, 1).endVertex();
            buffer.vertex(scrollbarPositionMinX, (minY + height - 1), 0.0D).color(SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, 1).endVertex();
            buffer.vertex((scrollbarPositionMaxX - 1), (minY + height - 1), 0.0D).color(SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, 1).endVertex();
            buffer.vertex((scrollbarPositionMaxX - 1), minY, 0.0D).color(SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, 1).endVertex();
            buffer.vertex(scrollbarPositionMinX, minY, 0.0D).color(SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, SCROLLBAR_TOP_COLOR, 1).endVertex();
            tesselator.end();
            RenderSystem.disableBlend();
            //RenderSystem.enableTexture();
        }
    }

    private void renderShadow(PoseStack matrices) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 0, 1);
        //RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        Matrix4f matrix = matrices.last().pose();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buffer.vertex(matrix, 0, TOP + 4, 0.0F).uv(0, 1).color(0, 0, 0, 0).endVertex();
        buffer.vertex(matrix, width, TOP + 4, 0.0F).uv(1, 1).color(0, 0, 0, 0).endVertex();
        buffer.vertex(matrix, width, TOP, 0.0F).uv(1, 0).color(0, 0, 0, 185).endVertex();
        buffer.vertex(matrix, 0, TOP, 0.0F).uv(0, 0).color(0, 0, 0, 185).endVertex();
        buffer.vertex(matrix, 0, height - BOTTOM, 0.0F).uv(0, 1).color(0, 0, 0, 185).endVertex();
        buffer.vertex(matrix, width, height - BOTTOM, 0.0F).uv(1, 1).color(0, 0, 0, 185).endVertex();
        buffer.vertex(matrix, width, height - BOTTOM - 4, 0.0F).uv(1, 0).color(0, 0, 0, 0).endVertex();
        buffer.vertex(matrix, 0, height - BOTTOM - 4, 0.0F).uv(0, 0).color(0, 0, 0, 0).endVertex();
        tesselator.end();
        //RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    protected void overlayBackground(PoseStack matrices, int h1, int h2, int color) {
        overlayBackground(matrices.last().pose(), 0, h1, width, h2, color, color, color, 255, 255);
    }

    protected void overlayBackground(Matrix4f matrix, int minX, int minY, int maxX, int maxY, int red, int green, int blue, int startAlpha, int endAlpha) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.setShaderTexture(0, Screen.BACKGROUND_LOCATION);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buffer.vertex(matrix, minX, maxY, 0.0F).uv(minX / 32.0F, maxY / 32.0F).color(red, green, blue, endAlpha).endVertex();
        buffer.vertex(matrix, maxX, maxY, 0.0F).uv(maxX / 32.0F, maxY / 32.0F).color(red, green, blue, endAlpha).endVertex();
        buffer.vertex(matrix, maxX, minY, 0.0F).uv(maxX / 32.0F, minY / 32.0F).color(red, green, blue, startAlpha).endVertex();
        buffer.vertex(matrix, minX, minY, 0.0F).uv(minX / 32.0F, minY / 32.0F).color(red, green, blue, startAlpha).endVertex();
        tesselator.end();
    }

    public int scrollHeight() {
        int totalHeight = totalHeight();
        int listHeight = height - BOTTOM - TOP;
        if (totalHeight <= listHeight) {
            return 0;
        }
        return totalHeight - listHeight;
    }

    public int totalHeight() {
        int i = 8;
        for (Option<?> option : options) {
            i += option.height();
        }
        return i;
    }

    public boolean hasErrors() {
        for (Option<?> option : options) {
            if (option.hasErrors) {
                return true;
            }
        }
        return false;
    }

    public boolean isEdited() {
        for (Option<?> option : options) {
            if (option.isEdited()) {
                return true;
            }
        }
        return false;
    }

    public void save() {
        for (Option option : options) {
            option.save();
            option.originalValue = option.value;
        }
    }

    @Override
    public void onClose() {
        if (isEdited()) {
            minecraft.setScreen(new ConfirmScreen(this::acceptConfirm, Component.translatable("t.clc.quit_config"),
                    Component.translatable("t.clc.quit_config_sure"),
                    Component.translatable("t.clc.quit_discard"),
                    Component.translatable("gui.cancel")));
        } else {
            minecraft.setScreen(parent);
        }
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        if (e >= TOP && e <= height - BOTTOM) {
            scrollerAmount = Mth.clamp(scrollerAmount - f * 16.0D, 0, scrollHeight());
            return true;
        }
        return super.mouseScrolled(d, e, f);
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        this.dragging = i == 0 && d >= width - 6 && d < width;
        return super.mouseClicked(d, e, i) || dragging;
    }

    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        if (super.mouseDragged(d, e, i, f, g)) {
            return true;
        }
        if (i != 0 || !this.dragging) {
            return false;
        }
        if (e < TOP) {
            scrollerAmount = 0;
        } else if (e > height - BOTTOM) {
            scrollerAmount = scrollHeight();
        } else {
            double h = Math.max(1, this.scrollHeight());
            int j = height - BOTTOM - TOP;
            int k = Mth.clamp((int) ((float) (j * j) / (float) this.scrollHeight()), 32, j - 8);
            double l = Math.max(1.0, h / (double) (j - k));
            scrollerAmount = Mth.clamp(scrollerAmount + g * l, 0, scrollHeight());
        }
        return true;
    }

    private void acceptConfirm(boolean t) {
        if (!t) {
            minecraft.setScreen(this);
        } else {
            minecraft.setScreen(parent);
        }
    }

    private void renderConfigTooltip(PoseStack stack, Font font, int mouseX, int mouseY, int startX, int startY, int sizeX, int sizeY, String title, String... description) {
        if (mouseX > startX && mouseX < startX + sizeX) {
            if (mouseY > startY && mouseY < startY + sizeY) {
                List<Component> list = new ArrayList<>();
                list.add(Component.translatable(ChatFormatting.BOLD + "" + ChatFormatting.YELLOW + title));
                for (String desc : description) {
                    list.add(Component.translatable(desc));
                }
                renderComponentTooltip(stack, list, mouseX, mouseY);
            }
        }
    }
}
