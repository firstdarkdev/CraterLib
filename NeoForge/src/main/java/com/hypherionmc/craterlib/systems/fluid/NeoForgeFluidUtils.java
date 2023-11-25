package com.hypherionmc.craterlib.systems.fluid;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class NeoForgeFluidUtils {

    public static TextureAtlasSprite getFluidTexture(FluidStack fluidStack, boolean still) {
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation fluidStill = still ? props.getStillTexture(fluidStack) : props.getFlowingTexture(fluidStack);
        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS);

        if (texture instanceof TextureAtlas atlas) {
            return atlas.getSprite(fluidStill);
        }

        return null;
    }

}
