package me.hypherionmc.craterlib.systems.fluid;

import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class FluidUtils {

    public static int fluidColorFromDye(DyeColor color) {
        return color.getMaterialColor().col | 0xFF000000;
    }

    public static void putFluid(CompoundTag compound, String key, FluidVariant fluidVariant) {
        CompoundTag savedTag = new CompoundTag();
        savedTag.put("fk", fluidVariant.toNbt());
        compound.put(key, savedTag);
    }

    public static FluidVariant getFluidCompatible(CompoundTag tag, String key) {
        if (tag == null || !tag.contains(key))
            return FluidVariant.blank();

        if (tag.get(key) instanceof StringTag) {
            return FluidVariant.of(BuiltInRegistries.FLUID.get(new ResourceLocation(tag.getString(key))));
        } else {
            CompoundTag compound = tag.getCompound(key);
            if (compound.contains("fk")) {
                return FluidVariant.fromNbt(compound.getCompound("fk"));
            } else {
                return FluidVariant.of(readLbaTag(tag.getCompound(key)));
            }
        }
    }

    private static Fluid readLbaTag(CompoundTag tag) {
        if (tag.contains("ObjName") && tag.getString("Registry").equals("f")) {
            return BuiltInRegistries.FLUID.get(new ResourceLocation(tag.getString("ObjName")));
        } else {
            return Fluids.EMPTY;
        }
    }

    public static TextureAtlasSprite getFluidTexture(FluidVariant fluidStack) {
        return FluidVariantRendering.getSprite(fluidStack);
    }

}
