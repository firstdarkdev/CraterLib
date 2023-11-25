package com.hypherionmc.craterlib.common;

import com.hypherionmc.craterlib.core.platform.CraterFluidHelper;
import com.hypherionmc.craterlib.core.systems.fluid.CraterFluidTank;
import com.hypherionmc.craterlib.core.systems.fluid.FluidHolder;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import com.hypherionmc.craterlib.systems.fluid.NeoForgeFluidTankInteractor;
import com.hypherionmc.craterlib.systems.fluid.NeoForgeFluidUtils;
import com.hypherionmc.craterlib.systems.fluid.NeoForgeWrappedFluidTank;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class NeoForgeFluidHelper implements CraterFluidHelper {

    @Override
    public CraterFluidTank createFluidTank(int capacity) {
        return new NeoForgeWrappedFluidTank(capacity);
    }

    @Override
    public CraterFluidTank createFluidTank(int capacity, Fluid... validFluids) {
        return new NeoForgeWrappedFluidTank(capacity, (variant) -> Arrays.stream(validFluids).allMatch(f -> f.isSame(variant.getFluid())));
    }

    @Override
    public boolean interactWithFluidHandler(Player player, InteractionHand hand, ICraterFluidHandler fluidHandler) {
        NeoForgeFluidTankInteractor interactor = new NeoForgeFluidTankInteractor(fluidHandler);
        return FluidUtil.interactWithFluidHandler(player, hand, interactor);
    }

    @Override
    public boolean interactWithFluidHandler(@NotNull Player player, @NotNull InteractionHand hand, @NotNull Level level, @NotNull BlockPos pos, @Nullable Direction side) {
        return FluidUtil.interactWithFluidHandler(player, hand, level, pos, side);
    }

    @Override
    public TextureAtlasSprite getFluidTexture(FluidHolder fluidHolder) {
        return NeoForgeFluidUtils.getFluidTexture(new FluidStack(fluidHolder.getFluid(), 0), true);
    }

    @Override
    public int getFluidColor(Fluid fluid) {
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluid);
        return props.getTintColor();
    }
}
