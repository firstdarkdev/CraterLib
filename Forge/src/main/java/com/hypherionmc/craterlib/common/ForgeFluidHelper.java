package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CraterFluidHelper;
import com.hypherionmc.craterlib.core.systems.fluid.CraterFluidTank;
import com.hypherionmc.craterlib.core.systems.fluid.FluidHolder;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import com.hypherionmc.craterlib.systems.fluid.ForgeFluidTankInteractor;
import com.hypherionmc.craterlib.systems.fluid.ForgeFluidUtils;
import com.hypherionmc.craterlib.systems.fluid.ForgeWrappedFluidTank;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ForgeFluidHelper implements CraterFluidHelper {

    @Override
    public CraterFluidTank createFluidTank(int capacity) {
        return new ForgeWrappedFluidTank(capacity);
    }

    @Override
    public CraterFluidTank createFluidTank(int capacity, Fluid... validFluids) {
        return new ForgeWrappedFluidTank(capacity, (variant) -> Arrays.stream(validFluids).allMatch(f -> f.isSame(variant.getFluid())));
    }

    @Override
    public boolean interactWithFluidHandler(Player player, InteractionHand hand, ICraterFluidHandler fluidHandler) {
        ForgeFluidTankInteractor interactor = new ForgeFluidTankInteractor(fluidHandler);
        return FluidUtil.interactWithFluidHandler(player, hand, interactor);
    }

    @Override
    public boolean interactWithFluidHandler(@NotNull Player player, @NotNull InteractionHand hand, @NotNull Level level, @NotNull BlockPos pos, @Nullable Direction side) {
        return FluidUtil.interactWithFluidHandler(player, hand, level, pos, side);
    }

    @Override
    public TextureAtlasSprite getFluidTexture(FluidHolder fluidHolder) {
        return ForgeFluidUtils.getFluidTexture(new FluidStack(fluidHolder.getFluid(), 0), true);
    }

    @Override
    public int getFluidColor(Fluid fluid) {
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluid);
        return props.getTintColor();
    }
}
