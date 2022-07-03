package me.hypherionmc.craterlib.client.gui.widgets;

/**
 * @author HypherionSA
 * @date 03/07/2022
 */

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.hypherionmc.craterlib.util.LangUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.Arrays;
import java.util.function.Supplier;

/** Copied from https://github.com/SleepyTrousers/EnderIO-Rewrite/blob/dev/1.18.x/enderio-machines/src/main/java/com/enderio/machines/client/FluidStackWidget.java*/
public class FluidStackWidget extends AbstractWidget {

    private final Screen displayOn;
    private final Supplier<FluidTank> getFluid;

    private final String toolTipTitle;

    public FluidStackWidget(Screen displayOn, Supplier<FluidTank> getFluid, int pX, int pY, int pWidth, int pHeight, String toolTipTitle) {
        super(pX, pY, pWidth, pHeight, Component.empty());
        this.displayOn = displayOn;
        this.getFluid = getFluid;
        this.toolTipTitle = toolTipTitle;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {}

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        FluidTank fluidTank = getFluid.get();
        if (!fluidTank.isEmpty()) {
            FluidStack fluidStack = fluidTank.getFluid();
            ResourceLocation still = fluidStack.getFluid().getAttributes().getStillTexture(fluidStack);
            if (still != null) {
                AbstractTexture texture = minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS);
                if (texture instanceof TextureAtlas atlas) {
                    TextureAtlasSprite sprite = atlas.getSprite(still);
                    RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);

                    int color = fluidStack.getFluid().getAttributes().getColor();
                    RenderSystem.setShaderColor(
                            FastColor.ARGB32.red(color) / 255.0F,
                            FastColor.ARGB32.green(color) / 255.0F,
                            FastColor.ARGB32.blue(color) / 255.0F,
                            FastColor.ARGB32.alpha(color) / 255.0F);
                    RenderSystem.enableBlend();

                    int stored = fluidTank.getFluidAmount();
                    float capacity = fluidTank.getCapacity();
                    float filledVolume = stored / capacity;
                    int renderableHeight = (int)(filledVolume * height);

                    int atlasWidth = (int)(sprite.getWidth() / (sprite.getU1() - sprite.getU0()));
                    int atlasHeight = (int)(sprite.getHeight() / (sprite.getV1() - sprite.getV0()));

                    pPoseStack.pushPose();
                    pPoseStack.translate(0, height-16, 0);
                    for (int i = 0; i < Math.ceil(renderableHeight / 16f); i++) {
                        int drawingHeight = Math.min(16, renderableHeight - 16*i);
                        int notDrawingHeight = 16 - drawingHeight;
                        blit(pPoseStack, x, y + notDrawingHeight, displayOn.getBlitOffset(), sprite.getU0()*atlasWidth, sprite.getV0()*atlasHeight + notDrawingHeight, this.width, drawingHeight, atlasWidth, atlasHeight);
                        pPoseStack.translate(0,-16, 0);
                    }

                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    pPoseStack.popPose();
                }
            }
            renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
    }
    @Override
    public void renderToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        if (isActive() && isHovered) {
            displayOn.renderTooltip(pPoseStack, Arrays.asList(LangUtils.getTooltipTitle(toolTipTitle).getVisualOrderText(), Component.literal((int) (((float)this.getFluid.get().getFluidAmount() / this.getFluid.get().getCapacity()) * 100) + "%").getVisualOrderText()), pMouseX, pMouseY);
        }
    }
}
