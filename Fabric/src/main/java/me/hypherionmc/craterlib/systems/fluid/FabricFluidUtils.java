package me.hypherionmc.craterlib.systems.fluid;

import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class FabricFluidUtils {

    public static TextureAtlasSprite getFluidTexture(FluidVariant fluidStack) {
        return FluidVariantRendering.getSprite(fluidStack);
    }

}
