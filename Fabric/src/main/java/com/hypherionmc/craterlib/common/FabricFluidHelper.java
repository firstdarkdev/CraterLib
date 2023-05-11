package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.services.LibFluidHelper;
import com.hypherionmc.craterlib.core.systems.fluid.CraterFluidTank;
import com.hypherionmc.craterlib.core.systems.fluid.FluidHolder;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import com.hypherionmc.craterlib.systems.fluid.FabricFluidUtils;
import com.hypherionmc.craterlib.systems.fluid.FluidTank;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantImpl;
import net.fabricmc.fabric.mixin.transfer.BucketItemAccessor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class FabricFluidHelper implements LibFluidHelper {

    @Override
    public CraterFluidTank createFluidTank(int capacity) {
        return new FluidTank(capacity);
    }

    @Override
    public CraterFluidTank createFluidTank(int capacity, Fluid... validFluids) {
        return new FluidTank(capacity, (variant) -> Arrays.stream(validFluids).allMatch(f -> f.isSame(variant.getFluid())));
    }

    @Override
    public boolean interactWithFluidHandler(Player player, InteractionHand hand, ICraterFluidHandler fluidHandler) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof BucketItem bucketItem) {
            FluidVariant fluidVariant = new FluidVariantImpl(((BucketItemAccessor) bucketItem).fabric_getFluid(), stack.getTag());
            if (fluidVariant.isBlank())
                return false;
            if (fluidHandler.insert(new FluidHolder(fluidVariant.getFluid(), 1000), ICraterFluidHandler.FluidAction.EXECUTE) > 0) {
                player.level().playSound(null, player.getOnPos(), SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean interactWithFluidHandler(@NotNull Player player, @NotNull InteractionHand hand, @NotNull Level level, @NotNull BlockPos pos, @Nullable Direction side) {
        return false;
    }

    @Override
    public TextureAtlasSprite getFluidTexture(FluidHolder fluidHolder) {
        return FabricFluidUtils.getFluidTexture(FluidVariant.of(fluidHolder.getFluid()));
    }

    @Override
    public int getFluidColor(Fluid fluid) {
        return FluidVariantRendering.getColor(FluidVariant.of(fluid));
    }
}
