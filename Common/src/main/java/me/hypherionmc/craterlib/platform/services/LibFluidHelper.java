package me.hypherionmc.craterlib.platform.services;

import me.hypherionmc.craterlib.systems.fluid.CraterFluidTank;
import me.hypherionmc.craterlib.systems.fluid.FluidHolder;
import me.hypherionmc.craterlib.systems.fluid.ICraterFluidHandler;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LibFluidHelper {

    public CraterFluidTank createFluidTank(int capacity);

    public CraterFluidTank createFluidTank(int capacity, Fluid... validFluids);

    public boolean interactWithFluidHandler(Player player, InteractionHand hand, ICraterFluidHandler fluidHandler);

    public boolean interactWithFluidHandler(@NotNull Player player, @NotNull InteractionHand hand, @NotNull Level level, @NotNull BlockPos pos, @Nullable Direction side);

    public TextureAtlasSprite getFluidTexture(FluidHolder fluidHolder);

    public int getFluidColor(Fluid fluid);

}
