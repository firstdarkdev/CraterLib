package com.hypherionmc.craterlib.core.platform;

import com.hypherionmc.craterlib.core.systems.fluid.CraterFluidTank;
import com.hypherionmc.craterlib.core.systems.fluid.FluidHolder;
import com.hypherionmc.craterlib.core.systems.fluid.ICraterFluidHandler;
import com.hypherionmc.craterlib.util.ServiceUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author HypherionSA
 */
public interface CraterFluidHelper {

    public CraterFluidHelper INSTANCE = ServiceUtil.load(CraterFluidHelper.class);

    CraterFluidTank createFluidTank(int capacity);

    CraterFluidTank createFluidTank(int capacity, Fluid... validFluids);

    boolean interactWithFluidHandler(Player player, InteractionHand hand, ICraterFluidHandler fluidHandler);

    boolean interactWithFluidHandler(@NotNull Player player, @NotNull InteractionHand hand, @NotNull Level level, @NotNull BlockPos pos, @Nullable Direction side);

    TextureAtlasSprite getFluidTexture(FluidHolder fluidHolder);

    int getFluidColor(Fluid fluid);

}
