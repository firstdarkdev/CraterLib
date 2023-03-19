package me.hypherionmc.craterlib.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class FluidUtils {

    public static int fluidColorFromDye(DyeColor color) {
        return color.getMaterialColor().col | 0xFF000000;
    }

    public static void putFluid(CompoundTag compound, String key, Fluid fluidVariant) {
        compound.putString("tankFluid", BuiltInRegistries.FLUID.getKey(fluidVariant).toString());
    }

    public static Fluid getFluidCompatible(CompoundTag tag) {
        if (tag == null || !tag.contains("tankFluid"))
            return Fluids.EMPTY;

        return BuiltInRegistries.FLUID.get(new ResourceLocation(tag.getString("tankFluid")));
    }

}
